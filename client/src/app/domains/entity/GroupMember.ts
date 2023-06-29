import Group from './Group';
import User from './User';

export interface GroupMember {
  id: number;
  approved: boolean;
  createdAt: string;
  at: string;
  requestFrom: User;
  forGroup: Group;
}
