import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/modules/auth/service/auth.service';
import { User } from 'src/modules/shared/model/user';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  authSubscription: Subscription;
  loggedUser: User;


  constructor(private authService: AuthService) { }

  ngOnInit(): void {

    this.authSubscription = this.authService
      .getSubjectCurrentUser()
      .subscribe(user => {
        this.loggedUser = user;
      
      });
  }

  sendCsr(){
    console.log("send");
  }

}
