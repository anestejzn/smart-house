import { DataSource } from "@angular/cdk/collections";
import { LogTableComponent } from "../components/log-table/log-table.component";
import { Observable, ReplaySubject } from "rxjs";
import { Log } from "./log";
import { Alarm } from "src/modules/shared/model/alarm";

export class AlarmDataSource extends DataSource<Alarm> {
    private _dataStream = new ReplaySubject<Alarm[]>();
  
    constructor(initialData: Alarm[]) {
      super();
      this.setData(initialData);
    }
  
    connect(): Observable<Alarm[]> {
      return this._dataStream;
    }
  
    disconnect() {}
  
    setData(data: Alarm[]) {
      this._dataStream.next(data);
    }
  }