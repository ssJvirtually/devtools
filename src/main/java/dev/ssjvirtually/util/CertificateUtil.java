package dev.ssjvirtually.util;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.stream.Collectors;

public final class CertificateUtil {

    private CertificateUtil() {
    }

    public static String decodeCertificate(String certString) {
        if (certString == null || certString.trim().isEmpty()) {
            return "";
        }

        try {
            String cleanCert = certString.replace("-----BEGIN CERTIFICATE-----", "")
                    .replace("-----END CERTIFICATE-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(cleanCert);
            CertificateFactory fact = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) fact.generateCertificate(new ByteArrayInputStream(decoded));

            StringBuilder sb = new StringBuilder();
            sb.append("Subject: ").append(cert.getSubjectX500Principal().getName()).append("\n");
            sb.append("Issuer: ").append(cert.getIssuerX500Principal().getName()).append("\n");
            sb.append("Serial Number: ").append(cert.getSerialNumber().toString(16).toUpperCase()).append("\n");
            sb.append("Valid From: ").append(cert.getNotBefore()).append("\n");
            sb.append("Valid To: ").append(cert.getNotAfter()).append("\n");
            sb.append("Signature Algorithm: ").append(cert.getSigAlgName()).append("\n");
            sb.append("Version: ").append(cert.getVersion()).append("\n");
            sb.append("Public Key Algorithm: ").append(cert.getPublicKey().getAlgorithm()).append("\n");

            if (cert.getSubjectAlternativeNames() != null) {
                sb.append("Subject Alternative Names: ").append(
                        cert.getSubjectAlternativeNames().stream()
                                .map(list -> list.get(1).toString())
                                .collect(Collectors.joining(", "))
                ).append("\n");
            }

            return sb.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to decode certificate: " + e.getMessage(), e);
        }
    }
}
