package ee.hitsa.ois.web.dto.sais;

import java.time.LocalDate;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.sais.SaisApplication;

public class SaisApplicationImportedRowDto {

    private int rowNr;
    private String applicationNr;
    private String message;
    private String idCode;
    private String firstName;
    private String lastName;
    private String admissionCode;
    private String applicationStatus;
    private LocalDate applicationDate;

    public SaisApplicationImportedRowDto() {
    }

    public SaisApplicationImportedRowDto(int rowNr, String message) {
        this.rowNr = rowNr;
        this.message = message;
    }

    public SaisApplicationImportedRowDto(String applicationNr, String message) {
        this.applicationNr = applicationNr;
        this.message = message;
    }
    
    public SaisApplicationImportedRowDto(String applicationNr, LocalDate applicationDate, String message) {
        this.applicationNr = applicationNr;
        this.applicationDate = applicationDate;
        this.message = message;
    }

    public SaisApplicationImportedRowDto(SaisApplication application, String message) {
        this.applicationDate = application.getSubmitted();
        this.message = message;
        this.applicationNr = application.getApplicationNr();
        this.idCode = application.getIdcode();
        this.firstName = application.getFirstname();
        this.lastName = application.getLastname();
        this.admissionCode = application.getSaisAdmission().getCode();
        this.applicationStatus = application.getStatus().getCode();
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("applicationNr: ");
        sb.append(applicationNr);
        if (message != null && message.isEmpty()) {
            sb.append(";firstName: ");
            sb.append(firstName);
            sb.append(";lastName: ");
            sb.append(lastName);
            sb.append(";idCode: ");
            sb.append(idCode);
            sb.append(";admissionCode: ");
            sb.append(admissionCode);
        } else {
            sb.append(";applicationDate: ");
            sb.append(applicationDate);
            sb.append(";message: ");
            sb.append(message);
        }
        return sb.toString();
    }


    public int getRowNr() {
        return rowNr;
    }

    public void setRowNr(int rowNr) {
        this.rowNr = rowNr;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getApplicationNr() {
        return applicationNr;
    }

    public void setApplicationNr(String applicationNr) {
        this.applicationNr = applicationNr;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdmissionCode() {
        return admissionCode;
    }

    public void setAdmissionCode(String admissionCode) {
        this.admissionCode = admissionCode;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

}
