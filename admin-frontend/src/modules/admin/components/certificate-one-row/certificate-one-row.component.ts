import { Component, Input, OnInit } from '@angular/core';
import { SortedAliases } from '../../model/sorted-aliases';

@Component({
  selector: 'app-certificate-one-row',
  templateUrl: './certificate-one-row.component.html',
  styleUrls: ['./certificate-one-row.component.scss']
})
export class CertificateOneRowComponent implements OnInit {
  @Input() alias: SortedAliases;
  @Input() index: number;

  constructor() { }

  ngOnInit(): void {
  }

}
