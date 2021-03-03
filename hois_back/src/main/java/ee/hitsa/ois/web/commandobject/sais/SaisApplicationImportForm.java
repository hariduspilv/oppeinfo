package ee.hitsa.ois.web.commandobject.sais;

import java.time.LocalDate;
import java.util.ArrayList;

import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

@DateRange(from = "applicationDateFrom", thru = "applicationDateTo")
public class SaisApplicationImportForm extends VersionedCommand {

    private LocalDate applicationDateFrom;
    private LocalDate applicationDateTo;
    private String idCode;
    private Long admissionId;
    private ArrayList<String> status;

    public LocalDate getApplicationDateFrom() {
        return applicationDateFrom;
    }

    public void setApplicationDateFrom(LocalDate applicationDateFrom) {
        this.applicationDateFrom = applicationDateFrom;
    }

    public LocalDate getApplicationDateTo() {
        return applicationDateTo;
    }

    public void setApplicationDateTo(LocalDate applicationDateTo) {
        this.applicationDateTo = applicationDateTo;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public Long getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(Long admissionId) {
        this.admissionId = admissionId;
    }

    public ArrayList<String> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<String> status) {
        this.status = status;
    }
}
