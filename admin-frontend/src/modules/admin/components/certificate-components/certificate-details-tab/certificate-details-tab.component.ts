import { Component, OnInit, Input } from '@angular/core';
import { CertificateData } from '../../../model/certificate-data';

@Component({
  selector: 'app-certificate-details-tab',
  templateUrl: './certificate-details-tab.component.html',
  styleUrls: ['./certificate-details-tab.component.scss']
})
export class CertificateDetailsTabComponent implements OnInit {
  @Input() certificate: CertificateData;

  constructor() {
  }

  ngOnInit(): void {
  }

}
