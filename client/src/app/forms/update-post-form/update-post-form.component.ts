import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Post } from 'src/app/models/entity/Post';
import { PostService } from 'src/app/services/post/post.service';

@Component({
  selector: 'app-update-post-form',
  templateUrl: './update-post-form.component.html',
  styleUrls: ['./update-post-form.component.scss'],
})
export class UpdatePostFormComponent {
  @Input() post!: Post;

  updateForm: FormGroup;
  constructor(
    private formBuilder: FormBuilder,
    private postService: PostService
  ) {
    this.updateForm = this.formBuilder.group({
      content: ['', [Validators.required]],
    });
  }

  ngOnInit() {
    this.updateForm = this.formBuilder.group({
      content: [this.post.content],
    });
  }

  onSubmit() {
    this.postService.updatePost(
      this.post.id,
      this.updateForm.get('content')?.value
    );
  }
}
