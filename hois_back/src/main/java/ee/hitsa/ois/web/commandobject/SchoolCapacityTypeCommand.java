package ee.hitsa.ois.web.commandobject;

public class SchoolCapacityTypeCommand extends SearchCommand {

    private Boolean isHigher;
    private Boolean isTimetable;
    
    private Long journalId;
    private Boolean entryTypes;

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }

    public Boolean getIsTimetable() {
        return isTimetable;
    }

    public void setIsTimetable(Boolean isTimetable) {
        this.isTimetable = isTimetable;
    }

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public Boolean getEntryTypes() {
        return entryTypes;
    }

    public void setEntryTypes(Boolean entryTypes) {
        this.entryTypes = entryTypes;
    }
}
