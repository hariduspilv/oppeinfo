package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;

public class AcademicCalendarEventDto {
    
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String nameEt;
    private final String nameEn;
    private final Long eventType;
    @ClassifierRestriction(MainClassCode.SYNDMUS)
    private final String eventTypeCode;
    
    public AcademicCalendarEventDto(Object[] row) {
        this.startDate = resultAsLocalDateTime(row, 0);
        this.endDate = resultAsLocalDateTime(row, 1);
        this.nameEt = (String) row[2];
        this.nameEn = (String) row[3];
        this.eventType = resultAsLong(row, 4);
        this.eventTypeCode = (String) row[5];
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public Long getEventType() {
        return eventType;
    }
    
    public String getEventTypeCode() {
        return eventTypeCode;
    }
}
