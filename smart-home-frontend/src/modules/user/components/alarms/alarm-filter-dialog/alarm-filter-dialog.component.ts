import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DeviceFilterData } from 'src/modules/shared/model/device';

@Component({
  selector: 'app-alarm-filter-dialog',
  templateUrl: './alarm-filter-dialog.component.html',
  styleUrls: ['./alarm-filter-dialog.component.scss']
})
export class AlarmFilterDialogComponent implements OnInit {

  sortPeriod = [
    { name: 'Last 7 Days', checked: true, value: 7 },
    { name: 'Last month', checked: false, value: 30 },
    { name: 'Last year', checked: false, value: -1 },
  ];

  constructor(@Inject(MAT_DIALOG_DATA) public data: DeviceFilterData) { }

  ngOnInit(): void {
  }

  changedDevice(id: number): void {
    this.data.deviceId = id;
    this.data.devices.filter(device => {
      if(device.id === id) {
        this.data.deviceName = device.name;
      }
    })
  }

  selectSortPeriod(value: number): void {
    this.data.filterPeriod = value;
  }


}
