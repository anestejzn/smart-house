import { Component, Input, OnInit } from '@angular/core';
import { SortedAliases } from '../../model/sorted-aliases';

@Component({
  selector: 'app-certificate-rows',
  templateUrl: './certificate-rows.component.html',
  styleUrls: ['./certificate-rows.component.scss']
})
export class CertificateRowsComponent implements OnInit {
  @Input() aliases: SortedAliases[];

  constructor() {
    this.aliases = [];
  }

  ngOnInit(): void {
  }

}
