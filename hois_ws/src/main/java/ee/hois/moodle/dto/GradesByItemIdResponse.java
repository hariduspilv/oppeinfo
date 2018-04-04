package ee.hois.moodle.dto;

import java.util.List;
import java.util.Map;

public class GradesByItemIdResponse {

    private Map<Long, List<Grade>> grades;

    public Map<Long, List<Grade>> getGrades() {
        return grades;
    }
    public void setGrades(Map<Long, List<Grade>> grades) {
        this.grades = grades;
    }
    
}
