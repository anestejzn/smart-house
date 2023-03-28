import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Csr } from '../../model/csr';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-certificate-dialog',
  templateUrl: './create-certificate-dialog.component.html',
  styleUrls: ['./create-certificate-dialog.component.scss']
})
export class CreateCertificateDialogComponent implements OnInit {
  keyUsages = [];
  extendedKeyUsages = [];

  constructor(@Inject(MAT_DIALOG_DATA) public request: Csr, private toast: ToastrService,  private dialogRef: MatDialogRef<CreateCertificateDialogComponent>) {
    console.log(request);
   }

  ngOnInit(): void {
  }

  checkKeyUsage(keyUsage: string){
    this.keyUsages.push(keyUsage);
    console.log(this.keyUsages);
  }

  checkExtendedKeyUsage(extendedKeyUsage: string){
    this.extendedKeyUsages.push(extendedKeyUsage);
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
      this.dialogRef.close();
    }
  }

}
