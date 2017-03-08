package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.validation.DateRange;

@DateRange(from = "insertedFrom", thru = "insertedThru")
@DateRange(from = "submitedFrom", thru = "submitedThru")
public class ApplicationSearchCommand {

    private List<String> type;
    private LocalDate insertedFrom;
    private LocalDate insertedThru;
    private LocalDate submitedFrom;
    private LocalDate submitedThru;
    private String status;
    private Long student;
    private String studentName;
    private String studentIdCode;
    private String ehisSchool;


    public List<String> getType() {
        return type;
    }
    public void setType(List<String> type) {
        this.type = type;
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
    public LocalDate getSubmitedFrom() {
        return submitedFrom;
    }
    public void setSubmitedFrom(LocalDate submitedFrom) {
        this.submitedFrom = submitedFrom;
    }
    public LocalDate getSubmitedThru() {
        return submitedThru;
    }
    public void setSubmitedThru(LocalDate submitedThru) {
        this.submitedThru = submitedThru;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Long getStudent() {
        return student;
    }
    public void setStudent(Long student) {
        this.student = student;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getStudentIdCode() {
        return studentIdCode;
    }
    public void setStudentIdCode(String studentIdCode) {
        this.studentIdCode = studentIdCode;
    }
    public String getEhisSchool() {
        return ehisSchool;
    }
    public void setEhisSchool(String ehisSchool) {
        this.ehisSchool = ehisSchool;
    }

}
