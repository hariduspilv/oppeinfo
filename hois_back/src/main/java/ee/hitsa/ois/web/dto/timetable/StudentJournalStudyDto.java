package ee.hitsa.ois.web.dto.timetable;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.time.LocalDate;

public class StudentJournalStudyDto {
    private final Long entryId;
    private final LocalDate date;
    private final String journalName;
    private final String content;
    
    public StudentJournalStudyDto(Object[] row) {
        this.entryId = resultAsLong(row, 0);
        this.date = resultAsLocalDate(row, 1);
        this.journalName = (String) row[2];
        this.content = (String) row[3];
    }

    public Long getEntryId() {
        return entryId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getJournalName() {
        return journalName;
    }

    public String getContent() {
        return content;
    }
    
}
