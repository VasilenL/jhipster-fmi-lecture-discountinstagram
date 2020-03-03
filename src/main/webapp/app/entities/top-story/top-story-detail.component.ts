import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITopStory } from 'app/shared/model/top-story.model';

@Component({
  selector: 'jhi-top-story-detail',
  templateUrl: './top-story-detail.component.html'
})
export class TopStoryDetailComponent implements OnInit {
  topStory: ITopStory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ topStory }) => (this.topStory = topStory));
  }

  previousState(): void {
    window.history.back();
  }
}
