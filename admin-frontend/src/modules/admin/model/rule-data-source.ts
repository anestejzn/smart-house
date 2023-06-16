import { DataSource } from "@angular/cdk/collections";
import { Observable, ReplaySubject } from "rxjs";
import { Rule } from "./rule";

export class RuleDataSource extends DataSource<Rule> {
    private _dataStream = new ReplaySubject<Rule[]>();
  
    constructor(initialData: Rule[]) {
      super();
      this.setData(initialData);
    }
  
    connect(): Observable<Rule[]> {
      return this._dataStream;
    }
  
    disconnect() {}
  
    setData(data: Rule[]) {
      this._dataStream.next(data);
    }
  }