import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegularUserRegistration } from 'src/modules/auth/model/registration_and_verification/regular-user-registration';
import { VerifyRequest } from 'src/modules/auth/model/registration_and_verification/verify-request';
import { User } from '../../model/user';
import { ConfigService } from '../config-service/config.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private configService: ConfigService) { }

  getAllTenantUsers() {
    return this.http.get<User[]>(this.configService.ALL_ACTIVE_TENANTS);
  }

  registerRegularUser(newUser: RegularUserRegistration): Observable<User> {
    return this.http.post<User>(
      this.configService.CREATE_REGULAR_USER_URL,
      newUser
    );
  }

  verify(verifyRequest: VerifyRequest): Observable<boolean> {
    return this.http.put<boolean>(this.configService.ACTIVATE_ACCOUNT_URL, verifyRequest);
  }

}
