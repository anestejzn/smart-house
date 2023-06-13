import { Component, Input, OnInit } from '@angular/core';
import { AlarmDataSource } from '../../model/alarm-data-source';

@Component({
  selector: 'app-alarms-table',
  templateUrl: './alarms-table.component.html',
  styleUrls: ['./alarms-table.component.scss']
})
export class AlarmsTableComponent implements OnInit {

  @Input() alarms: AlarmDataSource;
  displayedColumns: string[] = ['dateTime', 'message'];

  constructor() { }

  ngOnInit(): void {
  }

  formatDate(date: string){
    const newDate = new Date(date);
    const formattedDate = `${newDate.getDate().toString().padStart(2, '0')}.${(newDate.getMonth() + 1).toString().padStart(2, '0')}.${newDate.getFullYear()}.`;
    const formattedTime = `${newDate.getHours().toString().padStart(2, '0')}:${newDate.getMinutes().toString().padStart(2, '0')}:${newDate.getSeconds().toString().padStart(2, '0')}`;
    
    const formattedDateTime = `${formattedDate} ${formattedTime}`;
    return formattedDateTime;
  }

}
