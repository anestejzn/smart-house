import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificateRowsComponent } from './certificate-rows.component';

describe('CertificateRowsComponent', () => {
  let component: CertificateRowsComponent;
  let fixture: ComponentFixture<CertificateRowsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CertificateRowsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CertificateRowsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
