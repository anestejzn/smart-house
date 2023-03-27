import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CsrTableComponent } from './components/csr-table/csr-table.component';
import { MaterialModule } from '../material/material.module';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AdminRoutes } from './admin.routes';
import { HomeComponent } from './pages/home/home.component';
import { CsrDetailsDialogComponent } from './components/csr-details-dialog/csr-details-dialog.component';



@NgModule({
  declarations: [
    CsrTableComponent,
    HomeComponent,
    CsrDetailsDialogComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    RouterModule.forChild(AdminRoutes)
  ]
})
export class AdminModule { }
