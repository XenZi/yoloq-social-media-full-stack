import { Component, Input } from '@angular/core';
import { DeletePostDialogComponent } from 'src/app/dialogs/delete-post-dialog/delete-post-dialog.component';
import { UpdatePostFormComponent } from 'src/app/forms/update-post-form/update-post-form.component';
import { Post } from 'src/app/domains/entity/Post';
import { ModalService } from 'src/app/services/modal/modal.service';
import OptionsItem from 'src/app/domains/model/OptionsItem';
import { UserService } from 'src/app/services/user/user.service';
import User from 'src/app/domains/entity/User';
import { Observable } from 'rxjs';
import { ShowPostWithCommentsComponent } from '../show-post-with-comments/show-post-with-comments.component';
import Comment from 'src/app/domains/entity/Comment';
import { CommentService } from 'src/app/services/comment/comment.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss'],
})
export class PostComponent {
  @Input() post!: Post;
  optionsListVisible = false;
  clickedInputNumber: number = -1;
  comments: Observable<Comment[]> | undefined;
  options: OptionsItem[] = [];
  totalComments: number | undefined;
  constructor(
    private modalService: ModalService,
    private userService: UserService,
    private commentService: CommentService
  ) {}

  ngOnInit() {
    this.initializeOptionList();
    this.comments = this.commentService.getAllCommentsForPost(this.post.id);
    if (this.comments === undefined) {
      this.totalComments = 0;
      return;
    }
    this.comments.subscribe((data) => {
      this.totalComments = data.length;
    });
  }

  toggleOptionsList() {
    this.optionsListVisible = !this.optionsListVisible;
  }

  initializeOptionList() {
    let user: User | null = this.userService.getUser();
    if (user == null) {
      return;
    }
    if (user.id == this.post.postedBy.id) {
      this.options = [
        {
          icon: 'fa-solid fa-pen-to-square',
          text: 'Update post',
          onClick: () => {
            this.openUpdatePostForm();
          },
        },
        {
          icon: 'fa-solid fa-trash',
          text: 'Delete post',
          onClick: () => {
            this.openDialogForPostDelete();
          },
        },
      ];
      return;
    }
    this.options = [
      {
        icon: 'fa-solid fa-triangle-exclamation',
        text: 'Report post',
        onClick: this.reportPost,
      },
    ];
  }

  openUpdatePostForm() {
    const overlayRef = this.modalService.open(UpdatePostFormComponent, {
      post: this.post,
    });
    this.toggleOptionsList();
  }

  openDialogForPostDelete() {
    this.modalService.open(DeletePostDialogComponent, {
      text: 'Do you really want to delete this post?',
      postID: this.post.id,
    });
    this.toggleOptionsList();
  }

  reportPost() {
    console.log('report');
    this.toggleOptionsList();
  }

  closeOptionsMenu() {
    this.toggleOptionsList();
  }

  openModalWithComments() {
    this.modalService.open(ShowPostWithCommentsComponent, {
      post: this.post,
      comments: this.comments,
    });
  }
}
