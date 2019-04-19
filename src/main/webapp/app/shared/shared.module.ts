import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { GraeshoppegatewaySharedLibsModule, GraeshoppegatewaySharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [GraeshoppegatewaySharedLibsModule, GraeshoppegatewaySharedCommonModule],
    declarations: [HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    exports: [GraeshoppegatewaySharedCommonModule, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GraeshoppegatewaySharedModule {
    static forRoot() {
        return {
            ngModule: GraeshoppegatewaySharedModule
        };
    }
}
