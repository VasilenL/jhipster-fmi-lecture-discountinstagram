import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DiscountInstagramTestModule } from '../../../test.module';
import { TopStoryComponent } from 'app/entities/top-story/top-story.component';
import { TopStoryService } from 'app/entities/top-story/top-story.service';
import { TopStory } from 'app/shared/model/top-story.model';

describe('Component Tests', () => {
  describe('TopStory Management Component', () => {
    let comp: TopStoryComponent;
    let fixture: ComponentFixture<TopStoryComponent>;
    let service: TopStoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscountInstagramTestModule],
        declarations: [TopStoryComponent]
      })
        .overrideTemplate(TopStoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopStoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopStoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TopStory('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.topStories && comp.topStories[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
