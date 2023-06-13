

export interface deviceDialogData {
    device: Device,
    realEstateId: number,
}

export interface DeviceFilterData {
    realEstateId: number,
    deviceId: number,
    deviceName: string,
    filterPeriod: number,
    devices: Device[],
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