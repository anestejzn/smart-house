import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserRowsComponent } from './user-rows.component';

describe('UserRowsComponent', () => {
  let component: UserRowsComponent;
  let fixture: ComponentFixture<UserRowsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserRowsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserRowsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
