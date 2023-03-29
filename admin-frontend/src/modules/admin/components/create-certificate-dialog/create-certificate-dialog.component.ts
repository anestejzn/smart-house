import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Csr } from '../../model/csr';
import { ToastrService } from 'ngx-toastr';
import { CertificateService } from '../../service/certificate-service/certificate.service';
import { Subscription } from 'rxjs';
import { MatCheckbox } from '@angular/material/checkbox';

@Component({
  selector: 'app-create-certificate-dialog',
  templateUrl: './create-certificate-dialog.component.html',
  styleUrls: ['./create-certificate-dialog.component.scss']
})
export class CreateCertificateDialogComponent implements OnInit {
  @ViewChild('keyagreementcb') private keyagreementcb: MatCheckbox;
  @ViewChild('encipheronlycb') private encipheronlycb: MatCheckbox;
  @ViewChild('decipheronlycb') private decipheronlycb: MatCheckbox;
  keyUsages = [];
  extendedKeyUsages = [];
  certificateSubscription: Subscription;

  constructor(@Inject(MAT_DIALOG_DATA) public request: Csr, private toast: ToastrService,  private dialogRef: MatDialogRef<CreateCertificateDialogComponent>, private certificateService: CertificateService) {
    console.log(request);
   }

  ngOnInit(): void {
  }

  checkKeyUsage(keyUsage: string){
    if(['Encipher only', 'Decipher only'].includes(keyUsage)){
      this.checkSpecialKeyUsage(keyUsage);
    }
    else if(keyUsage === 'Key Agreement'){
      this.checkKeyAgreement(keyUsage);
    }
    else{
      if(this.keyUsages.includes(keyUsage)){
        this.keyUsages.splice(this.keyUsages.indexOf(keyUsage),1);
      }
      else{
        this.keyUsages.push(keyUsage);
      }
    }
    console.log(this.keyUsages);
  }

  checkKeyAgreement(keyUsage: string){
    if(this.keyUsages.includes(keyUsage)){
      this.keyUsages.splice(this.keyUsages.indexOf('Key Agreement'),1);
      if(this.keyUsages.includes('Encipher only')){
        this.keyUsages.splice(this.keyUsages.indexOf('Encipher only'),1);
        this.encipheronlycb.checked = false;
      }
      else if(this.keyUsages.includes('Decipher only')){
        this.keyUsages.splice(this.keyUsages.indexOf('Decipher only'),1);
        this.decipheronlycb.checked = false;
      }
    }
    else{
      this.keyUsages.push(keyUsage);
    }
      
  }

  checkSpecialKeyUsage(keyUsage: string){
    if(this.keyUsages.includes(keyUsage)){
      this.keyUsages.splice(this.keyUsages.indexOf(keyUsage),1);
    }
    else if(keyUsage === 'Encipher only'){
      if(this.keyUsages.includes('Decipher only')){
        this.toast.error("You have already checked Decipher only, it is not allowed to check Encipher only.");
        this.encipheronlycb.checked = false;
      }
      else{
        if(this.keyUsages.includes('Key Agreement')){
          this.keyUsages.push(keyUsage);
        }
        else{
          this.keyagreementcb.checked = true;
          this.keyUsages.push('Key Agreement');
          this.keyUsages.push(keyUsage);
        }
      }
    }
    else if(keyUsage === 'Decipher only'){
      if(this.keyUsages.includes('Encipher only')){
        this.toast.error("You have already checked Encipher only, it is not allowed to check Decipher only.");
        this.decipheronlycb.checked = false;
      }
      else{
        if(this.keyUsages.includes('Key Agreement')){
          this.keyUsages.push(keyUsage);
        }
        else{
          this.keyagreementcb.checked = true;
          this.keyUsages.push('Key Agreement');
          this.keyUsages.push(keyUsage);
        }
      }
    }
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