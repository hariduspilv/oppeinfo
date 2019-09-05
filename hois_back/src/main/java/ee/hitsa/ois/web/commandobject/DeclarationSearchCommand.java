package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.validation.DateRange;

@DateRange(from = "insertedFrom", thru = "insertedThru")
@DateRange(from = "confirmedFrom", thru = "confirmedThru")
public class DeclarationSearchCommand {
    @NotNull
    private Long studyPeriod;
    private EntityConnectionCommand curriculumVersion;
    private List<Long> studentGroups;
    private EntityConnectionCommand student;
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
    public EntityConnectionCommand getCurriculumVersion() {
        return curriculumVersion;
    }
    public void setCurriculumVersion(EntityConnectionCommand curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }
    public List<Long> getStudentGroups() {
        return studentGroups;
    }
    public void setStudentGroups(List<Long> studentGroups) {
        this.studentGroups = studentGroups;
    }
    public EntityConnectionCommand getStudent() {
        return student;
    }
    public void setStudent(EntityConnectionCommand student) {
        this.student = student;
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
