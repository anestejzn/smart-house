import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-successful-verification',
  templateUrl: './successful-verification.component.html',
  styleUrls: ['./successful-verification.component.scss']
})
export class SuccessfulVerificationComponent {

  constructor(private router: Router) { }

  redirectToLogin() {
    this.router.navigate(['/login']);
  }

}
