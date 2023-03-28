import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { CertificateRequest } from '../../model/certificate-request';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private configService: ConfigService, private http: HttpClient) { }

  createLeafCertificate(request: CertificateRequest){
    return this.http.post<void>(this.configService.getLeafCertificateUrl(), request);
  }
}
