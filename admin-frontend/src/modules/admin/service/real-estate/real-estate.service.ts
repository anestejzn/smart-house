import { Injectable } from '@angular/core';
import { NewRealEstateRequest, RealEstate, RealEstateView, UpdateRealEstateRequest } from '../../model/real-estate';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { HttpClient } from '@angular/common/http';

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

  createNewRealEstate(data: NewRealEstateRequest): Observable<boolean> {
    return this.http.post<boolean>(this.configService.REAL_ESTATE_CREATION, data);
  }

  editBasicRealEstateData(data: UpdateRealEstateRequest): Observable<RealEstate> {
    console.log(data);
    return this.http.put<RealEstate>(this.configService.REAL_ESTATE_BASIC_INFO_EDIT, data);
  }

  editOwnershipRealEstateData(data: UpdateRealEstateRequest): Observable<RealEstate> {
    console.log(data);
    return this.http.put<RealEstate>(this.configService.REAL_ESTATE_OWNERSHIP_INFO_EDIT, data);
  }

  block(id: number): Observable<boolean> {
    return this.http.put<boolean>(this.configService.getUserBlockURL(id), null);
  }

  unblock(id: number): Observable<boolean> {
    return this.http.put<boolean>(this.configService.getUserUnblockURL(id), null);
  }

  delete(id: number): Observable<boolean> {
     return this.http.delete<boolean>(this.configService.getUrlForRealEstateDeletion(id));
  }

}
