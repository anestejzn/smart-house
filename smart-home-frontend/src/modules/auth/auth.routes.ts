import { Routes } from "@angular/router";
import { LoginComponent } from "./pages/login/login.component";
import { RegistrationComponent } from "./pages/registration/registration.component";
import { SuccessfulVerificationComponent } from "./pages/successful-verification/successful-verification/successful-verification.component";
import { VerificationComponent } from "./pages/verification/verification.component";

export const AuthRoutes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: LoginComponent
  },
  {
    path: "login",
    pathMatch: "full",
    component: LoginComponent
  },
  {
    path: "register",
    pathMatch: "full",
    component: RegistrationComponent
  },
  {
    path: "verify/:id",
    pathMatch: "full",
    component: VerificationComponent
  },
  {
    path: "successfull-verification",
    pathMatch: "full",
    component: SuccessfulVerificationComponent,
  }
]