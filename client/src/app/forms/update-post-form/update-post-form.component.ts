import { Component, Input } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Post } from 'src/app/domains/entity/Post';
import { PostService } from 'src/app/services/post/post.service';

@Component({
  selector: 'app-update-post-form',
  templateUrl: './update-post-form.component.html',
  styleUrls: ['./update-post-form.component.scss'],
})
export class UpdatePostFormComponent {
  @Input() post!: Post;
  updatedPost: Post | null;
  updateForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private postService: PostService
  ) {
    this.updateForm = this.formBuilder.group({
      content: ['', Validators.required],
      pictures: this.formBuilder.array([]),
    });
    this.updatedPost = null;
  }

  ngOnInit() {
    this.updateForm = this.formBuilder.group({
      content: [this.post.content],
      pictures: this.formBuilder.array([]),
    });
    this.updatedPost = { ...this.post };
  }

  removeSpecifiedPicture(event: MouseEvent, pictureURL: string) {
    event.stopPropagation();
    if (this.updatedPost == null) {
      return;
    }
    this.updatedPost.imagePaths = this.updatedPost?.imagePaths.filter(
      (url) => url !== pictureURL
    );
  }
  onFileChange(event: any) {
    const files = event.target.files;
    console.log(files);

    const fileArray = this.updateForm.get('pictures') as FormArray;
    fileArray.clear();

    // Add each file to the form array
    for (let i = 0; i < files.length; i++) {
      const fileControl = this.formBuilder.control(files[i]);
      fileArray.push(fileControl);
    }
  }

  onSubmit() {
    const files = this.updateForm.get('pictures');

    this.postService.updatePost(this.updatedPost as Post, files?.value);
  }
}
