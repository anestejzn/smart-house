import { Injectable } from '@angular/core';
import { NewRealEstateRequest, RealEstateView } from '../../model/real-estate';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RealEstateService {
  
  constructor(private configService: ConfigService, private http: HttpClient) { }

  filterRealEstates(ascending: boolean, range: string, selectedOwner: number): Observable<RealEstateView[]> {
    return  this.http.get<RealEstateView[]>(this.configService.getUrlForFilteringRealEstates(ascending, range, selectedOwner));
  }

  createNewRealEstate(data: NewRealEstateRequest): Observable<boolean> {
    return this.http.post<boolean>(this.configService.REAL_ESTATE_CREATION, data);
  }

}
