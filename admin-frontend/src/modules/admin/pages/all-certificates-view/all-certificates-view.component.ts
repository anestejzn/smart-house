import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-all-certificates-view',
  templateUrl: './all-certificates-view.component.html',
  styleUrls: ['./all-certificates-view.component.scss']
})
export class AllCertificatesViewComponent implements OnInit {

  valid: boolean = true;
  type: string = 'All';

  constructor() { }

  ngOnInit(): void {
  }

  changedType(value: string) {
    this.type = this.type;
  }

  changedValidity(valid: boolean) {
    this.valid = valid;
  }

}
