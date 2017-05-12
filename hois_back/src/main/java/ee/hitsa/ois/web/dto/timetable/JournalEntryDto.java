package ee.hitsa.ois.web.dto.timetable;

import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryForm;

public class JournalEntryDto extends JournalEntryForm {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static JournalEntryDto of(JournalEntry journalEntry) {
        JournalEntryDto dto = EntityUtil.bindToDto(journalEntry, new JournalEntryDto());
        return dto;
    }

}
