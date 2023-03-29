import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Csr } from '../../model/csr';
import { ToastrService } from 'ngx-toastr';
import { CertificateService } from '../../service/certificate-service/certificate.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-create-certificate-dialog',
  templateUrl: './create-certificate-dialog.component.html',
  styleUrls: ['./create-certificate-dialog.component.scss']
})
export class CreateCertificateDialogComponent implements OnInit {
  keyUsages = [];
  extendedKeyUsages = [];
  certificateSubscription: Subscription;

  constructor(@Inject(MAT_DIALOG_DATA) public request: Csr, private toast: ToastrService,  private dialogRef: MatDialogRef<CreateCertificateDialogComponent>, private certificateService: CertificateService) {
    console.log(request);
   }

  ngOnInit(): void {
  }

  checkKeyUsage(keyUsage: string){
    if(this.keyUsages.includes(keyUsage)){
      console.log("daaa");
      this.keyUsages.splice(this.keyUsages.indexOf(keyUsage),1);
    }
    else{
      this.keyUsages.push(keyUsage);
    }
    console.log(this.keyUsages);
  }

  checkExtendedKeyUsage(extendedKeyUsage: string){
    if(this.extendedKeyUsages.includes(extendedKeyUsage)){
      this.extendedKeyUsages.splice(this.extendedKeyUsages.indexOf(extendedKeyUsage),1);
    }
    else{
      this.extendedKeyUsages.push(extendedKeyUsage);
    }
    console.log(this.extendedKeyUsages);
  }

  acceptCSR(){
    if(this.keyUsages.length === 0 || this.extendedKeyUsages.length === 0){
      this.toast.error("You must select any extension.", "Empty extensions")
    }
    else{
      const csrRequest = {
        csrId: this.request.id,
        keyUsages: this.keyUsages,
        extendedKeyUsages: this.extendedKeyUsages
      }
      console.log(csrRequest);
      this.certificateSubscription = this.certificateService.createLeafCertificate(csrRequest).subscribe(response => {
        console.log(response);
        this.dialogRef.close(true);
      },
      error => {
        this.toast.error(error.error, "Not created");
        this.dialogRef.close(false);
      })
      
    }
  }

}
