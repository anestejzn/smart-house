import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { Rule } from '../../model/rule';

@Injectable({
  providedIn: 'root'
})
export class RuleService {

  constructor(private configService: ConfigService, private http: HttpClient) { }

  saveRule(rule){
    return this.http.post<Rule>(this.configService.RULES_URL, rule);
  }

  getAllRules(){
    return this.http.get<Rule[]>(this.configService.RULES_URL);
  }
}
