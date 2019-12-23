import { IBanner } from 'app/shared/model/banner.model';

export interface ICampagne {
  id?: number;
  campagneName?: string;
  lienCampagne?: string;
  banners?: string;
  banners?: IBanner[];
  campagneNameId?: number;
}

export class Campagne implements ICampagne {
  constructor(
    public id?: number,
    public campagneName?: string,
    public lienCampagne?: string,
    public banners?: string,
    public banners?: IBanner[],
    public campagneNameId?: number
  ) {}
}
