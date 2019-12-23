import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { CampagneComponent } from './campagne.component';
import { CampagneDetailComponent } from './campagne-detail.component';
import { CampagneUpdateComponent } from './campagne-update.component';
import { CampagneDeleteDialogComponent } from './campagne-delete-dialog.component';
import { campagneRoute } from './campagne.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(campagneRoute)],
  declarations: [CampagneComponent, CampagneDetailComponent, CampagneUpdateComponent, CampagneDeleteDialogComponent],
  entryComponents: [CampagneDeleteDialogComponent]
})
export class JhipsterSampleApplicationCampagneModule {}
