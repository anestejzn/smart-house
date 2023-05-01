import { Component, Input, OnInit } from '@angular/core';
import { CertificateData } from '../../../model/certificate-data';

@Component({
  selector: 'app-certificates-chain-tab',
  templateUrl: './certificates-chain-tab.component.html',
  styleUrls: ['./certificates-chain-tab.component.scss']
})
export class CertificatesChainTabComponent implements OnInit {
  @Input() certificates: CertificateData[];

  constructor() {
    this.certificates = [];
  }

  ngOnInit(): void {
  }

}
