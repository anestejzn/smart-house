import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTenantsReDialogComponent } from './edit-tenants-re-dialog.component';

describe('EditTenantsReDialogComponent', () => {
  let component: EditTenantsReDialogComponent;
  let fixture: ComponentFixture<EditTenantsReDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditTenantsReDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditTenantsReDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
