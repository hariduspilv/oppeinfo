package ee.hitsa.ois.enums;

import java.util.Set;

import ee.hitsa.ois.util.EnumUtil;

public enum CertificateType {

    TOEND_LIIK_SOOR("TOEND_LIIK_SOOR_higher.xhtml", "TOEND_LIIK_SOOR_vocational.xhtml"),
    TOEND_LIIK_OPI("TOEND_LIIK_OPI_higher.xhtml", "TOEND_LIIK_OPI_vocational.xhtml"),
    TOEND_LIIK_SESS("TOEND_LIIK_SESS_higher.xhtml", "TOEND_LIIK_SESS_vocational.xhtml"),
    TOEND_LIIK_KONTAKT("TOEND_LIIK_KONTAKT_higher.xhtml", "TOEND_LIIK_KONTAKT_vocational.xhtml"),
    TOEND_LIIK_LOPET("TOEND_LIIK_LOPET_higher.xhtml", "TOEND_LIIK_LOPET_vocational.xhtml"),
    TOEND_LIIK_MUU("TOEND_LIIK_MUU.xhtml", "TOEND_LIIK_MUU.xhtml");
    
    private static final Set<String> CERTIFICATE_EDITABLE_FOR_ADMIN = EnumUtil.toNameSet(TOEND_LIIK_KONTAKT, TOEND_LIIK_SESS, TOEND_LIIK_MUU);

    private final String higherCertificate;
    private final String vocationalCertificate;

    CertificateType(String higherCertificate, String vocationalCertificate) {
        this.higherCertificate = higherCertificate;
        this.vocationalCertificate = vocationalCertificate;
    }

    public String getHigherCertificate() {
        return higherCertificate;
    }

    public String getVocationalCertificate() {
        return vocationalCertificate;
    }

    public static boolean isOther(String code) {
        return CertificateType.TOEND_LIIK_MUU.name().equals(code);
    }

    public static boolean schoolAdminCanEdit(String typeCode) {
        return CERTIFICATE_EDITABLE_FOR_ADMIN.contains(typeCode);
    }
}
