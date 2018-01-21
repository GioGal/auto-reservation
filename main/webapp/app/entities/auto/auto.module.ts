import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutoreservationSharedModule } from '../../shared';
import {
    AutoService,
    AutoPopupService,
    AutoComponent,
    AutoDetailComponent,
    AutoDialogComponent,
    AutoPopupComponent,
    AutoDeletePopupComponent,
    AutoDeleteDialogComponent,
    autoRoute,
    autoPopupRoute,
    AutoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...autoRoute,
    ...autoPopupRoute,
];

@NgModule({
    imports: [
        AutoreservationSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AutoComponent,
        AutoDetailComponent,
        AutoDialogComponent,
        AutoDeleteDialogComponent,
        AutoPopupComponent,
        AutoDeletePopupComponent,
    ],
    entryComponents: [
        AutoComponent,
        AutoDialogComponent,
        AutoPopupComponent,
        AutoDeleteDialogComponent,
        AutoDeletePopupComponent,
    ],
    providers: [
        AutoService,
        AutoPopupService,
        AutoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutoreservationAutoModule {}
