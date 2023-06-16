import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material/material.module';
import { UserRoutes } from './user.routes';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HomeComponent } from './pages/home/home.component';
import { AllRealEstatesViesComponent } from './pages/all-real-estates-vies/all-real-estates-vies.component';
import { DetailRealEstateComponent } from './pages/detail-real-estate/detail-real-estate.component';
import { BasicRealEstateInfoComponent } from './components/real-estate/basic-real-estate-info/basic-real-estate-info.component';
import { RealEstateSortingButtonsComponent } from './components/real-estate/real-estate-sorting-buttons/real-estate-sorting-buttons.component';
import { RealEstateRowsComponent } from './components/real-estate/real-estate-rows/real-estate-rows.component';
import { RealEstateRowComponent } from './components/real-estate/real-estate-row/real-estate-row.component';
import { OwnershipDataRealEstateComponent } from './components/real-estate/ownership-data-real-estate/ownership-data-real-estate.component';
import { EditTenantsReDialogComponent } from './components/real-estate/edit-tenants-re-dialog/edit-tenants-re-dialog.component';
import { OwnerDataComponent } from './components/real-estate/owner-data/owner-data.component';
import { TenantDataComponent } from './components/real-estate/tenant-data/tenant-data.component';
import { DevicesTabComponent } from './components/real-estate/devices-tab/devices-tab.component';
import { DeviceDetailsDialogComponent } from './components/real-estate/device-details-dialog/device-details-dialog.component';
import {CarouselModule} from "primeng/carousel";
import { AlarmRowsComponent } from './components/alarms/alarm-rows/alarm-rows.component';
import { AlarmRowComponent } from './components/alarms/alarm-row/alarm-row.component';
import { AlarmFilterDialogComponent } from './components/alarms/alarm-filter-dialog/alarm-filter-dialog.component';
import { ChartViewComponent } from './pages/chart-view/chart-view.component';
import { ReactiveFormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { MessageRowsComponent } from './components/messages/message-rows/message-rows.component';
import { MessageRowComponent } from './components/messages/message-row/message-row.component';

@NgModule({
  declarations: [HomeComponent, AllRealEstatesViesComponent, DetailRealEstateComponent, BasicRealEstateInfoComponent, RealEstateSortingButtonsComponent, RealEstateRowsComponent, RealEstateRowComponent, OwnershipDataRealEstateComponent, EditTenantsReDialogComponent, OwnerDataComponent, TenantDataComponent, DevicesTabComponent, DeviceDetailsDialogComponent, AlarmRowsComponent, AlarmRowComponent, AlarmFilterDialogComponent, ChartViewComponent, MessageRowsComponent, MessageRowComponent],
  imports: [
    CommonModule,
    MaterialModule,
    CarouselModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(UserRoutes)
  ],
  providers:[DatePipe],
})
export class UserModule { }
