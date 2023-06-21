import { Component, Input } from '@angular/core';
import { Observable } from 'rxjs';
import Comment from 'src/app/domains/entity/Comment';
import { Post } from 'src/app/domains/entity/Post';

@Component({
  selector: 'app-show-post-with-comments',
  templateUrl: './show-post-with-comments.component.html',
  styleUrls: ['./show-post-with-comments.component.scss'],
})
export class ShowPostWithCommentsComponent {
  @Input() post!: Post;
  @Input() comments!: Observable<Comment[]>;
}
