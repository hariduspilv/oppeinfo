package ee.hitsa.ois.web.commandobject.schoolcapacity;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class SchoolCapacityTypeLoadForm {

    private Long studyYearId;
    private Integer loadPercentage;
    @ClassifierRestriction(MainClassCode.KOEFITSIENT)
    private String coefficient;
    
    public Long getStudyYearId() {
        return studyYearId;
    }
    public void setStudyYearId(Long studyYearId) {
        this.studyYearId = studyYearId;
    }
    
    public Integer getLoadPercentage() {
        return loadPercentage;
    }
    public void setLoadPercentage(Integer loadPercentage) {
        this.loadPercentage = loadPercentage;
    }

    public String getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(String coefficient) {
        this.coefficient = coefficient;
    }
}
