import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersSortingButtonsComponent } from './users-sorting-buttons.component';

describe('UsersSortingButtonsComponent', () => {
  let component: UsersSortingButtonsComponent;
  let fixture: ComponentFixture<UsersSortingButtonsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsersSortingButtonsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsersSortingButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
