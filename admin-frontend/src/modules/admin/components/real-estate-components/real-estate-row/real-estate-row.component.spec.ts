import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RealEstateRowComponent } from './real-estate-row.component';

describe('RealEstateRowComponent', () => {
  let component: RealEstateRowComponent;
  let fixture: ComponentFixture<RealEstateRowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RealEstateRowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RealEstateRowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
