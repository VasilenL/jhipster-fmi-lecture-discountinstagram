import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITopStory, TopStory } from 'app/shared/model/top-story.model';
import { TopStoryService } from './top-story.service';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from 'app/entities/profile/profile.service';

@Component({
  selector: 'jhi-top-story-update',
  templateUrl: './top-story-update.component.html'
})
export class TopStoryUpdateComponent implements OnInit {
  isSaving = false;
  profiles: IProfile[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    profile: []
  });

  constructor(
    protected topStoryService: TopStoryService,
    protected profileService: ProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ topStory }) => {
      this.updateForm(topStory);

      this.profileService.query().subscribe((res: HttpResponse<IProfile[]>) => (this.profiles = res.body || []));
    });
  }

  updateForm(topStory: ITopStory): void {
    this.editForm.patchValue({
      id: topStory.id,
      title: topStory.title,
      profile: topStory.profile
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const topStory = this.createFromForm();
    if (topStory.id !== undefined) {
      this.subscribeToSaveResponse(this.topStoryService.update(topStory));
    } else {
      this.subscribeToSaveResponse(this.topStoryService.create(topStory));
    }
  }

  private createFromForm(): ITopStory {
    return {
      ...new TopStory(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      profile: this.editForm.get(['profile'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITopStory>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IProfile): any {
    return item.id;
  }
}
