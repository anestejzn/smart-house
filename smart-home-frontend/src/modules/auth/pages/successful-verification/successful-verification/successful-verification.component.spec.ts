import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuccessfulVerificationComponent } from './successful-verification.component';

describe('SuccessfulVerificationComponent', () => {
  let component: SuccessfulVerificationComponent;
  let fixture: ComponentFixture<SuccessfulVerificationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SuccessfulVerificationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SuccessfulVerificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
