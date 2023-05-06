
export interface Role {
  id: number,
  roleName: string
}

export interface User {
  id: number;
  email: string;
  name: string;
  surname: string;
  country: string;
  city: string;
  accountStatus: string;
  role: Role;
}
 