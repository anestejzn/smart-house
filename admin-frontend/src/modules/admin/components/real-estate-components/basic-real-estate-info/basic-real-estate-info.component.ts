import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { RealEstate } from 'src/modules/admin/model/real-estate';
import { EditBasicReDataDialogComponent } from '../edit-basic-re-data-dialog/edit-basic-re-data-dialog.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-basic-real-estate-info',
  templateUrl: './basic-real-estate-info.component.html',
  styleUrls: ['./basic-real-estate-info.component.scss']
})
export class BasicRealEstateInfoComponent implements OnInit {

  @Input() realEstate: RealEstate;
  @Output() updatedRealEstateEvent = new EventEmitter();

  constructor(
    private editBasicDataDialog: MatDialog
    ) { }

  ngOnInit(): void {
    
  }

  openEditDialog(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.minWidth = '40rem'; // Set the width of the dialog
    dialogConfig.minHeight = '20rem'; // Set the height of the dialog
    dialogConfig.maxHeight = '90vh'; // Set the maximum height of the dialog
    dialogConfig.maxWidth = '90vw'; // Set the maximum width of the dialog
    dialogConfig.data = this.realEstate;
    const dialogRef = this.editBasicDataDialog.open(EditBasicReDataDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.updatedRealEstateEvent.emit(true);
      }
    });
  }

}
