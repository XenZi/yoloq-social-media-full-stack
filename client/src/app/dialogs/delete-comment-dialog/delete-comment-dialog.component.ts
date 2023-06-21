import { Component, Input } from '@angular/core';
import { CommentService } from 'src/app/services/comment/comment.service';

@Component({
  selector: 'app-delete-comment-dialog',
  templateUrl: './delete-comment-dialog.component.html',
  styleUrls: ['./delete-comment-dialog.component.scss'],
})
export class DeleteCommentDialogComponent {
  @Input() text!: string;
  @Input() commentID!: number;
  constructor(private commentService: CommentService) {}

  confirmDelete() {
    this.commentService.deleteComment(this.commentID);
  }
}
