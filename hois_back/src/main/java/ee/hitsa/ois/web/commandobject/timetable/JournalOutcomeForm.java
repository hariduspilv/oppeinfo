package ee.hitsa.ois.web.commandobject.timetable;

import ee.hitsa.ois.web.dto.timetable.StudentCurriculumModuleOutcomesResultForm;

import javax.validation.Valid;
import java.util.List;

public class JournalOutcomeForm {

    @Valid
    private List<StudentCurriculumModuleOutcomesResultForm> outcomeStudents;

    public List<StudentCurriculumModuleOutcomesResultForm> getOutcomeStudents() {
        return outcomeStudents;
    }

    public void setOutcomeStudents(List<StudentCurriculumModuleOutcomesResultForm> outcomeStudents) {
        this.outcomeStudents = outcomeStudents;
    }

}
