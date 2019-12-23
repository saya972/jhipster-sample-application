import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CampagneUpdateComponent } from 'app/entities/campagne/campagne-update.component';
import { CampagneService } from 'app/entities/campagne/campagne.service';
import { Campagne } from 'app/shared/model/campagne.model';

describe('Component Tests', () => {
  describe('Campagne Management Update Component', () => {
    let comp: CampagneUpdateComponent;
    let fixture: ComponentFixture<CampagneUpdateComponent>;
    let service: CampagneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CampagneUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CampagneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CampagneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CampagneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Campagne(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Campagne();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
