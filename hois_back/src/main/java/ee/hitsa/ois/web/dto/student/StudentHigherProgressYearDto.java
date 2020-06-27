package ee.hitsa.ois.web.dto.student;

import java.util.ArrayList;
import java.util.List;

public class StudentHigherProgressYearDto {

    private Short studyYearNumber;
    private List<StudentHigherProgressPeriodDto> periods = new ArrayList<>();

    public Short getStudyYearNumber() {
        return studyYearNumber;
    }

    public void setStudyYearNumber(Short studyYearNumber) {
        this.studyYearNumber = studyYearNumber;
    }

    public List<StudentHigherProgressPeriodDto> getPeriods() {
        return periods;
    }

    public void setPeriods(List<StudentHigherProgressPeriodDto> periods) {
        this.periods = periods;
    }
}
