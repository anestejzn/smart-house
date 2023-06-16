import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { Device } from 'src/modules/shared/model/device';
import { DeviceService } from 'src/modules/user/service/device-service/device.service';
import { DeviceDetailsDialogComponent } from '../device-details-dialog/device-details-dialog.component';

@Component({
  selector: 'app-devices-tab',
  templateUrl: './devices-tab.component.html',
  styleUrls: ['./devices-tab.component.scss']
})
export class DevicesTabComponent implements OnInit, OnDestroy {
  @Input() realEstateId: number;

  devices: Device[] = [];
  deviceSubscription: Subscription;

  constructor(private deviceService: DeviceService,
              private dialogRef: MatDialog
  ) { }

  ngOnInit(): void {
    if (this.realEstateId) {
      this.loadDevices();
    }
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

  openDialog(device: Device): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.minWidth = '40rem'; 
    dialogConfig.minHeight = '30rem'; 
    dialogConfig.maxHeight = '90vh'; 
    dialogConfig.maxWidth = '90vw'; 
    dialogConfig.data = {device: device, realEstateId: this.realEstateId};
    const dialogRef = this.dialogRef.open(DeviceDetailsDialogComponent, dialogConfig);
  }

  ngOnDestroy(): void {
    if (this.deviceSubscription) {
      this.deviceSubscription.unsubscribe();
    }
  }

}
