import { Routes } from "@angular/router";
import { LoginComponent } from "./pages/login/login.component";
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
  }
]