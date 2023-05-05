import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { LoginRequest } from '../model/login/login-request';
import { LoginResponse } from '../model/login/login-response';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { HttpClient } from '@angular/common/http';
import { User } from '../../shared/model/user';
import { ConfirmPinRequest } from '../model/confirm-pin-request/confirm-pin-request';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public currentUser$: BehaviorSubject<User>;

  constructor(private configService: ConfigService, private http: HttpClient) {
    this.currentUser$ = new BehaviorSubject<User>(null);
   }

  login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      this.configService.getLoginUrl(),
      loginRequest
    );
  }

  setSessionStorage(loginResponse: LoginResponse): void {
    sessionStorage.setItem('token', loginResponse.token);
    sessionStorage.setItem('user', JSON.stringify(loginResponse.user));
    sessionStorage.setItem('email', loginResponse.user.email);
    this.currentUser$.next(loginResponse.user);
  }

  logOut(): Observable<null> {
    this.currentUser$.next(null);
    return this.http.post<null>(
      this.configService.getLogoutUrl(),
      null
    );
  }

  getSubjectCurrentUser(): BehaviorSubject<User> {
    const user = sessionStorage.getItem('user');
    if (user !== null && user !== undefined) {
      const parsedUser: User = JSON.parse(user);
      this.currentUser$.next(parsedUser);
    } else {
      this.currentUser$.next(null);
    }

    return this.currentUser$;
  }

  generatePin(email:string) {
    return this.http.get<void>(
      this.configService.getGeneratePinUrl(email));
  }

  confirmPin(confirmPinRequest: ConfirmPinRequest){
    return this.http.put<boolean>(this.configService.CONFIRM_PIN_URL, confirmPinRequest);
  }

  incrementFailedAttempts(email: string) {
    return this.http.get<boolean>(this.configService.getIncrementFailedAttempts(email));
  }
}
