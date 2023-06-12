import { Component, Input, OnInit } from '@angular/core';
import { Alarm } from 'src/modules/shared/model/alarm';

@Component({
  selector: 'app-alarm-row',
  templateUrl: './alarm-row.component.html',
  styleUrls: ['./alarm-row.component.scss']
})
export class AlarmRowComponent implements OnInit {
  @Input() alarm: Alarm;

  constructor() { }

  ngOnInit(): void {
  }

}
