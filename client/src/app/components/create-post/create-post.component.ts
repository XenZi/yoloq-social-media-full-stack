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
      title: ['', [Validators.required]],
      content: ['', [Validators.required]],
      pictures: this.formBuilder.array([]),
      attachedPDF: [''],
    });
  }

  onPDFFileChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.createPostForm.patchValue({
        attachedPDF: file
      });
      this.createPostForm.get('attachedPDF')?.updateValueAndValidity();
    }
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
    const title = this.createPostForm.get('title');
    const content = this.createPostForm.get('content');
    const files = this.createPostForm.get('pictures');
    const attachedPDF = this.createPostForm.get('attachedPDF');
    if (this.group != null) {
      this.postService.createPost(title?.value, content?.value, files?.value, attachedPDF?.value, this.group.id);
      return;
    }
    this.postService.createPost(title?.value, content?.value, files?.value, attachedPDF?.value);
  }
}
