import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BasicRealEstateInfoComponent } from './basic-real-estate-info.component';

describe('BasicRealEstateInfoComponent', () => {
  let component: BasicRealEstateInfoComponent;
  let fixture: ComponentFixture<BasicRealEstateInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BasicRealEstateInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BasicRealEstateInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
