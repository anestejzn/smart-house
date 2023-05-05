import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateCertificateDialogComponent } from './create-certificate-dialog.component';

describe('CreateCertificateDialogComponent', () => {
  let component: CreateCertificateDialogComponent;
  let fixture: ComponentFixture<CreateCertificateDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateCertificateDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateCertificateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
