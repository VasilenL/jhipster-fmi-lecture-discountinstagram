import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiscountInstagramSharedModule } from 'app/shared/shared.module';
import { PicturesComponent } from './pictures.component';
import { PicturesDetailComponent } from './pictures-detail.component';
import { PicturesUpdateComponent } from './pictures-update.component';
import { PicturesDeleteDialogComponent } from './pictures-delete-dialog.component';
import { picturesRoute } from './pictures.route';

@NgModule({
  imports: [DiscountInstagramSharedModule, RouterModule.forChild(picturesRoute)],
  declarations: [PicturesComponent, PicturesDetailComponent, PicturesUpdateComponent, PicturesDeleteDialogComponent],
  entryComponents: [PicturesDeleteDialogComponent]
})
export class DiscountInstagramPicturesModule {}
