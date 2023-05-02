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
  LOGIN_URL = `${this.AUTH_URL}/login-admin`;
  LOGOUT_URL = `${this.AUTH_URL}/logout`;

  getLoginUrl(): string {
    return this.LOGIN_URL;
  }

  getLogoutUrl(): string {
    return this.LOGOUT_URL;
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
    return `${this.CERTIFICATE_URL}/aliases/${type}/${validity}`;
  }

  getCertificate(alias: string): string {
    return `${this.CERTIFICATE_URL}/${alias}`;
  }

  getDeleteCertificate(): string {
    return `${this.CERTIFICATE_URL}/cancel`;
  }

  ///////////USER///////////////
  USER_URL = `${this.API_URL}/users`;
  ALL_ACTIVE_REGULAR_USERS = `${this.USER_URL}/all-active-regular`;


  ///////////REAL ESTATE////////
  REAL_ESTATE_URL = `${this.API_URL}/real-estates`;

  getUrlForFilteringRealEstates(ascending: boolean, range: string, selectedOwner: number): string {
    return `${this.REAL_ESTATE_URL}/${ascending}/${range}/${selectedOwner}`;
  }

}
