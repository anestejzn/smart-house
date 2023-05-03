import { Component, Input, OnInit } from '@angular/core';
import { RealEstate } from 'src/modules/admin/model/real-estate';

@Component({
  selector: 'app-ownership-data-real-estate',
  templateUrl: './ownership-data-real-estate.component.html',
  styleUrls: ['./ownership-data-real-estate.component.scss']
})
export class OwnershipDataRealEstateComponent implements OnInit {

  @Input() realEstate: RealEstate;

  constructor() { }

  ngOnInit(): void {
  }

}
