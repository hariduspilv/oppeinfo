package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;

public class ContractSearchCommand {

    private LocalDate startFrom;
    private LocalDate startThru;
    private LocalDate endFrom;
    private LocalDate endThru;
    private String studentName;
    private Long curriculumVersion;
    private Long studentGroup;
    private String enterpriseName;
    private String enterpriseContactPersonName;
    private Long teacher;
    private String status;

    public LocalDate getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(LocalDate startFrom) {
        this.startFrom = startFrom;
    }

    public LocalDate getStartThru() {
        return startThru;
    }

    public void setStartThru(LocalDate startThru) {
        this.startThru = startThru;
    }

    public LocalDate getEndFrom() {
        return endFrom;
    }

    public void setEndFrom(LocalDate endFrom) {
        this.endFrom = endFrom;
    }

    public LocalDate getEndThru() {
        return endThru;
    }

    public void setEndThru(LocalDate endThru) {
        this.endThru = endThru;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseContactPersonName() {
        return enterpriseContactPersonName;
    }

    public void setEnterpriseContactPersonName(String enterpriseContactPersonName) {
        this.enterpriseContactPersonName = enterpriseContactPersonName;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
