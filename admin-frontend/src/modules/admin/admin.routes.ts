import { Routes } from "@angular/router";
import { AllCertificatesViewComponent } from "./pages/all-certificates-view/all-certificates-view.component";
import { HomeComponent } from "./pages/home/home.component";
import { AllRealEstatesViewComponent } from "./pages/all-real-estates-view/all-real-estates-view.component";
import { DetailRealEstateComponent } from "./pages/detail-real-estate/detail-real-estate.component";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { CsrViewComponent } from "./pages/csr-view/csr-view.component";
import { LogsViewComponent } from "./components/logs-view/logs-view.component";
import { CreateNewRuleComponent } from "./pages/create-new-rule/create-new-rule.component";

export const AdminRoutes: Routes = [
    {
      path: "home",
      pathMatch: "full",
      component: HomeComponent,
      canActivate: [RoleGuard],
      data: { expectedRoles: 'ROLE_ADMIN' }
    },
    {
      path: "all-certificates",
      pathMatch: "full",
      component: AllCertificatesViewComponent,
      canActivate: [RoleGuard],
      data: { expectedRoles: 'ROLE_ADMIN' }
    },
    {
      path: "all-real-estates",
      pathMatch: "full",
      component: AllRealEstatesViewComponent,
      canActivate: [RoleGuard],
      data: { expectedRoles: 'ROLE_ADMIN' }
    },
    {
      path: "real-estate/:id",
      pathMatch: "full",
      component: DetailRealEstateComponent,
      canActivate: [RoleGuard],
      data: { expectedRoles: 'ROLE_ADMIN' }
    },
    {
      path: "csrs",
      pathMatch: "full",
      component: CsrViewComponent,
      canActivate: [RoleGuard],
      data: { expectedRoles: 'ROLE_ADMIN' }
    },
    {
      path: "logs",
      pathMatch: "full",
      component: LogsViewComponent,
      canActivate: [RoleGuard],
      data: { expectedRoles: 'ROLE_ADMIN' }
    },
    {
      path: "create-rule",
      pathMatch: "full",
      component: CreateNewRuleComponent,
      canActivate: [RoleGuard],
      data: { expectedRoles: 'ROLE_ADMIN' }
    }
  ]