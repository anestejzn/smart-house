import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { UserService } from 'src/modules/shared/service/user-service/user.service';
import { VerificationService } from '../../service/verification/verification.service';

@Component({
  selector: 'app-verification',
  templateUrl: './verification.component.html',
  styleUrls: ['./verification.component.scss']
})
export class VerificationComponent implements OnInit {

  firstDigit: string;
  secondDigit: string;
  thirdDigit: string;
  fourthDigit: string;
  verifyId: string;
  showForm = true;
  MAX_DIGIT_LENGTH = 4;

  verifySubscription: Subscription;
  sendCodeAgainSubscription: Subscription;

  constructor(
    private verifyService: VerificationService,
    private route: ActivatedRoute,
    private toast: ToastrService,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.verifyId = this.route.snapshot.paramMap.get('id');
  }

  containsOnlyNumbers(str: string) {
    return /^\d+$/.test(str);
  }

  checkValidationCode(): boolean {
    const securityCode: string =
      this.firstDigit + this.secondDigit + this.thirdDigit + this.fourthDigit;
    if (securityCode.length !== this.MAX_DIGIT_LENGTH) {
      this.toast.error('You need to add 4 digits.', 'Error');

      return false;
    } else if (!this.containsOnlyNumbers(securityCode)) {
      this.toast.error('You can input only digits!', 'Error');

      return false;
    } 
    
    return true;
  }

  verify() {
    if (this.checkValidationCode()) {
      const securityCode: string =
        this.firstDigit + this.secondDigit + this.thirdDigit + this.fourthDigit;

      this.verifySubscription = this.userService
        .verify(
          this.verifyService.createVerifyRequest(
            this.verifyId,
            Number(securityCode),
          )
        )
        .subscribe(
          res => {
            if (res) {
              this.toast.success(
                'You became a new member of SmartHome!',
                'Verification successfully'
              );
              this.router.navigate(['/successfull-verification']);
            } else {
              this.toast.error('Verification failed, try again later.', 'Verification failed!')
            }
          },
          error => this.toast.error(error.error, 'Verification failed!')
        );
    }
  }

  sendCodeAgain() {
    this.sendCodeAgainSubscription = this.verifyService
      .sendCodeAgain(this.verifyId)
      .subscribe(
        res => (this.showForm = !this.showForm),
        error =>{
          this.toast.error('Email cannot be sent.', 'Code cannot be sent');
        }
      );
  }

  
  goToLoginPage(){
    this.router.navigate(["/login"]);
  }
  
  ngOnDestroy(): void {
    if (this.verifySubscription) {
      this.verifySubscription.unsubscribe();
    }

    if (this.sendCodeAgainSubscription)
      this.sendCodeAgainSubscription.unsubscribe();
  }
  
}
