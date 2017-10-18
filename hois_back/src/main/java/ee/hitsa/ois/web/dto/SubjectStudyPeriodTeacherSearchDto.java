package ee.hitsa.ois.web.dto;

public class SubjectStudyPeriodTeacherSearchDto extends TeacherSearchDto {
    
    private Long studyPeriod;
    private Long timetable;

    public Long getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public Long getTimetable() {
        return timetable;
    }
    public void setTimetable(Long timetable) {
        this.timetable = timetable;
    }
}
