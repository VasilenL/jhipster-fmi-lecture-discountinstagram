import { IProfile } from 'app/shared/model/profile.model';

export interface IPictures {
  id?: string;
  name?: string;
  fullTitle?: string;
  description?: string;
  profile?: IProfile;
}

export class Pictures implements IPictures {
  constructor(
    public id?: string,
    public name?: string,
    public fullTitle?: string,
    public description?: string,
    public profile?: IProfile
  ) {}
}
