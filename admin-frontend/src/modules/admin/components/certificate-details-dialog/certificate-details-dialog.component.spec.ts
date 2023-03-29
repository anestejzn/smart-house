import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificateDetailsDialogComponent } from './certificate-details-dialog.component';

describe('CertificateDetailsDialogComponent', () => {
  let component: CertificateDetailsDialogComponent;
  let fixture: ComponentFixture<CertificateDetailsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CertificateDetailsDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CertificateDetailsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
