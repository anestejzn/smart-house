import { Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { Subscription } from 'rxjs';
import { RealEstateService } from 'src/modules/admin/service/real-estate/real-estate.service';
import { User } from 'src/modules/shared/model/user';
import { UserService } from 'src/modules/shared/service/user-service/user.service';

@Component({
  selector: 'app-real-estate-sorting-buttons',
  templateUrl: './real-estate-sorting-buttons.component.html',
  styleUrls: ['./real-estate-sorting-buttons.component.scss']
})
export class RealEstateSortingButtonsComponent implements OnInit, OnDestroy {

  @Output() changedSortByNameEvent = new EventEmitter();
  @Output() changedFilterBySquareAreaEvent = new EventEmitter();
  @Output() changedOwnerEvent = new EventEmitter();

  sortByName = [
    { name: 'Asceding', checked: true, asc: true },
    { name: 'Desceding', checked: false, asc: false },
  ];

  filterBySquareArea = [
    { name: 'All', checked: true, returnValue: '10:600' },
    { name: '<=50m2', checked: false, returnValue: '10:50' },
    { name: '>50m2 && <=100m2', checked: false, returnValue: '50:100' },
    { name: '>100m2 && <=200m2', checked: false, returnValue: '100:200' },
    { name: '>200m2', checked: false, returnValue: '200:600' },
  ];

  owners: String[] = [
    'All'
  ];

  selectedOwner: string;
  allActiveRegularUsers: User[];
  allActiveRegularUsersSubscription: Subscription;

  constructor(private userService: UserService) {
    this.selectedOwner = 'All';
  }

  ngOnInit(): void {
    this.allActiveRegularUsersSubscription =this.userService.getAllActiveRegularUsers().subscribe(
      res => {
        this.allActiveRegularUsers = res;
        for (let o of this.allActiveRegularUsers) {
          this.owners.push(o.email);
        }
      }
    )
  }

  selectSortByName(value: boolean): void {
    this.changedSortByNameEvent.emit(value);
  }

  selectFilterBySqArea(value: string): void {
    this.changedFilterBySquareAreaEvent.emit(value);
  }

  changedOwner(): void {
    if (this.selectedOwner === 'All') {
      this.changedOwnerEvent.emit(-1);
    } else {
      for (let o of this.allActiveRegularUsers) {
        if (o.email === this.selectedOwner) {
          this.changedOwnerEvent.emit(o.id);
        }
      }
    }
  }

  ngOnDestroy(): void {
    if (this.allActiveRegularUsersSubscription) {
      this.allActiveRegularUsersSubscription.unsubscribe();
    }  
  }

}
