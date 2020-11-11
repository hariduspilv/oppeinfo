package ee.hitsa.ois.web.dto.lessonPlan;

import java.math.BigDecimal;
import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class LessonPlanXlsCapacityDto {
    
    private Long id;
    private AutocompleteResult studyPeriod;
    private LocalDate studyPeriodStart;
    private Long hours;
    private Boolean isContact;
    private String capacityType;
    private BigDecimal hoursWithLoad;
    
    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public LocalDate getStudyPeriodStart() {
        return studyPeriodStart;
    }
    public void setStudyPeriodStart(LocalDate studyPeriodStart) {
        this.studyPeriodStart = studyPeriodStart;
    }
    public Long getHours() {
        return hours;
    }
    public void setHours(Long hours) {
        this.hours = hours;
    }
    public Boolean getIsContact() {
        return isContact;
    }
    public void setIsContact(Boolean isContact) {
        this.isContact = isContact;
    }
    public String getCapacityType() {
        return capacityType;
    }
    public void setCapacityType(String capacityType) {
        this.capacityType = capacityType;
    }
    public BigDecimal getHoursWithLoad() {
        return hoursWithLoad;
    }
    public void setHoursWithLoad(BigDecimal hoursWithLoad) {
        this.hoursWithLoad = hoursWithLoad;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    

}
