import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RealEstateSortingButtonsComponent } from './real-estate-sorting-buttons.component';

describe('RealEstateSortingButtonsComponent', () => {
  let component: RealEstateSortingButtonsComponent;
  let fixture: ComponentFixture<RealEstateSortingButtonsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RealEstateSortingButtonsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RealEstateSortingButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
