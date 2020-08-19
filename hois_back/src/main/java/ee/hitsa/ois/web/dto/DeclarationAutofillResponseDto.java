package ee.hitsa.ois.web.dto;

import java.util.ArrayList;
import java.util.List;

public class DeclarationAutofillResponseDto {

    private List<SubjectStudyPeriodSearchDto> subjectStudyPeriods = new ArrayList<>();
    private Long changedDeclarations;

    public List<SubjectStudyPeriodSearchDto> getSubjectStudyPeriods() {
        return subjectStudyPeriods;
    }

    public void setSubjectStudyPeriods(List<SubjectStudyPeriodSearchDto> subjectStudyPeriods) {
        this.subjectStudyPeriods = subjectStudyPeriods;
    }

    public Long getChangedDeclarations() {
        return changedDeclarations;
    }

    public void setChangedDeclarations(Long changedDeclarations) {
        this.changedDeclarations = changedDeclarations;
    }
}
