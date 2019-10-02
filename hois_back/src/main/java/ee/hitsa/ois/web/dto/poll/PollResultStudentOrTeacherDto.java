package ee.hitsa.ois.web.dto.poll;

public class PollResultStudentOrTeacherDto {
    
    private Boolean hasStudentResponse = Boolean.FALSE;
    private Boolean hasTeacherResponse = Boolean.FALSE;
    
    public Boolean getHasStudentResponse() {
        return hasStudentResponse;
    }
    public void setHasStudentResponse(Boolean hasStudentResponse) {
        this.hasStudentResponse = hasStudentResponse;
    }
    public Boolean getHasTeacherResponse() {
        return hasTeacherResponse;
    }
    public void setHasTeacherResponse(Boolean hasTeacherResponse) {
        this.hasTeacherResponse = hasTeacherResponse;
    }
}
