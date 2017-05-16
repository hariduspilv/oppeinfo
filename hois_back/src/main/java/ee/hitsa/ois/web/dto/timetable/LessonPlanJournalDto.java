package ee.hitsa.ois.web.dto.timetable;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.util.EntityUtil;

public class LessonPlanJournalDto {

    public static LessonPlanJournalDto of(Journal journal) {
        LessonPlanJournalDto dto = EntityUtil.bindToDto(journal, new LessonPlanJournalDto());
        return dto;
    }
}
