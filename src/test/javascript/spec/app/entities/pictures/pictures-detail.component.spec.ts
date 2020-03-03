import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscountInstagramTestModule } from '../../../test.module';
import { PicturesDetailComponent } from 'app/entities/pictures/pictures-detail.component';
import { Pictures } from 'app/shared/model/pictures.model';

describe('Component Tests', () => {
  describe('Pictures Management Detail Component', () => {
    let comp: PicturesDetailComponent;
    let fixture: ComponentFixture<PicturesDetailComponent>;
    const route = ({ data: of({ pictures: new Pictures('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscountInstagramTestModule],
        declarations: [PicturesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PicturesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PicturesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pictures on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pictures).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
