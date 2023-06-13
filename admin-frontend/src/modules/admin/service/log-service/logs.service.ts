import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { Log } from '../../model/log';
import { LogFilterRequest } from '../../model/log-filter-request';

@Injectable({
  providedIn: 'root'
})
export class LogsService {

  constructor(private configService: ConfigService, private http: HttpClient) { }

  getAllLogs(){
    return this.http.get<Log[]>(
      this.configService.LOG_URL
    );
  }

  filterLogs(logFilterRequest: LogFilterRequest){
    return this.http.put<Log[]>(this.configService.FILTER_LOGS_URL, logFilterRequest);
  }
}
