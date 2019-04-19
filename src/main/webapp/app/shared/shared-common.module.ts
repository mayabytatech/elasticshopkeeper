import { NgModule } from '@angular/core';

import { GraeshoppegatewaySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [GraeshoppegatewaySharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [GraeshoppegatewaySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class GraeshoppegatewaySharedCommonModule {}
