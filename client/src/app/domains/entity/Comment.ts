import User from './User';

export default interface Comment {
  id: number;
  createdAt: string;
  postedBy: User;
  text: string;
  postId: number;
  parentCommentID: number;
  commentReplies: Comment[];
}
