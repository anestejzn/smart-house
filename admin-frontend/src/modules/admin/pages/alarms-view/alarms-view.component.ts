import { Component, Input, OnInit } from '@angular/core';
import { LogDataSource } from '../../model/log-data-source';
import { Subscription } from 'rxjs';
import { AlarmService } from 'src/modules/shared/service/alarm-service/alarm.service';
import { AlarmDataSource } from '../../model/alarm-data-source';

@Component({
  selector: 'app-alarms-view',
  templateUrl: './alarms-view.component.html',
  styleUrls: ['./alarms-view.component.scss']
})
export class AlarmsViewComponent implements OnInit {
  alarmSubscription: Subscription;
  alarms: AlarmDataSource;
  noAlarms = false;

  constructor(private alarmService: AlarmService) { }

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
    console.log("create");
  }

  seeCreatedRules(){
    console.log("see created");
  }



}
