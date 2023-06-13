import { Device } from "./device";

export interface Alarm{
    device: Device;
    dateTime: Date;
    message: string;
}