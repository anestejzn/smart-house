import { Routes } from "@angular/router";
import { HomeComponent } from "./pages/home/home.component";

export const AdminRoutes: Routes = [
    {
      path: "home",
      pathMatch: "full",
      component: HomeComponent
    }
  ]