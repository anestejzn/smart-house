import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CsrTableComponent } from './components/csr-table/csr-table.component';
import { MaterialModule } from '../material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AdminRoutes } from './admin.routes';
import { HomeComponent } from './pages/home/home.component';
import { CsrDetailsDialogComponent } from './components/csr-details-dialog/csr-details-dialog.component';
import { CreateCertificateDialogComponent } from './components/certificate-components/create-certificate-dialog/create-certificate-dialog.component';
import { AllCertificatesViewComponent } from './pages/all-certificates-view/all-certificates-view.component';
import { CertificatesSortingButtonsComponent } from './components/certificate-components/certificates-sorting-buttons/certificates-sorting-buttons.component';
import { CertificateRowsComponent } from './components/certificate-components/certificate-rows/certificate-rows.component';
import { CertificateOneRowComponent } from './components/certificate-components/certificate-one-row/certificate-one-row.component';
import { CertificateDetailsDialogComponent } from './components/certificate-components/certificate-details-dialog/certificate-details-dialog.component';
import { CancelCertificateDialogComponent } from './components/certificate-components/cancel-certificate-dialog/cancel-certificate-dialog.component';
import { CertificateDetailsTabComponent } from './components/certificate-components/certificate-details-tab/certificate-details-tab.component';
import { CertificateGeneralTabComponent } from './components/certificate-components/certificate-general-tab/certificate-general-tab.component';
import { CertificatesChainTabComponent } from './components/certificate-components/certificates-chain-tab/certificates-chain-tab.component';
import { AllRealEstatesViewComponent } from './pages/all-real-estates-view/all-real-estates-view.component';
import { RealEstateRowComponent } from './components/real-estate-components/real-estate-row/real-estate-row.component';
import { RealEstateSortingButtonsComponent } from './components/real-estate-components/real-estate-sorting-buttons/real-estate-sorting-buttons.component';
import { RealEstateRowsComponent } from './components/real-estate-components/real-estate-rows/real-estate-rows.component';
import { AddNewRealEstateDialogComponent } from './components/real-estate-components/add-new-real-estate-dialog/add-new-real-estate-dialog.component';
import { DetailRealEstateComponent } from './pages/detail-real-estate/detail-real-estate.component';
import { BasicRealEstateInfoComponent } from './components/real-estate-components/basic-real-estate-info/basic-real-estate-info.component';
import { OwnershipDataRealEstateComponent } from './components/real-estate-components/ownership-data-real-estate/ownership-data-real-estate.component';
import { OwnerDataComponent } from './components/real-estate-components/owner-data/owner-data.component';
import { TenantDataComponent } from './components/real-estate-components/tenant-data/tenant-data.component';



@NgModule({
  declarations: [
    CsrTableComponent,
    HomeComponent,
    CsrDetailsDialogComponent,
    CreateCertificateDialogComponent,
    AllCertificatesViewComponent,
    CertificatesSortingButtonsComponent,
    CertificateRowsComponent,
    CertificateOneRowComponent,
    CertificateDetailsDialogComponent,
    CancelCertificateDialogComponent,
    CertificateDetailsTabComponent,
    CertificateGeneralTabComponent,
    CertificatesChainTabComponent,
    AllRealEstatesViewComponent,
    RealEstateRowComponent,
    RealEstateSortingButtonsComponent,
    RealEstateRowsComponent,
    AddNewRealEstateDialogComponent,
    DetailRealEstateComponent,
    BasicRealEstateInfoComponent,
    OwnershipDataRealEstateComponent,
    OwnerDataComponent,
    TenantDataComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(AdminRoutes)
  ]
})
export class AdminModule { }
