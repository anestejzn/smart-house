import { Routes } from "@angular/router";
import { HomeComponent } from "./pages/home/home.component";
import { AllRealEstatesViesComponent } from "./pages/all-real-estates-vies/all-real-estates-vies.component";
import { DetailRealEstateComponent } from "./pages/detail-real-estate/detail-real-estate.component";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { ChartViewComponent } from "./pages/chart-view/chart-view.component";

export const UserRoutes: Routes = [
  {
    path: "home",
    pathMatch: "full",
    component: HomeComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'ROLE_OWNER|ROLE_TENANT' }
  },
  {
    path: "all-real-estates",
    pathMatch: "full",
    component: AllRealEstatesViesComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'ROLE_OWNER|ROLE_TENANT' }
  },
  {
    path: "real-estate/:id",
    pathMatch: "full",
    component: DetailRealEstateComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'ROLE_OWNER|ROLE_TENANT' }
  },
  {
    path: "chart",
    pathMatch: "full",
    component: ChartViewComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'ROLE_OWNER|ROLE_TENANT' }
  }
]