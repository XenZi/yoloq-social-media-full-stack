import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss'],
})
export class ButtonComponent {
  @Input() size!: string;
  @Input() color!: string;
  @Input() text!: string;
  @Input() class: string | undefined;
  @Input() clickHandler!: () => void;

  onClick(): void {
    if (this.clickHandler) {
      this.clickHandler();
    }
  }
}
