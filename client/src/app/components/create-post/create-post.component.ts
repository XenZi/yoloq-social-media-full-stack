import { Component, Input } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import Group from 'src/app/domains/entity/Group';
import { PostService } from 'src/app/services/post/post.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss'],
})
export class CreatePostComponent {
  createPostForm: FormGroup;
  @Input() group!: Group | null;
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
    if (this.group != null) {
      this.postService.createPost(content?.value, files?.value, this.group.id);
      return;
    }
    this.postService.createPost(content?.value, files?.value);
  }
}
