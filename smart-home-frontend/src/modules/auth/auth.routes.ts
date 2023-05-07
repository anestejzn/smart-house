import { Routes } from "@angular/router";
import { LoginComponent } from "./pages/login/login.component";
import { RegistrationComponent } from "./pages/registration/registration.component";
import { SuccessfulVerificationComponent } from "./pages/successful-verification/successful-verification/successful-verification.component";
import { VerificationComponent } from "./pages/verification/verification.component";
import { UnauthorizedGuard } from "./guards/login/unauthorized.guard";

export const AuthRoutes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: LoginComponent,
    canActivate: [UnauthorizedGuard]
  },
  {
    path: "login",
    pathMatch: "full",
    component: LoginComponent,
    canActivate: [UnauthorizedGuard]
  },
  {
    path: "register",
    pathMatch: "full",
    component: RegistrationComponent,
    canActivate: [UnauthorizedGuard]
  },
  {
    path: "verify/:id",
    pathMatch: "full",
    component: VerificationComponent,
    canActivate: [UnauthorizedGuard] 
  },
  {
    path: "successfull-verification",
    pathMatch: "full",
    component: SuccessfulVerificationComponent,
    canActivate: [UnauthorizedGuard]
  }
]