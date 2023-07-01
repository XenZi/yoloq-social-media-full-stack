import { Component, Input } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { DeleteCommentDialogComponent } from 'src/app/dialogs/delete-comment-dialog/delete-comment-dialog.component';
import Comment from 'src/app/domains/entity/Comment';
import Reaction from 'src/app/domains/entity/Reaction';
import User from 'src/app/domains/entity/User';
import OptionsItem from 'src/app/domains/model/OptionsItem';
import { UpdateCommentFormComponent } from 'src/app/forms/update-comment-form/update-comment-form.component';
import { CommentService } from 'src/app/services/comment/comment.service';
import { ModalService } from 'src/app/services/modal/modal.service';
import { ReactionService } from 'src/app/services/reaction/reaction.service';
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
  totalReactions!: Observable<Reaction[]>;
  isReplyVisible: boolean = false;

  private repliesChangedSubscription!: Subscription;

  constructor(
    private userService: UserService,
    private modalService: ModalService,
    private commentService: CommentService,
    private reactionService: ReactionService
  ) {}

  ngOnInit() {
    this.initializeOptionList();
    this.totalReactions = this.reactionService.getAllReactionsForComment(
      this.comment.id
    );
    this.repliesChangedSubscription = this.commentService
      .getRepliesChangedObservable()
      .subscribe(() => {
        this.fetchReplies();
      });
  }

  fetchReplies() {
    this.commentService
      .getRepliesForComment(this.comment.id)
      .subscribe((data) => {
        this.comment.commentReplies = data;
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
    this.modalService.close();
    this.modalService.open(UpdateCommentFormComponent, {
      comment: this.comment,
    });
  }

  openDialogForCommentDelete() {
    this.modalService.close();
    this.modalService.open(DeleteCommentDialogComponent, {
      text: 'Do you really want to delete your comment?',
      commentID: this.comment.id,
    });
  }

  reportPost() {
    console.log('report post');
  }

  replyOnPost() {
    this.isReplyVisible = !this.isReplyVisible;
  }
}
