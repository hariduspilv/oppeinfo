package ee.hitsa.ois.web.commandobject.scholarship;

import ee.hitsa.ois.validation.Required;

import java.time.LocalDate;

public class ScholarshipApplicationSearchCommand {

    @Required
    private String scholarshipType; // in some places called as scholarship group
    private String type;
    private LocalDate appliedFrom;
    private LocalDate appliedThru;
    private String studentName;
    private Long studyYear;
    private String nameEt;
    private String status;

    public String getScholarshipType() {
        return scholarshipType;
    }

    public void setScholarshipType(String scholarshipType) {
        this.scholarshipType = scholarshipType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getAppliedFrom() {
        return appliedFrom;
    }

    public void setAppliedFrom(LocalDate appliedFrom) {
        this.appliedFrom = appliedFrom;
    }

    public LocalDate getAppliedThru() {
        return appliedThru;
    }

    public void setAppliedThru(LocalDate appliedThru) {
        this.appliedThru = appliedThru;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
