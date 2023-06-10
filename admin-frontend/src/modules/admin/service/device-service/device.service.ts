import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { Device } from '../../model/device';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  
  constructor(private configService: ConfigService, private http: HttpClient) { }
  
  getDevicesPerRealEstate(realEstateId: number): Observable<Device[]> {
    return this.http.get<Device[]>(this.configService.getDevicesPerRealEstateURL(realEstateId));
  }
  
  delete(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`${this.configService.DEVICES_URL}/${id}`);
  }
  
  create(device: Device): Observable<Device> {
    return this.http.post<Device>(`${this.configService.DEVICES_URL}`, device);
  }

  edit(device: Device): Observable<Device> {
    return this.http.put<Device>(`${this.configService.DEVICES_URL}/${device.id}`, device);
  }
}
