package ee.hitsa.ois.web.commandobject.report;

import java.math.BigDecimal;

public class EducationalSuccessGradeDto {
    
    private String gradeValue;
    private BigDecimal eap;
    private String gradeType;
    
    public String getGradeValue() {
        return gradeValue;
    }
    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }
    public BigDecimal getEap() {
        return eap;
    }
    public void setEap(BigDecimal eap) {
        this.eap = eap;
    }
    public String getGradeType() {
        return gradeType;
    }
    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

}
