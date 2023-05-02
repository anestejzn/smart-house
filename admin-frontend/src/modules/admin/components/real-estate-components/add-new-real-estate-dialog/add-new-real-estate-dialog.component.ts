import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { ToastrService } from 'ngx-toastr';
import { Observable, Subscription, map, startWith } from 'rxjs';
import { NewRealEstateRequest } from 'src/modules/admin/model/real-estate';
import { RealEstateService } from 'src/modules/admin/service/real-estate/real-estate.service';
import { cities } from 'src/modules/shared/model/cities';
import { User } from 'src/modules/shared/model/user';
import { UserService } from 'src/modules/shared/service/user-service/user.service';

@Component({
  selector: 'app-add-new-real-estate-dialog',
  templateUrl: './add-new-real-estate-dialog.component.html',
  styleUrls: ['./add-new-real-estate-dialog.component.scss']
})
export class AddNewRealEstateDialogComponent implements OnInit, OnDestroy {
  addingForm = new FormGroup(
    {
      nameFormControl: new FormControl('', [
        Validators.required,
        Validators.pattern('[a-zA-Z1-9 ]{1,20}'),
      ]),
      sqAreaFormControl: new FormControl('', [
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
      ]),
      ownerFormControl: new FormControl<User>(null, [
        Validators.required,
      ])
    }
  );


  filteredCities: Observable<string[]>;
  allActiveRegularUsers: User[];
  tenants: User[];
  tenantIds: number[] = [];
  currentTenant: User = null;
  allActiveRegularUsersSubscription: Subscription;
  realEstateCreationSubscription: Subscription;

  matcher = new MyErrorStateMatcher();

  constructor(
    private userService: UserService,
    private toast: ToastrService,
    private realEstateService: RealEstateService
  ) {
    this.allActiveRegularUsers = [];
    this.tenants = [];
    this.filteredCities = this.addingForm
      .get('cityFormControl')
      .valueChanges.pipe(
        startWith(''),
        map(city => (city ? this._filterCities(city) : cities.slice()))
      );
  }

  ngOnInit(): void {
    this.allActiveRegularUsersSubscription =this.userService.getAllActiveRegularUsers().subscribe(
      res => {
        this.allActiveRegularUsers = res;
      }
    )
  }

  addTenant(): void {
    if (this.tenants.length+1 > 6) {
      this.toast.error('You cannot add more than 5 tenants!', 'Error happened');
    }
    else if (this.currentTenant !== null && 
    (this.addingForm.get('ownerFormControl').value !== null && 
    this.currentTenant.id !== this.addingForm.get('ownerFormControl').value.id)) {
      if (!this.tenants.find(obj => obj.id === this.currentTenant.id)) {
        this.tenants.push(this.currentTenant);
        this.tenantIds.push(this.currentTenant.id);
        this.currentTenant = null;
      }
      else {
        this.toast.error('Your tenant is already added!', 'Error happened');
      }
    } 
    else {
      this.toast.error('Your must select tenant that is not owner! Select Owner first!', 'Error happened');
    }
  }

  deleteTenant(tenant: User): void {
    this.tenants = this.tenants.filter(element => element.id !== tenant.id);
    this.tenantIds = this.tenantIds.filter(element => element !== tenant.id);
  }

  save(): void {
    if (this.addingForm.valid) {
      const data: NewRealEstateRequest = {
        name: this.addingForm.get('nameFormControl').value,
        sqMeters: +this.addingForm.get('sqAreaFormControl').value,
        city: this.addingForm.get('cityFormControl').value,
        street: this.addingForm.get('streetFormControl').value,
        streetNum: this.addingForm.get('numberFormControl').value,
        ownerId: this.addingForm.get('ownerFormControl').value.id,
        tenantsIds: this.tenantIds
      }
      this.realEstateCreationSubscription = this.realEstateService.createNewRealEstate(data).subscribe(
        res => {
          console.log(res);
        },
        err => {
          this.toast.error(err.error, 'Error happened');
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
    if (this.allActiveRegularUsersSubscription) {
      this.allActiveRegularUsersSubscription.unsubscribe();
    }

    if (this.realEstateCreationSubscription) {
      this.realEstateCreationSubscription.unsubscribe();
    }

  }

}

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    const isSubmitted = form && form.submitted;
    return !!(
      control &&
      control.invalid &&
      (control.dirty || control.touched || isSubmitted)
    );
  }
}
