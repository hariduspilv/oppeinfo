package ee.hitsa.ois.web.commandobject.document;

import ee.hitsa.ois.enums.Language;

public class SupplementForm {

    private Long signer1Id;
    private Long signer2Id;
    private Language lang = Language.ET;
    private Boolean showSubjectCode;
    private Boolean showTeacher;
    private Long numeral;
    private Long additionalNumeral;
    private Long paddingChange;
    
    public Long getSigner1Id() {
        return signer1Id;
    }
    public void setSigner1Id(Long signer1Id) {
        this.signer1Id = signer1Id;
    }
    
    public Long getSigner2Id() {
        return signer2Id;
    }
    public void setSigner2Id(Long signer2Id) {
        this.signer2Id = signer2Id;
    }
    
    public Language getLang() {
        return lang;
    }
    public void setLang(Language lang) {
        this.lang = lang;
    }
    
    public Boolean getShowSubjectCode() {
        return showSubjectCode;
    }
    public void setShowSubjectCode(Boolean showSubjectCode) {
        this.showSubjectCode = showSubjectCode;
    }
    
    public Boolean getShowTeacher() {
        return showTeacher;
    }
    public void setShowTeacher(Boolean showTeacher) {
        this.showTeacher = showTeacher;
    }
    
    public Long getNumeral() {
        return numeral;
    }
    public void setNumeral(Long numeral) {
        this.numeral = numeral;
    }
    
    public Long getAdditionalNumeral() {
        return additionalNumeral;
    }
    public void setAdditionalNumeral(Long additionalNumeral) {
        this.additionalNumeral = additionalNumeral;
    }

    public Long getPaddingChange() {
        return paddingChange != null ? paddingChange : (paddingChange = Long.valueOf(0));
    }
    public void setPaddingChange(Long paddingChange) {
        this.paddingChange = paddingChange;
    }
}
