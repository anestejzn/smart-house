import { Observable, ReplaySubject } from "rxjs";
import { Csr } from "./csr";
import {DataSource} from '@angular/cdk/collections';

export class CsrDataSource extends DataSource<Csr> {
    private _dataStream = new ReplaySubject<Csr[]>();
  
    constructor(initialData: Csr[]) {
      super();
      this.setData(initialData);
    }
  
    connect(): Observable<Csr[]> {
      return this._dataStream;
    }
  
    disconnect() {}
  
    setData(data: Csr[]) {
      this._dataStream.next(data);
    }
  }