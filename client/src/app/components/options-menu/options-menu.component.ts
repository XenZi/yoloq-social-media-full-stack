import {
  Component,
  ElementRef,
  EventEmitter,
  HostListener,
  Input,
  Output,
} from '@angular/core';
import OptionsItem from 'src/app/domains/model/OptionsItem';

@Component({
  selector: 'app-options-menu',
  templateUrl: './options-menu.component.html',
  styleUrls: ['./options-menu.component.scss'],
})
export class OptionsMenuComponent {
  @Input() optionItems: OptionsItem[] = [];
  @Input() isVisible!: boolean;
  @Output() closeMenu: EventEmitter<boolean> = new EventEmitter<boolean>();
  counter: number = 0;

  constructor(private _elementRef: ElementRef) {}

  clickedOnItem(item: OptionsItem) {
    item.onClick();
    this.closeOptionsMenu();
  }

  closeOptionsMenu() {
    this.closeMenu.emit(true);
  }
}
