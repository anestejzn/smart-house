import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { User } from 'src/modules/shared/model/user';
import { AuthService } from '../../service/auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  constructor(public authService: AuthService, public  router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {

    const expectedRoles: string = route.data['expectedRoles'];
    const user: User = this.authService.getLoggedParsedUser();
    if (!user){
      this.router.navigate(["/smart-home/auth/login"]);
      return false;
    }

    const roles: string[] = expectedRoles.split("|", 3);
    if (roles.indexOf(user.role.roleName) === -1){
      this.router.navigate(["/smart-home/auth/login"]);
      return false;
    }

    return true;

  }
  
}
