import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../../service/auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class UnauthorizedGuard implements CanActivate {
  constructor(public authService: AuthService, public router: Router) {}

  canActivate(): boolean {
    if (this.authService.getLoggedParsedUser()){
      this.router.navigate(["/smart-home/user/home"]);

      return false;
    }

    return true;
  }
  
}
