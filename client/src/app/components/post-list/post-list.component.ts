import { Component } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { Post } from 'src/app/domains/entity/Post';
import { PostService } from 'src/app/services/post/post.service';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.scss'],
})
export class PostListComponent {
  posts!: Observable<Post[]>;
  isSortListOpen: boolean = false;
  currentSort: number = -1;
  private postsChangedSubscription!: Subscription;

  constructor(private postService: PostService) {}

  ngOnInit() {
    this.fetchPosts();
    this.postsChangedSubscription = this.postService
      .getPostsChangedObservable()
      .subscribe(() => {
        this.fetchPosts();
      });
  }

  private fetchPosts() {
    this.posts = this.postService.getAll();
  }

  ngOnDestroy() {
    // Unsubscribe to prevent memory leaks
    this.postsChangedSubscription.unsubscribe();
  }

  changeSortListOpenState() {
    this.isSortListOpen = !this.isSortListOpen;
  }

  changeCurrentSort(newSortNumber: number) {
    console.log('test');

    this.currentSort = newSortNumber;
    this.posts = this.postService.getAllPostsInOrder(
      this.currentSort == 1 ? 'asc' : 'dsc'
    );
  }
}
