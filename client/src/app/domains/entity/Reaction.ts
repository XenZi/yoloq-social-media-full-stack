/*
    private Integer id;
    private ReactionType type;
    private String timestamp;
    private Integer commentIdReactedTo;
    private Integer postIdReactedTo;
    private UserDTO reactedBy;*/

import { ReactionType } from '../enums/ReactionType';
import User from './User';

export default interface Reaction {
  id: number | null;
  type: ReactionType;
  reactedBy: User;
  commentIdReactedTo: number | null;
  postIdReactedTo: number | null;
}
