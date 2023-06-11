import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
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
      pictures: this.formBuilder.array([]),
    });
  }

  onFileChange(event: any) {
    const files = event.target.files;
    const fileArray = this.createPostForm.get('pictures') as FormArray;
    fileArray.clear();

    // Add each file to the form array
    for (let i = 0; i < files.length; i++) {
      const fileControl = this.formBuilder.control(files[i]);
      fileArray.push(fileControl);
    }
  }

  onSubmit() {
    const content = this.createPostForm.get('content');
    const files = this.createPostForm.get('pictures');
    this.postService.createPost(content?.value, files?.value);
  }
}
