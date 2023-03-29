import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificatesChainTabComponent } from './certificates-chain-tab.component';

describe('CertificatesChainTabComponent', () => {
  let component: CertificatesChainTabComponent;
  let fixture: ComponentFixture<CertificatesChainTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CertificatesChainTabComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CertificatesChainTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
