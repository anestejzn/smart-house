import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RealEstateRowsComponent } from './real-estate-rows.component';

describe('RealEstateRowsComponent', () => {
  let component: RealEstateRowsComponent;
  let fixture: ComponentFixture<RealEstateRowsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RealEstateRowsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RealEstateRowsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
