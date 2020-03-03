import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITopStory } from 'app/shared/model/top-story.model';
import { TopStoryService } from './top-story.service';
import { TopStoryDeleteDialogComponent } from './top-story-delete-dialog.component';

@Component({
  selector: 'jhi-top-story',
  templateUrl: './top-story.component.html'
})
export class TopStoryComponent implements OnInit, OnDestroy {
  topStories?: ITopStory[];
  eventSubscriber?: Subscription;

  constructor(protected topStoryService: TopStoryService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.topStoryService.query().subscribe((res: HttpResponse<ITopStory[]>) => (this.topStories = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTopStories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITopStory): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTopStories(): void {
    this.eventSubscriber = this.eventManager.subscribe('topStoryListModification', () => this.loadAll());
  }

  delete(topStory: ITopStory): void {
    const modalRef = this.modalService.open(TopStoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.topStory = topStory;
  }
}
