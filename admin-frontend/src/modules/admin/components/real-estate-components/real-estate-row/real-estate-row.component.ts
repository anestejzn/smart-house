import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RealEstateView } from 'src/modules/admin/model/real-estate';

@Component({
  selector: 'app-real-estate-row',
  templateUrl: './real-estate-row.component.html',
  styleUrls: ['./real-estate-row.component.scss', '../../certificate-components/certificate-one-row/certificate-one-row.component.scss']
})
export class RealEstateRowComponent implements OnInit {

  @Input() realEstate: RealEstateView;
  @Input() index: number;

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  goToDetailsPage(): void {
    this.router.navigate([`/smart-home/admin/real-estate/${this.realEstate.id}`]);
  }

}
