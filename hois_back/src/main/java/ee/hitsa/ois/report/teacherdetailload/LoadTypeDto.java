package ee.hitsa.ois.report.teacherdetailload;

public class LoadTypeDto {

    private Long periodIndex;
    private String name;
    private Boolean isCapacity = Boolean.FALSE;
    private String capacityValue;
    
    private Boolean plannedLessons = Boolean.FALSE;
    private Boolean journalOccurredLessons = Boolean.FALSE;
    private Boolean timetableOccurredLessons = Boolean.FALSE;
    private String capacityCode;

    private Boolean substitutableEvents = Boolean.FALSE;
    private Boolean singleEvents = Boolean.FALSE;
    private Boolean journalGrandTotal  = Boolean.FALSE;
    private Boolean timetableGrandTotal  = Boolean.FALSE;
    private Boolean koefLesson = Boolean.FALSE;
    private Boolean contactLesson = Boolean.FALSE;

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

    public Boolean getJournalOccurredLessons() {
        return journalOccurredLessons;
    }

    public void setJournalOccurredLessons(Boolean journalOccurredLessons) {
        this.journalOccurredLessons = journalOccurredLessons;
    }

    public Boolean getTimetableOccurredLessons() {
        return timetableOccurredLessons;
    }

    public void setTimetableOccurredLessons(Boolean timetableOccurredLessons) {
        this.timetableOccurredLessons = timetableOccurredLessons;
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

    public Boolean getJournalGrandTotal() {
        return journalGrandTotal;
    }

    public void setJournalGrandTotal(Boolean journalGrandTotal) {
        this.journalGrandTotal = journalGrandTotal;
    }

    public Boolean getTimetableGrandTotal() {
        return timetableGrandTotal;
    }

    public void setTimetableGrandTotal(Boolean timetableGrandTotal) {
        this.timetableGrandTotal = timetableGrandTotal;
    }

	public Boolean getKoefLesson() {
		return koefLesson;
	}

	public void setKoefLesson(Boolean koefLesson) {
		this.koefLesson = koefLesson;
	}

	public Boolean getContactLesson() {
		return contactLesson;
	}

	public void setContactLesson(Boolean contactLesson) {
		this.contactLesson = contactLesson;
	}

}
