package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.OccupationalGrade;

public class ProtocolStudentResultDto {

    private Long protocolStudent;
    private String grade;
    
    public ProtocolStudentResultDto() {
    }

    public ProtocolStudentResultDto(Long protocolStudent, HigherAssessment grade) {
        this.protocolStudent = protocolStudent;
        this.grade = grade != null ? grade.name() : null;
    }
    
    public ProtocolStudentResultDto(Long protocolStudent, OccupationalGrade grade) {
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
