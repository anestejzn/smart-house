import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { Device, deviceDialogData } from 'src/modules/admin/model/device';
import { DeviceService } from 'src/modules/admin/service/device-service/device.service';

@Component({
  selector: 'app-edit-device-dialog',
  templateUrl: './edit-device-dialog.component.html',
  styleUrls: ['./edit-device-dialog.component.scss']
})
export class EditDeviceDialogComponent implements OnInit, OnDestroy {

  addingForm = new FormGroup(
    {
      nameFormControl: new FormControl('', [
        Validators.required,
        Validators.pattern('[a-zA-Z0-9 ]{1,30}'),
      ]),
      deviceTypeFormControl: new FormControl('', 
        [Validators.required]
      ),
      filterRegexFormControl: new FormControl('', [
        Validators.required,
      ]),
      periodReadFormControl: new FormControl(0, [
        Validators.required,
        Validators.pattern('[1-9][0-9]*'),
      ])
    }
  );

  title: string = '';

  deviceTypes = [
    { name: 'Camera', checked: false, value: 'CAMERA' },
    { name: 'Air Conditiener', checked: false, value: 'AIR_CONDITIONER' },
    { name: 'Temperature Sensor', checked: false, value: 'TEMP_SENSOR' },
    { name: 'Smoke Sensor', checked: false, value: 'SMOKE_SENSOR' },
    { name: 'Water Sensor', checked: false, value: 'WATER_SENSOR' },
    { name: 'Air Quality Sensor', checked: false, value: 'AIR_SENSOR' },
    { name: 'Windows Locked Sensor', checked: false, value: 'WIN_LOCKED_SENSOR' },
  ];

  deletionSubscription: Subscription;
  deviceCreationSubscription: Subscription;
  deviceEditSubscription: Subscription;

  constructor(@Inject(MAT_DIALOG_DATA) public data: deviceDialogData,
              private deviceService: DeviceService,
              private toast: ToastrService,
              private dialogRef: MatDialogRef<EditDeviceDialogComponent> 
  ) { }

  ngOnInit(): void {
    if (this.data.device) {
      this.title = 'Edit device'
      this.populateFormWithExistingData();
    } else {
      this.title = 'Add new device'
    }

  }

  changeDeviceType(type: string): void {
    this.addingForm.get('deviceTypeFormControl').setValue(type);
  }

  populateFormWithExistingData(): void {
    this.addingForm.get('nameFormControl').setValue(this.data.device.name);
    this.addingForm.get('deviceTypeFormControl').setValue(this.data.device.deviceType);
    this.addingForm.get('filterRegexFormControl').setValue(this.data.device.filterRegex);
    this.addingForm.get('periodReadFormControl').setValue(this.data.device.periodRead);

    this.updateDeviceType();
  }

  save(): void {
    if (this.data.device && this.addingForm.valid) {
      this.editExistingDevice();
    } else if (this.addingForm.valid) {
      this.createNewDevice();
    }
  }

  editExistingDevice(): void {
    const device: Device = {
      id: this.data.device.id,
      deviceType: this.addingForm.get('deviceTypeFormControl').value,
      name: this.addingForm.get('nameFormControl').value,
      filterRegex: this.addingForm.get('filterRegexFormControl').value,
      periodRead: this.addingForm.get('periodReadFormControl').value
    }

    this.deviceEditSubscription = this.deviceService.edit(device).subscribe(
      res => {
        this.toast.success("Device data is successfully changed.", "Success!");
      },
      err => {
        this.toast.error(err.error, "Error happened!");
      }
    )
  }

  createNewDevice(): void {
    const device: Device = {
      realEstateId: this.data.realEstateId,
      deviceType: this.addingForm.get('deviceTypeFormControl').value,
      name: this.addingForm.get('nameFormControl').value,
      filterRegex: this.addingForm.get('filterRegexFormControl').value,
      periodRead: this.addingForm.get('periodReadFormControl').value
    }

    this.deviceEditSubscription = this.deviceService.create(device).subscribe(
      res => {
        this.toast.success("Device is successfully created.", "Success!");
      },
      err => {
        this.toast.error(err.error, "Error happened!");
      }
    )
  }

  deleteDevice(): void {
    if (this.data.device) {
      this.deletionSubscription = this.deviceService.delete(this.data.device.id).subscribe(
        res => {
          this.toast.success("Device is successfully deleted.", "Success!");
          this.dialogRef.close(true);
        },
        err => {
          this.toast.error(err.error, "Error happened!");
        }
      );
    }
  }

  updateDeviceType(): void {
    this.deviceTypes.forEach(element => {
      if (element.value === this.data.device.deviceType) element.checked = true;
    });
  }

  ngOnDestroy(): void {
      if (this.deletionSubscription) {
        this.deletionSubscription.unsubscribe();
      }

      if (this.deviceCreationSubscription) {
        this.deviceCreationSubscription.unsubscribe();
      }

      if (this.deviceEditSubscription) {
        this.deviceEditSubscription.unsubscribe();
      }

  }

}
