package ee.hitsa.ois.web.commandobject.student;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class StudentLanguageCommand {
    
    private Long id;
    @ClassifierRestriction(MainClassCode.EHIS_VOORKEEL)
    private String foreignLangCode;
    @ClassifierRestriction(MainClassCode.VOORKEEL_TYYP)
    private String foreignLangTypeCode;
    
    public String getForeignLangCode() {
        return foreignLangCode;
    }
    public void setForeignLangCode(String foreignLangCode) {
        this.foreignLangCode = foreignLangCode;
    }
    public String getForeignLangTypeCode() {
        return foreignLangTypeCode;
    }
    public void setForeignLangTypeCode(String foreignLangTypeCode) {
        this.foreignLangTypeCode = foreignLangTypeCode;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
