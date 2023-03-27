import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { HttpClient } from '@angular/common/http';
import { LoginRequest } from '../../model/login/login-request';
import { LoginResponse } from '../../model/login/login-response';
import { User } from 'src/modules/shared/model/user';
import { WebSocketService } from 'src/modules/shared/service/web-socket-service/web-socket.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public currentUser$: BehaviorSubject<User>;

  constructor(private configService: ConfigService, private http: HttpClient, private webSocketService: WebSocketService) {
    this.currentUser$ = new BehaviorSubject<User>(null);
   }

  login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      this.configService.getLoginUrl(),
      loginRequest
    );
  }

  setLocalStorage(loginResponse: LoginResponse): void {
    localStorage.setItem('token', loginResponse.token);
    localStorage.setItem('user', JSON.stringify(loginResponse.user));
    localStorage.setItem('email', loginResponse.user.email);
    this.currentUser$.next(loginResponse.user);
  }

  logOut() {
    localStorage.clear();
    this.webSocketService.disconnect();
    this.currentUser$.next(null);
  }

  getSubjectCurrentUser(): BehaviorSubject<User> {
    const user = localStorage.getItem('user');
    if (user !== null && user !== undefined) {
      const parsedUser: User = JSON.parse(user);
      this.currentUser$.next(parsedUser);
    } else {
      this.currentUser$.next(null);
    }

    return this.currentUser$;
  }
}
