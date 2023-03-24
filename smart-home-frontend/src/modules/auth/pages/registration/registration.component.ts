import { Component, OnInit } from '@angular/core';
import { ControlContainer, FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { map, Observable, startWith } from 'rxjs';
import { Country } from 'src/modules/shared/model/country';
import { countries } from 'src/modules/shared/model/country-data';
import { matchPasswordsValidator } from 'src/modules/shared/validators/confirm-password.validator';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  hidePassword = true;
  hideConfirmPassword = true;
  
  filteredCities: Observable<string[]>;
  public countries: Country[] = countries;
  public filteredCountries: Observable<Country[]> | undefined;
  
  constructor() {
    this.filteredCities = this.registrationForm
      .get('cityFormControl')
      .valueChanges.pipe(
        startWith(''),
        map(city => (city ? this._filterCities(city) : this.cities.slice()))
      );

      this.filteredCountries = this.registrationForm.get('countryFormControl')?.valueChanges.pipe(
        startWith(''),
        map((country: string) => (country ? this._filterCountries(country) : this.countries.slice()))
      );  
  }

  ngOnInit(): void {}

  registrationForm = new FormGroup(
    {
      emailFormControl: new FormControl('', [
        Validators.required,
        Validators.email,
      ]),
      nameFormControl: new FormControl('', [
        Validators.required,
        Validators.pattern('[a-zA-Z ]*'),
      ]),
      surnameFormControl: new FormControl('', [
        Validators.required,
        Validators.pattern('[a-zA-Z ]*'),
      ]),
      passwordAgainFormControl: new FormControl('', [
        Validators.required,
        Validators.minLength(8),
      ]),
      passwordFormControl: new FormControl('', [
        Validators.required,
        Validators.minLength(9),
      ]),
      cityFormControl: new FormControl('', [
        Validators.required,
        Validators.pattern('[a-zA-Z ]*'),
      ]),
      countryFormControl: new FormControl('', [
        Validators.required,
        Validators.pattern('[a-zA-Z ]*'),
      ]),
    },
    [matchPasswordsValidator()]
  );

  matcher = new MyErrorStateMatcher();
  cities: string[] = [
    'Beograd',
    'Novi Sad',
    'Kraljevo',
    'Kragujevac',
    'Jagodina',
    'Mladenovac',
    'Subotica',
    'Ruma',
    'Priboj',
    'Sabac',
    'Leskovac',
    'Vranje',
    'Smederevo',
    'Pozarevac',
    'Zrenjanin',
    'Sombor',
  ];

  getError() {
    return this.registrationForm.hasError('mismatch');
  }

  _filterCountries(value: string): Country[] {
    const filterValue = value.toLowerCase();

    return this.countries.filter(country => country.name.toLowerCase().includes(filterValue));
  }

  _filterCities(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.cities.filter(city => city.toLowerCase().includes(filterValue));
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
