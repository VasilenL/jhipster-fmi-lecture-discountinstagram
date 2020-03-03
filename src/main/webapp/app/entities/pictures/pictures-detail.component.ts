import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPictures } from 'app/shared/model/pictures.model';

@Component({
  selector: 'jhi-pictures-detail',
  templateUrl: './pictures-detail.component.html'
})
export class PicturesDetailComponent implements OnInit {
  pictures: IPictures | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pictures }) => (this.pictures = pictures));
  }

  previousState(): void {
    window.history.back();
  }
}
