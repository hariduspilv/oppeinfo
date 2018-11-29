package ee.hitsa.ois.web.dto.curriculum;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleThemeCapacity;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumVersionOccupationModuleThemeResult extends AutocompleteResult {
    
    private BigDecimal credits;
    private Short studyYearNumber;
    private Map<String, Short> capacities = new HashMap<>();
    private Boolean existsInOtherJournals;

    public CurriculumVersionOccupationModuleThemeResult (CurriculumVersionOccupationModuleTheme cvomt) {
        super(cvomt.getId(), cvomt.getNameEt(), cvomt.getNameEt());
        studyYearNumber = cvomt.getStudyYearNumber();
        for(CurriculumVersionOccupationModuleThemeCapacity cap : cvomt.getCapacities()) {
            capacities.put(EntityUtil.getCode(cap.getCapacityType()), cap.getHours());
        }
    }

    public CurriculumVersionOccupationModuleThemeResult(Long id, String nameEt, String nameEn, BigDecimal credits) {
        super(id, nameEt, nameEn);
        this.credits = credits;
    }

    public CurriculumVersionOccupationModuleThemeResult(Long id, String nameEt, String nameEn, BigDecimal credits,
            Short studyYearNumber) {
        this(id, nameEt, nameEn, credits);
        this.studyYearNumber = studyYearNumber;
    }

    public Short getStudyYearNumber() {
        return studyYearNumber;
    }

    public void setStudyYearNumber(Short studyYearNumber) {
        this.studyYearNumber = studyYearNumber;
    }

    public Map<String, Short> getCapacities() {
        return capacities;
    }

    public void setCapacities(Map<String, Short> capacities) {
        this.capacities = capacities;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public Boolean getExistsInOtherJournals() {
        return existsInOtherJournals;
    }

    public void setExistsInOtherJournals(Boolean existsInOtherJournals) {
        this.existsInOtherJournals = existsInOtherJournals;
    }

}
