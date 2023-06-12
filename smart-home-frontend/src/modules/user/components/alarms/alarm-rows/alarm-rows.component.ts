import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Alarm } from 'src/modules/shared/model/alarm';
import { Device } from 'src/modules/shared/model/device';
import { AlarmService } from 'src/modules/user/service/alarm-service/alarm.service';
import { DeviceService } from 'src/modules/user/service/device-service/device.service';

@Component({
  selector: 'app-alarm-rows',
  templateUrl: './alarm-rows.component.html',
  styleUrls: ['./alarm-rows.component.scss']
})
export class AlarmRowsComponent implements OnInit, OnDestroy {
  @Input() realEstateId: number;

  deviceId: number = -1;
  filterPeriod: number = -1;

  alarms: Alarm[] = [];
  devices: Device[] = [];

  deviceSubscription: Subscription;
  alarmSubscription: Subscription;

  constructor(private alarmService: AlarmService,
              private deviceService: DeviceService  
  ) { }

  ngOnInit(): void {
     if (this.realEstateId) {
      this.loadDevices();
      this.loadAlarms();
    }
  }

  loadAlarms(): void {
    this.alarmSubscription = this.alarmService.getFilteredAlarms(this.realEstateId, this.deviceId, this.filterPeriod).subscribe(
        res => {
          if (res) {
            this.alarms = res;
            // console.log(this.alarms);
          }
        },
        err => {
          console.log(err);//ne bi se trebalo desiti
        }
      )
  }

  loadDevices(): void {
    this.deviceSubscription = this.deviceService.getDevicesPerRealEstate(this.realEstateId).subscribe(
        res => {
          if (res) {
            this.devices = res;
          }
        },
        err => {
          console.log(err);//ne bi se trebalo desiti
        }
      )
  }

  ngOnDestroy(): void {
      if (this.deviceSubscription) {
        this.deviceSubscription.unsubscribe();
      }

      if (this.alarmSubscription) {
        this.alarmSubscription.unsubscribe();
      }
  }

}
