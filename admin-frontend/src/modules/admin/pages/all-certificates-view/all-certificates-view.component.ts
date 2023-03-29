import { Component, OnDestroy, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { SortedAliases } from '../../model/sorted-aliases';
import { CertificateService } from '../../service/certificate-service/certificate.service';

@Component({
  selector: 'app-all-certificates-view',
  templateUrl: './all-certificates-view.component.html',
  styleUrls: ['./all-certificates-view.component.scss']
})
export class AllCertificatesViewComponent implements OnInit, OnDestroy {

  valid: string = "VALID";
  type: string = 'ALL';
  allAliases: SortedAliases[];
  certificatesSubscription: Subscription;

  constructor(
    private certificateService: CertificateService,
    private toast: ToastrService
  ) { }

  ngOnInit(): void {
    this.certificatesSubscription = this.certificateService.getAllCertificates(this.type, this.valid).subscribe(
      res => {
        this.allAliases = res;
      }, err => {
        this.toast.error("Upps, data cannot be loaded.", "Error happened!")
      }
    )
  } 

  changedType(value: string) {
    this.type = value;
    this.certificatesSubscription = this.certificateService.getAllCertificates(this.type, this.valid).subscribe(
      res => {
        this.allAliases = res;
      }, err => {
        this.toast.error("Upps, data cannot be loaded.", "Error happened!")
      }
    )
  }

  changedValidity(value: string) {
    this.valid = value;
    this.certificatesSubscription = this.certificateService.getAllCertificates(this.type, this.valid).subscribe(
      res => {
        this.allAliases = res;
      }, err => {
        this.toast.error("Upps, data cannot be loaded.", "Error happened!")
      }
    )
  }

  ngOnDestroy(): void {
    if (this.certificatesSubscription) {
      this.certificatesSubscription.unsubscribe();
    }
  }

}
