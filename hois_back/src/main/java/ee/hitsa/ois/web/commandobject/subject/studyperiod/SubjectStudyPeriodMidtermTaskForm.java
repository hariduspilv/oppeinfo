package ee.hitsa.ois.web.commandobject.subject.studyperiod;

import java.util.Set;

import javax.validation.Valid;

import ee.hitsa.ois.web.dto.MidtermTaskStudentDto;
import ee.hitsa.ois.web.dto.MidtermTaskStudentResultDto;

public class SubjectStudyPeriodMidtermTaskForm {

    /** Used to save new subgroups for students */
    private Set<MidtermTaskStudentDto> students;
    
    @Valid
    private Set<MidtermTaskStudentResultDto> studentResults;

    public Set<MidtermTaskStudentDto> getStudents() {
        return students;
    }

    public void setStudents(Set<MidtermTaskStudentDto> students) {
        this.students = students;
    }

    public Set<MidtermTaskStudentResultDto> getStudentResults() {
        return studentResults;
    }

    public void setStudentResults(Set<MidtermTaskStudentResultDto> studentResults) {
        this.studentResults = studentResults;
    }
}
