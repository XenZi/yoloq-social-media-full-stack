import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommentService } from 'src/app/services/comment/comment.service';

@Component({
  selector: 'app-create-comment',
  templateUrl: './create-comment.component.html',
  styleUrls: ['./create-comment.component.scss'],
})
export class CreateCommentComponent {
  @Input() postID!: number;
  @Input() parentPostID: number | null = null;
  createCommentForm: FormGroup;
  constructor(
    private commentService: CommentService,
    private formBuilder: FormBuilder
  ) {
    this.createCommentForm = this.formBuilder.group({
      text: ['', Validators.required],
    });
  }

  onSubmit() {
    const text = this.createCommentForm.get('text');
    this.commentService.createComment(text?.value, this.postID);
  }
}
