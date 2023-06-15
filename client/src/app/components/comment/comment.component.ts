import { Component, Input } from '@angular/core';
import Comment from 'src/app/domains/entity/Comment';
import User from 'src/app/domains/entity/User';
import OptionsItem from 'src/app/domains/model/OptionsItem';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss'],
})
export class CommentComponent {
  @Input() comment!: Comment;
  optionsListVisible = false;
  options: OptionsItem[] = [];
  constructor(private userService: UserService) {}

  ngOnInit() {
    this.initializeOptionList();
  }

  toggleOptionsList() {
    this.optionsListVisible = !this.optionsListVisible;
  }

  initializeOptionList() {
    let user: User | null = this.userService.getUser();

    if (user == null) {
      return;
    }

    if (user.id == this.comment?.postedBy?.id) {
      this.options = [
        {
          icon: 'fa-solid fa-pen-to-square',
          text: 'Update comment',
          onClick: () => {
            this.openUpdateCommentForm();
          },
        },
        {
          icon: 'fa-solid fa-trash',
          text: 'Delete comment',
          onClick: () => {
            this.openDialogForCommentDelete();
          },
        },
      ];
      return;
    }
    this.options = [
      {
        icon: 'fa-solid fa-triangle-exclamation',
        text: 'Report comment',
        onClick: () => {
          this.reportPost();
        },
      },
    ];
  }

  openUpdateCommentForm() {
    console.log('update comment');
  }

  openDialogForCommentDelete() {
    console.log('delete comment');
  }

  reportPost() {
    console.log('report post');
  }
}
