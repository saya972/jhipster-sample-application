import { Moment } from 'moment';
import { ICampagne } from 'app/shared/model/campagne.model';
import { Languages } from 'app/shared/model/enumerations/languages.model';
import { Prenium } from 'app/shared/model/enumerations/prenium.model';

export interface IClient {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phone?: string;
  addressLine?: string;
  city?: string;
  country?: string;
  createdDate?: Moment;
  langueClient?: Languages;
  statutClient?: Prenium;
  campagnes?: ICampagne[];
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phone?: string,
    public addressLine?: string,
    public city?: string,
    public country?: string,
    public createdDate?: Moment,
    public langueClient?: Languages,
    public statutClient?: Prenium,
    public campagnes?: ICampagne[]
  ) {}
}
