package ee.hitsa.ois.web.dto.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.enums.SupportServiceType;
import ee.hitsa.ois.util.ApplicationUtil;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DirectiveUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class ApplicationDto extends InsertedChangedVersionDto {

    private Long id;
    private Boolean needsRepresentativeConfirm;
    private Boolean isAdult;
    private AutocompleteResult student;
    private String status;
    private String type;
    private String reason;
    private String rejectReason;
    private String addInfo;
    private String oldFin;
    private String oldFinSpecific;
    private String newFin;
    private String newFinSpecific;
    private String oldStudyForm;
    private String newStudyForm;
    private AutocompleteResult oldCurriculumVersion;
    private AutocompleteResult newCurriculumVersion;
    private Boolean isPeriod;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long studyPeriodStart;
    private Long studyPeriodEnd;
    private ValidAcademicLeaveDto validAcademicLeave;
    private LocalDateTime submitted;
    private Boolean isAbroad;
    private String abroadSchool;
    private String ehisSchool;
    private String country;
    private String abroadPurpose;
    private String abroadProgramme;
    private String otherText;
    private Set<ApplicationPlannedSubjectDto> plannedSubjects;
    private Set<ApplicationFileDto> files;
    
    private AutocompleteResult studentGroup;
    
    private AutocompleteResult committee;
    private Boolean isDecided;
    private String decision;
    private String implementationPlan;
    private Set<ClassifierDto> supportServices;
    private Set<ApplicationSupportServiceModuleDto> supportModules;

    private LocalDateTime committeeAdded;
    private LocalDateTime committeeDecisionAdded;
    private LocalDateTime representativeConfirmed;
    private String committeeAddInfo;
    private Boolean isRepresentativeConfirmed;
    private String representativeDecisionAddInfo;

    private Boolean canEditStudent;
    
    /** TUGI application specific */
    private Boolean canRemoveConfirmation;
    /** RAKKAVA application specific */
    private Boolean canChangeThemeReplacements;

    private Boolean hasBeenSeenByAdmin;

    public static ApplicationDto of(Application application) {
        ApplicationDto dto = EntityUtil.bindToDto(application, new ApplicationDto(), "files", "plannedSubjects", "validAcademicLeave", "studentGroup", "supportServices");
        dto.setFiles(StreamUtil.toMappedSet(ApplicationFileDto::of, application.getFiles()));
        dto.setPlannedSubjects(StreamUtil.toMappedSet(ApplicationPlannedSubjectDto::of, application.getPlannedSubjects()));
        if (application.getStudentGroup() != null) {
            dto.setStudentGroup(AutocompleteResult.of(application.getStudentGroup()));
        }

        DirectiveStudent directiveStudent = getDirectiveStudent(application);
        if (directiveStudent != null) {
            dto.setValidAcademicLeave(ValidAcademicLeaveDto.of(directiveStudent));
        }
        if (application.getCommittee() != null) {
            dto.setCommittee(AutocompleteResult.of(application.getCommittee(), true));
        }
        dto.setSupportServices(StreamUtil.toMappedSet(s -> ClassifierDto.of(s.getSupportService()), application.getSupportServices()));
        dto.setSupportModules(application.getSupportServices().stream()
                .filter(s -> ClassifierUtil.equals(SupportServiceType.TUGITEENUS_1, s.getSupportService()))
                .flatMap(s -> s.getModules().stream())
                .map(ApplicationSupportServiceModuleDto::of)
                .collect(Collectors.toSet()));
        dto.setIsAdult(Boolean.valueOf(StudentUtil.isAdultAndDoNotNeedRepresentative(application.getStudent())));
        dto.setCanEditStudent(Boolean.valueOf(StudentUtil.canBeEdited(application.getStudent())));
        return dto;
    }

    private static DirectiveStudent getDirectiveStudent(Application application) {
        Directive academicDirective = application.getDirective();
        if (academicDirective != null) {
            return DirectiveUtil.getDirectiveStudent(academicDirective,
                    EntityUtil.getId(application.getStudent()));
        }
        Application academicApplication = application.getAcademicApplication();
        if (academicApplication != null) {
            return ApplicationUtil.getDirectiveStudent(academicApplication);
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getNeedsRepresentativeConfirm() {
        return needsRepresentativeConfirm;
    }

    public void setNeedsRepresentativeConfirm(Boolean needsRepresentativeConfirm) {
        this.needsRepresentativeConfirm = needsRepresentativeConfirm;
    }

    public Boolean getIsAdult() {
        return isAdult;
    }

    public void setIsAdult(Boolean isAdult) {
        this.isAdult = isAdult;
    }

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

    public Set<ApplicationPlannedSubjectDto> getPlannedSubjects() {
        return plannedSubjects;
    }

    public void setPlannedSubjects(Set<ApplicationPlannedSubjectDto> plannedSubjects) {
        this.plannedSubjects = plannedSubjects;
    }

    public Set<ApplicationFileDto> getFiles() {
        return files;
    }

    public void setFiles(Set<ApplicationFileDto> files) {
        this.files = files;
    }

    public Boolean getCanEditStudent() {
        return canEditStudent;
    }

    public void setCanEditStudent(Boolean canEditStudent) {
        this.canEditStudent = canEditStudent;
    }

    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(AutocompleteResult studentGroup) {
        this.studentGroup = studentGroup;
    }

    public AutocompleteResult getCommittee() {
        return committee;
    }

    public void setCommittee(AutocompleteResult committee) {
        this.committee = committee;
    }

    public Boolean getIsDecided() {
        return isDecided;
    }

    public void setIsDecided(Boolean isDecided) {
        this.isDecided = isDecided;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getImplementationPlan() {
        return implementationPlan;
    }

    public void setImplementationPlan(String implementationPlan) {
        this.implementationPlan = implementationPlan;
    }

    public Set<ClassifierDto> getSupportServices() {
        return supportServices;
    }

    public void setSupportServices(Set<ClassifierDto> supportServices) {
        this.supportServices = supportServices;
    }

    public Set<ApplicationSupportServiceModuleDto> getSupportModules() {
        return supportModules;
    }

    public void setSupportModules(Set<ApplicationSupportServiceModuleDto> supportModules) {
        this.supportModules = supportModules;
    }

    public LocalDateTime getCommitteeAdded() {
        return committeeAdded;
    }

    public void setCommitteeAdded(LocalDateTime committeeAdded) {
        this.committeeAdded = committeeAdded;
    }

    public LocalDateTime getCommitteeDecisionAdded() {
        return committeeDecisionAdded;
    }

    public void setCommitteeDecisionAdded(LocalDateTime committeeDecisionAdded) {
        this.committeeDecisionAdded = committeeDecisionAdded;
    }

    public LocalDateTime getRepresentativeConfirmed() {
        return representativeConfirmed;
    }

    public void setRepresentativeConfirmed(LocalDateTime representativeConfirmed) {
        this.representativeConfirmed = representativeConfirmed;
    }

    public String getCommitteeAddInfo() {
        return committeeAddInfo;
    }

    public void setCommitteeAddInfo(String committeeAddInfo) {
        this.committeeAddInfo = committeeAddInfo;
    }

    public String getRepresentativeDecisionAddInfo() {
        return representativeDecisionAddInfo;
    }

    public void setRepresentativeDecisionAddInfo(String representativeDecisionAddInfo) {
        this.representativeDecisionAddInfo = representativeDecisionAddInfo;
    }

    public Boolean getIsRepresentativeConfirmed() {
        return isRepresentativeConfirmed;
    }

    public void setIsRepresentativeConfirmed(Boolean isRepresentativeConfirmed) {
        this.isRepresentativeConfirmed = isRepresentativeConfirmed;
    }

    public Boolean getHasBeenSeenByAdmin() {
        return hasBeenSeenByAdmin;
    }

    public void setHasBeenSeenByAdmin(Boolean hasBeenSeenByAdmin) {
        this.hasBeenSeenByAdmin = hasBeenSeenByAdmin;
    }

    public Boolean getCanRemoveConfirmation() {
        return canRemoveConfirmation;
    }

    public void setCanRemoveConfirmation(Boolean canRemoveConfirmation) {
        this.canRemoveConfirmation = canRemoveConfirmation;
    }

    public Boolean getCanChangeThemeReplacements() {
        return canChangeThemeReplacements;
    }

    public void setCanChangeThemeReplacements(Boolean canChangeThemeReplacements) {
        this.canChangeThemeReplacements = canChangeThemeReplacements;
    }

}
