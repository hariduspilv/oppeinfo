package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.validation.DateRange;

@DateRange(from = "insertedFrom", thru = "insertedThru")
@DateRange(from = "confirmedFrom", thru = "confirmedThru")
public class DeclarationSearchCommand {
    private Long studyPeriod;
    private Long curriculumVersion;
    private List<Long> studentGroups;
    private String studentsName;
    private String status;
    private Boolean repeatingDeclaration;
    private LocalDate insertedFrom;
    private LocalDate insertedThru;
    private LocalDate confirmedFrom;
    private LocalDate confirmedThru;

    public Long getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public Long getCurriculumVersion() {
        return curriculumVersion;
    }
    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }
    public List<Long> getStudentGroups() {
        return studentGroups;
    }
    public void setStudentGroups(List<Long> studentGroups) {
        this.studentGroups = studentGroups;
    }
    public String getStudentsName() {
        return studentsName;
    }
    public void setStudentsName(String studentsName) {
        this.studentsName = studentsName;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Boolean getRepeatingDeclaration() {
        return repeatingDeclaration;
    }
    public void setRepeatingDeclaration(Boolean repeatingDeclaration) {
        this.repeatingDeclaration = repeatingDeclaration;
    }
    public LocalDate getInsertedFrom() {
        return insertedFrom;
    }
    public void setInsertedFrom(LocalDate insertedFrom) {
        this.insertedFrom = insertedFrom;
    }
    public LocalDate getInsertedThru() {
        return insertedThru;
    }
    public void setInsertedThru(LocalDate insertedThru) {
        this.insertedThru = insertedThru;
    }
    public LocalDate getConfirmedFrom() {
        return confirmedFrom;
    }
    public void setConfirmedFrom(LocalDate confirmedFrom) {
        this.confirmedFrom = confirmedFrom;
    }
    public LocalDate getConfirmedThru() {
        return confirmedThru;
    }
    public void setConfirmedThru(LocalDate confirmedThru) {
        this.confirmedThru = confirmedThru;
    }
}
