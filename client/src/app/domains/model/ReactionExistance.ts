import { ReactionType } from '../enums/ReactionType';

export interface ReactionExistance {
  doesExist: boolean;
  reactionType: ReactionType | null;
  reactionID: number | null;
}
