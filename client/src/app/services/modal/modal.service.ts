import {
  Injectable,
  Injector,
  ComponentRef,
  ComponentFactoryResolver,
  Type,
} from '@angular/core';
import { Overlay, OverlayConfig, OverlayRef } from '@angular/cdk/overlay';
import { ComponentPortal, PortalInjector } from '@angular/cdk/portal';
import { ModalWrapperComponent } from 'src/app/components/modal-wrapper/modal-wrapper.component';

@Injectable()
export class ModalService {
  private overlayRef: OverlayRef | null = null;

  constructor(
    private overlay: Overlay,
    private injector: Injector,
    private cfr: ComponentFactoryResolver
  ) {}

  public open<T>(component: Type<T>, inputs: Partial<T>): void {
    const overlayConfig = new OverlayConfig({
      hasBackdrop: true,
      panelClass: 'modal',
    });
    this.overlayRef = this.overlay.create(overlayConfig);
    const wrapperRef = this.attachComponent(
      this.overlayRef,
      ModalWrapperComponent,
      {}
    );

    const componentRef: ComponentRef<any> =
      wrapperRef.instance.loadChildComponent(component);

    if (componentRef) {
      this.assignInputs(componentRef.instance, inputs);
    }

    this.overlayRef.backdropClick().subscribe(() => {
      console.log('test');

      this.close();
    });
  }

  public close(): void {
    if (this.overlayRef) {
      this.overlayRef.detach();
      this.overlayRef = null;
    }
  }

  private attachComponent<T>(
    overlayRef: OverlayRef,
    component: Type<T>,
    inputs: Partial<T>
  ): ComponentRef<T> {
    const injector = this.createInjector(overlayRef);
    const portal = new ComponentPortal(component, null, injector);
    const componentRef = overlayRef.attach(portal);

    this.assignInputs(componentRef.instance, inputs);

    return componentRef;
  }

  private assignInputs<T>(instance: T, inputs: Partial<T>) {
    for (const key of Object.keys(inputs) as Array<keyof T>) {
      instance[key] = inputs[key] as T[keyof T];
    }
  }

  private createInjector(overlayRef: OverlayRef): PortalInjector {
    const tokens = new WeakMap();
    tokens.set(OverlayRef, overlayRef);

    return new PortalInjector(this.injector, tokens);
  }

  public isOpen(): boolean {
    return this.overlayRef !== null;
  }
}
