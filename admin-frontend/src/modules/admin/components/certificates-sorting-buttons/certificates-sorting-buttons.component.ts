import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-certificates-sorting-buttons',
  templateUrl: './certificates-sorting-buttons.component.html',
  styleUrls: ['./certificates-sorting-buttons.component.scss']
})
export class CertificatesSortingButtonsComponent implements OnInit {

  @Output() changedTypeEvent = new EventEmitter();
  @Output() changedValidityEvent = new EventEmitter();

  valid: boolean = true;

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
    this.changedTypeEvent.emit(value);
  }

  selectSortValidity(value: string) {
    this.valid = (value === "Invalid") ? false : true;
    this.changedValidityEvent.emit(this.valid);
  }

}
