import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';
import * as SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { User } from '../../model/user';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient = null;
  initialized = false;

  constructor( private toast: ToastrService) {
    if (!this.stompClient) {
      this.initialized = false;
      this.connect();
    }
   }

   connect() {
    if (!this.initialized && localStorage.getItem('email') !== null) {
      this.initialized = true;
      const serverUrl = environment.webSocketUrl;
      const ws = new SockJS(serverUrl);
      this.stompClient = Stomp.over(ws);

      const that = this;

      this.stompClient.connect({}, function () {
        that.rejectCsrNotification();
        that.cancelCertificateNotification();
        that.createCertificateNotification();
      })
    }
  }

  disconnect(): void {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }

    this.initialized = false;
  }

  rejectCsrNotification(){
    this.stompClient.subscribe(
      environment.publisherUrl +
      localStorage.getItem('email') +
      '/reject-csr',
      message => {
        var user: User = JSON.parse(localStorage.getItem('user'));
        user.accountStatus = 'REJECTED_CSR';
        localStorage.setItem('user', JSON.stringify(user));
        this.toast.info(message.body, "Rejected csr");
      }
    );
  }

  createCertificateNotification(){
    this.stompClient.subscribe(
      environment.publisherUrl +
      localStorage.getItem('email') +
      '/create-certificate',
      message => {
        var user: User = JSON.parse(localStorage.getItem('user'));
        user.accountStatus = 'ACTIVE';
        localStorage.setItem('user', JSON.stringify(user));
        this.toast.info(message.body, "Created certificate");
      }
    );
  }

  cancelCertificateNotification(){
    this.stompClient.subscribe(
      environment.publisherUrl +
      localStorage.getItem('email') +
      '/cancel-certificate',
      message => {
        var user: User = JSON.parse(localStorage.getItem('user'));
        user.accountStatus = 'BLOCKED';
        localStorage.setItem('user', JSON.stringify(user));
        this.toast.info(message.body, "Cancelled certificate");
      }
    );
  }
}
