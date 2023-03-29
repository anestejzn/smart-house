package com.ftn.security.smarthomebackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameStyle;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.util.encoders.Hex;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
public class CertificateResponse {
    private String validFrom;
    private String validTo;
    private String issuedToCN;
    private String issuedToO;
    private String issuedToOU;
    private String issuedByCN;
    private String issuedByO;
    private String issuedByOU;
    private String serialNumber;
    private String publicKeyAlgorithm;
    private List<String> keyUsages = new ArrayList<>();
    private List<String> extendedKeyUsages = new ArrayList<>();
    private int version;
    private String authorityKeyIdentifier;  //identifikuje javni kljuc izdavaoca
    private String subjectKeyIdentifier;    //identifikuje javni kljuc subjekta sertifikata

    public CertificateResponse(X509Certificate certificate) throws CertificateParsingException {
        this();
        X500Name x500name = new X500Name(certificate.getSubjectX500Principal().getName());
        extractIssuedToData(x500name);

        x500name = new X500Name(certificate.getIssuerX500Principal().getName());
        extractIssuerData(x500name);

        extractDateData(certificate);
        extractDetailsData(certificate);
    }

    private void setKeyUsages(boolean[] list){
        this.keyUsages = new ArrayList<>();
        if (list[0])
            this.keyUsages.add("Digital Signature");
        if (list[1])
            this.keyUsages.add("Non Repudiation");
        if (list[2])
            this.keyUsages.add("Key Encipherment");
        if (list[3])
            this.keyUsages.add("Data Encipherment");
        if (list[4])
            this.keyUsages.add("Key Agreement");
        if (list[5])
            this.keyUsages.add("Key Cert Sign");
        if (list[6])
            this.keyUsages.add("cRL Sign");
        if (list[7])
            this.keyUsages.add("Encipher Only");
        if (list[8])
            this.keyUsages.add("Decipher Only");
    }

    private void setExtendedKeyUsages(List<String> extendedKeyUsagesCodes) {
        this.extendedKeyUsages = new ArrayList<>();
        if (extendedKeyUsagesCodes.contains("1.3.6.1.5.5.7.3.1"))
            this.extendedKeyUsages.add("TLS Web Server Authentication: 1.3.6.1.5.5.7.3.1");
        if (extendedKeyUsagesCodes.contains("1.3.6.1.5.5.7.3.2"))
            this.extendedKeyUsages.add("TLS Web Client Authentication: 1.3.6.1.5.5.7.3.2");
        if (extendedKeyUsagesCodes.contains("1.3.6.1.5.5.7.3.3"))
            this.extendedKeyUsages.add("Code signing: 1.3.6.1.5.5.7.3.3");
        if (extendedKeyUsagesCodes.contains("0.4.0.2231.3.0"))
            this.extendedKeyUsages.add("TSL Signing: 0.4.0.2231.3.0");
        if (extendedKeyUsagesCodes.contains("1.3.6.1.5.5.7.3.4"))
            this.extendedKeyUsages.add("E-mail Protection: 1.3.6.1.5.5.7.3.4");
        if (extendedKeyUsagesCodes.contains("1.3.6.1.5.5.7.3.8"))
            this.extendedKeyUsages.add("Time Stamping: 1.3.6.1.5.5.7.3.8");
        if (extendedKeyUsagesCodes.contains("1.3.6.1.5.5.7.3.9"))
            this.extendedKeyUsages.add("OCSP Signing: 1.3.6.1.5.5.7.3.9");
        if (extendedKeyUsagesCodes.contains("2.5.29.37.0"))
            this.extendedKeyUsages.add("Any Extended Key Usage: 2.5.29.37.0");
    }

    private void extractDetailsData(X509Certificate certificate)
            throws CertificateParsingException
    {
        this.serialNumber = certificate.getSerialNumber().toString();
        this.publicKeyAlgorithm = certificate.getPublicKey().getAlgorithm();

        if (certificate.getKeyUsage() != null)
            this.setKeyUsages(certificate.getKeyUsage());

        if (certificate.getExtendedKeyUsage() != null)
            this.setExtendedKeyUsages(certificate.getExtendedKeyUsage());

        this.version = certificate.getVersion();

        byte[] extensionValue = certificate.getExtensionValue("2.5.29.35");
        byte[] octets = DEROctetString.getInstance(extensionValue).getOctets();
        AuthorityKeyIdentifier authorityKeyIdentifier = AuthorityKeyIdentifier.getInstance(octets);
        this.authorityKeyIdentifier = getKeyIdentifier(authorityKeyIdentifier.getKeyIdentifier());

        extensionValue = certificate.getExtensionValue("2.5.29.14");
        octets = DEROctetString.getInstance(extensionValue).getOctets();
        SubjectKeyIdentifier subjectKeyIdentifier = SubjectKeyIdentifier.getInstance(octets);
        this.subjectKeyIdentifier = getKeyIdentifier(subjectKeyIdentifier.getKeyIdentifier());
    }

    private void extractIssuerData(X500Name x500name) {
        RDN cn = x500name.getRDNs(BCStyle.CN)[0];
        cn = x500name.getRDNs(BCStyle.CN)[0];
        this.issuedByCN = IETFUtils.valueToString(cn.getFirst().getValue());
        cn = x500name.getRDNs(BCStyle.O)[0];
        this.issuedByO = IETFUtils.valueToString(cn.getFirst().getValue());
        cn = x500name.getRDNs(BCStyle.OU)[0];
        this.issuedByOU = IETFUtils.valueToString(cn.getFirst().getValue());
    }

    private void extractIssuedToData(X500Name x500name) {
        RDN cn = x500name.getRDNs(BCStyle.CN)[0];
        this.issuedToCN = IETFUtils.valueToString(cn.getFirst().getValue());
        cn = x500name.getRDNs(BCStyle.O)[0];
        this.issuedToO = IETFUtils.valueToString(cn.getFirst().getValue());
        cn = x500name.getRDNs(BCStyle.OU)[0];
        this.issuedToOU = IETFUtils.valueToString(cn.getFirst().getValue());
    }

    private void extractDateData(X509Certificate certificate) {
        String pattern = "MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);

        this.validFrom = df.format(certificate.getNotBefore());
        this.validTo = df.format(certificate.getNotAfter());
    }

    private String getKeyIdentifier(byte[] keyIdentifier) {
        String keyIdentifierHex = new String(Hex.encode(keyIdentifier));
        String uppercase = keyIdentifierHex.toUpperCase(Locale.ROOT);
        StringBuilder sb = new StringBuilder();
        sb.append("0x"+uppercase.charAt(0));
        for (int i = 1; i < uppercase.length(); i++){
            if (i % 4 == 0) sb.append(" ");
            sb.append(uppercase.charAt(i));
        }
        return sb.toString();
    }

}
