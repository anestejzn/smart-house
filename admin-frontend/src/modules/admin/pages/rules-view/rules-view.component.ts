import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { RuleService } from '../../service/rule-service/rule.service';
import { RuleDataSource } from '../../model/rule-data-source';
import { Router } from '@angular/router';

@Component({
  selector: 'app-rules-view',
  templateUrl: './rules-view.component.html',
  styleUrls: ['./rules-view.component.scss']
})
export class RulesViewComponent implements OnInit {

  ruleSubscription: Subscription;
  noRules = false;
  rules:RuleDataSource;
  displayedColumns: string[] = ['deviceType', 'regexPattern'];

  constructor(private ruleService: RuleService, private router:Router) { }

  ngOnInit(): void {
    this.getRules();
  }

  getRules(){
    this.ruleSubscription = this.ruleService.getAllRules().subscribe(
      rules => {
        console.log(rules);
        if(rules.length === 0){
          this.noRules = true;
        }
        else{
          this.rules = new RuleDataSource(rules);
        }
      }
    )
  }

  createRulePage(){
    this.router.navigate(['/smart-home/admin/create-rule']);
  }


}
