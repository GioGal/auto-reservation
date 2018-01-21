/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AutoreservationTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AutoDetailComponent } from '../../../../../../main/webapp/app/entities/auto/auto-detail.component';
import { AutoService } from '../../../../../../main/webapp/app/entities/auto/auto.service';
import { Auto } from '../../../../../../main/webapp/app/entities/auto/auto.model';

describe('Component Tests', () => {

    describe('Auto Management Detail Component', () => {
        let comp: AutoDetailComponent;
        let fixture: ComponentFixture<AutoDetailComponent>;
        let service: AutoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AutoreservationTestModule],
                declarations: [AutoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AutoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AutoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AutoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AutoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Auto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auto).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
