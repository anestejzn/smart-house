import { Component, Input, OnInit } from '@angular/core';
import { User } from 'src/modules/shared/model/user';

@Component({
  selector: 'app-owner-data',
  templateUrl: './owner-data.component.html',
  styleUrls: ['./owner-data.component.scss']
})
export class OwnerDataComponent implements OnInit {

  @Input() owner: User;

  constructor() { }

  ngOnInit(): void {
  }

}
