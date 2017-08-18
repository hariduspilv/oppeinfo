package ee.hitsa.ois.util;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

public class CertificateUtil {

    private static final String CERTIFICATE_TYPE = "X.509";
    private static final String CERTIFICATE_HEADER = "SSL_CLIENT_CERT";
    private static final String BEGIN_CERTIFICATE = "-----BEGIN CERTIFICATE-----";
    private static final String BEGIN_CERTIFICATE_NEW_LINE = "-----BEGIN CERTIFICATE-----\n";
    private static final int HEX_RADIX = 16;

    public static X509Certificate getCertificateFromRequest(HttpServletRequest request) {
        String cert = request.getHeader(CERTIFICATE_HEADER);
        if (cert == null) {
            return null;
        }
        //nginx replaces new line characters with tabs in http request header, but generateCertificate expects new line
        cert = cert.replace(BEGIN_CERTIFICATE, BEGIN_CERTIFICATE_NEW_LINE);
        X509Certificate certificate = null;
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance(CERTIFICATE_TYPE);
            certificate = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(cert.getBytes()));
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return certificate;
    }

    public static X509Certificate getCertificateFromHex(String hex) {
        X509Certificate certificate = null;
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance(CERTIFICATE_TYPE);
            certificate = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(hexToBytes(hex)));
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return certificate;
    }

    public static String digestToHex(byte[] digestToSign) {
        return new BigInteger(1, digestToSign).toString(HEX_RADIX);
    }

    public static byte[] hexToBytes(String hex) {
        return new BigInteger(hex, HEX_RADIX).toByteArray();
    }

}
