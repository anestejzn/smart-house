
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