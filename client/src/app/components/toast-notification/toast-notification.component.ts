import { Component, Input } from '@angular/core';
import { ToastNotificationType } from 'src/app/domains/enums/ToastNotificationType';
import ToastNotification from 'src/app/domains/model/ToastNotification';
import { ToastService } from 'src/app/services/toast/toast.service';

@Component({
  selector: 'app-toast-notification',
  templateUrl: './toast-notification.component.html',
  styleUrls: ['./toast-notification.component.scss'],
})
export class ToastNotificationComponent {
  @Input() toast!: ToastNotification;
  constructor(private toastService: ToastService) {}

  ngOnInit(): void {
    setTimeout(() => {
      this.dismissToast();
    }, 3000);
  }

  dismissToast(): void {
    this.toastService.hideToast(this.toast);
  }
}
