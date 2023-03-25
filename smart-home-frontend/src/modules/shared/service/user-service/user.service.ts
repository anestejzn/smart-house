import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/modules/auth/model/login/user';
import { RegularUserRegistration } from 'src/modules/auth/model/regular-user-registration';
import { ConfigService } from '../config-service/config.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private configService: ConfigService) { }

  registerRegularUser(newUser: RegularUserRegistration): Observable<User> {
    return this.http.post<User>(
      this.configService.CREATE_REGULAR_USER_URL,
      newUser
    );
  }

}
