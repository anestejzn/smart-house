
export interface Alarm {
    id: number,
    message: string,
    deviceId: number,
    dateTime: Date,
    deviceName?: string,
}