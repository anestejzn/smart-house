import { Component, Inject, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Csr } from '../../../model/csr';
import { ToastrService } from 'ngx-toastr';
import { CertificateService } from '../../../service/certificate-service/certificate.service';
import { Subscription } from 'rxjs';
import { MatCheckbox } from '@angular/material/checkbox';
import { MatRadioChange } from '@angular/material/radio';
import { CertificateRequest } from '../../../model/certificate-request';

@Component({
  selector: 'app-create-certificate-dialog',
  templateUrl: './create-certificate-dialog.component.html',
  styleUrls: ['./create-certificate-dialog.component.scss']
})
export class CreateCertificateDialogComponent implements OnInit {
  @ViewChild('keyagreementcb') private keyagreementcb: MatCheckbox;
  @ViewChild('encipheronlycb') private encipheronlycb: MatCheckbox;
  @ViewChild('decipheronlycb') private decipheronlycb: MatCheckbox;
  @ViewChild('digitalSignatureCb') private digitalSignatureCb: MatCheckbox;
  @ViewChildren(MatCheckbox) checkboxes: QueryList<MatCheckbox>;
  keyUsages = [];
  extendedKeyUsages = [];
  templates = ['Code Signing Certificate', 'SSL/TSL Certificate', 'Custom'];
  expiringPeriods = ['6 months', '12 months', '24 months'];
  intermediates = ['intermediate1', 'intermediate2'];
  chosenExpiringPeriod = '6 months';
  chosenTemplate = 'Custom';
  chosenIntermediate = 'intermediate1';
  certificateSubscription: Subscription;

  constructor(@Inject(MAT_DIALOG_DATA) public request: Csr, private toast: ToastrService,  private dialogRef: MatDialogRef<CreateCertificateDialogComponent>, private certificateService: CertificateService) {
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
  }

  acceptCSR(){
    if(this.keyUsages.length === 0 || this.extendedKeyUsages.length === 0){
      this.toast.error("You must select any extension.", "Empty extensions")
    } else if (this.chosenExpiringPeriod === '') {
      this.toast.error("You must select validity period.", "Validity period")
    } else{
      const certificateRequest: CertificateRequest = {
        csrId: this.request.id,
        keyUsages: this.keyUsages,
        extendedKeyUsages: this.extendedKeyUsages,
        validityPeriod: this.chosenExpiringPeriod.split(' ')[0],
        intermediateAlias: this.chosenIntermediate
      }
      this.certificateSubscription = this.certificateService.createLeafCertificate(certificateRequest).subscribe(response => {
        this.dialogRef.close(true);
      },
      error => {
        this.toast.error(error.error, "Not created");
        this.dialogRef.close(false);
      })
      
    }
  }

  changeTemplate(event:  MatRadioChange){
    this.chosenTemplate = event.value;
    if(event.value === 'Custom'){
      this.keyUsages.splice(0, this.keyUsages.length);
      this.extendedKeyUsages.splice(0, this.extendedKeyUsages.length);
      this.uncheckAll();
    }
    else if(event.value === 'Code Signing Certificate'){
      this.keyUsages = [];
      this.extendedKeyUsages = [];
      this.uncheckAll();
      this.digitalSignatureCb.checked = true;
      this.keyUsages.push('Digital Signature');
      this.extendedKeyUsages.push('Code Signing');
    }
    else{
      this.keyUsages = [];
      this.extendedKeyUsages = [];
      this.uncheckAll();
      this.digitalSignatureCb.checked = true;
      this.keyUsages.push('Digital Signature');
      this.keyUsages.push('Key Encipherment');
      this.extendedKeyUsages.push('Server Authentication');
      this.extendedKeyUsages.push('Client Authentication');
    }
  }

  changeExpiringPeriod(event: MatRadioChange) {
    this.chosenExpiringPeriod = event.value;
  }

  uncheckAll() {
    this.checkboxes.forEach((checkbox) => {
      checkbox.checked = false;
    });
  }

  isCheckedDS(){
    if(this.chosenTemplate === 'Custom'){
      return false;
    }
    return true;
  }


}
