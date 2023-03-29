import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CsrTableComponent } from './components/csr-table/csr-table.component';
import { MaterialModule } from '../material/material.module';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AdminRoutes } from './admin.routes';
import { HomeComponent } from './pages/home/home.component';
import { CsrDetailsDialogComponent } from './components/csr-details-dialog/csr-details-dialog.component';
import { CreateCertificateDialogComponent } from './components/create-certificate-dialog/create-certificate-dialog.component';
import { AllCertificatesViewComponent } from './pages/all-certificates-view/all-certificates-view.component';
import { CertificatesSortingButtonsComponent } from './components/certificates-sorting-buttons/certificates-sorting-buttons.component';
import { CertificateRowsComponent } from './components/certificate-rows/certificate-rows.component';
import { CertificateOneRowComponent } from './components/certificate-one-row/certificate-one-row.component';



@NgModule({
  declarations: [
    CsrTableComponent,
    HomeComponent,
    CsrDetailsDialogComponent,
    CreateCertificateDialogComponent,
    AllCertificatesViewComponent,
    CertificatesSortingButtonsComponent,
    CertificateRowsComponent,
    CertificateOneRowComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    RouterModule.forChild(AdminRoutes)
  ]
})
export class AdminModule { }
