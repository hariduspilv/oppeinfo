package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class TimetableCopyForm {
    Long id;
    @NotNull
    Long originalTimetable;
    @NotNull
    LocalDate start;
    @NotNull
    Long studyPeriod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOriginalTimetable() {
        return originalTimetable;
    }

    public void setOriginalTimetable(Long originalTimetable) {
        this.originalTimetable = originalTimetable;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

}
