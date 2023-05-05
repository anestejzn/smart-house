import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RealEstate, RealEstateView, UpdateRealEstateRequest } from 'src/modules/shared/model/real-estate';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';

@Injectable({
  providedIn: 'root'
})
export class RealEstateService {

  constructor(private configService: ConfigService, private http: HttpClient) { }

  getRealEstate(id: string): Observable<RealEstate> {
    return this.http.get<RealEstate>(this.configService.getUrlForRealEstateById(id));
  }

  filterRealEstates(ascending: boolean, range: string, selectedOwner: number): Observable<RealEstateView[]> {
    return  this.http.get<RealEstateView[]>(this.configService.getUrlForFilteringRealEstates(ascending, range, selectedOwner));
  }

  editTenantsRealEstateData(data: UpdateRealEstateRequest) {
    return this.http.put<RealEstate>(this.configService.REAL_ESTATE_TENANTS_INFO_EDIT, data);
  }

}
