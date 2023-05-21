import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from 'src/app/models/entity/Post';
import { PostService } from 'src/app/services/post/post.service';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.scss'],
})
export class PostListComponent {
  posts!: Observable<Post[]>;
  constructor(private postService: PostService) {}

  ngOnInit() {
    this.posts = this.postService.getAll();
  }
}
