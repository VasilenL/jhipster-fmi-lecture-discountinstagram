import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DiscountInstagramTestModule } from '../../../test.module';
import { PicturesComponent } from 'app/entities/pictures/pictures.component';
import { PicturesService } from 'app/entities/pictures/pictures.service';
import { Pictures } from 'app/shared/model/pictures.model';

describe('Component Tests', () => {
  describe('Pictures Management Component', () => {
    let comp: PicturesComponent;
    let fixture: ComponentFixture<PicturesComponent>;
    let service: PicturesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscountInstagramTestModule],
        declarations: [PicturesComponent]
      })
        .overrideTemplate(PicturesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PicturesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PicturesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Pictures('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pictures && comp.pictures[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
