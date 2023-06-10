import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditDeviceDialogComponent } from './edit-device-dialog.component';

describe('EditDeviceDialogComponent', () => {
  let component: EditDeviceDialogComponent;
  let fixture: ComponentFixture<EditDeviceDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditDeviceDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditDeviceDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
