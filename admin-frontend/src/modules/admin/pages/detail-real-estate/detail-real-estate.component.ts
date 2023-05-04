import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RealEstateService } from '../../service/real-estate/real-estate.service';
import { Subscription } from 'rxjs';
import { RealEstate } from '../../model/real-estate';
import { ToastrService } from 'ngx-toastr';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from 'src/modules/shared/components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-detail-real-estate',
  templateUrl: './detail-real-estate.component.html',
  styleUrls: ['./detail-real-estate.component.scss']
})
export class DetailRealEstateComponent implements OnInit, OnDestroy {

  id: string;
  realEstate: RealEstate;
  realEstateSubscription: Subscription;
  realEstateDeletionSubscription: Subscription;

  constructor(private route: ActivatedRoute,
              private realEstateService: RealEstateService,
              private toast: ToastrService,
              private confirmationDialog: MatDialog,
              private router: Router
  ) {
    this.realEstate = null;
    this.id = '';
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    if (this.id) {
      this.loadRealEstateData();
    }
  }

  loadRealEstateData(): void {
    this.realEstateSubscription = this.realEstateService.getRealEstate(this.id).subscribe(
      res => {
        if (res) {
          this.realEstate = res;
        }
      },
      err => {
        this.toast.error(`Real estate does not exist!`, 'Error happened!');
      }
    )
  }

  updatedData(): void {
    this.loadRealEstateData();
  }

  deleteRealEstate(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.minWidth = '15rem'; // Set the width of the dialog
    dialogConfig.minHeight = '15rem'; // Set the height of the dialog
    dialogConfig.maxHeight = '90vh'; // Set the maximum height of the dialog
    dialogConfig.maxWidth = '90vw'; // Set the maximum width of the dialog
    dialogConfig.data = 'Are you sure that you want to delete this real estate?';
    const dialogRef = this.confirmationDialog.open(ConfirmationDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.realEstateDeletionSubscription = this.realEstateService.delete(this.realEstate.id).subscribe(
          res => {
            this.toast.success(`Real estate is successfully deleted!`, 'Success!');
            this.router.navigate(['/smart-home/admin/all-real-estates']);
          },
          err => {
            this.toast.error(`Real estate cannot be deleted!`, 'Error happened!');
          }
        )
      }
    });
  }

  ngOnDestroy(): void {
    if (this.realEstateSubscription) {
      this.realEstateSubscription.unsubscribe();
    }

    if (this.realEstateDeletionSubscription) {
      this.realEstateDeletionSubscription.unsubscribe();
    }
  }

}
