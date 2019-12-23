import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'campagne',
        loadChildren: () => import('./campagne/campagne.module').then(m => m.JhipsterSampleApplicationCampagneModule)
      },
      {
        path: 'banner',
        loadChildren: () => import('./banner/banner.module').then(m => m.JhipsterSampleApplicationBannerModule)
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.JhipsterSampleApplicationClientModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JhipsterSampleApplicationEntityModule {}
