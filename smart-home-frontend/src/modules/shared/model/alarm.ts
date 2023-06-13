import { Device } from "./device";

export interface Alarm {
    id?: number;
    message: string;
    device: Device;
    // deviceId: number,
    dateTime: Date,
    // deviceName?: string,
}