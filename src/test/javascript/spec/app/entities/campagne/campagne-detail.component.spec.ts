import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CampagneDetailComponent } from 'app/entities/campagne/campagne-detail.component';
import { Campagne } from 'app/shared/model/campagne.model';

describe('Component Tests', () => {
  describe('Campagne Management Detail Component', () => {
    let comp: CampagneDetailComponent;
    let fixture: ComponentFixture<CampagneDetailComponent>;
    const route = ({ data: of({ campagne: new Campagne(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CampagneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CampagneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CampagneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load campagne on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.campagne).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
