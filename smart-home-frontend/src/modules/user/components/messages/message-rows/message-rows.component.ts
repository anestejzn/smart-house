import { Component, Input, OnInit } from '@angular/core';
import { Message } from 'src/modules/shared/model/message';

@Component({
  selector: 'app-message-rows',
  templateUrl: './message-rows.component.html',
  styleUrls: ['./message-rows.component.scss', '../../alarms/alarm-rows/alarm-rows.component.scss']
})
export class MessageRowsComponent implements OnInit {

  @Input() messages: Message[];

  constructor() { }

  ngOnInit(): void {
  }

}
