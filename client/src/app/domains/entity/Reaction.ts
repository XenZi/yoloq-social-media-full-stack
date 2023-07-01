import { ReactionType } from '../enums/ReactionType';
import User from './User';

export default interface Reaction {
  id: number | null;
  type: ReactionType;
  reactedBy: User;
  commentIdReactedTo: number | null;
  postIdReactedTo: number | null;
}
