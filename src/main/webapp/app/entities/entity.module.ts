import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'profile',
        loadChildren: () => import('./profile/profile.module').then(m => m.DiscountInstagramProfileModule)
      },
      {
        path: 'top-story',
        loadChildren: () => import('./top-story/top-story.module').then(m => m.DiscountInstagramTopStoryModule)
      },
      {
        path: 'follower',
        loadChildren: () => import('./follower/follower.module').then(m => m.DiscountInstagramFollowerModule)
      },
      {
        path: 'pictures',
        loadChildren: () => import('./pictures/pictures.module').then(m => m.DiscountInstagramPicturesModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class DiscountInstagramEntityModule {}
