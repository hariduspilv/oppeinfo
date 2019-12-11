package ee.hitsa.ois.web.commandobject.report;

import java.time.LocalDate;

public class ForeignStudentStatisticsCommand extends GuestStudentStatisticsCommand {
    
    private String foreignSchool;
    private String foreignCountry;
    private Boolean isExtended;
    private LocalDate durationStart;
    private LocalDate durationEnd;
    
    public String getForeignSchool() {
        return foreignSchool;
    }
    public void setForeignSchool(String foreignSchool) {
        this.foreignSchool = foreignSchool;
    }
    public String getForeignCountry() {
        return foreignCountry;
    }
    public void setForeignCountry(String foreignCountry) {
        this.foreignCountry = foreignCountry;
    }
    public Boolean getIsExtended() {
        return isExtended;
    }
    public void setIsExtended(Boolean isExtended) {
        this.isExtended = isExtended;
    }
    public LocalDate getDurationStart() {
        return durationStart;
    }
    public void setDurationStart(LocalDate durationStart) {
        this.durationStart = durationStart;
    }
    public LocalDate getDurationEnd() {
        return durationEnd;
    }
    public void setDurationEnd(LocalDate durationEnd) {
        this.durationEnd = durationEnd;
    }

}
