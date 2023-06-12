import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Alarm } from 'src/modules/shared/model/alarm';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';

@Injectable({
  providedIn: 'root'
})
export class AlarmService {

  constructor(private configService: ConfigService, private http: HttpClient) { }

  getFilteredAlarms(realEstateId: number, deviceId: number, filterPeriod: number): Observable<Alarm[]> {
    return this.http.get<Alarm[]>(this.configService.getFilteredAlarms(realEstateId, deviceId, filterPeriod));
  }
}
