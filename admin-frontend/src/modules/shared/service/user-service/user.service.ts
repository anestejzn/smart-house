import { Injectable } from '@angular/core';
import { ConfigService } from '../config-service/config.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private configService: ConfigService, private http: HttpClient) {
  }

  getAllActiveRegularUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.configService.ALL_ACTIVE_REGULAR_USERS);
  }

}
