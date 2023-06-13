import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Device } from 'src/modules/shared/model/device';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  
  constructor(private configService: ConfigService, private http: HttpClient) { }

  getDevicesPerRealEstate(realEstateId: number): Observable<Device[]> {
    return this.http.get<Device[]>(this.configService.getDevicesPerRealEstateURL(realEstateId));
  }
  
}
