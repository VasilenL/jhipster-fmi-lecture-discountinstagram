import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITopStory } from 'app/shared/model/top-story.model';
import { TopStoryService } from './top-story.service';

@Component({
  templateUrl: './top-story-delete-dialog.component.html'
})
export class TopStoryDeleteDialogComponent {
  topStory?: ITopStory;

  constructor(protected topStoryService: TopStoryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.topStoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('topStoryListModification');
      this.activeModal.close();
    });
  }
}
