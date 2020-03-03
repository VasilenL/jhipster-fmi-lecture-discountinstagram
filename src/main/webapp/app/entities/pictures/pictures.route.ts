import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPictures, Pictures } from 'app/shared/model/pictures.model';
import { PicturesService } from './pictures.service';
import { PicturesComponent } from './pictures.component';
import { PicturesDetailComponent } from './pictures-detail.component';
import { PicturesUpdateComponent } from './pictures-update.component';

@Injectable({ providedIn: 'root' })
export class PicturesResolve implements Resolve<IPictures> {
  constructor(private service: PicturesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPictures> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pictures: HttpResponse<Pictures>) => {
          if (pictures.body) {
            return of(pictures.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pictures());
  }
}

export const picturesRoute: Routes = [
  {
    path: '',
    component: PicturesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'discountInstagramApp.pictures.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PicturesDetailComponent,
    resolve: {
      pictures: PicturesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'discountInstagramApp.pictures.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PicturesUpdateComponent,
    resolve: {
      pictures: PicturesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'discountInstagramApp.pictures.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PicturesUpdateComponent,
    resolve: {
      pictures: PicturesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'discountInstagramApp.pictures.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
