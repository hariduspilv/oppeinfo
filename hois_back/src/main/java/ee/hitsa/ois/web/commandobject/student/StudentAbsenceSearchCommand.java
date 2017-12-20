package ee.hitsa.ois.web.commandobject.student;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StudentAbsenceSearchCommand {
        
    private Long studyPeriod;
    @NotNull
    private Long studyYear;
    @Size(max=255)
    private String studentName;
    private Set<Long> curriculumVersions;
    private Boolean isAccepted;
    @Size(max=255)
    private String studentGroupCode;

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Set<Long> getCurriculumVersions() {
        return curriculumVersions;
    }

    public void setCurriculumVersions(Set<Long> curriculumVersions) {
        this.curriculumVersions = curriculumVersions;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getStudentGroupCode() {
        return studentGroupCode;
    }

    public void setStudentGroupCode(String studentGroupCode) {
        this.studentGroupCode = studentGroupCode;
    }

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }
}