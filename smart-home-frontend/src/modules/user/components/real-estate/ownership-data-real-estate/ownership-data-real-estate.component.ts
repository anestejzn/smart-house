import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { RealEstate } from 'src/modules/shared/model/real-estate';
import { EditTenantsReDialogComponent } from '../edit-tenants-re-dialog/edit-tenants-re-dialog.component';
import { AuthService } from 'src/modules/auth/service/auth/auth.service';
import { User } from 'src/modules/shared/model/user';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-ownership-data-real-estate',
  templateUrl: './ownership-data-real-estate.component.html',
  styleUrls: ['./ownership-data-real-estate.component.scss']
})
export class OwnershipDataRealEstateComponent implements OnInit, OnDestroy {

  @Input() realEstate: RealEstate;
  @Output() updatedRealEstateEvent = new EventEmitter();

  loggedUser: User = null;
  authSubscription: Subscription;

  constructor(
    private tenantsDataDialog: MatDialog,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.authSubscription = this.authService.getSubjectCurrentUser().subscribe(
      res => {
        if (res) {
          this.loggedUser = res;
        }
      }
    )

  }

  editOwnership() {
    if (this.loggedUser && this.loggedUser.role.roleName === 'ROLE_OWNER') {
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

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

}
