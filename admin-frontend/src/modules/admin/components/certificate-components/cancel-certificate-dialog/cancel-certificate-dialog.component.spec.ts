import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CancelCertificateDialogComponent } from './cancel-certificate-dialog.component';

describe('CancelCertificateDialogComponent', () => {
  let component: CancelCertificateDialogComponent;
  let fixture: ComponentFixture<CancelCertificateDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CancelCertificateDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CancelCertificateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
