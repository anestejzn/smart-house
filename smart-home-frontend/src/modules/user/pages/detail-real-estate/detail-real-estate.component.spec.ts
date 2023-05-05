import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailRealEstateComponent } from './detail-real-estate.component';

describe('DetailRealEstateComponent', () => {
  let component: DetailRealEstateComponent;
  let fixture: ComponentFixture<DetailRealEstateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetailRealEstateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailRealEstateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
