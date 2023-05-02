import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewRealEstateDialogComponent } from './add-new-real-estate-dialog.component';

describe('AddNewRealEstateDialogComponent', () => {
  let component: AddNewRealEstateDialogComponent;
  let fixture: ComponentFixture<AddNewRealEstateDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddNewRealEstateDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddNewRealEstateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
