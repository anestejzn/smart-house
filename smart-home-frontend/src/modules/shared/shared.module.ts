import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { CustomInterceptor } from './interceptors/custom.interceptor';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule,
    ToastrModule.forRoot({
      positionClass: 'toast-bottom-left',
      preventDuplicates: true,
      closeButton: true,
    })
  ],
  providers:[
    { provide: HTTP_INTERCEPTORS, useClass: CustomInterceptor, multi: true },]
})
export class SharedModule { }
