import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Comment from 'src/app/domains/entity/Comment';
import { CommentService } from 'src/app/services/comment/comment.service';

@Component({
  selector: 'app-reply',
  templateUrl: './reply.component.html',
  styleUrls: ['./reply.component.scss'],
})
export class ReplyComponent {
  @Input() comment!: Comment;
  createReply: FormGroup;
  constructor(
    private commentService: CommentService,
    private formBuilder: FormBuilder
  ) {
    this.createReply = this.formBuilder.group({
      text: ['', Validators.required],
    });
  }

  createComment() {
    let text = this.createReply.get('text')?.value;
    this.commentService.createReplyOnComment(text, this.comment.id);
  }
}
