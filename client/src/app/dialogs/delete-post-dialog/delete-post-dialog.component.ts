import { Component, Input } from '@angular/core';
import { PostService } from 'src/app/services/post/post.service';

@Component({
  selector: 'app-delete-post-dialog',
  templateUrl: './delete-post-dialog.component.html',
  styleUrls: ['./delete-post-dialog.component.scss'],
})
export class DeletePostDialogComponent {
  @Input() text!: string;
  @Input() postID!: number;

  constructor(private postService: PostService) {}

  confirmDelete() {
    this.postService.deletePost(this.postID);
  }
}
