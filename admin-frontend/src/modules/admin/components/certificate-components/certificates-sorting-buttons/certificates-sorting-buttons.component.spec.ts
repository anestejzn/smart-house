import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificatesSortingButtonsComponent } from './certificates-sorting-buttons.component';

describe('CertificatesSortingButtonsComponent', () => {
  let component: CertificatesSortingButtonsComponent;
  let fixture: ComponentFixture<CertificatesSortingButtonsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CertificatesSortingButtonsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CertificatesSortingButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
