import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { CsrService } from '../../service/csr-service/csr.service';
import { CsrDataSource } from '../../model/csr-data-source';
import { Csr } from '../../model/csr';
import { MatDialog } from '@angular/material/dialog';
import { CsrDetailsDialogComponent } from '../csr-details-dialog/csr-details-dialog.component';
import { CreateCertificateDialogComponent } from '../certificate-components/create-certificate-dialog/create-certificate-dialog.component';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-csr-table',
  templateUrl: './csr-table.component.html',
  styleUrls: ['./csr-table.component.scss']
})
export class CsrTableComponent implements OnInit {
  csrSubscription: Subscription
  dataSource: CsrDataSource;
  displayedColumns: string[] = ['name', 'surname', 'email', 'details', 'accept', 'reject'];
  noCsrs = false;

  constructor(private csrService: CsrService, private dialog: MatDialog, private toast: ToastrService) { }

  ngOnInit(): void {
   this.getAllCsrs();
  }

  getAllCsrs(){
    this.csrSubscription = this.csrService.getAllPendingCsrs().subscribe(
      csrs => {
        if(csrs.length === 0){
          this.noCsrs = true;
        }
        else{
          this.dataSource = new CsrDataSource(csrs);
        }
      }
    )
  }

  acceptCsr(element: Csr){
    const dialogRef = this.dialog.open(CreateCertificateDialogComponent, {
      data: element,
      width: '37rem',
      height: '36rem'
    });

    dialogRef.afterClosed().subscribe(
      response => {
        if(response){
          this.toast.success("Certificate is created.");
          this.ngOnInit();
        }
      }
    )
  }

  rejectCsr(id:number){
    this.csrSubscription = this.csrService.rejectCsr(id).subscribe(
      response => {
        console.log(response);
        this.ngOnInit();}
    )
  }

  viewDetails(element:Csr){
    this.dialog.open(CsrDetailsDialogComponent, {
      data: element
    });
  }

}
