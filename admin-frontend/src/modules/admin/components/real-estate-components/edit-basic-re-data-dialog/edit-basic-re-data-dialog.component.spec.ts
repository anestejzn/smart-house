import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditBasicReDataDialogComponent } from './edit-basic-re-data-dialog.component';

describe('EditBasicReDataDialogComponent', () => {
  let component: EditBasicReDataDialogComponent;
  let fixture: ComponentFixture<EditBasicReDataDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditBasicReDataDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditBasicReDataDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
