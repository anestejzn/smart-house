import { Component, Input, OnInit } from '@angular/core';
import { LogDataSource } from '../../model/log-data-source';
import { Subscription } from 'rxjs';
import { AlarmService } from 'src/modules/shared/service/alarm-service/alarm.service';
import { AlarmDataSource } from '../../model/alarm-data-source';
import { Router } from '@angular/router';

@Component({
  selector: 'app-alarms-view',
  templateUrl: './alarms-view.component.html',
  styleUrls: ['./alarms-view.component.scss']
})
export class AlarmsViewComponent implements OnInit {
  alarmSubscription: Subscription;
  alarms: AlarmDataSource;
  noAlarms = false;

  constructor(private alarmService: AlarmService, private router: Router) { }

  ngOnInit(): void {
    this.getAlarms();
  }

  getAlarms(){
    this.alarmSubscription = this.alarmService.getAllAlarms().subscribe(
      alarms => {
        console.log(alarms);
        if(alarms.length === 0){
          this.noAlarms = true;
        }
        else{
          this.alarms = new AlarmDataSource(alarms);
        }
      }
    )
  }

  createRulePage(){
    this.router.navigate([`/smart-home/admin/create-rule`]);
  }

  seeCreatedRules(){
    this.router.navigate([`/smart-home/admin/rules`]);
  }



}
