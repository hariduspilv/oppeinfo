package ee.hitsa.ois.web.dto.poll;

public class PollResultStudentOrTeacherDto {
    
    private PollResultTargetDto studentResponse;
    private PollResultTargetDto teacherResponse;
    
    public PollResultTargetDto getStudentResponse() {
        return studentResponse;
    }
    public void setStudentResponse(PollResultTargetDto studentResponse) {
        this.studentResponse = studentResponse;
    }
    public PollResultTargetDto getTeacherResponse() {
        return teacherResponse;
    }
    public void setTeacherResponse(PollResultTargetDto teacherResponse) {
        this.teacherResponse = teacherResponse;
    }
}
