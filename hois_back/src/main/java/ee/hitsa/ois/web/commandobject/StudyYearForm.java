package ee.hitsa.ois.web.commandobject;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.validation.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@DateRange(from = "startDate", thru = "endDate")
public class StudyYearForm extends VersionedCommand {
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @ClassifierRestriction(MainClassCode.OPPEAASTA)
    private String year;

    private Set<StudyPeriodForm> studyPeriods;

    private Set<StudyPeriodEventForm> studyPeriodEvents;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Set<StudyPeriodForm> getStudyPeriods() {
        return studyPeriods;
    }

    public void setStudyPeriods(Set<StudyPeriodForm> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }

    public Set<StudyPeriodEventForm> getStudyPeriodEvents() {
        return studyPeriodEvents;
    }

    public void setStudyPeriodEvents(Set<StudyPeriodEventForm> studyPeriodEvents) {
        this.studyPeriodEvents = studyPeriodEvents;
    }

    @DateRange(from = "startDate", thru = "endDate")
    private class StudyPeriodForm {
        private Long id;
        @NotEmpty
        private String nameEt;
        private String nameEn;
        @NotEmpty
        @ClassifierRestriction(MainClassCode.OPPEPERIOOD)
        private String type;
        @NotNull
        private LocalDate startDate;
        @NotNull
        private LocalDate endDate;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNameEt() {
            return nameEt;
        }

        public void setNameEt(String nameEt) {
            this.nameEt = nameEt;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }
    }

    @DateRange(from = "startDate", thru = "endDate")
    private class StudyPeriodEventForm {
        private Long id;
        private Long studyYear;
        private Long studyPeriod;
        @NotEmpty
        private String descriptionEt;
        private String descriptionEn;
        @NotEmpty
        @ClassifierRestriction(MainClassCode.SYNDMUS)
        private String eventType;
        @NotNull
        private LocalDate startDate;
        private LocalDate endDate;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getStudyYear() {
            return studyYear;
        }

        public void setStudyYear(Long studyYear) {
            this.studyYear = studyYear;
        }

        public Long getStudyPeriod() {
            return studyPeriod;
        }

        public void setStudyPeriod(Long studyPeriod) {
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

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }
    }
}
