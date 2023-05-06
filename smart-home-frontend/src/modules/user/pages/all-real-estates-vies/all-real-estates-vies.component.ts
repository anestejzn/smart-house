import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { RealEstateView } from 'src/modules/shared/model/real-estate';
import { RealEstateService } from '../../service/real-estate.service';
import {AuthService} from '../../../auth/service/auth/auth.service'
import { User } from 'src/modules/shared/model/user';

@Component({
  selector: 'app-all-real-estates-vies',
  templateUrl: './all-real-estates-vies.component.html',
  styleUrls: ['./all-real-estates-vies.component.scss']
})
export class AllRealEstatesViesComponent implements OnInit, OnDestroy {

  realEstates: RealEstateView[] = [];
  realEstateSubscription: Subscription;

  ascending: boolean = true;
  range: string = "10:600";
  currentUser: User;
  loggedUserSubscription: Subscription;

  constructor(
    private realEstateService: RealEstateService,
    private authService: AuthService,
  ) { }

  ngOnInit(): void {
    this.loadRealEstates();
  }

  loadRealEstates(): void {
    this.loggedUserSubscription = this.authService.getSubjectCurrentUser().subscribe(
      res => {
        if (res) {
          this.currentUser = res;
        }
      }
    )

    this.realEstateSubscription = this.realEstateService.filterRealEstates(this.ascending, this.range, this.currentUser.id, this.currentUser.role.roleName).subscribe(
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

  ngOnDestroy(): void {
    if (this.realEstateSubscription) {
      this.realEstateSubscription.unsubscribe();
    }
  }

}
