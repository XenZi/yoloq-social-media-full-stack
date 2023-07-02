import { ReportReason } from '../enums/ReportReason';
import Comment from './Comment';
import { Post } from './Post';
import User from './User';

export default interface Report {
  id: number;
  reportReason: ReportReason;
  timestamp: String;
  reportedPost: Post | null;
  byUser: User;
  reportedComment: Comment | null;
}
