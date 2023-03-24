import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LoginRequest } from '../../model/login/login-request';
import { AuthService } from '../../service/auth.service';
import { Subscription } from 'rxjs';
import { LoginResponse } from '../../model/login/login-response';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
    ]),
  });

  authSubscription: Subscription;
  hide = true;

  constructor(
    private authService: AuthService,
    private router: Router,
    private toast: ToastrService,
  ) { }

  ngOnInit(): void {
  }

  getErrorMessage() {
    if (this.loginForm.get('email').hasError('required')) {
      return 'Email is required';
    }

    return this.loginForm.get('email').hasError('email')
      ? 'Not a valid email'
      : '';
  }

  logIn() {
    if (!this.loginForm.invalid) {
      const loginRequest: LoginRequest = {
        email: this.loginForm.get('email').value,
        password: this.loginForm.get('password').value,
      };
      this.authSubscription = this.authService.login(loginRequest).subscribe({
        next(loggedUser: LoginResponse): void {
          this.authService.setLocalStorage(loggedUser);
          this.router.navigate(['']);
        },
        error(error): void {
          this.toast.error('Email or password is not correct!', 'Login failed');
        }
      });
    }
  }


  ngOnDestroy(){
    if(this.authSubscription){
      this.authSubscription.unsubscribe();
    }
  }


}
