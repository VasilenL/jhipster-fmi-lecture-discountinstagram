import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITopStory, TopStory } from 'app/shared/model/top-story.model';
import { TopStoryService } from './top-story.service';
import { TopStoryComponent } from './top-story.component';
import { TopStoryDetailComponent } from './top-story-detail.component';
import { TopStoryUpdateComponent } from './top-story-update.component';

@Injectable({ providedIn: 'root' })
export class TopStoryResolve implements Resolve<ITopStory> {
  constructor(private service: TopStoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITopStory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((topStory: HttpResponse<TopStory>) => {
          if (topStory.body) {
            return of(topStory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TopStory());
  }
}

export const topStoryRoute: Routes = [
  {
    path: '',
    component: TopStoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'discountInstagramApp.topStory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TopStoryDetailComponent,
    resolve: {
      topStory: TopStoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'discountInstagramApp.topStory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TopStoryUpdateComponent,
    resolve: {
      topStory: TopStoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'discountInstagramApp.topStory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TopStoryUpdateComponent,
    resolve: {
      topStory: TopStoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'discountInstagramApp.topStory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
