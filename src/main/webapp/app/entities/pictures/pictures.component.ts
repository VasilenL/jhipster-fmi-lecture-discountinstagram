import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPictures } from 'app/shared/model/pictures.model';
import { PicturesService } from './pictures.service';
import { PicturesDeleteDialogComponent } from './pictures-delete-dialog.component';

@Component({
  selector: 'jhi-pictures',
  templateUrl: './pictures.component.html'
})
export class PicturesComponent implements OnInit, OnDestroy {
  pictures?: IPictures[];
  eventSubscriber?: Subscription;

  constructor(protected picturesService: PicturesService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.picturesService.query().subscribe((res: HttpResponse<IPictures[]>) => (this.pictures = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPictures();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPictures): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPictures(): void {
    this.eventSubscriber = this.eventManager.subscribe('picturesListModification', () => this.loadAll());
  }

  delete(pictures: IPictures): void {
    const modalRef = this.modalService.open(PicturesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pictures = pictures;
  }
}
