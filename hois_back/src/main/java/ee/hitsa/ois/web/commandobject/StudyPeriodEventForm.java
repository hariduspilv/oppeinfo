package ee.hitsa.ois.web.commandobject;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.DateTimeRange;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.dto.AutocompleteResult;

@DateTimeRange(from = "start", thru = "end")
public class StudyPeriodEventForm extends VersionedCommand {

    private AutocompleteResult studyPeriod;
    @NotEmpty
    @Size(max = 1000)
    private String descriptionEt;
    @Size(max = 1000)
    private String descriptionEn;
    @NotEmpty
    @ClassifierRestriction(MainClassCode.SYNDMUS)
    private String eventType;
    @NotNull
    private LocalDateTime start;
    private LocalDateTime end;

    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public String getDescriptionEt() {
        return descriptionEt;
    }

    public void setDescriptionEt(String descriptionEt) {
        this.descriptionEt = descriptionEt;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
