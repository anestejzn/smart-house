import { Routes } from "@angular/router";
import { AllCertificatesViewComponent } from "./pages/all-certificates-view/all-certificates-view.component";
import { HomeComponent } from "./pages/home/home.component";
import { AllRealEstatesViewComponent } from "./pages/all-real-estates-view/all-real-estates-view.component";

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
    },
    {
      path: "all-real-estates",
      pathMatch: "full",
      component: AllRealEstatesViewComponent
    }
  ]