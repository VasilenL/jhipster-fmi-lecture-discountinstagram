import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPictures, Pictures } from 'app/shared/model/pictures.model';
import { PicturesService } from './pictures.service';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from 'app/entities/profile/profile.service';

@Component({
  selector: 'jhi-pictures-update',
  templateUrl: './pictures-update.component.html'
})
export class PicturesUpdateComponent implements OnInit {
  isSaving = false;
  profiles: IProfile[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    fullTitle: [],
    description: [],
    profile: []
  });

  constructor(
    protected picturesService: PicturesService,
    protected profileService: ProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pictures }) => {
      this.updateForm(pictures);

      this.profileService.query().subscribe((res: HttpResponse<IProfile[]>) => (this.profiles = res.body || []));
    });
  }

  updateForm(pictures: IPictures): void {
    this.editForm.patchValue({
      id: pictures.id,
      name: pictures.name,
      fullTitle: pictures.fullTitle,
      description: pictures.description,
      profile: pictures.profile
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pictures = this.createFromForm();
    if (pictures.id !== undefined) {
      this.subscribeToSaveResponse(this.picturesService.update(pictures));
    } else {
      this.subscribeToSaveResponse(this.picturesService.create(pictures));
    }
  }

  private createFromForm(): IPictures {
    return {
      ...new Pictures(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      fullTitle: this.editForm.get(['fullTitle'])!.value,
      description: this.editForm.get(['description'])!.value,
      profile: this.editForm.get(['profile'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPictures>>): void {
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
