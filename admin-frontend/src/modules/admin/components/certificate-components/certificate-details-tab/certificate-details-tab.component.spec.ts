import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificateDetailsTabComponent } from './certificate-details-tab.component';

describe('CertificateDetailsTabComponent', () => {
  let component: CertificateDetailsTabComponent;
  let fixture: ComponentFixture<CertificateDetailsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CertificateDetailsTabComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CertificateDetailsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
