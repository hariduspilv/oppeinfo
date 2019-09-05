package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ContractSupervisorDto;

@DateRange(from = "startDate", thru = "endDate")
public class ContractForm extends VersionedCommand {

    private AutocompleteResult student;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @Size(max = 255)
    private String practicePlace;
    @NotNull
    private AutocompleteResult enterprise;
    @NotNull
    @Size(max = 100)
    private String contactPersonName;
    @Size(max = 100)
    private String contactPersonPhone;
    @NotNull
    @Size(max = 100)
    private String contactPersonEmail;
    @NotNull
    private AutocompleteResult teacher;
    @Size(max = 255)
    private String otherSupervisor;
    private Long contractCoordinator;
    @NotNull
    @Size(max = 20000)
    private String practicePlan;
    private Boolean isHigher;
    private Boolean isPracticeAbsence;
    private Boolean isPracticeHidden;
    private Boolean isPracticeSchool;
    private Boolean isPracticeTelework;
    private Boolean isPracticeEnterprise;
    private Boolean isPracticeOther;
    private Long practiceEvaluation;
    private Long studentPracticeEvaluation;
    private Long practiceApplication;
    private List<ContractSupervisorDto> supervisors;
    private List<AutocompleteResult> students;
    @NotEmpty
    @Valid
    private List<ContractModuleSubjectForm> moduleSubjects;

    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
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

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }

    public Boolean getIsPracticeAbsence() {
        return isPracticeAbsence;
    }

    public void setIsPracticeAbsence(Boolean isPracticeAbsence) {
        this.isPracticeAbsence = isPracticeAbsence;
    }

    public Boolean getIsPracticeHidden() {
        return isPracticeHidden;
    }

    public void setIsPracticeHidden(Boolean isPracticeHidden) {
        this.isPracticeHidden = isPracticeHidden;
    }

    public Long getPracticeApplication() {
        return practiceApplication;
    }

    public void setPracticeApplication(Long practiceApplication) {
        this.practiceApplication = practiceApplication;
    }

    public Boolean getIsPracticeSchool() {
        return isPracticeSchool;
    }

    public void setIsPracticeSchool(Boolean isPracticeSchool) {
        this.isPracticeSchool = isPracticeSchool;
    }

    public Boolean getIsPracticeTelework() {
        return isPracticeTelework;
    }

    public void setIsPracticeTelework(Boolean isPracticeTelework) {
        this.isPracticeTelework = isPracticeTelework;
    }

    public Boolean getIsPracticeEnterprise() {
        return isPracticeEnterprise;
    }

    public void setIsPracticeEnterprise(Boolean isPracticeEnterprise) {
        this.isPracticeEnterprise = isPracticeEnterprise;
    }

    public Boolean getIsPracticeOther() {
        return isPracticeOther;
    }

    public void setIsPracticeOther(Boolean isPracticeOther) {
        this.isPracticeOther = isPracticeOther;
    }

    public List<ContractSupervisorDto> getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(List<ContractSupervisorDto> supervisors) {
        this.supervisors = supervisors;
    }

    public List<ContractModuleSubjectForm> getModuleSubjects() {
        return moduleSubjects;
    }

    public void setModuleSubjects(List<ContractModuleSubjectForm> moduleSubjects) {
        this.moduleSubjects = moduleSubjects;
    }

    public Long getPracticeEvaluation() {
        return practiceEvaluation;
    }

    public void setPracticeEvaluation(Long practiceEvaluation) {
        this.practiceEvaluation = practiceEvaluation;
    }

    public Long getStudentPracticeEvaluation() {
        return studentPracticeEvaluation;
    }

    public void setStudentPracticeEvaluation(Long studentPracticeEvaluation) {
        this.studentPracticeEvaluation = studentPracticeEvaluation;
    }

    public List<AutocompleteResult> getStudents() {
        return students;
    }

    public void setStudents(List<AutocompleteResult> students) {
        this.students = students;
    }

}
