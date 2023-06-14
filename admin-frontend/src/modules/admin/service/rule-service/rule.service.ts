import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';

@Injectable({
  providedIn: 'root'
})
export class RuleService {

  constructor(private configService: ConfigService, private http: HttpClient) { }

  saveRule(rule){
    return this.http.post(this.configService.ALARMS_URL, rule);
  }

  getAllRules(){
    return this.http.get(this.configService.RULES_URL);
  }
}
