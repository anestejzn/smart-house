import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { CancelCertificateRequest } from '../../model/cancel-certificate-request';
import { CertificateData } from '../../model/certificate-data';
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

  getCertificateChain(alias: string): Observable<CertificateData[]> {
    return  this.http.get<CertificateData[]>(this.configService.getCertificate(alias));
  }

  createLeafCertificate(request: CertificateRequest) {
    return this.http.post<void>(this.configService.getLeafCertificateUrl(), request);
  }

  cancelCertificate(request: CancelCertificateRequest): Observable<boolean> {
    return this.http.put<boolean>(this.configService.getDeleteCertificate(), request);
  }

}
