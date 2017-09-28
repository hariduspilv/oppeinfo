package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;

public class AcademicCalendarEventDto {
    
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String descriptionEt;
    private final String descriptionEn;
    private final Long eventType;
    
    public AcademicCalendarEventDto(Object[] row) {
        this.startDate = resultAsLocalDateTime(row, 0);
        this.endDate = resultAsLocalDateTime(row, 1);
        this.descriptionEt = (String) row[2];
        this.descriptionEn = (String) row[3];
        this.eventType = resultAsLong(row, 4);
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getDescriptionEt() {
        return descriptionEt;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public Long getEventType() {
        return eventType;
    }
    
}
