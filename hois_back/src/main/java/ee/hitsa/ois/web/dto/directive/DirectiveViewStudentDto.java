package ee.hitsa.ois.web.dto.directive;

import java.time.LocalDate;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class DirectiveViewStudentDto {

    private Long student;
    private String idcode;
    private String firstname;
    private String lastname;
    private String fullname;
    private Boolean isPeriod;
    private LocalDate startDate;
    private LocalDate endDate;
    private AutocompleteResult studyPeriodStart;
    private AutocompleteResult studyPeriodEnd;
    private String reason;
    private String addInfo;
    private String fin;
    private String oldFinSpecific;
    private String finSpecific;
    private String language;
    private String previousStudyLevel;
    private String oldStudyForm;
    private String studyForm;
    private String studyLoad;
    private AutocompleteResult oldCurriculumVersion;
    private AutocompleteResult curriculumVersion;
    private String studentGroup;
    private Boolean applicationIsPeriod;
    private LocalDate applicationStartDate;
    private LocalDate applicationEndDate;
    private AutocompleteResult applicationStudyPeriodStart;
    private AutocompleteResult applicationStudyPeriodEnd;
    private Boolean isAbroad;
    private String ehisSchool;
    private String abroadSchool;
    private String email;
    private String country;
    private String abroadPurpose;
    private String abroadProgramme;
    private Boolean isCumLaude;
    private Boolean isOccupationExamPassed;
    private String curriculumGrade;

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public AutocompleteResult getStudyPeriodStart() {
        return studyPeriodStart;
    }

    public void setStudyPeriodStart(AutocompleteResult studyPeriodStart) {
        this.studyPeriodStart = studyPeriodStart;
    }

    public AutocompleteResult getStudyPeriodEnd() {
        return studyPeriodEnd;
    }

    public void setStudyPeriodEnd(AutocompleteResult studyPeriodEnd) {
        this.studyPeriodEnd = studyPeriodEnd;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getOldFinSpecific() {
        return oldFinSpecific;
    }

    public void setOldFinSpecific(String oldFinSpecific) {
        this.oldFinSpecific = oldFinSpecific;
    }

    public String getFinSpecific() {
        return finSpecific;
    }

    public void setFinSpecific(String finSpecific) {
        this.finSpecific = finSpecific;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPreviousStudyLevel() {
        return previousStudyLevel;
    }

    public void setPreviousStudyLevel(String previousStudyLevel) {
        this.previousStudyLevel = previousStudyLevel;
    }

    public String getOldStudyForm() {
        return oldStudyForm;
    }

    public void setOldStudyForm(String oldStudyForm) {
        this.oldStudyForm = oldStudyForm;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public String getStudyLoad() {
        return studyLoad;
    }

    public void setStudyLoad(String studyLoad) {
        this.studyLoad = studyLoad;
    }

    public AutocompleteResult getOldCurriculumVersion() {
        return oldCurriculumVersion;
    }

    public void setOldCurriculumVersion(AutocompleteResult oldCurriculumVersion) {
        this.oldCurriculumVersion = oldCurriculumVersion;
    }

    public AutocompleteResult getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(AutocompleteResult curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Boolean getApplicationIsPeriod() {
        return applicationIsPeriod;
    }

    public void setApplicationIsPeriod(Boolean applicationIsPeriod) {
        this.applicationIsPeriod = applicationIsPeriod;
    }

    public LocalDate getApplicationStartDate() {
        return applicationStartDate;
    }

    public void setApplicationStartDate(LocalDate applicationStartDate) {
        this.applicationStartDate = applicationStartDate;
    }

    public LocalDate getApplicationEndDate() {
        return applicationEndDate;
    }

    public void setApplicationEndDate(LocalDate applicationEndDate) {
        this.applicationEndDate = applicationEndDate;
    }

    public AutocompleteResult getApplicationStudyPeriodStart() {
        return applicationStudyPeriodStart;
    }

    public void setApplicationStudyPeriodStart(AutocompleteResult applicationStudyPeriodStart) {
        this.applicationStudyPeriodStart = applicationStudyPeriodStart;
    }

    public AutocompleteResult getApplicationStudyPeriodEnd() {
        return applicationStudyPeriodEnd;
    }

    public void setApplicationStudyPeriodEnd(AutocompleteResult applicationStudyPeriodEnd) {
        this.applicationStudyPeriodEnd = applicationStudyPeriodEnd;
    }

    public Boolean getIsAbroad() {
        return isAbroad;
    }

    public void setIsAbroad(Boolean isAbroad) {
        this.isAbroad = isAbroad;
    }

    public String getEhisSchool() {
        return ehisSchool;
    }

    public void setEhisSchool(String ehisSchool) {
        this.ehisSchool = ehisSchool;
    }

    public String getAbroadSchool() {
        return abroadSchool;
    }

    public void setAbroadSchool(String abroadSchool) {
        this.abroadSchool = abroadSchool;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Boolean getIsCumLaude() {
        return isCumLaude;
    }

    public void setIsCumLaude(Boolean isCumLaude) {
        this.isCumLaude = isCumLaude;
    }

    public Boolean getIsOccupationExamPassed() {
        return isOccupationExamPassed;
    }

    public void setIsOccupationExamPassed(Boolean isOccupationExamPassed) {
        this.isOccupationExamPassed = isOccupationExamPassed;
    }

    public String getCurriculumGrade() {
        return curriculumGrade;
    }

    public void setCurriculumGrade(String curriculumGrade) {
        this.curriculumGrade = curriculumGrade;
    }

    public static DirectiveViewStudentDto of(DirectiveStudent directiveStudent) {
        DirectiveViewStudentDto dto = EntityUtil.bindToDto(directiveStudent, new DirectiveViewStudentDto());
        Student student = directiveStudent.getStudent();
        Person person;
        if(student != null) {
            dto.setStudent(student.getId());
            person = student.getPerson();
        } else {
            // possible only on unconfirmed IMMAT or IMMATV
            person = directiveStudent.getPerson();
        }
        if(person != null) {
            dto.setIdcode(person.getIdcode());
            dto.setFirstname(person.getFirstname());
            dto.setLastname(person.getLastname());
            dto.setFullname(person.getFullname());
        }

        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directiveStudent.getDirective().getType()));
        Application application = directiveStudent.getApplication();
        switch(directiveType) {
        case KASKKIRI_AKAD:
            dto.setApplicationIsPeriod(application != null ? application.getIsPeriod() : null);
            dto.setApplicationStartDate(application != null ? application.getStartDate() : null);
            dto.setApplicationEndDate(application != null ? application.getEndDate() : null);
            dto.setApplicationStudyPeriodStart(application != null && application.getStudyPeriodStart() != null ? AutocompleteResult.of(application.getStudyPeriodStart()) : null);
            dto.setApplicationStudyPeriodEnd(application != null && application.getStudyPeriodEnd() != null ? AutocompleteResult.of(application.getStudyPeriodEnd()) : null);
            break;
        case KASKKIRI_ENNIST:
            dto.setCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
            dto.setStudyForm(EntityUtil.getNullableCode(student.getStudyForm()));
            dto.setStudyLoad(EntityUtil.getNullableCode(student.getStudyLoad()));
            dto.setFin(EntityUtil.getNullableCode(student.getFin()));
            dto.setFinSpecific(EntityUtil.getNullableCode(student.getFinSpecific()));
            dto.setLanguage(EntityUtil.getNullableCode(student.getLanguage()));
            break;
        case KASKKIRI_EKSMAT:
            if(application != null) {
                dto.setAddInfo(application.getAddInfo());
            }
            break;
        case KASKKIRI_FINM:
            dto.setOldFinSpecific(EntityUtil.getNullableCode(student.getFinSpecific()));
            break;
        case KASKKIRI_IMMAT:
        case KASKKIRI_IMMATV:
            dto.setStudentGroup(directiveStudent.getStudentGroup() != null ? directiveStudent.getStudentGroup().getCode() : null);
            break;
        case KASKKIRI_LOPET:
            dto.setCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
            // TODO
            // dto.setIsCumLaude(isCumLaude);
            // dto.setIsOccupationExamPassed(isOccupationExamPassed);
            // dto.setCurriculumGrade(curriculumGrade);
            break;
        case KASKKIRI_OKAVA:
            dto.setOldStudyForm(EntityUtil.getNullableCode(student.getStudyForm()));
            dto.setOldCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
            dto.setCurriculumVersion(AutocompleteResult.of(directiveStudent.getCurriculumVersion()));
            dto.setStudentGroup(student.getStudentGroup() != null ? student.getStudentGroup().getCode() : null);
            break;
        case KASKKIRI_OVORM:
            dto.setOldStudyForm(EntityUtil.getNullableCode(student.getStudyForm()));
            dto.setStudentGroup(student.getStudentGroup() != null ? student.getStudentGroup().getCode() : null);
            break;
        default:
            break;
        }

        return dto;
    }
}    
