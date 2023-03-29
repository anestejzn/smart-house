import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllCertificatesViewComponent } from './all-certificates-view.component';

describe('AllCertificatesViewComponent', () => {
  let component: AllCertificatesViewComponent;
  let fixture: ComponentFixture<AllCertificatesViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllCertificatesViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllCertificatesViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
