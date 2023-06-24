import { ChangeDetectorRef, Component, Input } from '@angular/core';
import { Observable } from 'rxjs';
import Reaction from 'src/app/domains/entity/Reaction';
import { ReactionType } from 'src/app/domains/enums/ReactionType';
import { ReactionExistance } from 'src/app/domains/model/ReactionExistance';
import { ReactionService } from 'src/app/services/reaction/reaction.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-reactions',
  templateUrl: './reactions.component.html',
  styleUrls: ['./reactions.component.scss'],
})
export class ReactionsComponent {
  @Input() postID: number | null = null;
  @Input() commentID: number | null = null;
  @Input() reactions!: Observable<Reaction[]>;
  allReactions!: Reaction[];
  totalReactions: number = 0;
  reactionExistance: ReactionExistance = {
    doesExist: false,
    reactionID: null,
    reactionType: null,
  };
  constructor(
    private reactionService: ReactionService,
    private userService: UserService,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  likeEntity() {
    if (this.reactionExistance.doesExist) {
      console.log('test');

      if (this.reactionExistance.reactionType === ReactionType.Like) {
        this.reactionService.deleteReaction(
          this.reactionExistance.reactionID as number
        );
        return;
      }

      this.reactionService.updateReaction(
        this.reactionExistance.reactionID as number,
        ReactionType.Like
      );
      return;
    }

    if (this.postID != null) {
      this.reactionService.createReactionForPost(
        ReactionType.Like,
        this.postID
      );
      return;
    }
    if (this.commentID != null) {
      console.log('test');
      this.reactionService.createReactionForComment(
        ReactionType.Like,
        this.commentID
      );
    }
  }

  dislikeEntity() {
    if (this.reactionExistance.doesExist) {
      if (this.reactionExistance.reactionType === ReactionType.Dislike) {
        this.reactionService.deleteReaction(
          this.reactionExistance.reactionID as number
        );
        return;
      }

      this.reactionService.updateReaction(
        this.reactionExistance.reactionID as number,
        ReactionType.Dislike
      );
      return;

      return;
    }

    if (this.postID != null) {
      this.reactionService.createReactionForPost(
        ReactionType.Dislike,
        this.postID
      );
      return;
    }
    if (this.commentID != null) {
      this.reactionService.createReactionForComment(
        ReactionType.Dislike,
        this.commentID
      );
    }
  }

  heartEntity() {
    if (this.reactionExistance.doesExist) {
      if (this.reactionExistance.reactionType === ReactionType.Heart) {
        this.reactionService.deleteReaction(
          this.reactionExistance.reactionID as number
        );
        return;
      }

      this.reactionService.updateReaction(
        this.reactionExistance.reactionID as number,
        ReactionType.Heart
      );
      return;
    }

    if (this.postID != null) {
      this.reactionService.createReactionForPost(
        ReactionType.Heart,
        this.postID
      );
      return;
    }
    if (this.commentID != null) {
      this.reactionService.createReactionForComment(
        ReactionType.Heart,
        this.commentID
      );
    }
  }

  ngOnInit() {
    this.reactions.subscribe((data) => {
      this.totalReactions = data.length;
      this.allReactions = data;
      let user = this.userService.getUser();
      this.allReactions.forEach((reaction) => {
        if (reaction.reactedBy.id == user?.id) {
          this.reactionExistance = {
            doesExist: true,
            reactionID: reaction.id,
            reactionType: reaction.type,
          };
        }
      });
    });
  }
}
