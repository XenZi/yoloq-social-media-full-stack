import User from './User';

export interface FriendRequest {
  id: number;
  userFrom: User;
  userTo: User;
  approved: boolean;
  createdAt: string;
  at: string;
}
