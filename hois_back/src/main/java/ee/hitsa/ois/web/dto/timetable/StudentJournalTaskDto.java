package ee.hitsa.ois.web.dto.timetable;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.time.LocalDate;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class StudentJournalTaskDto {
    private final Long entryId;
    @ClassifierRestriction(MainClassCode.SISSEKANNE)
    private final String entryType;
    private final String journalName;
    private final LocalDate date;
    private final String taskContent;
    
    public StudentJournalTaskDto(Object[] row) {
        this.entryId = resultAsLong(row, 0);
        this.entryType = (String) row[1];
        this.journalName = (String) row[2];
        this.date = resultAsLocalDate(row, 3);
        this.taskContent = (String) row[4];
    }

    public Long getEntryId() {
        return entryId;
    }
    
    public String getEntryType() {
        return entryType;
    }

    public String getJournalName() {
        return journalName;
    }

    public LocalDate getDate() {
        return date;
    }
    
    public String getTaskContent() {
        return taskContent;
    }
    
}
