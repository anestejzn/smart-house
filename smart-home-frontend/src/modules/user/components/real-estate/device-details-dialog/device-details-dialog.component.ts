import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { deviceDialogData } from 'src/modules/shared/model/device';
import { Message } from 'src/modules/shared/model/message';
import { MessageService } from 'src/modules/user/service/message-service/message.service';

@Component({
  selector: 'app-device-details-dialog',
  templateUrl: './device-details-dialog.component.html',
  styleUrls: ['./device-details-dialog.component.scss']
})
export class DeviceDetailsDialogComponent implements OnInit, OnDestroy {

  constructor(@Inject(MAT_DIALOG_DATA) public data: deviceDialogData,
            private messageService: MessageService) { }

  messages: Message[] = [];
  messagesSubscription: Subscription;

  ngOnInit(): void {
    this.messagesSubscription = this.messageService.getMessagesForDevice(this.data.device.id).subscribe(
      res => {
        this.messages = res;
      },
      err => {
        console.log(err);
      }
    )

  }

  ngOnDestroy(): void {
      if (this.messagesSubscription) {
        this.messagesSubscription.unsubscribe();
      }
  }

}
