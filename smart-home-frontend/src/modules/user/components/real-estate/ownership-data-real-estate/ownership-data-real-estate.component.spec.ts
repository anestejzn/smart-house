import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnershipDataRealEstateComponent } from './ownership-data-real-estate.component';

describe('OwnershipDataRealEstateComponent', () => {
  let component: OwnershipDataRealEstateComponent;
  let fixture: ComponentFixture<OwnershipDataRealEstateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OwnershipDataRealEstateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnershipDataRealEstateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
