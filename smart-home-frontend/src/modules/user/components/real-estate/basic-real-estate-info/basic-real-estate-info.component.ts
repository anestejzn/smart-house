import { Component, Input, OnInit } from '@angular/core';
import { RealEstate } from 'src/modules/shared/model/real-estate';

@Component({
  selector: 'app-basic-real-estate-info',
  templateUrl: './basic-real-estate-info.component.html',
  styleUrls: ['./basic-real-estate-info.component.scss']
})
export class BasicRealEstateInfoComponent implements OnInit {

  @Input() realEstate: RealEstate;

  constructor() { }

  ngOnInit(): void {
  }

}
