import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from './profile.service';
import { ProfileDeleteDialogComponent } from './profile-delete-dialog.component';

@Component({
  selector: 'jhi-profile',
  templateUrl: './profile.component.html'
})
export class ProfileComponent implements OnInit, OnDestroy {
  profiles?: IProfile[];
  eventSubscriber?: Subscription;

  constructor(protected profileService: ProfileService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.profileService.query().subscribe((res: HttpResponse<IProfile[]>) => (this.profiles = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProfiles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProfile): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProfiles(): void {
    this.eventSubscriber = this.eventManager.subscribe('profileListModification', () => this.loadAll());
  }

  delete(profile: IProfile): void {
    const modalRef = this.modalService.open(ProfileDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.profile = profile;
  }
}
