import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICampagne } from 'app/shared/model/campagne.model';

type EntityResponseType = HttpResponse<ICampagne>;
type EntityArrayResponseType = HttpResponse<ICampagne[]>;

@Injectable({ providedIn: 'root' })
export class CampagneService {
  public resourceUrl = SERVER_API_URL + 'api/campagnes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/campagnes';

  constructor(protected http: HttpClient) {}

  create(campagne: ICampagne): Observable<EntityResponseType> {
    return this.http.post<ICampagne>(this.resourceUrl, campagne, { observe: 'response' });
  }

  update(campagne: ICampagne): Observable<EntityResponseType> {
    return this.http.put<ICampagne>(this.resourceUrl, campagne, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICampagne>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICampagne[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICampagne[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
