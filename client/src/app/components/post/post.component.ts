import { Component, Input } from '@angular/core';
import { Post } from 'src/app/models/entity/Post';
import { ModalService } from 'src/app/services/modal/modal.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss'],
})
export class PostComponent {
  @Input() post!: Post;
  optionsListVisible = false;
  clickedInputNumber: number = -1;
  constructor(private modalService: ModalService) {}

  openUpdateModalForm() {
    this.clickedInputNumber = 1;
    this.modalService.openModal();
  }

  openModalDialogForDelete() {
    this.clickedInputNumber = -1;
    this.modalService.openModal();
  }

  showOptionsList() {
    this.optionsListVisible = true;
  }

  hideOptionsList() {
    this.optionsListVisible = false;
  }
}
