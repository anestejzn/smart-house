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
  ALL_CERTIFIED_OWNERS = `${this.USER_URL}/all-certified-owners`;
  ALL_ACTIVE_TENANTS = `${this.USER_URL}/all-active-tenants`;

  getFilterUsersURL(asc: boolean, status: string): string {

    return `${this.USER_URL}/${asc}/${status}`;
  }

  ///////////REAL ESTATE////////
  REAL_ESTATE_URL = `${this.API_URL}/real-estates`;
  REAL_ESTATE_CREATION = `${this.REAL_ESTATE_URL}/create/real-estate`;
  REAL_ESTATE_BASIC_INFO_EDIT = `${this.REAL_ESTATE_URL}/edit-basic-info/real-estate`;
  REAL_ESTATE_OWNERSHIP_INFO_EDIT = `${this.REAL_ESTATE_URL}/edit-ownership/real-estate`;

  getUrlForFilteringRealEstates(ascending: boolean, range: string, selectedOwner: number): string {
    return `${this.REAL_ESTATE_URL}/owner-real-estates/${ascending}/${range}/${selectedOwner}`;
  }

  getUrlForRealEstateById(id: string): string {
    return `${this.REAL_ESTATE_URL}/${id}`;
  }

  getUrlForRealEstateDeletion(id: number): string {
    return `${this.REAL_ESTATE_URL}/${id}`;
  }


  ///////LOGS/////

  LOG_URL = `${this.API_URL}/logs`;
  FILTER_LOGS_URL = `${this.LOG_URL}/filter`;

  getUserBlockURL(id: number): string {
    return `${this.REAL_ESTATE_URL}/block/${id}`;
  }

  getUserUnblockURL(id: number): string {
    return `${this.REAL_ESTATE_URL}/unblock/${id}`;
  }

  ///////////////////DEVICES//////////////////
  DEVICES_URL = `${this.API_URL}/devices`;

  getDevicesPerRealEstateURL(id: number): string {
    return `${this.DEVICES_URL}/devices-per-real-estate/${id}`;
  }

   //////////////////ALARMS///////////////////
  ALARMS_URL = `${this.API_URL}/alarms`;

  getFilteredAlarms(realEstateId: number, deviceId: number, period: number): string {
    return `${this.ALARMS_URL}/${realEstateId}/${deviceId}/${period}`;
  }


  //////////RULES/////////
  RULES_URL = `${this.API_URL}/rules`;
  


}
