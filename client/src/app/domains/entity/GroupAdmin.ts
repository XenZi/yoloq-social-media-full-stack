import User from './User';

export interface GroupAdmin {
  id: number;
  user: User;
  groupID: number;
}
