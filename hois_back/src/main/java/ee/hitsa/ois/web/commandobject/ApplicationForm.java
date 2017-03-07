package ee.hitsa.ois.web.commandobject;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.dto.ApplicationFileDto;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class ApplicationForm extends InsertedChangedVersionDto {

    @NotNull
    private AutocompleteResult student;

    @NotEmpty
    @ClassifierRestriction(MainClassCode.AVALDUS_STAATUS)
    private String status;

    @NotEmpty
    @ClassifierRestriction(MainClassCode.AVALDUS_LIIK)
    private String type;

    @ClassifierRestriction({MainClassCode.AKADPUHKUS_POHJUS, MainClassCode.EKSMAT_POHJUS})
    private String reason;

    @Size(max = 4000)
    private String additionalInfo;

    @ClassifierRestriction(MainClassCode.FINALLIKAS)
    private String oldFinancialSource;

    @ClassifierRestriction(MainClassCode.FINTAPSUSTUS)
    private String oldFinancialSourceSpecification;

    @ClassifierRestriction(MainClassCode.FINALLIKAS)
    private String newFinancialSource;

    @ClassifierRestriction(MainClassCode.FINTAPSUSTUS)
    private String newFinancialSourceSpecification;

    private Set<ApplicationFileDto> files;


    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getOldFinancialSource() {
        return oldFinancialSource;
    }

    public void setOldFinancialSource(String oldFinancialSource) {
        this.oldFinancialSource = oldFinancialSource;
    }

    public String getOldFinancialSourceSpecification() {
        return oldFinancialSourceSpecification;
    }

    public void setOldFinancialSourceSpecification(String oldFinancialSourceSpecification) {
        this.oldFinancialSourceSpecification = oldFinancialSourceSpecification;
    }

    public String getNewFinancialSource() {
        return newFinancialSource;
    }

    public void setNewFinancialSource(String newFinancialSource) {
        this.newFinancialSource = newFinancialSource;
    }

    public String getNewFinancialSourceSpecification() {
        return newFinancialSourceSpecification;
    }

    public void setNewFinancialSourceSpecification(String newFinancialSourceSpecification) {
        this.newFinancialSourceSpecification = newFinancialSourceSpecification;
    }

    public Set<ApplicationFileDto> getFiles() {
        return files;
    }

    public void setFiles(Set<ApplicationFileDto> files) {
        this.files = files;
    }

}
