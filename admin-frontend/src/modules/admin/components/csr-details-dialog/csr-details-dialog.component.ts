import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Csr } from '../../model/csr';

@Component({
  selector: 'app-csr-details-dialog',
  templateUrl: './csr-details-dialog.component.html',
  styleUrls: ['./csr-details-dialog.component.scss']
})
export class CsrDetailsDialogComponent implements OnInit {

  constructor( @Inject(MAT_DIALOG_DATA) public request: Csr) { }

  ngOnInit(): void {
  }

}
