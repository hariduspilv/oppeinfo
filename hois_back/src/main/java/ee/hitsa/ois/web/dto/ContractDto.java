package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class ContractDto extends VersionedCommand {

    private Long id;
    private String contractNr;
    private String status;
    private AutocompleteResult student;
    private AutocompleteResult module;
    private AutocompleteResult theme;
    private BigDecimal credits;
    private Short hours;
    private LocalDate startDate;
    private LocalDate endDate;
    private String practicePlace;
    private AutocompleteResult enterprise;
    private String contactPersonName;
    private String contactPersonPhone;
    private String contactPersonEmail;
    private String supervisorName;
    private String supervisorPhone;
    private String supervisorEmail;
    private AutocompleteResult teacher;
    private String otherSupervisor;
    private AutocompleteResult contractCoordinator;
    private String practicePlan;
    private AutocompleteResult subject;
    private Long wdId;
    private Boolean isPracticeAbsence;

    public static ContractDto of(Contract contract) {
        if (contract == null) {
            return null;
        }
        return EntityUtil.bindToDto(contract, new ContractDto());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractNr() {
        return contractNr;
    }

    public void setContractNr(String contractNr) {
        this.contractNr = contractNr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }

    public AutocompleteResult getModule() {
        return module;
    }

    public void setModule(AutocompleteResult module) {
        this.module = module;
    }

    public AutocompleteResult getTheme() {
        return theme;
    }

    public void setTheme(AutocompleteResult theme) {
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

    public AutocompleteResult getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(AutocompleteResult enterprise) {
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

    public AutocompleteResult getTeacher() {
        return teacher;
    }

    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }

    public String getOtherSupervisor() {
        return otherSupervisor;
    }

    public void setOtherSupervisor(String otherSupervisor) {
        this.otherSupervisor = otherSupervisor;
    }

    public AutocompleteResult getContractCoordinator() {
        return contractCoordinator;
    }

    public void setContractCoordinator(AutocompleteResult contractCoordinator) {
        this.contractCoordinator = contractCoordinator;
    }

    public String getPracticePlan() {
        return practicePlan;
    }

    public void setPracticePlan(String practicePlan) {
        this.practicePlan = practicePlan;
    }

    public AutocompleteResult getSubject() {
        return subject;
    }

    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }

    public Long getWdId() {
        return wdId;
    }

    public void setWdId(Long wdId) {
        this.wdId = wdId;
    }

    public Boolean getIsPracticeAbsence() {
        return isPracticeAbsence;
    }

    public void setIsPracticeAbsence(Boolean isPracticeAbsence) {
        this.isPracticeAbsence = isPracticeAbsence;
    }

}
