import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LoginRequest } from '../../model/login/login-request';
import { Subscription } from 'rxjs';
import { AuthService } from '../../service/auth/auth.service';
import { WebSocketService } from 'src/modules/shared/service/web-socket-service/web-socket.service';
import { ConfirmPinRequest } from '../../model/confirm-pin-request/confirm-pin-request';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {
  firstDigit: string;
  secondDigit: string;
  thirdDigit: string;
  fourthDigit: string;
  enterPin = false;
  user = null;
  showSpiner = false;

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(12),
    ]),
  });

  authSubscription: Subscription;
  hide = true;

  constructor(
    private authService: AuthService,
    private router: Router,
    private toast: ToastrService,
    private webSocketService: WebSocketService
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
      this.showSpiner = true;
      this.authSubscription = this.authService.login(loginRequest).subscribe(
        userResponse => {
          this.user = userResponse;
          this.authSubscription = this.authService.generatePin(userResponse.user.email).subscribe(
            pin => {
              this.showSpiner = false;
              this.enterPin = true;
              
            }
          )
        },
        error => {
          console.log(error.error);
          if(error.error === "Invalid creds!"){
            this.toast.error('Email or password is not correct!', 'Login failed');
            this.incrementFailedAttempts(loginRequest.email);
          }
          else {
            this.showSpiner = false;
            this.toast.error("Your account is locked.", "Locked account");
          }
        }
      );
    }
  }

  confirmPin(){
    const pin: string =
        this.firstDigit + this.secondDigit + this.thirdDigit + this.fourthDigit;
    const confirmPinRequest: ConfirmPinRequest = {
      email: this.user.user.email,
      pin: pin
    }
    this.authSubscription = this.authService.confirmPin(confirmPinRequest).subscribe(
      response => {
        if(response){
          this.authService.setSessionStorage(this.user);
          this.webSocketService.connect();
          
          this.router.navigate(['/smart-home/user/home']);
        }
      },
      error => {
        this.toast.error(error.error);
        this.incrementFailedAttempts(confirmPinRequest.email);
      }
    )
  }

  incrementFailedAttempts(email: string){
    this.authSubscription = this.authService.incrementFailedAttempts(email).subscribe(
      response => {
        this.showSpiner = false;
        console.log(response);
        if(!response){
          this.toast.error('Your account is locked. You can login again for 24 hours.', 'Locked account');
          this.enterPin = false;
          this.loginForm.get('email').setValue('');
          this.loginForm.get('password').setValue('');
        }
      }
    );
  }


  ngOnDestroy(){
    if(this.authSubscription){
      this.authSubscription.unsubscribe();
    }
  }


}
