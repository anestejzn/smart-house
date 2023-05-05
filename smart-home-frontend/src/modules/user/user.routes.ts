import { Routes } from "@angular/router";
import { HomeComponent } from "./pages/home/home.component";
import { AllRealEstatesViesComponent } from "./pages/all-real-estates-vies/all-real-estates-vies.component";
import { DetailRealEstateComponent } from "./pages/detail-real-estate/detail-real-estate.component";

export const UserRoutes: Routes = [
  {
    path: "home",
    pathMatch: "full",
    component: HomeComponent
  },
  {
    path: "all-real-estates",
    pathMatch: "full",
    component: AllRealEstatesViesComponent
  },
  {
    path: "real-estate/:id",
    pathMatch: "full",
    component: DetailRealEstateComponent
  }
]