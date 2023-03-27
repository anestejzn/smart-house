import { User } from '../../../shared/model/user';

export interface LoginResponse {
  token: string;
  user: User;
}
