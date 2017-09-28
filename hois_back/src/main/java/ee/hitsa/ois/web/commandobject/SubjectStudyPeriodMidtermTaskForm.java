package ee.hitsa.ois.web.commandobject;

import java.util.Set;

import javax.validation.Valid;

import ee.hitsa.ois.web.dto.MidtermTaskStudentResultDto;

public class SubjectStudyPeriodMidtermTaskForm {
    
    @Valid
    private Set<MidtermTaskStudentResultDto> studentResults;

    public Set<MidtermTaskStudentResultDto> getStudentResults() {
        return studentResults;
    }

    public void setStudentResults(Set<MidtermTaskStudentResultDto> studentResults) {
        this.studentResults = studentResults;
    }
}
