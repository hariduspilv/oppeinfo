package ee.hitsa.ois.web.dto.timetable;

public class LessonPlanTeacherLoadDto {

    private Short weekNr;
    private Long studyPeriod;
    private Long sum;

    public LessonPlanTeacherLoadDto(Short weekNr, Long studyPeriod, Long sum) {
        this.weekNr = weekNr;
        this.studyPeriod = studyPeriod;
        this.sum = sum;
    }

    public Short getWeekNr() {
        return weekNr;
    }

    public void setWeekNr(Short weekNr) {
        this.weekNr = weekNr;
    }

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

}
