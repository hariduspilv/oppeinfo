package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryForm;

public class JournalEntryDto extends JournalEntryForm {

    private Long id;
    private LocalDateTime inserted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

    public static JournalEntryDto of(JournalEntry journalEntry) {
        JournalEntryDto dto = EntityUtil.bindToDto(journalEntry, new JournalEntryDto(), "journalEntryStudents");
        dto.setJournalEntryStudents(journalEntry.getJournalEntryStudents().stream().map(JournalEntryStudentDto::of).collect(Collectors.toList()));
        return dto;
    }

}
