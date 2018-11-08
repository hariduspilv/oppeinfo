package ee.hitsa.ois.report.studyyearschedule;

import java.util.List;

import ee.hitsa.ois.web.dto.StudyYearScheduleLegendDto;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;

public class ReportStudentGroup {

    private StudentGroupSearchDto group;
    private List<StudyYearScheduleLegendDto> schedule;
    
    public StudentGroupSearchDto getGroup() {
        return group;
    }
    public void setGroup(StudentGroupSearchDto group) {
        this.group = group;
    }
    
    public List<StudyYearScheduleLegendDto> getSchedule() {
        return schedule;
    }
    public void setSchedule(List<StudyYearScheduleLegendDto> schedule) {
        this.schedule = schedule;
    }
    
}
