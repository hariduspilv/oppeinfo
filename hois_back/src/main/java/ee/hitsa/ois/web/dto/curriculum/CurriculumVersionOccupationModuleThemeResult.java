package ee.hitsa.ois.web.dto.curriculum;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleThemeCapacity;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumVersionOccupationModuleThemeResult extends AutocompleteResult {

    private Long moduleId;
    private BigDecimal credits;
    private Short studyYearNumber;
    private Map<String, Short> capacities = new HashMap<>();
    private Boolean existsInOtherJournals;
    private Boolean moduleOutcomes;

    public CurriculumVersionOccupationModuleThemeResult (CurriculumVersionOccupationModuleTheme cvomt) {
        super(cvomt.getId(), cvomt.getNameEt(), cvomt.getNameEt());
        moduleId = EntityUtil.getId(cvomt.getModule());
        studyYearNumber = cvomt.getStudyYearNumber();
        for(CurriculumVersionOccupationModuleThemeCapacity cap : cvomt.getCapacities()) {
            capacities.put(EntityUtil.getCode(cap.getCapacityType()), cap.getHours());
        }
        moduleOutcomes = cvomt.getModuleOutcomes();
    }

    public CurriculumVersionOccupationModuleThemeResult(Long id, String nameEt, String nameEn, BigDecimal credits, Long moduleId) {
        super(id, nameEt, nameEn);
        this.moduleId = moduleId;
        this.credits = credits;
    }

    public CurriculumVersionOccupationModuleThemeResult(Long id, String nameEt, String nameEn, BigDecimal credits,
            Short studyYearNumber, Long moduleId) {
        this(id, nameEt, nameEn, credits, moduleId);
        this.studyYearNumber = studyYearNumber;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
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

    public Boolean getModuleOutcomes() {
        return moduleOutcomes;
    }

    public void setModuleOutcomes(Boolean moduleOutcomes) {
        this.moduleOutcomes = moduleOutcomes;
    }
}
