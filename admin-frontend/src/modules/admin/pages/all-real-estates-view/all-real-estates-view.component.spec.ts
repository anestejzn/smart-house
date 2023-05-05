import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllRealEstatesViewComponent } from './all-real-estates-view.component';

describe('AllRealEstatesViewComponent', () => {
  let component: AllRealEstatesViewComponent;
  let fixture: ComponentFixture<AllRealEstatesViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllRealEstatesViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllRealEstatesViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
