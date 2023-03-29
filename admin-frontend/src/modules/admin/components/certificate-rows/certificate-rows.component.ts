import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SortedAliases } from '../../model/sorted-aliases';
import { CancelCertificateDialogComponent } from '../cancel-certificate-dialog/cancel-certificate-dialog.component';
import { CertificateDetailsDialogComponent } from '../certificate-details-dialog/certificate-details-dialog.component';

@Component({
  selector: 'app-certificate-rows',
  templateUrl: './certificate-rows.component.html',
  styleUrls: ['./certificate-rows.component.scss']
})
export class CertificateRowsComponent implements OnInit {
  @Input() aliases: SortedAliases[];
  @Output() canceledCertificateEvent = new EventEmitter();

  constructor(
    private detailsDialog: MatDialog
  ) {
    this.aliases = [];
  }

  ngOnInit(): void {
  }

  goToDetails(alias: SortedAliases): void {
    const dialogRef = this.detailsDialog.open(CertificateDetailsDialogComponent, {
      data: alias
    });
  }

  cancelCertificate(alias: SortedAliases): void {
    event.stopPropagation();
    const dialogRef = this.detailsDialog.open(CancelCertificateDialogComponent, {
      data: alias
    });

    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.canceledCertificateEvent.emit(res);
      }
    });
  }

}
