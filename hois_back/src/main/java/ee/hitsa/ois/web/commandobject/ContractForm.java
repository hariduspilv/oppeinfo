package ee.hitsa.ois.web.commandobject;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.ContractValidation;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.web.dto.AutocompleteResult;

@DateRange(from = "startDate", thru = "endDate")
public class ContractForm extends VersionedCommand {

    @NotNull
    private AutocompleteResult student;
    @NotNull(groups = ContractValidation.Vocational.class)
    private Long module;
    private Long theme;
    @NotNull
    @DecimalMin("0")
    @DecimalMax("999.9")
    private BigDecimal credits;
    @NotNull
    @Min(0)
    @Max(Short.MAX_VALUE)
    private Short hours;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    @Size(max = 255)
    private String practicePlace;
    @NotNull
    private Long enterprise;
    @NotNull
    @Size(max = 100)
    private String contactPersonName;
    @Size(max = 100)
    private String contactPersonPhone;
    @NotNull
    @Size(max = 100)
    private String contactPersonEmail;
    @NotNull
    @Size(max = 100)
    private String supervisorName;
    @Size(max = 100)
    private String supervisorPhone;
    @NotNull
    @Size(max = 100)
    private String supervisorEmail;
    @NotNull
    private Long teacher;
    @Size(max = 255)
    private String otherSupervisor;
    @NotNull
    private Long contractCoordinator;
    @NotNull
    @Size(max = 20000)
    private String practicePlan;
    @NotNull(groups = ContractValidation.Higher.class)
    private Long subject;
    private Boolean isHigher;

    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }

    public Long getModule() {
        return module;
    }

    public void setModule(Long module) {
        this.module = module;
    }

    public Long getTheme() {
        return theme;
    }

    public void setTheme(Long theme) {
        this.theme = theme;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public Short getHours() {
        return hours;
    }

    public void setHours(Short hours) {
        this.hours = hours;
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

    public String getPracticePlace() {
        return practicePlace;
    }

    public void setPracticePlace(String practicePlace) {
        this.practicePlace = practicePlace;
    }

    public Long getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Long enterprise) {
        this.enterprise = enterprise;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getSupervisorPhone() {
        return supervisorPhone;
    }

    public void setSupervisorPhone(String supervisorPhone) {
        this.supervisorPhone = supervisorPhone;
    }

    public String getSupervisorEmail() {
        return supervisorEmail;
    }

    public void setSupervisorEmail(String supervisorEmail) {
        this.supervisorEmail = supervisorEmail;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    public String getOtherSupervisor() {
        return otherSupervisor;
    }

    public void setOtherSupervisor(String otherSupervisor) {
        this.otherSupervisor = otherSupervisor;
    }

    public Long getContractCoordinator() {
        return contractCoordinator;
    }

    public void setContractCoordinator(Long contractCoordinator) {
        this.contractCoordinator = contractCoordinator;
    }

    public String getPracticePlan() {
        return practicePlan;
    }

    public void setPracticePlan(String practicePlan) {
        this.practicePlan = practicePlan;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }
}
