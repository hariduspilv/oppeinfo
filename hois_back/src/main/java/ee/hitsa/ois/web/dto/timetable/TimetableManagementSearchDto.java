package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;

public class TimetableManagementSearchDto {

    private Long id;
    private String status;
    private LocalDate start;
    private LocalDate end;
    private Boolean isHigher;

    public TimetableManagementSearchDto(Long id, String status, LocalDate start, LocalDate end, Boolean isHigher) {
        this.id = id;
        this.status = status;
        this.start = start;
        this.end = end;
        this.isHigher = isHigher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }
}
