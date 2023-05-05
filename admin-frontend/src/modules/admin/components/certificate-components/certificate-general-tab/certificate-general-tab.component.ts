import { Component, Input, OnInit } from '@angular/core';
import { CertificateData } from '../../../model/certificate-data';

@Component({
  selector: 'app-certificate-general-tab',
  templateUrl: './certificate-general-tab.component.html',
  styleUrls: ['./certificate-general-tab.component.scss']
})
export class CertificateGeneralTabComponent implements OnInit {
  @Input() certificate: CertificateData;

  constructor() { }

  ngOnInit(): void {
  }

}
