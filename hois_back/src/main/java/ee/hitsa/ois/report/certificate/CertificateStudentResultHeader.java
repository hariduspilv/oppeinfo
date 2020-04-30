package ee.hitsa.ois.report.certificate;

import ee.hitsa.ois.util.Translatable;

public class CertificateStudentResultHeader implements Translatable {

    private String code;
    private String nameEt;
    private String nameEn;
    
    public CertificateStudentResultHeader(String code, String nameEt, String nameEn) {
        this.code = code;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    @Override
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (obj == null || code == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        return code.equals(((CertificateStudentResultHeader) obj).code);
    }
    
    @Override
    public int hashCode() {
        return code == null ? 31 : code.hashCode();
    }
}
