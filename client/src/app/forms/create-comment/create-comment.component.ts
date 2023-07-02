import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import User from 'src/app/domains/entity/User';
import { CommentService } from 'src/app/services/comment/comment.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-create-comment',
  templateUrl: './create-comment.component.html',
  styleUrls: ['./create-comment.component.scss'],
})
export class CreateCommentComponent {
  @Input() postID!: number;
  @Input() parentPostID: number | null = null;
  createCommentForm: FormGroup;
  user!: User;
  constructor(
    private commentService: CommentService,
    private formBuilder: FormBuilder,
    private userService: UserService
  ) {
    this.createCommentForm = this.formBuilder.group({
      text: ['', Validators.required],
    });
  }

  ngOnInit() {
    this.user = this.userService.getUser() as User;
  }

  onSubmit() {
    const text = this.createCommentForm.get('text');
    this.commentService.createComment(text?.value, this.postID);
  }
}
