import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Log } from '../../model/log';

@Component({
  selector: 'app-log-search',
  templateUrl: './log-search.component.html',
  styleUrls: ['./log-search.component.scss']
})
export class LogSearchComponent implements OnInit {
  @Input() noLogs: boolean;
  @Output() checkedRadioButtons = new EventEmitter<any>();
  showDateTime = "All";
  showLogLevel = "All";

  showDateAndTime = ['Today', 'This week', 'Two weeks', 'This month', 'All'];

  showLogLevels = ['INFO', 'WARN', 'ERROR', 'FATAL', 'All'];
  constructor() { }

  ngOnInit(): void {
  }


  selectLogLevel(selected: string){
    this.showLogLevel = selected;
    this.checkedRadioButtons.emit({logLevel: this.showLogLevel,
      dateTime: this.showDateTime});
  }


  selectDateTime(selected: string){
    this.showDateTime = selected;
    this.checkedRadioButtons.emit({logLevel: this.showLogLevel,
      dateTime: this.showDateTime});
  }

}
