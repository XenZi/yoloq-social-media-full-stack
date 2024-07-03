import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GroupElastic } from '../domains/entity/GroupElastic';

@Injectable({
  providedIn: 'root',
})
export class SearchGroupsService {
  private baseURL: string = 'http://localhost:8080/api/groups';
  constructor(private http: HttpClient) {}

  public sendQueryForName(name: string): Observable<GroupElastic[]> {
    return this.http.get<GroupElastic[]>(`${this.baseURL}/name/${name}`);
  }

  public sendQueryForDescription(
    description: string
  ): Observable<GroupElastic[]> {
    return this.http.get<GroupElastic[]>(
      `${this.baseURL}/description/${description}`
    );
  }

  public sendQueryForPDFContent(
    pdfContent: string
  ): Observable<GroupElastic[]> {
    return this.http.get<GroupElastic[]>(
      `${this.baseURL}/pdf-content/${pdfContent}`
    );
  }

  public sendMixedQuery(
    name: string,
    description: string,
    pdfContent: string,
    useAndOperator: boolean
  ): Observable<GroupElastic[]> {
    return this.http.get<GroupElastic[]>(
      `${this.baseURL}/search?name=${name}&description=${description}&pdfContent=${pdfContent}&useAndOperator=${useAndOperator}`
    );
  }
  public sendFuzzyQueryForName(name: string): Observable<GroupElastic[]> {
    return this.http.get<GroupElastic[]>(
      `${this.baseURL}/search/name/fuzzy?name=${name}`
    );
  }

  public sendFuzzyQueryForDescription(
    description: string
  ): Observable<GroupElastic[]> {
    return this.http.get<GroupElastic[]>(
      `${this.baseURL}/search/description/fuzzy?description=${description}`
    );
  }

  public sendPhraseNameQuery(name: string): Observable<GroupElastic[]> {
    return this.http.get<GroupElastic[]>(
      `${this.baseURL}/search/name/phrase?phrase=${name}`
    );
  }

  public sendPhraseDescriptionQuery(
    description: string
  ): Observable<GroupElastic[]> {
    return this.http.get<GroupElastic[]>(
      `${this.baseURL}/search/description/phrase?phrase=${description}`
    );
  }
}
