package ee.hitsa.ois.web.commandobject.teacher;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

@DateRange(from = "start", thru = "end")
public class TeacherMobilityForm extends VersionedCommand {

    private Long id;
    @NotNull
    private LocalDate start;
    @NotNull
    private LocalDate end;
    @Required
    @ClassifierRestriction(MainClassCode.EHIS_MOBIILSUS)
    private String target;
    @Required
    @Size(max = 255)
    private String school;
    @Required
    @ClassifierRestriction(MainClassCode.RIIK)
    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
