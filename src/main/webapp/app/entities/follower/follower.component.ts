import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFollower } from 'app/shared/model/follower.model';
import { FollowerService } from './follower.service';
import { FollowerDeleteDialogComponent } from './follower-delete-dialog.component';

@Component({
  selector: 'jhi-follower',
  templateUrl: './follower.component.html'
})
export class FollowerComponent implements OnInit, OnDestroy {
  followers?: IFollower[];
  eventSubscriber?: Subscription;

  constructor(protected followerService: FollowerService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.followerService.query().subscribe((res: HttpResponse<IFollower[]>) => (this.followers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFollowers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFollower): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFollowers(): void {
    this.eventSubscriber = this.eventManager.subscribe('followerListModification', () => this.loadAll());
  }

  delete(follower: IFollower): void {
    const modalRef = this.modalService.open(FollowerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.follower = follower;
  }
}
