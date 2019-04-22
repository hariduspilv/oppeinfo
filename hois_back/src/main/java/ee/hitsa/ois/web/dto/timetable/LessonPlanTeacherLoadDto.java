package ee.hitsa.ois.web.dto.timetable;

public class LessonPlanTeacherLoadDto {

    private Short weekNr;
    private Long studyPeriod;
    private String capacity;
    private Long sum;

    public LessonPlanTeacherLoadDto(Short weekNr, Long studyPeriod, String capacity, Long sum) {
        this.weekNr = weekNr;
        this.studyPeriod = studyPeriod;
        this.capacity = capacity;
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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

}
