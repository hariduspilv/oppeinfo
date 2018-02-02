package ee.hitsa.ois.web.dto.finalexamprotocol;

import java.util.Collection;

public class FinalExamHigherProtocolSubjectDto {
    
    private Collection<FinalExamHigherProtocolStudentDto> subjectStudents;

    public Collection<FinalExamHigherProtocolStudentDto> getSubjectStudents() {
        return subjectStudents;
    }

    public void setSubjectStudents(Collection<FinalExamHigherProtocolStudentDto> subjectStudents) {
        this.subjectStudents = subjectStudents;
    }

}
