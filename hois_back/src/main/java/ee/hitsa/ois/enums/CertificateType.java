package ee.hitsa.ois.enums;

public enum CertificateType {
    TOEND_LIIK_SOOR, 
    TOEND_LIIK_OPI, 
    TOEND_LIIK_SESS, 
    TOEND_LIIK_KONTAKT,
    TOEND_LIIK_LOPET, 
    TOEND_LIIK_MUU;
    
    public static boolean isOther(String code) {
        return CertificateType.TOEND_LIIK_MUU.name().equals(code);
    }
    
}
