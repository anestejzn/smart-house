import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/modules/auth/service/auth/auth.service';
import { User } from 'src/modules/shared/model/user';
import { CsrService } from '../../service/csr-service/csr.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  authSubscription: Subscription;
  loggedUser: User;
  csrSubscription: Subscription;


  constructor(private authService: AuthService, private csrService: CsrService) { }

  ngOnInit(): void {

    this.authSubscription = this.authService
      .getSubjectCurrentUser()
      .subscribe(user => {
        this.loggedUser = user;
      
      });
  }

  sendCsr(){
    this.csrSubscription = this.csrService.sendCsr(this.loggedUser.email).subscribe(response=>{
      this.loggedUser.accountStatus = 'NON_CERTIFICATED';
      localStorage.setItem('user',  JSON.stringify(this.loggedUser));
    })
  }

}
