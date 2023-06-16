import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Message } from 'src/modules/shared/model/message';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private configService: ConfigService, private http: HttpClient) { }

  getMessagesForDevice(id: number) {
     return this.http.get<Message[]>(this.configService.getMessagesForDeviceURL(id));
  }

}
