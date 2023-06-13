import { DataSource } from "@angular/cdk/collections";
import { LogTableComponent } from "../components/log-table/log-table.component";
import { Observable, ReplaySubject } from "rxjs";
import { Log } from "./log";

export class LogDataSource extends DataSource<Log> {
    private _dataStream = new ReplaySubject<Log[]>();
  
    constructor(initialData: Log[]) {
      super();
      this.setData(initialData);
    }
  
    connect(): Observable<Log[]> {
      return this._dataStream;
    }
  
    disconnect() {}
  
    setData(data: Log[]) {
      this._dataStream.next(data);
    }
  }