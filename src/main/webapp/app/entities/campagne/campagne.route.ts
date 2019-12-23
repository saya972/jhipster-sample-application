import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICampagne, Campagne } from 'app/shared/model/campagne.model';
import { CampagneService } from './campagne.service';
import { CampagneComponent } from './campagne.component';
import { CampagneDetailComponent } from './campagne-detail.component';
import { CampagneUpdateComponent } from './campagne-update.component';

@Injectable({ providedIn: 'root' })
export class CampagneResolve implements Resolve<ICampagne> {
  constructor(private service: CampagneService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICampagne> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((campagne: HttpResponse<Campagne>) => {
          if (campagne.body) {
            return of(campagne.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Campagne());
  }
}

export const campagneRoute: Routes = [
  {
    path: '',
    component: CampagneComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.campagne.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CampagneDetailComponent,
    resolve: {
      campagne: CampagneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.campagne.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CampagneUpdateComponent,
    resolve: {
      campagne: CampagneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.campagne.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CampagneUpdateComponent,
    resolve: {
      campagne: CampagneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterSampleApplicationApp.campagne.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
