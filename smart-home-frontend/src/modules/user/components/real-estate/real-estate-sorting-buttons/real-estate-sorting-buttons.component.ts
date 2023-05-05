import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-real-estate-sorting-buttons',
  templateUrl: './real-estate-sorting-buttons.component.html',
  styleUrls: ['./real-estate-sorting-buttons.component.scss']
})
export class RealEstateSortingButtonsComponent implements OnInit {

  @Output() changedSortByNameEvent = new EventEmitter();
  @Output() changedFilterBySquareAreaEvent = new EventEmitter();

  sortByName = [
    { name: 'Asceding', checked: true, asc: true },
    { name: 'Desceding', checked: false, asc: false },
  ];

  filterBySquareArea = [
    { name: 'All', checked: true, returnValue: '10:600' },
    { name: '<=50m2', checked: false, returnValue: '10:50' },
    { name: '>50m2 && <=100m2', checked: false, returnValue: '50:100' },
    { name: '>100m2 && <=200m2', checked: false, returnValue: '100:200' },
    { name: '>=200m2', checked: false, returnValue: '200:600' },
  ];

  constructor() { }

  ngOnInit(): void {
  }

  selectSortByName(value: boolean): void {
    this.changedSortByNameEvent.emit(value);
  }

  selectFilterBySqArea(value: string): void {
    this.changedFilterBySquareAreaEvent.emit(value);
  }

}
