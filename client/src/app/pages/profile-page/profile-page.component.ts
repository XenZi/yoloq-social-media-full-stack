import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Group from 'src/app/domains/entity/Group';
import { Post } from 'src/app/domains/entity/Post';
import User from 'src/app/domains/entity/User';
import { GroupService } from 'src/app/services/group/group.service';
import { PostService } from 'src/app/services/post/post.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.scss'],
})
export class ProfilePageComponent {
  loggedUser!: User;
  userID!: number;
  user!: User;
  groups!: Group[];
  posts!: Post[];
  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private groupService: GroupService,
    private postService: PostService
  ) {}

  ngOnInit() {
    this.loggedUser = this.userService.getUser() as User;
    this.route.paramMap.subscribe((params) => {
      this.userID = Number(params.get('id'));
    });
    this.userService.getUserDetails(this.userID).subscribe((data) => {
      this.user = data;
    });
    this.groupService.getAllGroupsForUser(this.userID).subscribe((data) => {
      this.groups = data;
    });
    this.postService.getAllPostsForUser(this.userID).subscribe((data) => {
      this.posts = data;
    });
  }
}
