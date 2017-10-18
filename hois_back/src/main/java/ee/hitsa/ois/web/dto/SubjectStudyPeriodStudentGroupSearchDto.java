package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;

public class SubjectStudyPeriodStudentGroupSearchDto extends StudentGroupSearchDto {
    
    private Long timetable;
    private Long studyPeriod;

    public Long getTimetable() {
        return timetable;
    }
    public void setTimetable(Long timetable) {
        this.timetable = timetable;
    }
    public Long getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
}
