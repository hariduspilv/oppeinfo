package ee.hitsa.ois.web.dto;

public class HigherProtocolStudentResultDto {

    private Long protocolStudent;
    private String grade;
    
    public HigherProtocolStudentResultDto() {
    }

    public HigherProtocolStudentResultDto(Long protocolStudent, String grade) {
        this.protocolStudent = protocolStudent;
        this.grade = grade;
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
