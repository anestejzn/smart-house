
export interface deviceDialogData {
    device: Device,
    realEstateId: number,
}

export interface Device {
    id?: number,
    deviceType: string,
    name: string,
    filterRegex: string,
    periodRead: number,
    base64Photo?: string,
    realEstateId?: number,
}