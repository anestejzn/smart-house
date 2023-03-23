import { ModuleWithProviders, NgModule, Type } from "@angular/core";


const MaterialConstants: any[] | Type<any> | ModuleWithProviders<{}> = []

@NgModule({
  imports: [MaterialConstants],
  exports: [MaterialConstants]
})
export class MaterialModule { }