import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-users-sorting-buttons',
  templateUrl: './users-sorting-buttons.component.html',
  styleUrls: ['./users-sorting-buttons.component.scss']
})
export class UsersSortingButtonsComponent implements OnInit {

  @Output() changedSortByNameEvent = new EventEmitter();
  @Output() changedFilterByAccStatus = new EventEmitter();

  sortByName = [
    { name: 'Asceding', checked: true, asc: true },
    { name: 'Desceding', checked: false, asc: false },
  ];

  filterByAccountStatus = [
    { name: 'Active', checked: true, returnValue: 'ACTIVE' },
    { name: 'Blocked', checked: false, returnValue: 'BLOCKED' },
    { name: 'Locked', checked: false, returnValue: 'LOCKED' }, 
  ];

  constructor() { }

  ngOnInit(): void {
  }

  selectSortByName(value: boolean): void {
    this.changedSortByNameEvent.emit(value);
  }

  selectFilterByStatus(value: string): void {
    this.changedFilterByAccStatus.emit(value);
  }

}
