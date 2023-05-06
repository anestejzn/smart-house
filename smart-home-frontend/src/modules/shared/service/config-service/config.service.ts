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
  LOGIN_URL = `${this.AUTH_URL}/login-reg-user`;
  LOGOUT_URL = `${this.AUTH_URL}/logout`;
  CONFIRM_PIN_URL = `${this.AUTH_URL}/confirm-pin`;

  getLoginUrl(): string {
    return this.LOGIN_URL;
  }

  getLogoutUrl(): string {
    return this.LOGOUT_URL;
  }

  getGeneratePinUrl(email: string){
    return `${this.AUTH_URL}/generate-pin/${email}`;
  }

  getIncrementFailedAttempts(email: string){
    return `${this.AUTH_URL}/increment-failed-attempts/${email}`;
  }



  //////////////////USERS////////////////
  USERS_URL = `${this.API_URL}/users`;
  CREATE_REGULAR_USER_URL = `${this.USERS_URL}/register`;
  ACTIVATE_ACCOUNT_URL = `${this.USERS_URL}/activate-account`;
  ALL_ACTIVE_TENANTS = `${this.USERS_URL}/all-active-tenants`;

  /////////////////VERIFY/////////////////
  VERIFY_URL = `${this.API_URL}/verify`;
  SEND_CODE_AGAIN_URL = `${this.VERIFY_URL}/send-code-again`;

  ///////////CSR/////////////////////////
  CSR_URL = `${this.API_URL}/csr`;
 
  getCreateCsrUrl(): string {
    return this.CSR_URL;
  }

   ///////////REAL ESTATE////////
  REAL_ESTATE_URL = `${this.API_URL}/real-estates`;
  REAL_ESTATE_CREATION = `${this.REAL_ESTATE_URL}/create/real-estate`;
  REAL_ESTATE_TENANTS_INFO_EDIT = `${this.REAL_ESTATE_URL}/edit-tenants-regular/real-estate`;

  getUrlForOwnerFilteringRealEstates(ascending: boolean, range: string, selectedOwner: number): string {
    return `${this.REAL_ESTATE_URL}/owner-real-estates/${ascending}/${range}/${selectedOwner}`;
  }

  getUrlForTenantFilteringRealEstates(ascending: boolean, range: string, selectedOwner: number): string {
    return `${this.REAL_ESTATE_URL}/tenant-real-estates/${ascending}/${range}/${selectedOwner}`;
  }


  getUrlForRealEstateById(id: string): string {
    return `${this.REAL_ESTATE_URL}/${id}`;
  }

}
