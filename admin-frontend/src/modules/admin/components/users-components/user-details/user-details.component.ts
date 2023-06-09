import { Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output } from '@angular/core';
import { User } from 'src/modules/shared/model/user';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnChanges {

  @Input() selectedUser: User;
  @Input() isBlocked: boolean;
  @Output() blockEvent = new EventEmitter();
  @Output() unblockEvent = new EventEmitter();

  constructor() { }

  ngOnChanges() {
  }

  performAction(): void {
    if (this.isBlocked) {
      this.unblockEvent.emit(this.selectedUser.id);
    } else {
      this.blockEvent.emit(this.selectedUser.id);
    }
  }
}
