import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllUsersViewComponent } from './all-users-view.component';

describe('AllUsersViewComponent', () => {
  let component: AllUsersViewComponent;
  let fixture: ComponentFixture<AllUsersViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllUsersViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllUsersViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
