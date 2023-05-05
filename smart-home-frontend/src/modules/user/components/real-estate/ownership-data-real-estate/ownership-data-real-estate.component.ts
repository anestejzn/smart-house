import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { RealEstate } from 'src/modules/shared/model/real-estate';
import { EditTenantsReDialogComponent } from '../edit-tenants-re-dialog/edit-tenants-re-dialog.component';

@Component({
  selector: 'app-ownership-data-real-estate',
  templateUrl: './ownership-data-real-estate.component.html',
  styleUrls: ['./ownership-data-real-estate.component.scss']
})
export class OwnershipDataRealEstateComponent implements OnInit {

  @Input() realEstate: RealEstate;
  @Output() updatedRealEstateEvent = new EventEmitter();

  constructor(
    private tenantsDataDialog: MatDialog
  ) { }

  ngOnInit(): void {
  }

  editOwnership() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.minWidth = '52rem'; // Set the width of the dialog
    dialogConfig.minHeight = '25rem'; // Set the height of the dialog
    dialogConfig.maxHeight = '90vh'; // Set the maximum height of the dialog
    dialogConfig.maxWidth = '90vw'; // Set the maximum width of the dialog
    dialogConfig.data = this.realEstate;
    const dialogRef = this.tenantsDataDialog.open(EditTenantsReDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.updatedRealEstateEvent.emit(true);
      }
    });
  }

}
