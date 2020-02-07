package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;
import java.util.List;

public class CurriculumProgramDto {

    private String code;
    private AutocompleteResult subject;
    private BigDecimal credits;
    private SubjectStudyPeriodPlanDto plan;
    
    private Boolean alreadyExistsForGroup;
    
    private List<AutocompleteResult> subjectStudyPeriods;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AutocompleteResult getSubject() {
        return subject;
    }

    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public SubjectStudyPeriodPlanDto getPlan() {
        return plan;
    }

    public void setPlan(SubjectStudyPeriodPlanDto plan) {
        this.plan = plan;
    }

    public Boolean getAlreadyExistsForGroup() {
        return alreadyExistsForGroup;
    }

    public void setAlreadyExistsForGroup(Boolean alreadyExistsForGroup) {
        this.alreadyExistsForGroup = alreadyExistsForGroup;
    }

    public List<AutocompleteResult> getSubjectStudyPeriods() {
        return subjectStudyPeriods;
    }

    public void setSubjectStudyPeriods(List<AutocompleteResult> subjectStudyPeriods) {
        this.subjectStudyPeriods = subjectStudyPeriods;
    }
}
