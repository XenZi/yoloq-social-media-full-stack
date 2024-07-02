import { Component } from '@angular/core';
import { ModalService } from 'src/app/services/modal/modal.service';
import { SearchPostsElasticComponent } from '../search-posts-elastic/search-posts-elastic.component';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent {

  constructor(private modalService: ModalService) {}


  openModalForSearch() {
    this.modalService.open(SearchPostsElasticComponent, {})
  }
}
