import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RealEstateService } from '../../service/real-estate/real-estate.service';
import { Subscription } from 'rxjs';
import { RealEstate } from '../../model/real-estate';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-detail-real-estate',
  templateUrl: './detail-real-estate.component.html',
  styleUrls: ['./detail-real-estate.component.scss']
})
export class DetailRealEstateComponent implements OnInit, OnDestroy {

  id: string;
  realEstate: RealEstate;
  realEstateSubscription: Subscription;

  constructor(private route: ActivatedRoute,
              private realEstateService: RealEstateService,
              private toast: ToastrService
  ) {
    this.realEstate = null;
    this.id = '';
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    if (this.id) {
      this.loadRealEstateData();
    }
  }

  loadRealEstateData(): void {
    this.realEstateSubscription = this.realEstateService.getRealEstate(this.id).subscribe(
      res => {
        if (res) {
          this.realEstate = res;
        }
      },
      err => {
        this.toast.error(`Real estate with ${this.id} does not exist!`, 'Error happened!');
      }
    )
  }

  ngOnDestroy(): void {
    if (this.realEstateSubscription) {
      this.realEstateSubscription.unsubscribe();
    }
  }

}
