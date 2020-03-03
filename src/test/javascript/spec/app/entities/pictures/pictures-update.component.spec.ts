import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscountInstagramTestModule } from '../../../test.module';
import { PicturesUpdateComponent } from 'app/entities/pictures/pictures-update.component';
import { PicturesService } from 'app/entities/pictures/pictures.service';
import { Pictures } from 'app/shared/model/pictures.model';

describe('Component Tests', () => {
  describe('Pictures Management Update Component', () => {
    let comp: PicturesUpdateComponent;
    let fixture: ComponentFixture<PicturesUpdateComponent>;
    let service: PicturesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscountInstagramTestModule],
        declarations: [PicturesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PicturesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PicturesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PicturesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pictures('123');
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
        const entity = new Pictures();
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
