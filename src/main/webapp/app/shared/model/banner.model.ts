export interface IBanner {
  id?: number;
  bannerName?: string;
  bannerSize?: string;
  bannerNameId?: number;
}

export class Banner implements IBanner {
  constructor(public id?: number, public bannerName?: string, public bannerSize?: string, public bannerNameId?: number) {}
}
