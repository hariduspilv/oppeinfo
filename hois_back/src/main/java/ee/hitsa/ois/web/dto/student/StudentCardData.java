package ee.hitsa.ois.web.dto.student;

import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentCardData {

    private Long studentId;
    private AutocompleteResult school;
    private String studentName;
    private String studentIdcode;
    private LocalDate validThru;
    private String studentCardNr;
    private Long photoId;
    
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public AutocompleteResult getSchool() {
        return school;
    }
    public void setSchool(AutocompleteResult school) {
        this.school = school;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getStudentIdcode() {
        return studentIdcode;
    }
    public void setStudentIdcode(String studentIdcode) {
        this.studentIdcode = studentIdcode;
    }
    public LocalDate getValidThru() {
        return validThru;
    }
    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }
    public String getStudentCardNr() {
        return studentCardNr;
    }
    public void setStudentCardNr(String studentCardNr) {
        this.studentCardNr = studentCardNr;
    }
    public Long getPhotoId() {
        return photoId;
    }
    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }
}
