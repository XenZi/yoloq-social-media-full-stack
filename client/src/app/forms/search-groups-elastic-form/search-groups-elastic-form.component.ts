import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { GroupElastic } from 'src/app/domains/entity/GroupElastic';
import { SearchGroupsService } from 'src/app/services/search-groups.service';

@Component({
  selector: 'app-search-groups-elastic-form',
  templateUrl: './search-groups-elastic-form.component.html',
  styleUrl: './search-groups-elastic-form.component.scss',
})
export class SearchGroupsElasticFormComponent {
  searchGroups: FormGroup;
  groups: GroupElastic[];
  constructor(
    private formBuilder: FormBuilder,
    private elasticGroupService: SearchGroupsService
  ) {
    this.groups = [];
    this.searchGroups = this.formBuilder.group({
      name: [''],
      description: [''],
      pdfContent: [''],
      phraze: [],
      fuzzy: [],
      useAndQuery: [],
    });
  }

  onSubmit() {
    const name = this.searchGroups.get('name')?.value;
    const description = this.searchGroups.get('description')?.value;
    const pdfContent = this.searchGroups.get('pdfContent')?.value;
    const phraze = this.searchGroups.get('phraze')?.value;
    const fuzzy = this.searchGroups.get('fuzzy')?.value;
    const useAndQuery = this.searchGroups.get('useAndQuery')?.value;

    if (name && !description && !pdfContent && !phraze && !fuzzy) {
      this.elasticGroupService.sendQueryForName(name).subscribe({
        next: (res) => {
          console.log(res);
          this.groups = res;
        },
      });
    }

    if (!name && description && !pdfContent && !phraze && !fuzzy) {
      this.elasticGroupService.sendQueryForDescription(description).subscribe({
        next: (res) => {
          console.log(res);
          this.groups = res;
        },
      });
    }

    if (!name && !description && pdfContent && !phraze && !fuzzy) {
      this.elasticGroupService.sendQueryForPDFContent(pdfContent).subscribe({
        next: (res) => {
          console.log(res);
          this.groups = res;
        },
      });
    }

    if (name && !description && !pdfContent && phraze && !fuzzy) {
      this.elasticGroupService.sendPhraseNameQuery(name).subscribe({
        next: (res) => {
          console.log(res);
          this.groups = res;
        },
      });
    }

    if (!name && description && !pdfContent && phraze && !fuzzy) {
      this.elasticGroupService
        .sendPhraseDescriptionQuery(description)
        .subscribe({
          next: (res) => {
            console.log(res);
            this.groups = res;
          },
        });
    }

    if (name && !description && !pdfContent && !phraze && fuzzy) {
      this.elasticGroupService.sendFuzzyQueryForName(name).subscribe({
        next: (res) => {
          console.log(res);
          this.groups = res;
        },
      });
    }

    if (!name && description && !pdfContent && !phraze && fuzzy) {
      this.elasticGroupService
        .sendFuzzyQueryForDescription(description)
        .subscribe({
          next: (res) => {
            console.log(res);
            this.groups = res;
          },
        });
    }
  }
}
