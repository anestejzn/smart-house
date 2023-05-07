import { Routes } from "@angular/router";
import { AllCertificatesViewComponent } from "./pages/all-certificates-view/all-certificates-view.component";
import { HomeComponent } from "./pages/home/home.component";
import { AllRealEstatesViewComponent } from "./pages/all-real-estates-view/all-real-estates-view.component";
import { DetailRealEstateComponent } from "./pages/detail-real-estate/detail-real-estate.component";
import { RoleGuard } from "../auth/guards/role/role.guard";

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
    }
  ]