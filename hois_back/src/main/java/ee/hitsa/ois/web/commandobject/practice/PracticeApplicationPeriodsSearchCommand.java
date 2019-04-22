package ee.hitsa.ois.web.commandobject.practice;

import java.time.LocalDate;

public class PracticeApplicationPeriodsSearchCommand {
    
    private String enterpriseName;
    private LocalDate validFrom;
    private LocalDate validThru;
    private Boolean opened;
    
    public String getEnterpriseName() {
        return enterpriseName;
    }
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    public LocalDate getValidFrom() {
        return validFrom;
    }
    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }
    public LocalDate getValidThru() {
        return validThru;
    }
    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }
    public Boolean getOpened() {
        return opened;
    }
    public void setOpened(Boolean opened) {
        this.opened = opened;
    }

}
