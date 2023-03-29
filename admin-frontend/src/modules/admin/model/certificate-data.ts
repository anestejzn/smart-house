
export interface CertificateData {
    validFrom: string;
    validTo: string;
    issuedToCN: string;
    issuedToO: string;
    issuedToOU: string;
    issuedByCN: string;
    issuedByO: string;
    issuedByOU: string;
    serialNumber: string;
    publicKeyAlgorithm: string;
    keyUsages: string[];
    extendedKeyUsages: string[];
    version: number;
    authorityKeyIdentifier: string;  //identifikuje javni kljuc izdavaoca
    subjectKeyIdentifier: string;    //identifikuje javni kljuc subjekta sertifikata
    alias: string;
    valid: boolean;

}