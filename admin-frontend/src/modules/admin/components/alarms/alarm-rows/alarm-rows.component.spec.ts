import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlarmRowsComponent } from './alarm-rows.component';

describe('AlarmRowsComponent', () => {
  let component: AlarmRowsComponent;
  let fixture: ComponentFixture<AlarmRowsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AlarmRowsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AlarmRowsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
