import { Injectable } from '@angular/core';
import { RealEstateView } from '../../model/real-estate-view';
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

}
