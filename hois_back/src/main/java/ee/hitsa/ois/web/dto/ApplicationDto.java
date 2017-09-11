package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.web.dto.directive.DirectiveStudentDto;

public class ApplicationDto extends InsertedChangedVersionDto {

    private Long id;
    private Boolean needsRepresentativeConfirm;
    private Boolean isAdult;
    private DirectiveStudentDto directiveStudent;
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
    private ApplicationDto academicApplication;
    private LocalDateTime submitted;
    private Boolean isAbroad;
    private String abroadSchool;
    private String ehisSchool;
    private String country;
    private String abroadPurpose;
    private String abroadProgramme;
    private Set<ApplicationPlannedSubjectDto> plannedSubjects;
    private Set<ApplicationFileDto> files;

    public static ApplicationDto of(Application application) {
        if (application == null) {
            return null;
        }

        ApplicationDto dto = EntityUtil.bindToDto(application, new ApplicationDto(), "files", "plannedSubjects", "academicApplication",
                "directiveStudent");
        dto.setFiles(StreamUtil.toMappedSet(ApplicationFileDto::of, application.getFiles()));
        dto.setPlannedSubjects(StreamUtil.toMappedSet(ApplicationPlannedSubjectDto::of, application.getPlannedSubjects()));
        dto.setAcademicApplication(ApplicationDto.of(application.getAcademicApplication()));

        if (ClassifierUtil.equals(ApplicationType.AVALDUS_LIIK_AKAD, application.getType())) {
            // lets use the last directive student ?
            application.getDirectiveStudents().stream().max(Comparator.comparingLong(DirectiveStudent::getId))
                    .ifPresent(it -> dto.setDirectiveStudent(DirectiveStudentDto.of(it)));
        }
        dto.setIsAdult(Boolean.valueOf(StudentUtil.isAdultAndDoNotNeedRepresentative(application.getStudent())));
        return dto;
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

    public DirectiveStudentDto getDirectiveStudent() {
        return directiveStudent;
    }

    public void setDirectiveStudent(DirectiveStudentDto directiveStudent) {
        this.directiveStudent = directiveStudent;
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

    public ApplicationDto getAcademicApplication() {
        return academicApplication;
    }

    public void setAcademicApplication(ApplicationDto academicApplication) {
        this.academicApplication = academicApplication;
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

}
