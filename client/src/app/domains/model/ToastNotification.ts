import { ToastNotificationType } from '../enums/ToastNotificationType';

export default interface ToastNotification {
  title: string;
  message: string;
  type: ToastNotificationType;
}
