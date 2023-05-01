import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-certificates-sorting-buttons',
  templateUrl: './certificates-sorting-buttons.component.html',
  styleUrls: ['./certificates-sorting-buttons.component.scss']
})
export class CertificatesSortingButtonsComponent implements OnInit {

  @Output() changedTypeEvent = new EventEmitter();
  @Output() changedValidityEvent = new EventEmitter();

  sortByType = [
    { name: 'All', checked: true },
    { name: 'Root', checked: false },
    { name: 'Intermediate', checked: false },
    { name: 'Leaf', checked: false }
  ];

  sortByValidity = [
    { name: 'Valid', checked: true },
    { name: 'Invalid', checked: false }
  ];

  constructor() { }

  ngOnInit(): void {
  }

  selectSortType(value: string) {
    this.changedTypeEvent.emit(value.toUpperCase());
  }

  selectSortValidity(value: string) {
    this.changedValidityEvent.emit(value.toUpperCase());
  }

}
