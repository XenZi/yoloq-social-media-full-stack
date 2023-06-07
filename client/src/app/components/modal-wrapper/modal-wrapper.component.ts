import {
  Component,
  OnInit,
  ViewChild,
  ComponentFactoryResolver,
  OnDestroy,
  ComponentRef,
  HostListener,
  ElementRef,
  Inject,
} from '@angular/core';
import { DynamicHostDirective } from 'src/app/directives/dynamic-host-directive/dynamic-host.directive';
import { ModalService } from 'src/app/services/modal/modal.service';

@Component({
  selector: 'app-modal-wrapper',
  templateUrl: './modal-wrapper.component.html',
  styleUrls: ['./modal-wrapper.component.scss'],
})
export class ModalWrapperComponent {
  private counter: number = 0;

  @ViewChild(DynamicHostDirective, { static: true })
  dynamicHost!: DynamicHostDirective;
  constructor(
    private componentFactoryResolver: ComponentFactoryResolver,
    private modalService: ModalService,
    private _elementRef: ElementRef
  ) {}

  loadChildComponent(componentType: any): ComponentRef<any> {
    let componentFactory =
      this.componentFactoryResolver.resolveComponentFactory(componentType);
    let viewContainerRef = this.dynamicHost.viewContainerRef;

    viewContainerRef.clear();
    return viewContainerRef.createComponent(componentFactory);
  }

  @HostListener('document:click', ['$event'])
  public onDocumentClick(event: MouseEvent): void {
    const clickedInside = this._elementRef.nativeElement.contains(event.target);
    if (!clickedInside && this.modalService.isOpen() && this.counter > 0) {
      this.modalService.close();
      this.counter = 0;
      return;
    }
    this.counter++;
  }
}
