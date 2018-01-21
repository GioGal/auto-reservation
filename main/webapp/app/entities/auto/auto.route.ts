import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AutoComponent } from './auto.component';
import { AutoDetailComponent } from './auto-detail.component';
import { AutoPopupComponent } from './auto-dialog.component';
import { AutoDeletePopupComponent } from './auto-delete-dialog.component';

@Injectable()
export class AutoResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const autoRoute: Routes = [
    {
        path: 'auto',
        component: AutoComponent,
        resolve: {
            'pagingParams': AutoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Autos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'auto/:id',
        component: AutoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Autos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const autoPopupRoute: Routes = [
    {
        path: 'auto-new',
        component: AutoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Autos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auto/:id/edit',
        component: AutoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Autos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auto/:id/delete',
        component: AutoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Autos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
