import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import PostElastic from 'src/app/domains/entity/PostElastic';

@Injectable({
  providedIn: 'root',
})
export class ElasticPostSearchService {
  private baseURL: string = 'http://localhost:8080/api/posts';
  constructor(private http: HttpClient) {}

  public sendQueryForTitleSearchOnly(title: string): Observable<PostElastic[]> {
    return this.http.get<PostElastic[]>(`${this.baseURL}/title/${title}`);
  }

  public sendQueryForContentSearchOnly(
    content: string
  ): Observable<PostElastic[]> {
    return this.http.get<PostElastic[]>(`${this.baseURL}/content/${content}`);
  }

  public sendQueryForPDFContentOnly(
    pdfContent: string
  ): Observable<PostElastic[]> {
    return this.http.get<PostElastic[]>(
      `${this.baseURL}/pdf-content/${pdfContent}`
    );
  }

  public sendQueryForMixed(
    title: string,
    content: string,
    pdfContent: string,
    useAndOperator: boolean
  ): Observable<PostElastic[]> {
    return this.http.get<PostElastic[]>(
      `${this.baseURL}/search?title=${title}&content=${content}&pdfContent=${pdfContent}&useAndOperator=${useAndOperator}`
    );
  }

  public sendQueryForTitlePhrase(phrase: string): Observable<PostElastic[]> {
    return this.http.get<PostElastic[]>(
      `${this.baseURL}/search/title/phrase?title=${phrase}`
    );
  }

  public sendQueryForContentPhrase(content: string): Observable<PostElastic[]> {
    return this.http.get<PostElastic[]>(
      `${this.baseURL}/search/content/phrase?content=${content}`
    );
  }

  public sendQueryForTitleFuzzy(title: string): Observable<PostElastic[]> {
    return this.http.get<PostElastic[]>(
      `${this.baseURL}/search/title/fuzzy?name=${title}`
    );
  }

  public sendQueryForContentFuzzy(content: string): Observable<PostElastic[]> {
    return this.http.get<PostElastic[]>(
      `${this.baseURL}/search/content/fuzzy?content=${content}`
    );
  }
}
