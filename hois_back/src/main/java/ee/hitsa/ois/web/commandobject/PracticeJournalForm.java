package ee.hitsa.ois.web.commandobject;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.validation.PracticeJournalValidation;
import ee.hitsa.ois.web.dto.AutocompleteResult;

@DateRange(from = "startDate", thru = "endDate")
public class PracticeJournalForm extends VersionedCommand {

    @NotNull
    private AutocompleteResult student;
    @NotNull(groups = PracticeJournalValidation.Vocational.class)
    private Long module;
    @NotNull(groups = PracticeJournalValidation.Vocational.class)
    private Long theme;
    @NotNull
    private BigDecimal credits;
    @NotNull
    private Integer hours;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private String practicePlace;
    @NotNull
    private Long teacher;
    @NotNull
    private String practicePlan;
    @NotNull(groups = PracticeJournalValidation.Higher.class)
    private Long subject;
    private Boolean isHigher;

    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }

    public Long getModule() {
        return module;
    }

    public void setModule(Long module) {
        this.module = module;
    }

    public Long getTheme() {
        return theme;
    }

    public void setTheme(Long theme) {
        this.theme = theme;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
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

    public String getPracticePlace() {
        return practicePlace;
    }

    public void setPracticePlace(String practicePlace) {
        this.practicePlace = practicePlace;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    public String getPracticePlan() {
        return practicePlan;
    }

    public void setPracticePlan(String practicePlan) {
        this.practicePlan = practicePlan;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }

}
