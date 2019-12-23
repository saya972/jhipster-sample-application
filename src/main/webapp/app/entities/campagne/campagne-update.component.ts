import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICampagne, Campagne } from 'app/shared/model/campagne.model';
import { CampagneService } from './campagne.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';

@Component({
  selector: 'jhi-campagne-update',
  templateUrl: './campagne-update.component.html'
})
export class CampagneUpdateComponent implements OnInit {
  isSaving = false;

  clients: IClient[] = [];

  editForm = this.fb.group({
    id: [],
    campagneName: [],
    lienCampagne: [],
    banners: [],
    campagneNameId: []
  });

  constructor(
    protected campagneService: CampagneService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ campagne }) => {
      this.updateForm(campagne);

      this.clientService
        .query()
        .pipe(
          map((res: HttpResponse<IClient[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IClient[]) => (this.clients = resBody));
    });
  }

  updateForm(campagne: ICampagne): void {
    this.editForm.patchValue({
      id: campagne.id,
      campagneName: campagne.campagneName,
      lienCampagne: campagne.lienCampagne,
      banners: campagne.banners,
      campagneNameId: campagne.campagneNameId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const campagne = this.createFromForm();
    if (campagne.id !== undefined) {
      this.subscribeToSaveResponse(this.campagneService.update(campagne));
    } else {
      this.subscribeToSaveResponse(this.campagneService.create(campagne));
    }
  }

  private createFromForm(): ICampagne {
    return {
      ...new Campagne(),
      id: this.editForm.get(['id'])!.value,
      campagneName: this.editForm.get(['campagneName'])!.value,
      lienCampagne: this.editForm.get(['lienCampagne'])!.value,
      banners: this.editForm.get(['banners'])!.value,
      campagneNameId: this.editForm.get(['campagneNameId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICampagne>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IClient): any {
    return item.id;
  }
}
