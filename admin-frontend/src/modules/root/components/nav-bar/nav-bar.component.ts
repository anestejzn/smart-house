import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/modules/auth/service/auth.service';
import { User } from 'src/modules/shared/model/user';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {
  loggedUser:User;
  authSubscription: Subscription;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.authSubscription = this.authService
    .getSubjectCurrentUser()
    .subscribe(user => {
      this.loggedUser = user;
      console.log(this.loggedUser);
    });
}

logOut(){
  
  this.authService.logOut();
  this.router.navigate(['/smart-home/auth/login'])
}

}
