package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.WsEhisTeacherMetaLog;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EhisTeacherMetaLogDto {

    private LocalDate from;
    private LocalDate thru;
    private LocalDateTime inserted;

    public static EhisTeacherMetaLogDto of(WsEhisTeacherMetaLog metaLog) {
        EhisTeacherMetaLogDto dto = new EhisTeacherMetaLogDto();
        dto.setFrom(metaLog.getValidFrom());
        dto.setThru(metaLog.getValidThru());
        dto.setInserted(metaLog.getInserted());
        return dto;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getThru() {
        return thru;
    }

    public void setThru(LocalDate thru) {
        this.thru = thru;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }
}
