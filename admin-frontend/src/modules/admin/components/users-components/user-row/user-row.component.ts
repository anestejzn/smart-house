import { Component, Input, OnInit } from '@angular/core';
import { User } from 'src/modules/shared/model/user';

@Component({
  selector: 'app-user-row',
  templateUrl: './user-row.component.html',
  styleUrls: ['./user-row.component.scss']
})
export class UserRowComponent implements OnInit {

  @Input() user: User;
  @Input() index: number;
  @Input() selected: boolean;

  constructor() { }

  ngOnInit(): void {
  }

}
