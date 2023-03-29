import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { CancelCertificateRequest } from '../../model/cancel-certificate-request';
import { SortedAliases } from '../../model/sorted-aliases';
import { CertificateService } from '../../service/certificate-service/certificate.service';

@Component({
  selector: 'app-cancel-certificate-dialog',
  templateUrl: './cancel-certificate-dialog.component.html',
  styleUrls: ['./cancel-certificate-dialog.component.scss']
})
export class CancelCertificateDialogComponent implements OnInit, OnDestroy {

  reason: string;
  certificateSubscription: Subscription;

  constructor(
    @Inject(MAT_DIALOG_DATA) public alias: SortedAliases,
    private certificateService: CertificateService,
    private toast: ToastrService
    ) {
    this.reason = '';
  }

  ngOnInit(): void {
  }

  checkEnteredReason() {
    return this.reason.length > 5;
  }

  cancelCertificate(alias: SortedAliases): void {
    const request: CancelCertificateRequest = {
      alias: this.alias.alias,
      cancelReason: this.reason
    }

    this.certificateSubscription = this.certificateService.cancelCertificate(request).subscribe(
      res => {
        if (res) {
          this.toast.success("Certificate is cancelled!", 'Certificate is successfully canceled!');
        }
      }, err => {
        this.toast.error(err.error, 'Error happened');
      }
    )
  }

  ngOnDestroy(): void {
    if (this.certificateSubscription) {
      this.certificateSubscription.unsubscribe();
    }
  }

}
