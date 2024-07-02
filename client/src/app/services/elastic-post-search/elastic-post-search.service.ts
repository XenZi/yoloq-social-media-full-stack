import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import PostElastic from 'src/app/domains/entity/PostElastic';

@Injectable({
  providedIn: 'root'
})
export class ElasticPostSearchService {
  private baseURL: string = 'http://localhost:8080/api/posts';
  constructor(
    private http: HttpClient,
  ) { }


  public sendQueryForTitleSearchOnly(title: string) {
    this.http.get<PostElastic>(`${this.baseURL}/title/${title}`).subscribe({
      next: (res) => {
        console.log(res)
      },
      error: (err) => {
        console.log(err)
      }
    })
  }

  public sendQueryForContentSearchOnly(content: string) {
    this.http.get<PostElastic>(`${this.baseURL}/content/${content}`).subscribe({
      next: (res) => {
        console.log(res)
      },
      error: (err) => {
        console.log(err)
      }
    })
  }

  public sendQueryForPDFContentOnly(pdfContent: string) {
    this.http.get<PostElastic>(`${this.baseURL}/pdf-content/${pdfContent}`).subscribe({
      next: (res) => {
        console.log(res)
      },
      error: (err) => {
        console.log(err)
      }
    })
  }


  public sendQueryForMixed(title: string, content: string, pdfContent: string, useAndOperator: boolean) {
    this.http.get<PostElastic>(`${this.baseURL}/search?title=${title}&content=${content}&pdfContent=${pdfContent}&useAndOperator=${useAndOperator}`).subscribe({
      next: (res) => {
        console.log(res)
      },
      error: (err) => {
        console.log(err)
      }
    })
  }

  public sendQueryForTitlePhrase(phrase: string) {
    this.http.get<PostElastic>(`${this.baseURL}/search/title/phrase?title=${phrase}`).subscribe({
      next: (res) => {
        console.log(res)
      },
      error: (err) => {
        console.log(err)
      }
    })
  }

  public sendQueryForContentPhrase(content: string) {
    this.http.get<PostElastic>(`${this.baseURL}/search/content/phrase?content=${content}`).subscribe({
      next: (res) => {
        console.log(res)
      },
      error: (err) => {
        console.log(err)
      }
    })
  }

  public sendQueryForTitleFuzzy(title: string) {
    this.http.get<PostElastic>(`${this.baseURL}/search/title/fuzzy?name=${title}`).subscribe({
      next: (res) => {
        console.log(res)
      },
      error: (err) => {
        console.log(err)
      }
    })
  }

  public sendQueryForContentFuzzy(content: string) {
    this.http.get<PostElastic>(`${this.baseURL}/search/content/fuzzy?content=${content}`).subscribe({
      next: (res) => {
        console.log(res)
      },
      error: (err) => {
        console.log(err)
      }
    })
  }
}
