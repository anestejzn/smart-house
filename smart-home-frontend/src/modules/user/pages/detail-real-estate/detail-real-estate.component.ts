import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { RealEstate } from 'src/modules/shared/model/real-estate';
import { RealEstateService } from '../../service/real-estate.service';
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
              private toast: ToastrService) 
  {
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
        this.toast.error(`Real estate does not exist!`, 'Error happened!');
      }
    )
  }

  updatedData(): void {
    this.loadRealEstateData();
  }

  ngOnDestroy(): void {
    if (this.realEstateSubscription) {
      this.realEstateSubscription.unsubscribe();
    }
  }

}
