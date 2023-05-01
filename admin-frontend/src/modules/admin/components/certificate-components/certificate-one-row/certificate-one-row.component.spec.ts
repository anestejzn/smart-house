import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificateOneRowComponent } from './certificate-one-row.component';

describe('CertificateOneRowComponent', () => {
  let component: CertificateOneRowComponent;
  let fixture: ComponentFixture<CertificateOneRowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CertificateOneRowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CertificateOneRowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
