import User from './User';

export interface Post {
  id: number;
  content: string;
  imagePaths: string[];
  totalComments: number;
  comments: Comment | null;
  postedIn: number;
  postedBy: User;
  creationDate: string;
}
