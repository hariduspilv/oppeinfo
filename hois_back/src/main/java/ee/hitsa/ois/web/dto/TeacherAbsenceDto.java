package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ee.hitsa.ois.domain.teacher.TeacherAbsence;
import ee.hitsa.ois.util.EntityUtil;

public class TeacherAbsenceDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private LocalDateTime changed;
    
    public static TeacherAbsenceDto of(TeacherAbsence absence) {
        return EntityUtil.bindToDto(absence, new TeacherAbsenceDto());
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getChanged() {
        return changed;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }
    
}
