import { Component, Input } from '@angular/core';
import { Post } from 'src/app/domains/entity/Post';

@Component({
  selector: 'app-show-post-with-comments',
  templateUrl: './show-post-with-comments.component.html',
  styleUrls: ['./show-post-with-comments.component.scss'],
})
export class ShowPostWithCommentsComponent {
  @Input() post!: Post;
}
