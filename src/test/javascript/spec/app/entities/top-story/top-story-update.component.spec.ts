import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscountInstagramTestModule } from '../../../test.module';
import { TopStoryUpdateComponent } from 'app/entities/top-story/top-story-update.component';
import { TopStoryService } from 'app/entities/top-story/top-story.service';
import { TopStory } from 'app/shared/model/top-story.model';

describe('Component Tests', () => {
  describe('TopStory Management Update Component', () => {
    let comp: TopStoryUpdateComponent;
    let fixture: ComponentFixture<TopStoryUpdateComponent>;
    let service: TopStoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscountInstagramTestModule],
        declarations: [TopStoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TopStoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopStoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopStoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TopStory('123');
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
        const entity = new TopStory();
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
