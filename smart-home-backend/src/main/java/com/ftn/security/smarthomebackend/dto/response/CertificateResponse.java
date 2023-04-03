package com.ftn.security.smarthomebackend.dto.response;

import com.ftn.security.smarthomebackend.util.CertificateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
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
import java.util.Objects;
import java.util.stream.Collectors;

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
    private String alias;
    private boolean valid;

    public CertificateResponse(X509Certificate certificate, boolean valid) {
        this();
        this.valid = valid;
        X500Name x500name = new X500Name(certificate.getSubjectX500Principal().getName());
        this.alias = String.valueOf(x500name.getRDNs(BCStyle.UID)[0].getTypesAndValues()[0].getValue());
        extractIssuedToData(x500name);

        x500name = new X500Name(certificate.getIssuerX500Principal().getName());
        extractIssuerData(x500name);

        extractDateData(certificate);
        try {
            extractDetailsData(certificate);
        } catch (CertificateParsingException e) {
            throw new RuntimeException(e);
        }
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
            this.keyUsages.add("Certificate Signing");
        if (list[6])
            this.keyUsages.add("CRL Signing");
        if (list[7])
            this.keyUsages.add("Encipher only");
        if (list[8])
            this.keyUsages.add("Decipher only");
    }

    private void setExtendedKeyUsages(List<String> extendedKeyUsagesCodes) {
        this.extendedKeyUsages = extendedKeyUsagesCodes.stream()
                                    .map(CertificateUtils::mapExtendedKeyUsageCodeToStr)
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList());

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
        sb.append("0x").append(uppercase.charAt(0));
        for (int i = 1; i < uppercase.length(); i++){
            if (i % 4 == 0) sb.append(" ");
            sb.append(uppercase.charAt(i));
        }
        return sb.toString();
    }

}
