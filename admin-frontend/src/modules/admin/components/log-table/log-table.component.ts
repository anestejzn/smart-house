import { Component, Input, OnInit } from '@angular/core';
import { LogDataSource } from '../../model/log-data-source';
import { Subscription } from 'rxjs';
import { LogsService } from '../../service/log-service/logs.service';
import { Log } from '../../model/log';

@Component({
  selector: 'app-log-table',
  templateUrl: './log-table.component.html',
  styleUrls: ['./log-table.component.scss']
})
export class LogTableComponent implements OnInit {
  @Input() noLogs: boolean;
  @Input() logs: LogDataSource;
  displayedColumns: string[] = ['dateTime', 'logLevel', 'logMessage'];

  constructor(private logService: LogsService) { }

ngOnInit(): void {
  console.log(this.noLogs);
}
formatDate(date: string){
  const newDate = new Date(date);
  const formattedDate = `${newDate.getDate().toString().padStart(2, '0')}.${(newDate.getMonth() + 1).toString().padStart(2, '0')}.${newDate.getFullYear()}.`;
  const formattedTime = `${newDate.getHours().toString().padStart(2, '0')}:${newDate.getMinutes().toString().padStart(2, '0')}:${newDate.getSeconds().toString().padStart(2, '0')}`;
  
  const formattedDateTime = `${formattedDate} ${formattedTime}`;
  return formattedDateTime;
}

}
