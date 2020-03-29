package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;

public class TimetableManagementSearchDto {

    private Long id;
    private String status;
    private LocalDate start;
    private LocalDate end;
    private Boolean isHigher;
    private Long studyPeriod;
    private Boolean canExport = Boolean.FALSE;
    private Boolean canImport = Boolean.FALSE;

    public TimetableManagementSearchDto(Long id, String status, LocalDate start, LocalDate end, Boolean isHigher) {
        this.id = id;
        this.status = status;
        this.start = start;
        this.end = end;
        this.isHigher = isHigher;
    }

    public TimetableManagementSearchDto(Long id, LocalDate start, LocalDate end, Long studyPeriod) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.studyPeriod = studyPeriod;
    }

    public TimetableManagementSearchDto(Long id, String status, LocalDate start, LocalDate end, Boolean isHigher,
            Long studyPeriod) {
        this(id, status, start, end, isHigher);
        this.studyPeriod = studyPeriod;
    }
    
    public TimetableManagementSearchDto(Long id, String status, LocalDate start, LocalDate end, Boolean isHigher,
            Long studyPeriod, Boolean canExport, Boolean canImport) {
        this(id, status, start, end, isHigher, studyPeriod);
        this.canExport = canExport;
        this.canImport = canImport;
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

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Boolean getCanExport() {
        return canExport;
    }

    public void setCanExport(Boolean canExport) {
        this.canExport = canExport;
    }

    public Boolean getCanImport() {
        return canImport;
    }

    public void setCanImport(Boolean canImport) {
        this.canImport = canImport;
    }

}
