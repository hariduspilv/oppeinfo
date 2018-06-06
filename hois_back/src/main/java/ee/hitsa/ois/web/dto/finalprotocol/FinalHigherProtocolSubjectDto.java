package ee.hitsa.ois.web.dto.finalprotocol;

import java.util.Collection;

public class FinalHigherProtocolSubjectDto {
    
    private Collection<FinalHigherProtocolStudentDto> subjectStudents;

    public Collection<FinalHigherProtocolStudentDto> getSubjectStudents() {
        return subjectStudents;
    }

    public void setSubjectStudents(Collection<FinalHigherProtocolStudentDto> subjectStudents) {
        this.subjectStudents = subjectStudents;
    }

}
