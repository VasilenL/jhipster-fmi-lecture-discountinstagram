import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DiscountInstagramTestModule } from '../../../test.module';
import { FollowerComponent } from 'app/entities/follower/follower.component';
import { FollowerService } from 'app/entities/follower/follower.service';
import { Follower } from 'app/shared/model/follower.model';

describe('Component Tests', () => {
  describe('Follower Management Component', () => {
    let comp: FollowerComponent;
    let fixture: ComponentFixture<FollowerComponent>;
    let service: FollowerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscountInstagramTestModule],
        declarations: [FollowerComponent]
      })
        .overrideTemplate(FollowerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FollowerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FollowerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Follower('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.followers && comp.followers[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
