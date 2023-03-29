import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  constructor() { }

  API_URL = environment.apiUrl;
  ///////////////////AUTH///////////////////
  AUTH_URL = `${this.API_URL}/auth`;
  LOGIN_URL = `${this.AUTH_URL}/login`;

  getLoginUrl(): string {
    return this.LOGIN_URL;
  }

  ///////////CSR/////////////////////////
  CSR_URL = `${this.API_URL}/csr`;
  PENDING_CSR_URL = `${this.CSR_URL}/pending`
  REJECT_CSR_URL = `${this.CSR_URL}/reject`;
 
  getCreateCsrUrl(): string {
    return this.CSR_URL;
  }

  getAllPendingCsrsUrl(): string{
    return this.PENDING_CSR_URL;
  }

  rejectCsrUrl(): string {
    return this.REJECT_CSR_URL;
  }


  /////////////CERTIFICATE/////////
  CERTIFICATE_URL = `${this.API_URL}/api/certificate`;
  LEAF_CERTIFICATE_URL = `${this.CERTIFICATE_URL}/create/leaf`;

  getLeafCertificateUrl(): string{
    return this.LEAF_CERTIFICATE_URL;
  }

  getAllCertificates(type: string, validity: string): string {
    return `${this.CERTIFICATE_URL}/aliases/${type}/${validity}`
  }


}
