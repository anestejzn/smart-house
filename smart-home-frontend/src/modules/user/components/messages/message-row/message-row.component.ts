import { Component, Input, OnInit } from '@angular/core';
import { Message } from 'src/modules/shared/model/message';

@Component({
  selector: 'app-message-row',
  templateUrl: './message-row.component.html',
  styleUrls: ['./message-row.component.scss', '../../alarms/alarm-row/alarm-row.component.scss']
})
export class MessageRowComponent implements OnInit {
  @Input() message: Message;

  constructor() { }

  ngOnInit(): void {
  }

}
