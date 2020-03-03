import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProfile, Profile } from 'app/shared/model/profile.model';
import { ProfileService } from './profile.service';

@Component({
  selector: 'jhi-profile-update',
  templateUrl: './profile-update.component.html'
})
export class ProfileUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    profileName: [],
    name: [],
    totalPosts: [],
    totalFollowers: [],
    follows: [],
    profileDescription: [],
    profilePicture: []
  });

  constructor(protected profileService: ProfileService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profile }) => {
      this.updateForm(profile);
    });
  }

  updateForm(profile: IProfile): void {
    this.editForm.patchValue({
      id: profile.id,
      profileName: profile.profileName,
      name: profile.name,
      totalPosts: profile.totalPosts,
      totalFollowers: profile.totalFollowers,
      follows: profile.follows,
      profileDescription: profile.profileDescription,
      profilePicture: profile.profilePicture
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const profile = this.createFromForm();
    if (profile.id !== undefined) {
      this.subscribeToSaveResponse(this.profileService.update(profile));
    } else {
      this.subscribeToSaveResponse(this.profileService.create(profile));
    }
  }

  private createFromForm(): IProfile {
    return {
      ...new Profile(),
      id: this.editForm.get(['id'])!.value,
      profileName: this.editForm.get(['profileName'])!.value,
      name: this.editForm.get(['name'])!.value,
      totalPosts: this.editForm.get(['totalPosts'])!.value,
      totalFollowers: this.editForm.get(['totalFollowers'])!.value,
      follows: this.editForm.get(['follows'])!.value,
      profileDescription: this.editForm.get(['profileDescription'])!.value,
      profilePicture: this.editForm.get(['profilePicture'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfile>>): void {
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
}
