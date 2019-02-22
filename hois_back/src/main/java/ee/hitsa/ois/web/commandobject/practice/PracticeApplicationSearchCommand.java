package ee.hitsa.ois.web.commandobject.practice;

import java.time.LocalDate;

public class PracticeApplicationSearchCommand {

    private Long studentGroup;
    private String studentName;
    private LocalDate submitFrom;
    private LocalDate submitThru;
    private String status;
    private Long enterprise;
    
    public Long getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public LocalDate getSubmitFrom() {
        return submitFrom;
    }
    public void setSubmitFrom(LocalDate submitFrom) {
        this.submitFrom = submitFrom;
    }
    public LocalDate getSubmitThru() {
        return submitThru;
    }
    public void setSubmitThru(LocalDate submitThru) {
        this.submitThru = submitThru;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Long getEnterprise() {
        return enterprise;
    }
    public void setEnterprise(Long enterprise) {
        this.enterprise = enterprise;
    }
    
}
