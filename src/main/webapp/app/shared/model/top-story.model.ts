import { IProfile } from 'app/shared/model/profile.model';

export interface ITopStory {
  id?: string;
  title?: string;
  profile?: IProfile;
}

export class TopStory implements ITopStory {
  constructor(public id?: string, public title?: string, public profile?: IProfile) {}
}
