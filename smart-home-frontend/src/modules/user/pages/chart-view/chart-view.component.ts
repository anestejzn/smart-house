import { DatePipe } from '@angular/common';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import Chart from 'chart.js/auto';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/modules/auth/service/auth/auth.service';
import { ReportData, ReportRequest } from 'src/modules/shared/model/report';
import { User } from 'src/modules/shared/model/user';
import { AlarmService } from '../../service/alarm-service/alarm.service';
import { Subscription } from 'rxjs';

const today = new Date();
const month = today.getMonth();
const year = today.getFullYear();

@Component({
  selector: 'app-chart-view',
  templateUrl: './chart-view.component.html',
  styleUrls: ['./chart-view.component.scss']
})
export class ChartViewComponent implements OnInit, OnDestroy {

  dateFormGroup = new FormGroup({
    start: new FormControl(new Date(year, month, 1)),
    end: new FormControl(new Date(year, month, 25)),
  });

  // @ViewChild('chartCanvas') chartCanvas: any;

  chart: Chart;
  loggedUser: User;
  chartData: ReportData[] = [];

  authSubscription: Subscription;
  alarmSubscription: Subscription;

  constructor(private toast: ToastrService,
            private datePipe: DatePipe,
            private authService: AuthService,
            private alarmService: AlarmService,
  ) { }

  ngOnInit(): void {
    this.authSubscription = this.authService.getSubjectCurrentUser().subscribe(
      res => {
        this.loggedUser = res;
        this.generateReport();
      }
    )

    this.createChart();
    this.generateReport();
  }

  generateReport(): void {
    if (this.loggedUser.role.roleName === "ROLE_OWNER") {
        let request: ReportRequest = {
        userId: this.loggedUser.id,
        startTime: this.dateFormGroup.get('start').value,
        endTime: this.dateFormGroup.get('end').value
      }

      console.log(request)

      this.alarmSubscription = this.alarmService.getReportData(request).subscribe(
        res =>{
          this.chartData = res;
          this.chart.data.datasets[0].data = this.chartData.map(row => row.numOfAlarms);
          this.chart.options.plugins.title.text = `Report for a period ${this.datePipe.transform(this.dateFormGroup.get('start').value, 'dd.MM.yyyy.')} to ${this.datePipe.transform(this.dateFormGroup.get('end').value, 'dd.MM.yyyy.')}`;
          this.chart.data.labels = this.chartData.map(row => row.realEstateName);
          this.chart.update();
          console.log(res);
        },
        err => {
          this.toast.error(err.error, 'Error happened');
        }
      );
    }
  }


  createChart(): void {
    this.chart = new Chart("chart", {
      data: {
      labels: this.chartData.map(row => row.realEstateName),
        datasets: [{
        label: 'Number of alarms',
        data: this.chartData.map(row => row.numOfAlarms),
        backgroundColor: [
          'rgba(110, 202, 68, 0.2)'
        ],
        borderWidth: 1
      }],
    },
      type: 'bar',
      options: {
        plugins: {
          title: {
            display: true,
            font: {
              size: 20
            },
            text: `Report for a period ${this.datePipe.transform(this.dateFormGroup.get('start').value, 'dd.MM.yyyy.')} to ${this.datePipe.transform(this.dateFormGroup.get('end').value, 'dd.MM.yyyy.')}`
          }
        }
      }
    });
  }

  ngOnDestroy(): void {
      if (this.authSubscription) {
        this.authSubscription.unsubscribe();
      }
  }

}
