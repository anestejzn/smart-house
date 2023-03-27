import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';

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
}
