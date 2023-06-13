import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { Alarm } from 'src/modules/shared/model/alarm';
import { Device } from 'src/modules/shared/model/device';
import { AlarmService } from 'src/modules/user/service/alarm-service/alarm.service';
import { DeviceService } from 'src/modules/user/service/device-service/device.service';
import { AlarmFilterDialogComponent } from '../alarm-filter-dialog/alarm-filter-dialog.component';

@Component({
  selector: 'app-alarm-rows',
  templateUrl: './alarm-rows.component.html',
  styleUrls: ['./alarm-rows.component.scss']
})
export class AlarmRowsComponent implements OnInit, OnDestroy {
  @Input() realEstateId: number;

  deviceId: number;
  deviceName: string;
  filterPeriod: number;

  alarms: Alarm[] = [];
  devices: Device[] = [];

  deviceSubscription: Subscription;
  alarmSubscription: Subscription;

  constructor(private alarmService: AlarmService,
              private deviceService: DeviceService,
              private dialogRef: MatDialog,  
  ) {
    this.deviceId = -1;
    this.deviceName = 'All';
    this.filterPeriod = 7;
  }

  ngOnInit(): void {
     if (this.realEstateId) {
      console.log(this.deviceId)
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

  openDialog(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.minWidth = '30rem'; 
    dialogConfig.minHeight = '30rem'; 
    dialogConfig.maxHeight = '90vh'; 
    dialogConfig.maxWidth = '90vw'; 
    dialogConfig.data = {realEstateId: this.realEstateId, deviceId: this.deviceId, 
      filterPeriod: this.filterPeriod, devices: this.devices, deviceName: this.deviceName};
    const dialogRef = this.dialogRef.open(AlarmFilterDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(res => {
        if (res) {
          this.deviceId = res.deviceId;
          this.deviceName = res.deviceName;
          this.filterPeriod = res.filterPeriod;
          this.loadDevices();
          this.loadAlarms();
        }
      });
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
