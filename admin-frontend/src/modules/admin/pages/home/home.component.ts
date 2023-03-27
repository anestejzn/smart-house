import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/modules/auth/service/auth.service';
import { User } from 'src/modules/shared/model/user';
import { CsrService } from '../../service/csr-service/csr.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {




  constructor(private authService: AuthService, private csrService: CsrService) { }

  ngOnInit(): void {

    
  }



}
