import { User } from "src/modules/shared/model/user"

export interface RealEstateView {
    id: number,
    ownerFullName: string,
    name: string,
    sqArea: number
}

export interface NewRealEstateRequest {
    name: string,
    sqMeters: number,
    city: string,
    street: string,
    streetNum: string,
    ownerId: number,
    tenantsIds: number[]
}

export interface UpdateRealEstateRequest {
    id: number,
    name?: string,
    sqMeters?: number,
    city?: string,
    street?: string,
    streetNum?: string,
    ownerId?: number,
    tenantsIds?: number[]
}

export interface RealEstate {
    id: number,
    name: string,
    sqMeters: number,
    city: string,
    street: string,
    streetNum: string,
    owner: User,
    tenants: User[]
}