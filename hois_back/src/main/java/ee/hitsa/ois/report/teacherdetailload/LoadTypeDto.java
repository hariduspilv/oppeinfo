package ee.hitsa.ois.report.teacherdetailload;

public class LoadTypeDto {

    private Long periodIndex;
    private String name;
    private Boolean isCapacity = Boolean.FALSE;
    private String capacityValue;
    
    private Boolean plannedLessons;
    private Boolean occurredLessons;
    private String capacityCode;

    private Boolean substitutableEvents;
    private Boolean singleEvents;
    private Boolean grandTotal;

    public Long getPeriodIndex() {
        return periodIndex;
    }

    public void setPeriodIndex(Long periodIndex) {
        this.periodIndex = periodIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsCapacity() {
        return isCapacity;
    }

    public void setIsCapacity(Boolean isCapacity) {
        this.isCapacity = isCapacity;
    }

    public String getCapacityValue() {
        return capacityValue;
    }

    public void setCapacityValue(String capacityValue) {
        this.capacityValue = capacityValue;
    }

    public Boolean getPlannedLessons() {
        return plannedLessons;
    }

    public void setPlannedLessons(Boolean plannedLessons) {
        this.plannedLessons = plannedLessons;
    }

    public Boolean getOccurredLessons() {
        return occurredLessons;
    }

    public void setOccurredLessons(Boolean occurredLessons) {
        this.occurredLessons = occurredLessons;
    }

    public String getCapacityCode() {
        return capacityCode;
    }

    public void setCapacityCode(String capacityCode) {
        this.capacityCode = capacityCode;
    }

    public Boolean getSubstitutableEvents() {
        return substitutableEvents;
    }

    public void setSubstitutableEvents(Boolean substitutableEvents) {
        this.substitutableEvents = substitutableEvents;
    }

    public Boolean getSingleEvents() {
        return singleEvents;
    }

    public void setSingleEvents(Boolean singleEvents) {
        this.singleEvents = singleEvents;
    }

    public Boolean getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Boolean grandTotal) {
        this.grandTotal = grandTotal;
    }

}
