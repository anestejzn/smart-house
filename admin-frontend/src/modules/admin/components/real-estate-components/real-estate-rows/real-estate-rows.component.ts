import { Component, Input, OnInit } from '@angular/core';
import { RealEstateView } from 'src/modules/admin/model/real-estate';

@Component({
  selector: 'app-real-estate-rows',
  templateUrl: './real-estate-rows.component.html',
  styleUrls: ['./real-estate-rows.component.scss', '../../certificate-components/certificate-rows/certificate-rows.component.scss']
})
export class RealEstateRowsComponent implements OnInit {

  @Input() realEstates: RealEstateView[];

  constructor() { }

  ngOnInit(): void {
  }

}
