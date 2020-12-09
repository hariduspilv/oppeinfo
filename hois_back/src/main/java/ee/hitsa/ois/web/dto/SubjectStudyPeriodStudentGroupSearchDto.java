package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;

public class SubjectStudyPeriodStudentGroupSearchDto extends StudentGroupSearchDto {
    
    private Long timetable;
    private AutocompleteResult studyPeriod;

    public Long getTimetable() {
        return timetable;
    }
    public void setTimetable(Long timetable) {
        this.timetable = timetable;
    }
    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
}
