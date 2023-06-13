import { Injectable } from '@angular/core';
import { ConfigService } from '../config-service/config.service';
import { HttpClient } from '@angular/common/http';
import { Alarm } from '../../model/alarm';

@Injectable({
  providedIn: 'root'
})
export class AlarmService {

  constructor(private configService: ConfigService, private http: HttpClient) { }

  getAllAlarms(){
    return this.http.get<Alarm[]>(this.configService.ALARM_URL);
  }

}
