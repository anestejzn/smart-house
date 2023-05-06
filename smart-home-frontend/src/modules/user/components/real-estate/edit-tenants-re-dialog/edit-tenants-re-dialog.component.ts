import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/modules/auth/service/auth/auth.service';
import { RealEstate, UpdateRealEstateRequest } from 'src/modules/shared/model/real-estate';
import { User } from 'src/modules/shared/model/user';
import { UserService } from 'src/modules/shared/service/user-service/user.service';
import { RealEstateService } from 'src/modules/user/service/real-estate.service';

@Component({
  selector: 'app-edit-tenants-re-dialog',
  templateUrl: './edit-tenants-re-dialog.component.html',
  styleUrls: ['./edit-tenants-re-dialog.component.scss']
})
export class EditTenantsReDialogComponent implements OnInit, OnDestroy {

  allActiveTenants: User[];
  tenants: User[] = [];
  tenantIds: number[] = [];
  currentTenant: User = null;
  selectedOwner: User = null;
  realEstateSubscription: Subscription;
  allActiveTenantsSubscription: Subscription;
  authSubscription: Subscription;
  showPage: boolean = false;

  constructor(@Inject(MAT_DIALOG_DATA) public realEstate: RealEstate,
    private userService: UserService,
    private toast: ToastrService,
    private realEstateService: RealEstateService,
    private authService: AuthService,
  ) { }

  ngOnInit(): void {
    this.allActiveTenantsSubscription =this.userService.getAllTenantUsers().subscribe(
      res => {
        this.allActiveTenants = res;
      }
    )

    if (this.realEstate) {
      this.realEstate.tenants.filter(element => this.tenants.push(element));
      this.tenants.filter(element => this.tenantIds.push(element.id));
      this.selectedOwner = this.realEstate.owner;
      this.checkOwner();
    }
  }

  checkOwner(): void {
    this.authSubscription = this.authService.getSubjectCurrentUser().subscribe(
      res => {
        if (res) {
          if (this.selectedOwner.id !== res.id) {
            this.showPage = false;
          } else {
            this.showPage = true;
          }
        }
      }
    )
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
        tenantsIds: this.tenantIds
      }
      this.realEstateSubscription = this.realEstateService.editTenantsRealEstateData(data).subscribe(
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
    if (this.allActiveTenantsSubscription) {
      this.allActiveTenantsSubscription.unsubscribe();
    }
  }

}
