export interface Device{
    id?: number;
    deviceType: string;
    name: string;
    filterRegex: string;
    periodRead: number;
}