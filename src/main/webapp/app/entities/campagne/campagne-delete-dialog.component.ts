import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICampagne } from 'app/shared/model/campagne.model';
import { CampagneService } from './campagne.service';

@Component({
  templateUrl: './campagne-delete-dialog.component.html'
})
export class CampagneDeleteDialogComponent {
  campagne?: ICampagne;

  constructor(protected campagneService: CampagneService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.campagneService.delete(id).subscribe(() => {
      this.eventManager.broadcast('campagneListModification');
      this.activeModal.close();
    });
  }
}
