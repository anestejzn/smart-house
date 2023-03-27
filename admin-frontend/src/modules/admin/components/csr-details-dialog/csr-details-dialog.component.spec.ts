import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CsrDetailsDialogComponent } from './csr-details-dialog.component';

describe('CsrDetailsDialogComponent', () => {
  let component: CsrDetailsDialogComponent;
  let fixture: ComponentFixture<CsrDetailsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CsrDetailsDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CsrDetailsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
