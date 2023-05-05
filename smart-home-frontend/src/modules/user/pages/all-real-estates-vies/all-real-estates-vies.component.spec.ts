import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllRealEstatesViesComponent } from './all-real-estates-vies.component';

describe('AllRealEstatesViesComponent', () => {
  let component: AllRealEstatesViesComponent;
  let fixture: ComponentFixture<AllRealEstatesViesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllRealEstatesViesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllRealEstatesViesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
