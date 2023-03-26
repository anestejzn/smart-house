import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '../material/material.module';
import { RootLayoutComponent } from './components/root-layout/root-layout.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { UserModule } from '../user/user.module';
import { CommonModule } from '@angular/common';
import { AuthModule } from '../auth/auth.module';

@NgModule({
  declarations: [
    AppComponent,
    RootLayoutComponent,
    NavBarComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    AuthModule,
    UserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
