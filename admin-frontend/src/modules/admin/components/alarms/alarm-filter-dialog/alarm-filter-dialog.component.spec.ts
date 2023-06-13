import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlarmFilterDialogComponent } from './alarm-filter-dialog.component';

describe('AlarmFilterDialogComponent', () => {
  let component: AlarmFilterDialogComponent;
  let fixture: ComponentFixture<AlarmFilterDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AlarmFilterDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AlarmFilterDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
