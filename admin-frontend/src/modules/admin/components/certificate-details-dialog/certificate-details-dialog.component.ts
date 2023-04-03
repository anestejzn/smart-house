import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { CertificateData } from '../../model/certificate-data';
import { SortedAliases } from '../../model/sorted-aliases';
import { CertificateService } from '../../service/certificate-service/certificate.service';

@Component({
  selector: 'app-certificate-details-dialog',
  templateUrl: './certificate-details-dialog.component.html',
  styleUrls: ['./certificate-details-dialog.component.scss']
})
export class CertificateDetailsDialogComponent implements OnInit, OnDestroy {

  certificateSubscription: Subscription;
  certificates: CertificateData[];
  mainCertificate: CertificateData;
  detailsCertificates: CertificateData[];

  constructor(
    private certificateService: CertificateService,
    @Inject(MAT_DIALOG_DATA) public alias: SortedAliases,
    private toast: ToastrService
  ) {
    this.detailsCertificates = [];
  }

  
  ngOnInit(): void {
    this.certificateSubscription = this.certificateService.getCertificateChain(this.alias.alias).subscribe(
      res => {
        if (res) {
          this.certificates = res;
          this.selectCertificates();
        }
      }, err => {
        this.toast.error(err.error, 'Error happened');
      }
    )
  }

  selectCertificates(): void {
    this.certificates.forEach(cert => {
      if (cert.alias === this.alias.alias)
        this.mainCertificate = cert;
      else 
        this.detailsCertificates.push(cert);
    });
    this.detailsCertificates.reverse();
  }

  ngOnDestroy(): void {
    if (this.certificateSubscription) {
      this.certificateSubscription.unsubscribe();
    }
  }
}
