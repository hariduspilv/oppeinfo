package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.enums.HigherAssessment;

public class HigherProtocolStudentResultDto {

    private Long protocolStudent;
    private String grade;
    
    public HigherProtocolStudentResultDto() {
    }

    public HigherProtocolStudentResultDto(Long protocolStudent, HigherAssessment grade) {
        this.protocolStudent = protocolStudent;
        this.grade = grade != null ? grade.name() : null;
    }

    public Long getProtocolStudent() {
        return protocolStudent;
    }
    public void setProtocolStudent(Long protocolStudent) {
        this.protocolStudent = protocolStudent;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }    
}
