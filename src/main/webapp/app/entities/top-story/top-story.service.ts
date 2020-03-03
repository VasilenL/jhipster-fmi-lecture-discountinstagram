import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITopStory } from 'app/shared/model/top-story.model';

type EntityResponseType = HttpResponse<ITopStory>;
type EntityArrayResponseType = HttpResponse<ITopStory[]>;

@Injectable({ providedIn: 'root' })
export class TopStoryService {
  public resourceUrl = SERVER_API_URL + 'api/top-stories';

  constructor(protected http: HttpClient) {}

  create(topStory: ITopStory): Observable<EntityResponseType> {
    return this.http.post<ITopStory>(this.resourceUrl, topStory, { observe: 'response' });
  }

  update(topStory: ITopStory): Observable<EntityResponseType> {
    return this.http.put<ITopStory>(this.resourceUrl, topStory, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ITopStory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITopStory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
