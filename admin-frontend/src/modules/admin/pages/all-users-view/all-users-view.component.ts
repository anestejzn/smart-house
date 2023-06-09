import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { ConfirmationDialogComponent } from 'src/modules/shared/components/confirmation-dialog/confirmation-dialog.component';
import { User } from 'src/modules/shared/model/user';
import { UserService } from 'src/modules/shared/service/user-service/user.service';
import { RealEstateService } from '../../service/real-estate/real-estate.service';

@Component({
  selector: 'app-all-users-view',
  templateUrl: './all-users-view.component.html',
  styleUrls: ['./all-users-view.component.scss']
})
export class AllUsersViewComponent implements OnInit, OnDestroy {

  users: User[] = [];
  selectedUser: User = null;
  
  asc: boolean = true;
  userStatus: string = 'ACTIVE';

  userSubscription: Subscription;
  userActionSubscription: Subscription;

  constructor(private userService: UserService,
              private realEstateService: RealEstateService,
              private confirmationDialog: MatDialog,
              private toast: ToastrService) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.userSubscription = this.userService.filterUsers(this.asc, this.userStatus).subscribe(
      res => {
        if (res) {
          this.users = res;
          // console.log(this.users);
        }
      },
      err => {
        console.log(err);
      }
    )
  }

  changedUser(user: User): void {
    this.selectedUser = user;
  }

  changeSortByName(ascending: boolean): void {
    this.asc = ascending;
    this.selectedUser = null;
    this.loadData();
  }

  changeFilterByAccStatus(status: string): void {
    this.userStatus = status;
    this.selectedUser = null;
    this.loadData();
  }

  blockUser(id: number): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.minWidth = '15rem'; // Set the width of the dialog
    dialogConfig.minHeight = '15rem'; // Set the height of the dialog
    dialogConfig.maxHeight = '90vh'; // Set the maximum height of the dialog
    dialogConfig.maxWidth = '90vw'; // Set the maximum width of the dialog
    dialogConfig.data = 'Are you sure that you want to block the user?';
    const dialogRef = this.confirmationDialog.open(ConfirmationDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.userActionSubscription = this.realEstateService.block(id).subscribe(
          res => {
            this.toast.success(`User is successfully blocked!`, 'Success!');
            this.loadData();
            this.selectedUser = null;
          },
          err => {
            this.toast.error(err.error, 'Error happened!');
          }
        )
      }
    });
  }

  unblockUser(id: number): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.minWidth = '15rem'; // Set the width of the dialog
    dialogConfig.minHeight = '15rem'; // Set the height of the dialog
    dialogConfig.maxHeight = '90vh'; // Set the maximum height of the dialog
    dialogConfig.maxWidth = '90vw'; // Set the maximum width of the dialog
    dialogConfig.data = 'Are you sure that you want to unblock the user?';
    const dialogRef = this.confirmationDialog.open(ConfirmationDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.userActionSubscription = this.realEstateService.unblock(id).subscribe(
          res => {
            this.toast.success(`User is successfully unblocked!`, 'Success!');
            this.loadData();
            this.selectedUser = null;
          },
          err => {
            this.toast.error(err.error, 'Error happened!');
          }
        )
      }
    });
  }

  ngOnDestroy(): void {
      if (this.userSubscription) {
        this.userSubscription.unsubscribe();
      }

      if (this.userActionSubscription) {
        this.userActionSubscription.unsubscribe();
      }
  }

}
