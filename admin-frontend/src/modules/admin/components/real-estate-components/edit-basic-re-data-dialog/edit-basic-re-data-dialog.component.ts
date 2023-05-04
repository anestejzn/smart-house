import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import {  RealEstate, UpdateRealEstateRequest } from 'src/modules/admin/model/real-estate';
import { cities } from 'src/modules/shared/model/cities';
import { Observable, Subscription, map, startWith } from 'rxjs';
import { MyErrorStateMatcher } from '../add-new-real-estate-dialog/add-new-real-estate-dialog.component';
import { ToastrService } from 'ngx-toastr';
import { RealEstateService } from 'src/modules/admin/service/real-estate/real-estate.service';

@Component({
  selector: 'app-edit-basic-re-data-dialog',
  templateUrl: './edit-basic-re-data-dialog.component.html',
  styleUrls: ['./edit-basic-re-data-dialog.component.scss']
})
export class EditBasicReDataDialogComponent implements OnInit, OnDestroy {


  editForm = new FormGroup(
    {
      nameFormControl: new FormControl('', [
        Validators.required,
        Validators.pattern('[a-zA-Z1-9 ]{1,20}'),
      ]),
      sqAreaFormControl: new FormControl(0, [
        Validators.required,
        Validators.pattern('(?:[1-9][0-9]|[1-5][0-9]{2}|600)'),
      ]),
      cityFormControl: new FormControl('', [
        Validators.required,
        Validators.pattern('[a-zA-Z ]{1,20}'),
      ]),
      streetFormControl: new FormControl('', [
        Validators.required,
        Validators.pattern('[a-zA-Z ]{1,20}'),
      ]),
      numberFormControl: new FormControl('', [
        Validators.required,
        Validators.pattern('[1-9][0-9]*'),
      ])
    }
  );

  matcher = new MyErrorStateMatcher();
  filteredCities: Observable<string[]>;
  realEstateSubscription: Subscription;

  constructor(@Inject(MAT_DIALOG_DATA) public realEstate: RealEstate,
            private toast: ToastrService,
            private realEstateService: RealEstateService
  ) {
    this.filteredCities = this.editForm
      .get('cityFormControl')
      .valueChanges.pipe(
        startWith(''),
        map(city => (city ? this._filterCities(city) : cities.slice()))
      );
  }

  ngOnInit(): void {
    if (this.editForm) {
      this.editForm.get("nameFormControl").setValue(this.realEstate.name);
      this.editForm.get("sqAreaFormControl").setValue(this.realEstate.sqMeters);
      this.editForm.get("cityFormControl").setValue(this.realEstate.city);
      this.editForm.get("streetFormControl").setValue(this.realEstate.street);
      this.editForm.get("numberFormControl").setValue(this.realEstate.streetNum);
    }
  }

  save(): void {
    if (this.editForm.valid) {
      const data: UpdateRealEstateRequest = {
        id: this.realEstate.id,
        name: this.editForm.get('nameFormControl').value,
        sqMeters: +this.editForm.get('sqAreaFormControl').value,
        city: this.editForm.get('cityFormControl').value,
        street: this.editForm.get('streetFormControl').value,
        streetNum: this.editForm.get('numberFormControl').value
      }
      this.realEstateSubscription = this.realEstateService.editBasicRealEstateData(data).subscribe(
        res => {
          if (res) {
            this.realEstate = res;
            this.toast.success('Real Estate Object is successfully updated.', 'Success!');
          }
        },
        err => {
          if (err) {
            this.toast.error(err.error, 'Error happened');
          }
        }
      )
    } else {
      this.toast.error('Please make sure that your form is valid!', 'Error happened');
    }
  }

  _filterCities(value: string): string[] {
    const filterValue = value.toLowerCase();

    return cities.filter(city => city.toLowerCase().includes(filterValue));
  }

  ngOnDestroy(): void {
    if (this.realEstateSubscription) {
      this.realEstateSubscription.unsubscribe();
    }
  }

}
