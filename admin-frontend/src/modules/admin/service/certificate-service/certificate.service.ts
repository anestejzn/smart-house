import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { CertificateRequest } from '../../model/certificate-request';
import { SortedAliases } from '../../model/sorted-aliases';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private configService: ConfigService, private http: HttpClient) { }

  getAllCertificates(type: string, validity: string): Observable<SortedAliases[]> {
    return  this.http.get<SortedAliases[]>(this.configService.getAllCertificates(type, validity));
  }

  createLeafCertificate(request: CertificateRequest) {
    return this.http.post<void>(this.configService.getLeafCertificateUrl(), request);
  }

}
