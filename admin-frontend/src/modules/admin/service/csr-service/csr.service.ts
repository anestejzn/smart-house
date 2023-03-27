import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { Csr } from '../../model/csr';

@Injectable({
  providedIn: 'root'
})
export class CsrService {

  constructor(private configService: ConfigService, private http: HttpClient) { }

  sendCsr(email: string){
    return this.http.post<void>(
      this.configService.getCreateCsrUrl(),
      {
        "email": email
      }
    );
  }

  getAllPendingCsrs(){
    return this.http.get<Csr[]>(
      this.configService.getAllPendingCsrsUrl()
    );
  }

  rejectCsr(id: number){
    return this.http.post<void>(this.configService.rejectCsrUrl(), id);
  }
}
