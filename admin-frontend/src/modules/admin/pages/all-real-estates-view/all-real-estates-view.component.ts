import { Component, OnDestroy, OnInit } from '@angular/core';
import { RealEstateView } from '../../model/real-estate-view';
import { RealEstateService } from '../../service/real-estate/real-estate.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-all-real-estates-view',
  templateUrl: './all-real-estates-view.component.html',
  styleUrls: ['./all-real-estates-view.component.scss']
})
export class AllRealEstatesViewComponent implements OnInit, OnDestroy {

  realEstates: RealEstateView[];
  realEstateSubscription: Subscription;

  ascending: boolean = true;
  range: string = "10:600";
  selectedOwner: number = -1;

  constructor(private realEstateService: RealEstateService) {
    this.realEstates = [];
  }

  ngOnInit(): void {
    this.loadRealEstates();
  }

  loadRealEstates(): void {
    this.realEstateSubscription = this.realEstateService.filterRealEstates(this.ascending, this.range, this.selectedOwner).subscribe(
      res => {
        if (res) {
          this.realEstates = res;
        }
      }
    )
  }

  changeSortByName(asc: boolean): void {
    this.ascending = asc;
    this.loadRealEstates();
  }

  changeFilterBySqArea(range: string): void {
    this.range = range;
    this.loadRealEstates();
  }

  changedOwner(id: number): void {
    this.selectedOwner = id;
    this.loadRealEstates();
  }

  ngOnDestroy(): void {
    if (this.realEstateService) {
      this.realEstateSubscription.unsubscribe();
    }
  }

}
