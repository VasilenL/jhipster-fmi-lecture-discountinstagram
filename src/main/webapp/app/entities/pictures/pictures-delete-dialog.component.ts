import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPictures } from 'app/shared/model/pictures.model';
import { PicturesService } from './pictures.service';

@Component({
  templateUrl: './pictures-delete-dialog.component.html'
})
export class PicturesDeleteDialogComponent {
  pictures?: IPictures;

  constructor(protected picturesService: PicturesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.picturesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('picturesListModification');
      this.activeModal.close();
    });
  }
}
