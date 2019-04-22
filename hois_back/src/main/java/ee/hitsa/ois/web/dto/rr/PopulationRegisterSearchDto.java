package ee.hitsa.ois.web.dto.rr;

import java.time.LocalDate;

public class PopulationRegisterSearchDto {
    
    private String idcode;
    private LocalDate from;
    private LocalDate thru;
    private Boolean errors;
    
    public String getIdcode() {
        return idcode;
    }
    public void setIdcode(String idcode) {
        this.idcode = idcode;
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
    public Boolean getErrors() {
        return errors;
    }
    public void setErrors(Boolean errors) {
        this.errors = errors;
    }

}
