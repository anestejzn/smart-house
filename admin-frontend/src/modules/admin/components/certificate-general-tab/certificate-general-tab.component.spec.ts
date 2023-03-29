import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificateGeneralTabComponent } from './certificate-general-tab.component';

describe('CertificateGeneralTabComponent', () => {
  let component: CertificateGeneralTabComponent;
  let fixture: ComponentFixture<CertificateGeneralTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CertificateGeneralTabComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CertificateGeneralTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
