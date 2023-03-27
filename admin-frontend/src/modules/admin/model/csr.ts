import { User } from "src/modules/shared/model/user";

export interface Csr{
    id: number,
    user: User,
    commonName: string,
    organizationUnit: string,
    organization: string,
    city: string,
    state: string,
    country: string,
    status: string
}