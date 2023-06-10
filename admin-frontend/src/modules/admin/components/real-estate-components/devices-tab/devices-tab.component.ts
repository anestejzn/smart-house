import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { Device } from 'src/modules/admin/model/device';
import { DeviceService } from 'src/modules/admin/service/device-service/device.service';
import { EditDeviceDialogComponent } from '../edit-device-dialog/edit-device-dialog.component';

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
              private editDialog: MatDialog
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
    dialogConfig.minWidth = '48rem'; 
    dialogConfig.minHeight = '40rem'; 
    dialogConfig.maxHeight = '90vh'; 
    dialogConfig.maxWidth = '90vw'; 
    dialogConfig.data = {device: device, realEstateId: this.realEstateId};
    const dialogRef = this.editDialog.open(EditDeviceDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.loadDevices();
      }
    });
  }

  ngOnDestroy(): void {
    if (this.deviceSubscription) {
      this.deviceSubscription.unsubscribe();
    }
  }

}
