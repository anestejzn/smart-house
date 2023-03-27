import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RootLayoutComponent } from './pages/root-layout/root-layout.component';

const routes: Routes = [
  {
    path: "smart-home",
    component: RootLayoutComponent,
    children: [
      {
        path: "admin",
        loadChildren: () =>
          import("./../admin/admin.module").then((m) => m.AdminModule),
      },
      {
        path: "auth",
        loadChildren: () => 
          import("./../auth/auth.module").then((m) => m.AuthModule)
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
