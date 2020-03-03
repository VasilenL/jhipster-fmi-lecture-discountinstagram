import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPictures } from 'app/shared/model/pictures.model';

type EntityResponseType = HttpResponse<IPictures>;
type EntityArrayResponseType = HttpResponse<IPictures[]>;

@Injectable({ providedIn: 'root' })
export class PicturesService {
  public resourceUrl = SERVER_API_URL + 'api/pictures';

  constructor(protected http: HttpClient) {}

  create(pictures: IPictures): Observable<EntityResponseType> {
    return this.http.post<IPictures>(this.resourceUrl, pictures, { observe: 'response' });
  }

  update(pictures: IPictures): Observable<EntityResponseType> {
    return this.http.put<IPictures>(this.resourceUrl, pictures, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IPictures>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPictures[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
