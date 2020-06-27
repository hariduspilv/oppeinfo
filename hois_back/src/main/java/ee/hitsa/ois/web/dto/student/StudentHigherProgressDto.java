package ee.hitsa.ois.web.dto.student;

import java.util.List;

public class StudentHigherProgressDto {

    private List<StudentHigherProgressYearDto> years;
    private StudentHigherProgressPeriodDto extraCurriculumResults;

    public List<StudentHigherProgressYearDto> getYears() {
        return years;
    }

    public void setYears(List<StudentHigherProgressYearDto> years) {
        this.years = years;
    }

    public StudentHigherProgressPeriodDto getExtraCurriculumResults() {
        return extraCurriculumResults;
    }

    public void setExtraCurriculumResults(StudentHigherProgressPeriodDto extraCurriculumResults) {
        this.extraCurriculumResults = extraCurriculumResults;
    }
}
