package ee.hitsa.ois.web.dto.timetable;

public class LessonPlanTeacherLoadDto {

    private Short weekNr;
    private Long studyPeriod;
    private String capacity;
    private Boolean isContact;
    private Long sum;

    public LessonPlanTeacherLoadDto(Short weekNr, Long studyPeriod, String capacity, Boolean isContact, Long sum) {
        this.weekNr = weekNr;
        this.studyPeriod = studyPeriod;
        this.capacity = capacity;
        this.isContact = isContact;
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

    public Boolean getIsContact() {
        return isContact;
    }

    public void setIsContact(Boolean isContact) {
        this.isContact = isContact;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

}
