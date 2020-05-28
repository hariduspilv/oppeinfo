package ee.hitsa.ois.enums;

import java.util.Set;

import ee.hitsa.ois.util.EnumUtil;

public enum CertificateType {

    TOEND_LIIK_SOOR("TOEND_LIIK_SOOR_higher.xhtml", "TOEND_LIIK_SOOR_vocational.xhtml", Boolean.TRUE),
    TOEND_LIIK_OPI("TOEND_LIIK_OPI_higher.xhtml", "TOEND_LIIK_OPI_vocational.xhtml", Boolean.FALSE),
    TOEND_LIIK_SESS("TOEND_LIIK_SESS_higher.xhtml", "TOEND_LIIK_SESS_vocational.xhtml", Boolean.FALSE),
    TOEND_LIIK_KONTAKT("TOEND_LIIK_KONTAKT_higher.xhtml", "TOEND_LIIK_KONTAKT_vocational.xhtml", Boolean.FALSE),
    TOEND_LIIK_LOPET("TOEND_LIIK_LOPET_higher.xhtml", "TOEND_LIIK_LOPET_vocational.xhtml", Boolean.FALSE),
    TOEND_LIIK_MUU("TOEND_LIIK_MUU.xhtml", "TOEND_LIIK_MUU.xhtml", Boolean.FALSE);
    
    private static final Set<String> CERTIFICATE_EDITABLE_FOR_ADMIN = EnumUtil.toNameSet(TOEND_LIIK_KONTAKT, TOEND_LIIK_SESS, TOEND_LIIK_MUU);

    private final String higherCertificate;
    private final String vocationalCertificate;
    private final Boolean translated;

    CertificateType(String higherCertificate, String vocationalCertificate, Boolean translated) {
        this.higherCertificate = higherCertificate;
        this.vocationalCertificate = vocationalCertificate;
        this.translated = translated;
    }

    public String getHigherCertificate(Language lang) {
        return (Boolean.TRUE.equals(translated) ? lang.name() + "_" : "") + higherCertificate;
    }

    public String getVocationalCertificate(Language lang) {
        return (Boolean.TRUE.equals(translated) ? lang.name() + "_" : "") + vocationalCertificate;
    }

    public static boolean isOther(String code) {
        return CertificateType.TOEND_LIIK_MUU.name().equals(code);
    }

    public static boolean schoolAdminCanEdit(String typeCode) {
        return CERTIFICATE_EDITABLE_FOR_ADMIN.contains(typeCode);
    }
}
