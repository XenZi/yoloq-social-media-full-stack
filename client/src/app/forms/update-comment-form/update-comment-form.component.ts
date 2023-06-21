import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Comment from 'src/app/domains/entity/Comment';
import { CommentService } from 'src/app/services/comment/comment.service';

@Component({
  selector: 'app-update-comment-form',
  templateUrl: './update-comment-form.component.html',
  styleUrls: ['./update-comment-form.component.scss'],
})
export class UpdateCommentFormComponent {
  @Input() comment!: Comment;
  updateForm: FormGroup;
  updatedComment: Comment | undefined;
  constructor(
    private formBuilder: FormBuilder,
    private commentService: CommentService
  ) {
    this.updateForm = this.formBuilder.group({
      text: ['', Validators.required],
    });
  }

  ngOnInit() {
    this.updateForm = this.formBuilder.group({
      text: [this.comment.text, Validators.required],
    });
    this.updatedComment = { ...this.comment };
  }

  submitUpdate() {
    if (!this.updatedComment) {
      return;
    }

    this.updatedComment.text = this.updateForm.get('text')?.value;
    this.commentService.updateComment(this.updatedComment);
  }
}
