
export interface Role {
  id: number,
  roleName: string
}

export interface User {
  id: number;
  email: string;
  name: string;
  surname: string;
  accountStatus?: string;
  role?: Role;
}
 