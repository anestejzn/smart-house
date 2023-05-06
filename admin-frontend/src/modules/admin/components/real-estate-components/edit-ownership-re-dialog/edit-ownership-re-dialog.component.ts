import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { RealEstate, UpdateRealEstateRequest } from 'src/modules/admin/model/real-estate';
import { RealEstateService } from 'src/modules/admin/service/real-estate/real-estate.service';
import { User } from 'src/modules/shared/model/user';
import { UserService } from 'src/modules/shared/service/user-service/user.service';

@Component({
  selector: 'app-edit-ownership-re-dialog',
  templateUrl: './edit-ownership-re-dialog.component.html',
  styleUrls: ['./edit-ownership-re-dialog.component.scss', '../../real-estate-components/add-new-real-estate-dialog/add-new-real-estate-dialog.component.scss']
})
export class EditOwnershipReDialogComponent implements OnInit, OnDestroy {

  constructor(
    @Inject(MAT_DIALOG_DATA) public realEstate: RealEstate,
    private userService: UserService,
    private toast: ToastrService,
    private realEstateService: RealEstateService
  ) { }

  allActiveOwners: User[];
  allActiveTenants: User[];
  tenants: User[] = [];
  tenantIds: number[] = [];
  currentTenant: User = null;
  selectedOwner: User = null;
  realEstateSubscription: Subscription;
  allActiveOwnersSubscription: Subscription;
  allActiveTenantsSubscription: Subscription;

  ngOnInit(): void {
    this.allActiveOwnersSubscription =this.userService.getAllActiveOwners().subscribe(
      res => {
        this.allActiveOwners = res;
      }
    )

    this.allActiveTenantsSubscription =this.userService.getAllActiveTenants().subscribe(
      res => {
        this.allActiveTenants = res;
      }
    )

    if (this.realEstate) {
      this.realEstate.tenants.filter(element => this.tenants.push(element));
      this.tenants.filter(element => this.tenantIds.push(element.id));
      this.selectedOwner = this.realEstate.owner;
    }
  }

  addTenant(): void {
    if (this.tenants.length+1 > 6) {
      this.toast.error('You cannot add more than 5 tenants!', 'Error happened');
    }
    else if (this.currentTenant !== null && 
    (this.selectedOwner !== null && 
    this.currentTenant.id !== this.selectedOwner.id)) {
      if (!this.tenants.find(obj => obj.id === this.currentTenant.id)) {
        this.tenants.push(this.currentTenant);
        this.tenantIds.push(this.currentTenant.id);
        this.currentTenant = null;
      }
      else {
        this.toast.error('Your tenant is already added!', 'Error happened');
      }
    } 
    else {
      this.toast.error('Your must select tenant that is not owner! Select Owner first!', 'Error happened');
    }
  }

  deleteTenant(tenant: User): void {
    this.tenants = this.tenants.filter(element => element.id !== tenant.id);
    this.tenantIds = this.tenantIds.filter(element => element !== tenant.id);
  }

  changedTenant(event: User): void {
    this.currentTenant = event;
  }

  save(): void {
    if (this.selectedOwner) {
      const data: UpdateRealEstateRequest = {
        id: this.realEstate.id,
        ownerId: this.selectedOwner.id,
        tenantsIds: this.tenantIds
      }
      this.realEstateSubscription = this.realEstateService.editOwnershipRealEstateData(data).subscribe(
        res => {
          if (res) {
            this.realEstate = res;
            this.toast.success('Real Estate Object is successfully updated.', 'Success!');
          }
        },
        err => {
          if (err) {
            this.toast.error(err.error, 'Error happened');
          }
        }
      )


    } else {
      this.toast.error('Your must select tenant that is not owner! Select Owner first!', 'Error happened');
    }
  }

  ngOnDestroy(): void {
    if (this.allActiveOwnersSubscription) {
      this.allActiveOwnersSubscription.unsubscribe();
    }

    if (this.allActiveTenantsSubscription) {
      this.allActiveTenantsSubscription.unsubscribe();
    }
  }

}
