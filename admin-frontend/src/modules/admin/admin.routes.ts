import { Routes } from "@angular/router";
import { AllCertificatesViewComponent } from "./pages/all-certificates-view/all-certificates-view.component";
import { HomeComponent } from "./pages/home/home.component";

export const AdminRoutes: Routes = [
    {
      path: "home",
      pathMatch: "full",
      component: HomeComponent
    },
    {
      path: "all-certificates",
      pathMatch: "full",
      component: AllCertificatesViewComponent
    }
  ]