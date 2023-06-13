
export interface ReportData {
    realEstateId: number,
    realEstateName: string,
    numOfAlarms: number,
}

export interface ReportRequest {
    userId: number,
    startTime: Date,
    endTime: Date,
}