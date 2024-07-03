import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import PostElastic from 'src/app/domains/entity/PostElastic';
import { ElasticPostSearchService } from 'src/app/services/elastic-post-search/elastic-post-search.service';

@Component({
  selector: 'app-search-posts',
  templateUrl: './search-posts.component.html',
  styleUrl: './search-posts.component.scss',
})
export class SearchPostsComponent {
  searchPosts: FormGroup;
  posts: PostElastic[];
  constructor(
    private formBuilder: FormBuilder,
    private elasticPostSearch: ElasticPostSearchService
  ) {
    this.posts = [];
    this.searchPosts = this.formBuilder.group({
      title: [''],
      content: [''],
      pdfContent: [''],
      phraze: [],
      fuzzy: [],
      useAndQuery: [],
      minNumberOfComments: [],
      maxNumberOfComments: [],
      minNumberOfLikes: [],
      maxNumberOfLikes: [],
    });
  }

  onSubmit() {
    const title = this.searchPosts.get('title')?.value;
    const content = this.searchPosts.get('content')?.value;
    const pdfContent = this.searchPosts.get('pdfContent')?.value;
    const phraze = this.searchPosts.get('phraze')?.value;
    const fuzzy = this.searchPosts.get('fuzzy')?.value;
    const useAndQuery = this.searchPosts.get('useAndQuery')?.value;
    const minNumberOfComments = this.searchPosts.get(
      'minNumberOfComments'
    )?.value;
    const maxNumberOfComments = this.searchPosts.get(
      'maxNumberOfComments'
    )?.value;
    const minNumberOfLikes = this.searchPosts.get('minNumberOfLikes')?.value;
    const maxNumberOfLikes = this.searchPosts.get('maxNumberOfLikes')?.value;

    if (title && !content && !pdfContent && !phraze && !fuzzy) {
      console.log('USLO OVDE');
      this.elasticPostSearch.sendQueryForTitleSearchOnly(title).subscribe({
        next: (res) => {
          this.posts = res;
        },
      });
    }

    if (!title && content && !pdfContent && !phraze && !fuzzy) {
      this.elasticPostSearch.sendQueryForContentSearchOnly(content).subscribe({
        next: (res) => {
          this.posts = res;
        },
      });
    }

    if (!title && !content && pdfContent && !phraze && !fuzzy) {
      this.elasticPostSearch.sendQueryForPDFContentOnly(pdfContent).subscribe({
        next: (res) => {
          this.posts = res;
        },
      });
    }

    if (title && content && pdfContent) {
      this.elasticPostSearch
        .sendQueryForMixed(title, content, pdfContent, useAndQuery ?? false)
        .subscribe({
          next: (res) => {
            this.posts = res;
          },
        });
    }

    if (title && !content && phraze) {
      this.elasticPostSearch.sendQueryForTitlePhrase(title).subscribe({
        next: (res) => {
          this.posts = res;
        },
      });
    }

    if (!title && content && phraze) {
      this.elasticPostSearch.sendQueryForContentPhrase(content).subscribe({
        next: (res) => {
          this.posts = res;
        },
      });
    }

    if (title && !content && fuzzy) {
      this.elasticPostSearch.sendQueryForTitleFuzzy(title).subscribe({
        next: (res) => {
          this.posts = res;
        },
      });
    }

    if (!title && content && fuzzy) {
      this.elasticPostSearch.sendQueryForContentFuzzy(content).subscribe({
        next: (res) => {
          this.posts = res;
        },
      });
    }
    console.log(title, content, pdfContent);
    // console.log(minNumberOfComments, maxNumberOfComments, minNumberOfLikes, maxNumberOfLikes);
  }
}
