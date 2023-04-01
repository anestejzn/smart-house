export interface CertificateRequest{
    csrId: number,
    keyUsages: string[],
    extendedKeyUsages: string[],
    validityPeriod: string;
}