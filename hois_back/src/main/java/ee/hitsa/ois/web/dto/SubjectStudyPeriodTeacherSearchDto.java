package ee.hitsa.ois.web.dto;

public class SubjectStudyPeriodTeacherSearchDto {

    private Long id;
    private String name;
    private AutocompleteResult studyPeriod;
    private Long timetable;
    /**
     * Planned number of hours in studyPeriod
     */
    private Long hours;
    private Long higherHours;
    private Long vocationalHours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Long getTimetable() {
        return timetable;
    }

    public void setTimetable(Long timetable) {
        this.timetable = timetable;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Long getHigherHours() {
        return higherHours;
    }

    public void setHigherHours(Long higherHours) {
        this.higherHours = higherHours;
    }

    public Long getVocationalHours() {
        return vocationalHours;
    }

    public void setVocationalHours(Long vocationalHours) {
        this.vocationalHours = vocationalHours;
    }
}
