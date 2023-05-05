import { Component, Input, OnInit } from '@angular/core';
import { User } from 'src/modules/shared/model/user';

@Component({
  selector: 'app-tenant-data',
  templateUrl: './tenant-data.component.html',
  styleUrls: ['./tenant-data.component.scss']
})
export class TenantDataComponent implements OnInit {

  @Input() tenants: User[];

  constructor() { }

  ngOnInit(): void {
  }

}
