package ee.hitsa.ois.web.dto.student;

import ee.hitsa.ois.web.commandobject.student.StudentMatchedResultForm;

import java.time.LocalDate;

public class StudentMatchedResultDto extends StudentMatchedResultForm {

    private LocalDate matchingDate;
    private String matcher;
    private Boolean canEdit;

    public LocalDate getMatchingDate() {
        return matchingDate;
    }

    public void setMatchingDate(LocalDate matchingDate) {
        this.matchingDate = matchingDate;
    }

    public String getMatcher() {
        return matcher;
    }

    public void setMatcher(String matcher) {
        this.matcher = matcher;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }
}
