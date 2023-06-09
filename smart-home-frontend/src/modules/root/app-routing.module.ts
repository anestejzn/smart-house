import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RootLayoutComponent } from './components/root-layout/root-layout.component';

const routes: Routes = [
  {
    path: "smart-home",
    component: RootLayoutComponent,
    children: [
      {
        path: "user",
        loadChildren: () =>
          import("./../user/user.module").then((m) => m.UserModule),
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
  exports: [RouterModule],
})
export class AppRoutingModule { }
