package ee.hitsa.ois.web.commandobject.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.commandobject.OisFileForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelSchoolForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;
import ee.hitsa.ois.web.dto.application.ApplicationOccupationModuleThemeDto;
import ee.hitsa.ois.web.dto.application.ApplicationPlannedSubjectDto;
import ee.hitsa.ois.web.dto.application.ApplicationSupportServiceModuleDto;
import ee.hitsa.ois.web.dto.application.ValidAcademicLeaveDto;

@DateRange(from = "startDate", thru = "endDate")
public class ApplicationForm extends InsertedChangedVersionDto {

    @NotNull
    private AutocompleteResult student;

    @Required
    @ClassifierRestriction(MainClassCode.AVALDUS_STAATUS)
    private String status;

    @Required
    @ClassifierRestriction(MainClassCode.AVALDUS_LIIK)
    private String type;

    @ClassifierRestriction({MainClassCode.AKADPUHKUS_POHJUS, MainClassCode.EKSMAT_POHJUS})
    private String reason;

    @Size(max = 4000)
    private String rejectReason;

    @Size(max = 4000)
    private String addInfo;

    @ClassifierRestriction(MainClassCode.FINALLIKAS)
    private String oldFin;

    @ClassifierRestriction(MainClassCode.FINTAPSUSTUS)
    private String oldFinSpecific;

    @ClassifierRestriction(MainClassCode.FINALLIKAS)
    private String newFin;

    @ClassifierRestriction(MainClassCode.FINTAPSUSTUS)
    private String newFinSpecific;

    @ClassifierRestriction(MainClassCode.OPPEVORM)
    private String oldStudyForm;

    @ClassifierRestriction(MainClassCode.OPPEVORM)
    private String newStudyForm;

    private AutocompleteResult oldCurriculumVersion;
    private AutocompleteResult newCurriculumVersion;

    private Boolean isPeriod;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long apelSchool;
    private Long studyPeriodStart;
    private Long studyPeriodEnd;
    private ValidAcademicLeaveDto validAcademicLeave;

    private LocalDateTime submitted;

    private Boolean isAbroad;

    @Size(max = 255)
    private String abroadSchool;

    @ClassifierRestriction(MainClassCode.EHIS_KOOL)
    private String ehisSchool;

    @ClassifierRestriction(MainClassCode.RIIK)
    private String country;

    @ClassifierRestriction(MainClassCode.VALISOPE_EESMARK)
    private String abroadPurpose;

    @ClassifierRestriction(MainClassCode.VALISKOOL_PROGRAMM)
    private String abroadProgramme;

    @Size(max = 10000)
    private String otherText;
    
    private EntityConnectionCommand studentGroup;
    
    private EntityConnectionCommand committee;
    @Size(max = 10000)
    private String decision;
    private Boolean isDecided;
    @Size(max = 10000)
    private String implementationPlan;
    @Size(max = 4000)
    private String committeeAddInfo;
    private Set<ClassifierDto> supportServices;
    @Valid
    private Set<ApplicationSupportServiceModuleDto> selectedModules;

    private Set<OisFileForm> files;

    private Set<ApplicationPlannedSubjectDto> plannedSubjects;

    private Set<ApplicationOccupationModuleThemeDto> themeReplacements;
    
    private ApelSchoolForm newApelSchool;

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

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getOldFin() {
        return oldFin;
    }

    public void setOldFin(String oldFin) {
        this.oldFin = oldFin;
    }

    public String getOldFinSpecific() {
        return oldFinSpecific;
    }

    public void setOldFinSpecific(String oldFinSpecific) {
        this.oldFinSpecific = oldFinSpecific;
    }

    public String getNewFin() {
        return newFin;
    }

    public void setNewFin(String newFin) {
        this.newFin = newFin;
    }

    public String getNewFinSpecific() {
        return newFinSpecific;
    }

    public void setNewFinSpecific(String newFinSpecific) {
        this.newFinSpecific = newFinSpecific;
    }

    public String getOldStudyForm() {
        return oldStudyForm;
    }

    public void setOldStudyForm(String oldStudyForm) {
        this.oldStudyForm = oldStudyForm;
    }

    public String getNewStudyForm() {
        return newStudyForm;
    }

    public void setNewStudyForm(String newStudyForm) {
        this.newStudyForm = newStudyForm;
    }

    public AutocompleteResult getOldCurriculumVersion() {
        return oldCurriculumVersion;
    }

    public void setOldCurriculumVersion(AutocompleteResult oldCurriculumVersion) {
        this.oldCurriculumVersion = oldCurriculumVersion;
    }

    public AutocompleteResult getNewCurriculumVersion() {
        return newCurriculumVersion;
    }

    public void setNewCurriculumVersion(AutocompleteResult newCurriculumVersion) {
        this.newCurriculumVersion = newCurriculumVersion;
    }

    public Boolean getIsPeriod() {
        return isPeriod;
    }

    public void setIsPeriod(Boolean isPeriod) {
        this.isPeriod = isPeriod;
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

    public Long getStudyPeriodStart() {
        return studyPeriodStart;
    }

    public void setStudyPeriodStart(Long studyPeriodStart) {
        this.studyPeriodStart = studyPeriodStart;
    }

    public Long getStudyPeriodEnd() {
        return studyPeriodEnd;
    }

    public void setStudyPeriodEnd(Long studyPeriodEnd) {
        this.studyPeriodEnd = studyPeriodEnd;
    }

    public ValidAcademicLeaveDto getValidAcademicLeave() {
        return validAcademicLeave;
    }

    public void setValidAcademicLeave(ValidAcademicLeaveDto validAcademicLeave) {
        this.validAcademicLeave = validAcademicLeave;
    }

    public LocalDateTime getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDateTime submitted) {
        this.submitted = submitted;
    }

    public Boolean getIsAbroad() {
        return isAbroad;
    }

    public void setIsAbroad(Boolean isAbroad) {
        this.isAbroad = isAbroad;
    }

    public String getAbroadSchool() {
        return abroadSchool;
    }

    public void setAbroadSchool(String abroadSchool) {
        this.abroadSchool = abroadSchool;
    }

    public String getEhisSchool() {
        return ehisSchool;
    }

    public void setEhisSchool(String ehisSchool) {
        this.ehisSchool = ehisSchool;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAbroadPurpose() {
        return abroadPurpose;
    }

    public void setAbroadPurpose(String abroadPurpose) {
        this.abroadPurpose = abroadPurpose;
    }

    public String getAbroadProgramme() {
        return abroadProgramme;
    }

    public void setAbroadProgramme(String abroadProgramme) {
        this.abroadProgramme = abroadProgramme;
    }

    public String getOtherText() {
        return otherText;
    }

    public void setOtherText(String otherText) {
        this.otherText = otherText;
    }

    public EntityConnectionCommand getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(EntityConnectionCommand studentGroup) {
        this.studentGroup = studentGroup;
    }

    public EntityConnectionCommand getCommittee() {
        return committee;
    }

    public void setCommittee(EntityConnectionCommand committee) {
        this.committee = committee;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Boolean getIsDecided() {
        return isDecided;
    }

    public void setIsDecided(Boolean isDecided) {
        this.isDecided = isDecided;
    }

    public String getImplementationPlan() {
        return implementationPlan;
    }

    public void setImplementationPlan(String implementationPlan) {
        this.implementationPlan = implementationPlan;
    }

    public String getCommitteeAddInfo() {
        return committeeAddInfo;
    }

    public void setCommitteeAddInfo(String committeeAddInfo) {
        this.committeeAddInfo = committeeAddInfo;
    }

    public Set<ApplicationSupportServiceModuleDto> getSelectedModules() {
        return selectedModules;
    }

    public void setSelectedModules(Set<ApplicationSupportServiceModuleDto> selectedModules) {
        this.selectedModules = selectedModules;
    }

    public Set<ClassifierDto> getSupportServices() {
        return supportServices;
    }

    public void setSupportServices(Set<ClassifierDto> supportServices) {
        this.supportServices = supportServices;
    }

    public Set<ApplicationPlannedSubjectDto> getPlannedSubjects() {
        return plannedSubjects;
    }

    public void setPlannedSubjects(Set<ApplicationPlannedSubjectDto> plannedSubjects) {
        this.plannedSubjects = plannedSubjects;
    }

    public Set<OisFileForm> getFiles() {
        return files;
    }

    public void setFiles(Set<OisFileForm> files) {
        this.files = files;
    }

    public Set<ApplicationOccupationModuleThemeDto> getThemeReplacements() {
        return themeReplacements;
    }

    public void setThemeReplacements(Set<ApplicationOccupationModuleThemeDto> themeReplacements) {
        this.themeReplacements = themeReplacements;
    }

    public Long getApelSchool() {
        return apelSchool;
    }

    public void setApelSchool(Long apelSchool) {
        this.apelSchool = apelSchool;
    }

    public ApelSchoolForm getNewApelSchool() {
        return newApelSchool;
    }

    public void setNewApelSchool(ApelSchoolForm newApelSchool) {
        this.newApelSchool = newApelSchool;
    }
}
