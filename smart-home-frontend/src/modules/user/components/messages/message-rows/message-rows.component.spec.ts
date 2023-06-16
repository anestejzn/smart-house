import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MessageRowsComponent } from './message-rows.component';

describe('MessageRowsComponent', () => {
  let component: MessageRowsComponent;
  let fixture: ComponentFixture<MessageRowsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MessageRowsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MessageRowsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
