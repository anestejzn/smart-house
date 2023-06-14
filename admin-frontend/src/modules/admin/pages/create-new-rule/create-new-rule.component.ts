import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { RuleService } from '../../service/rule-service/rule.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-new-rule',
  templateUrl: './create-new-rule.component.html',
  styleUrls: ['./create-new-rule.component.scss']
})
export class CreateNewRuleComponent implements OnInit {

  ruleForm = new FormGroup({
    regexPattern: new FormControl('', [Validators.required]),
    deviceType: new FormControl('', [
      Validators.required,
    ]),
  });

  constructor(private ruleService: RuleService, private toast: ToastrService, private router: Router) { }

  ngOnInit(): void {
  }

  createRule(){
    const rule = {
      deviceType: this.ruleForm.get('deviceType').value,
      regexPattern: this.ruleForm.get('regexPattern').value
    }
    this.ruleService.saveRule(rule).subscribe(
      rule => {
        this.toast.info("Saved rule");
        this.ruleForm.get('deviceType').setValue('');
        this.ruleForm.get('regexPattern').setValue('');
      },
      error => {
        this.toast.error(error.error);
      }
    )
  }

  seeCreatedRules(){
    this.router.navigate([`/smart-home/admin/rules`]);
  }

}
