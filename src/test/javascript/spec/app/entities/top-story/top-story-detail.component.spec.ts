import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscountInstagramTestModule } from '../../../test.module';
import { TopStoryDetailComponent } from 'app/entities/top-story/top-story-detail.component';
import { TopStory } from 'app/shared/model/top-story.model';

describe('Component Tests', () => {
  describe('TopStory Management Detail Component', () => {
    let comp: TopStoryDetailComponent;
    let fixture: ComponentFixture<TopStoryDetailComponent>;
    const route = ({ data: of({ topStory: new TopStory('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscountInstagramTestModule],
        declarations: [TopStoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TopStoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopStoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topStory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topStory).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
