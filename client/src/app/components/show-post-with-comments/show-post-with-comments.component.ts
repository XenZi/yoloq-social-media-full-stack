import { Component, Input } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import Comment from 'src/app/domains/entity/Comment';
import { Post } from 'src/app/domains/entity/Post';
import { CommentService } from 'src/app/services/comment/comment.service';

@Component({
  selector: 'app-show-post-with-comments',
  templateUrl: './show-post-with-comments.component.html',
  styleUrls: ['./show-post-with-comments.component.scss'],
})
export class ShowPostWithCommentsComponent {
  @Input() post!: Post;
  @Input() comments!: Observable<Comment[]>;

  private commentChangedSubscription!: Subscription;

  constructor(private commentService: CommentService) {}

  ngOnInit() {
    this.commentChangedSubscription = this.commentService
      .getCommentsChangedObservable()
      .subscribe((postId) => {
        if (postId === this.post.id) {
          this.fetchComments();
        }
      });
  }

  private fetchComments() {
    this.comments = this.commentService.getAllCommentsForPost(this.post.id);
  }
}
