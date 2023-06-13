import { Component, OnInit } from '@angular/core';
import { LogDataSource } from '../../model/log-data-source';
import { Subscription } from 'rxjs';
import { LogsService } from '../../service/log-service/logs.service';

@Component({
  selector: 'app-logs-view',
  templateUrl: './logs-view.component.html',
  styleUrls: ['./logs-view.component.scss']
})
export class LogsViewComponent implements OnInit {
  logs = [];
  logSubscription: Subscription;
  dataSource: LogDataSource;
  noLogs = false;
  messageRegex = '';

  constructor(private logService: LogsService) { }

  ngOnInit(): void {
    this.getAllLogs();
  }

  getAllLogs(){
    this.logSubscription = this.logService.getAllLogs().subscribe(
      logs => {
        if(logs.length === 0){
          this.noLogs = true;
        }
        else{
          this.dataSource = new LogDataSource(logs);
          this.logs = logs;
        }
      }
    )
  }

  filterLogs(event:any){
    const logFilterRequest = {
      logLevel: event.logLevel,
      dateTime: event.dateTime,
      regex: this.messageRegex
    };

    this.logService.filterLogs(logFilterRequest).subscribe(
      logs => {
        if(logs.length === 0){
          this.noLogs = true;
          
        }
        else{
          this.dataSource = new LogDataSource(logs);
          this.logs = logs;
        }
      }
    );

  }

}
