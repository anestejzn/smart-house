import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AuthRoutes } from './auth.routes';
import { SharedModule } from '../shared/shared.module';
import { RegistrationComponent } from './pages/registration/registration.component';
import { VerificationComponent } from './pages/verification/verification.component';
import { SuccessfulVerificationComponent } from './pages/successful-verification/successful-verification/successful-verification.component';
import { LoginComponent } from './pages/login/login.component';


@NgModule({
  declarations: [
    LoginComponent,
    RegistrationComponent,
    VerificationComponent,
    SuccessfulVerificationComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
    RouterModule.forChild(AuthRoutes)
  ]
})
export class AuthModule { }
