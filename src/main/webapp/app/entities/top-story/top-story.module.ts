import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiscountInstagramSharedModule } from 'app/shared/shared.module';
import { TopStoryComponent } from './top-story.component';
import { TopStoryDetailComponent } from './top-story-detail.component';
import { TopStoryUpdateComponent } from './top-story-update.component';
import { TopStoryDeleteDialogComponent } from './top-story-delete-dialog.component';
import { topStoryRoute } from './top-story.route';

@NgModule({
  imports: [DiscountInstagramSharedModule, RouterModule.forChild(topStoryRoute)],
  declarations: [TopStoryComponent, TopStoryDetailComponent, TopStoryUpdateComponent, TopStoryDeleteDialogComponent],
  entryComponents: [TopStoryDeleteDialogComponent]
})
export class DiscountInstagramTopStoryModule {}
