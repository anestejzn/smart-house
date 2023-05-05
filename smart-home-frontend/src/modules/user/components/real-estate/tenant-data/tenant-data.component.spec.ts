import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TenantDataComponent } from './tenant-data.component';

describe('TenantDataComponent', () => {
  let component: TenantDataComponent;
  let fixture: ComponentFixture<TenantDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TenantDataComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TenantDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
