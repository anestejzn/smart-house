import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlarmRowComponent } from './alarm-row.component';

describe('AlarmRowComponent', () => {
  let component: AlarmRowComponent;
  let fixture: ComponentFixture<AlarmRowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AlarmRowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AlarmRowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
