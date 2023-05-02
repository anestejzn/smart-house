import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { ToastrService } from 'ngx-toastr';
import { Observable, Subscription, map, startWith } from 'rxjs';
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
      ]),
      currentTenantFormControl: new FormControl<User>(null, [])
    }
  );


  filteredCities: Observable<string[]>;
  allActiveRegularUsers: User[];
  tenants: User[];
  allActiveRegularUsersSubscription: Subscription;

  matcher = new MyErrorStateMatcher();

  constructor(
    private userService: UserService,
    private toast: ToastrService
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
    if (this.tenants.length+1 > 5) {
      this.toast.error('You cannot add more than 5 tenants!', 'Error happened');
    }
    else if (this.addingForm.get('currentTenantFormControl') !== null && 
    (this.addingForm.get('ownerFormControl').value !== null && 
    this.addingForm.get('currentTenantFormControl').value.id !== 
    this.addingForm.get('ownerFormControl').value.id)) {
      if (!this.tenants.find(obj => obj.id === this.addingForm.get('currentTenantFormControl').value.id)) {
        this.tenants.push(this.addingForm.get('currentTenantFormControl').value);
        this.addingForm.get('currentTenantFormControl').setValue(null);
        this.addingForm.markAsUntouched();
        this.addingForm.markAsPristine();
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
  }

  _filterCities(value: string): string[] {
    const filterValue = value.toLowerCase();

    return cities.filter(city => city.toLowerCase().includes(filterValue));
  }

  ngOnDestroy(): void {
    if (this.allActiveRegularUsersSubscription) {
      this.allActiveRegularUsersSubscription.unsubscribe();
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
