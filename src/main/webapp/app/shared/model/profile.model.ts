import { ITopStory } from 'app/shared/model/top-story.model';
import { IFollower } from 'app/shared/model/follower.model';
import { IPictures } from 'app/shared/model/pictures.model';

export interface IProfile {
  id?: string;
  profileName?: string;
  name?: string;
  totalPosts?: number;
  totalFollowers?: number;
  follows?: number;
  profileDescription?: string;
  profilePicture?: string;
  topStories?: ITopStory[];
  followers?: IFollower[];
  pictures?: IPictures[];
}

export class Profile implements IProfile {
  constructor(
    public id?: string,
    public profileName?: string,
    public name?: string,
    public totalPosts?: number,
    public totalFollowers?: number,
    public follows?: number,
    public profileDescription?: string,
    public profilePicture?: string,
    public topStories?: ITopStory[],
    public followers?: IFollower[],
    public pictures?: IPictures[]
  ) {}
}
