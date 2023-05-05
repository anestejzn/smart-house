import { Component, OnDestroy, OnInit } from '@angular/core';
import { RealEstateView } from '../../model/real-estate';
import { RealEstateService } from '../../service/real-estate/real-estate.service';
import { Subscription } from 'rxjs';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AddNewRealEstateDialogComponent } from '../../components/real-estate-components/add-new-real-estate-dialog/add-new-real-estate-dialog.component';

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

  constructor(private realEstateService: RealEstateService,
              private addNewDialog: MatDialog
  ) {
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

  addNewRealEstate(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.minWidth = '48rem'; // Set the width of the dialog
    dialogConfig.minHeight = '35rem'; // Set the height of the dialog
    dialogConfig.maxHeight = '90vh'; // Set the maximum height of the dialog
    dialogConfig.maxWidth = '90vw'; // Set the maximum width of the dialog
    const dialogRef = this.addNewDialog.open(AddNewRealEstateDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.loadRealEstates();
      }
    });

  }

  ngOnDestroy(): void {
    if (this.realEstateSubscription) {
      this.realEstateSubscription.unsubscribe();
    }
  }

}
