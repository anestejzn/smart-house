import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditOwnershipReDialogComponent } from './edit-ownership-re-dialog.component';

describe('EditOwnershipReDialogComponent', () => {
  let component: EditOwnershipReDialogComponent;
  let fixture: ComponentFixture<EditOwnershipReDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditOwnershipReDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditOwnershipReDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
