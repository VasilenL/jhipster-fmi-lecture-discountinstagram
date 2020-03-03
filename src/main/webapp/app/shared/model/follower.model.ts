import { IProfile } from 'app/shared/model/profile.model';

export interface IFollower {
  id?: string;
  profileName?: string;
  profile?: IProfile;
}

export class Follower implements IFollower {
  constructor(public id?: string, public profileName?: string, public profile?: IProfile) {}
}
