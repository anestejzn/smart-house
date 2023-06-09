import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';
import { User } from 'src/modules/shared/model/user';

@Component({
  selector: 'app-user-rows',
  templateUrl: './user-rows.component.html',
  styleUrls: ['./user-rows.component.scss']
})
export class UserRowsComponent implements OnChanges {

  @Input() users: User[];
  @Output() changedUserEvent = new EventEmitter();

  selectedUser: User = null;

  constructor() { }

  ngOnChanges(): void {
    this.selectedUser = null;
  }

  changeSelectedUser(user: User): void {
    this.selectedUser = user;
    this.changedUserEvent.emit(user);
  }

}
