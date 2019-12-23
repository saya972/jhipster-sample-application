import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IBanner, Banner } from 'app/shared/model/banner.model';
import { BannerService } from './banner.service';
import { ICampagne } from 'app/shared/model/campagne.model';
import { CampagneService } from 'app/entities/campagne/campagne.service';

@Component({
  selector: 'jhi-banner-update',
  templateUrl: './banner-update.component.html'
})
export class BannerUpdateComponent implements OnInit {
  isSaving = false;

  campagnes: ICampagne[] = [];

  editForm = this.fb.group({
    id: [],
    bannerName: [],
    bannerSize: [],
    bannerNameId: []
  });

  constructor(
    protected bannerService: BannerService,
    protected campagneService: CampagneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ banner }) => {
      this.updateForm(banner);

      this.campagneService
        .query()
        .pipe(
          map((res: HttpResponse<ICampagne[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICampagne[]) => (this.campagnes = resBody));
    });
  }

  updateForm(banner: IBanner): void {
    this.editForm.patchValue({
      id: banner.id,
      bannerName: banner.bannerName,
      bannerSize: banner.bannerSize,
      bannerNameId: banner.bannerNameId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const banner = this.createFromForm();
    if (banner.id !== undefined) {
      this.subscribeToSaveResponse(this.bannerService.update(banner));
    } else {
      this.subscribeToSaveResponse(this.bannerService.create(banner));
    }
  }

  private createFromForm(): IBanner {
    return {
      ...new Banner(),
      id: this.editForm.get(['id'])!.value,
      bannerName: this.editForm.get(['bannerName'])!.value,
      bannerSize: this.editForm.get(['bannerSize'])!.value,
      bannerNameId: this.editForm.get(['bannerNameId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBanner>>): void {
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

  trackById(index: number, item: ICampagne): any {
    return item.id;
  }
}
