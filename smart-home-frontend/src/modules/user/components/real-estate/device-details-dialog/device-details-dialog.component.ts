import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { deviceDialogData } from 'src/modules/shared/model/device';

@Component({
  selector: 'app-device-details-dialog',
  templateUrl: './device-details-dialog.component.html',
  styleUrls: ['./device-details-dialog.component.scss']
})
export class DeviceDetailsDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: deviceDialogData) { }

  ngOnInit(): void {
  }

}
