import { Component, Input } from '@angular/core';
import { DeletePostDialogComponent } from 'src/app/dialogs/delete-post-dialog/delete-post-dialog.component';
import { UpdatePostFormComponent } from 'src/app/forms/update-post-form/update-post-form.component';
import { Post } from 'src/app/domains/entity/Post';
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

  showOptionsList() {
    this.optionsListVisible = true;
  }

  hideOptionsList() {
    this.optionsListVisible = false;
  }

  openUpdatePostForm() {
    const overlayRef = this.modalService.open(UpdatePostFormComponent, {
      post: this.post,
    });
  }

  openDialogForPostDelete() {
    this.modalService.open(DeletePostDialogComponent, {
      text: 'Do you really want to delete this post?',
      postID: this.post.id,
    });
  }
}
