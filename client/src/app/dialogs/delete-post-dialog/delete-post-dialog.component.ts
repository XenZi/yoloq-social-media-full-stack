import { Component, Input } from '@angular/core';
import { ModalService } from 'src/app/services/modal/modal.service';
import { PostService } from 'src/app/services/post/post.service';

@Component({
  selector: 'app-delete-post-dialog',
  templateUrl: './delete-post-dialog.component.html',
  styleUrls: ['./delete-post-dialog.component.scss'],
})
export class DeletePostDialogComponent {
  @Input() text!: string;
  @Input() postID!: number;

  constructor(
    private modalService: ModalService,
    private postService: PostService
  ) {}

  confirmDelete() {
    this.modalService.closeModal();
    this.postService.deletePost(this.postID);
  }

  rejectDelete() {
    this.modalService.closeModal();
  }
}
