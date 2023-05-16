import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PostService } from 'src/app/services/post/post.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss'],
})
export class CreatePostComponent {
  createPostForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private postService: PostService
  ) {
    this.createPostForm = this.formBuilder.group({
      content: ['', [Validators.required]],
    });
  }

  onSubmit() {
    const content = this.createPostForm.get('content');
    this.postService.createPost(content?.value);
  }
}
