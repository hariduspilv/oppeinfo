package ee.hitsa.ois.web.commandobject.student;

import java.time.LocalDate;
import java.time.LocalTime;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentRemarkForm extends VersionedCommand {

    @Required
    private AutocompleteResult student;
    @Required
    @ClassifierRestriction(MainClassCode.MARKUS)
    private String reason;
    @Required
    private String remark;
    @Required
    private LocalDate remarkDate;
    private LocalTime remarkTime;

    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDate getRemarkDate() {
        return remarkDate;
    }

    public void setRemarkDate(LocalDate remarkDate) {
        this.remarkDate = remarkDate;
    }

    public LocalTime getRemarkTime() {
        return remarkTime;
    }

    public void setRemarkTime(LocalTime remarkTime) {
        this.remarkTime = remarkTime;
    }

}
