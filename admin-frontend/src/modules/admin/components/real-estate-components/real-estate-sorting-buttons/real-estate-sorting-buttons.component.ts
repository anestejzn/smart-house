import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-real-estate-sorting-buttons',
  templateUrl: './real-estate-sorting-buttons.component.html',
  styleUrls: ['./real-estate-sorting-buttons.component.scss']
})
export class RealEstateSortingButtonsComponent implements OnInit {

  @Output() changedSortByName = new EventEmitter();
  @Output() changedFilterBySquareArea = new EventEmitter();

  sortByName = [
    { name: 'Asceding', checked: true },
    { name: 'Desceding', checked: false },
  ];

  filterBySquareArea = [
    { name: 'All', checked: true },
    { name: '<=50m2', checked: false },
    { name: '>50m2 && <=100m2', checked: false },
    { name: '>100m2 && <=200m2', checked: false },
    { name: '>200m2', checked: false },
  ];

  owners = [
    'All'
  ];

  selectedOwner: string;

  constructor() {
    this.selectedOwner = 'All';
  }

  ngOnInit(): void {
  }

  selectSortByName(value: string) {
    this.changedSortByName.emit(value.toUpperCase());
  }

  selectFilterBySqArea(value: string) {
    this.changedFilterBySquareArea.emit(value.toUpperCase());
  }

}
