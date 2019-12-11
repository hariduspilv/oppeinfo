package ee.hitsa.ois.web.dto.directive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentBase;
import ee.hitsa.ois.domain.student.StudentHistory;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.SupportServiceType;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm.DirectiveFormStudentModule;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;

public class DirectiveViewStudentDto {

    private Long student;
    private String idcode;
    private String foreignIdcode;
    private String firstname;
    private String lastname;
    private String fullname;
    private LocalDate birthdate;
    private String sex;
    private String citizenship;
    private Boolean isPeriod;
    private LocalDate startDate;
    private LocalDate endDate;
    private AutocompleteResult newStudyPeriodStart;
    private AutocompleteResult newStudyPeriodEnd;
    private LocalDate nominalStudyEnd;
    private String reason;
    private String fin;
    private String oldFinSpecific;
    private String finSpecific;
    private String language;
    private String previousStudyLevel;
    private String oldStudyForm;
    private String studyForm;
    private String studyLoad;
    private AutocompleteResult oldCurriculumVersion;
    private AutocompleteResult newCurriculumVersion;
    private String newStudentGroup;
    private Boolean applicationIsPeriod;
    private LocalDate applicationStartDate;
    private LocalDate applicationEndDate;
    private AutocompleteResult apelSchool;
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
    private List<String> occupations;
    private Map<String, List<String>> specialities;
    private AutocompleteResult curriculumGrade;
    private String bankAccount;
    private BigDecimal amountPaid;
    private AutocompleteResult studentGroupObject;
    private AutocompleteResult curriculumVersionObject;
    private String addInfo;
    private Boolean isAbsence;
    private AutocompleteResult scholarshipApplicationObject;
    private List<DirectiveFormStudentModule> modules;
    private ExistingDirectiveStudentDto directiveStudentObject;
    private List<AutocompleteResult> supportServices;
    private List<AutocompleteResult> supportModules;
    private String dormitory;

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

    public String getForeignIdcode() {
        return foreignIdcode;
    }

    public void setForeignIdcode(String foreignIdcode) {
        this.foreignIdcode = foreignIdcode;
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
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

    public AutocompleteResult getNewStudyPeriodStart() {
        return newStudyPeriodStart;
    }

    public void setNewStudyPeriodStart(AutocompleteResult newStudyPeriodStart) {
        this.newStudyPeriodStart = newStudyPeriodStart;
    }

    public AutocompleteResult getNewStudyPeriodEnd() {
        return newStudyPeriodEnd;
    }

    public void setNewStudyPeriodEnd(AutocompleteResult newStudyPeriodEnd) {
        this.newStudyPeriodEnd = newStudyPeriodEnd;
    }

    public LocalDate getNominalStudyEnd() {
        return nominalStudyEnd;
    }

    public void setNominalStudyEnd(LocalDate nominalStudyEnd) {
        this.nominalStudyEnd = nominalStudyEnd;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public AutocompleteResult getNewCurriculumVersion() {
        return newCurriculumVersion;
    }

    public void setNewCurriculumVersion(AutocompleteResult newCurriculumVersion) {
        this.newCurriculumVersion = newCurriculumVersion;
    }

    public String getNewStudentGroup() {
        return newStudentGroup;
    }

    public void setNewStudentGroup(String newStudentGroup) {
        this.newStudentGroup = newStudentGroup;
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

    public List<String> getOccupations() {
        return occupations;
    }

    public void setOccupations(List<String> occupations) {
        this.occupations = occupations;
    }

    public Map<String, List<String>> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Map<String, List<String>> specialities) {
        this.specialities = specialities;
    }

    public AutocompleteResult getCurriculumGrade() {
        return curriculumGrade;
    }

    public void setCurriculumGrade(AutocompleteResult curriculumGrade) {
        this.curriculumGrade = curriculumGrade;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public AutocompleteResult getStudentGroupObject() {
        return studentGroupObject;
    }

    public void setStudentGroupObject(AutocompleteResult studentGroupObject) {
        this.studentGroupObject = studentGroupObject;
    }

    public AutocompleteResult getCurriculumVersionObject() {
        return curriculumVersionObject;
    }

    public void setCurriculumVersionObject(AutocompleteResult curriculumVersionObject) {
        this.curriculumVersionObject = curriculumVersionObject;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Boolean getIsAbsence() {
        return isAbsence;
    }

    public void setIsAbsence(Boolean isAbsence) {
        this.isAbsence = isAbsence;
    }

    public AutocompleteResult getScholarshipApplicationObject() {
        return scholarshipApplicationObject;
    }

    public void setScholarshipApplicationObject(AutocompleteResult scholarshipApplicationObject) {
        this.scholarshipApplicationObject = scholarshipApplicationObject;
    }

    public List<DirectiveFormStudentModule> getModules() {
        return modules;
    }

    public void setModules(List<DirectiveFormStudentModule> modules) {
        this.modules = modules;
    }

    public ExistingDirectiveStudentDto getDirectiveStudentObject() {
        return directiveStudentObject;
    }

    public void setDirectiveStudentObject(ExistingDirectiveStudentDto directiveStudentObject) {
        this.directiveStudentObject = directiveStudentObject;
    }

    public List<AutocompleteResult> getSupportServices() {
        return supportServices;
    }

    public void setSupportServices(List<AutocompleteResult> supportServices) {
        this.supportServices = supportServices;
    }

    public List<AutocompleteResult> getSupportModules() {
        return supportModules;
    }

    public void setSupportModules(List<AutocompleteResult> supportModules) {
        this.supportModules = supportModules;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }
    
    public AutocompleteResult getApelSchool() {
        return apelSchool;
    }

    public void setApelSchool(AutocompleteResult apelSchool) {
        this.apelSchool = apelSchool;
    }

    public static DirectiveViewStudentDto of(DirectiveStudent directiveStudent) {
        DirectiveViewStudentDto dto = EntityUtil.bindToDto(directiveStudent, new DirectiveViewStudentDto(), "occupations");
        StudentBase student = directiveStudent.getStudent();
        Person person;
        if(student != null) {
            dto.setStudent(student.getId());
            person = ((Student)student).getPerson();
        } else {
            // possible only on unconfirmed IMMAT or IMMATV
            person = directiveStudent.getPerson();
        }
        if(person != null) {
            dto.setIdcode(person.getIdcode());
            dto.setForeignIdcode(person.getForeignIdcode());
            dto.setFirstname(person.getFirstname());
            dto.setLastname(person.getLastname());
            dto.setFullname(person.getFullname());
            dto.setBirthdate(person.getBirthdate());
            dto.setSex(EntityUtil.getNullableCode(person.getSex()));
            dto.setCitizenship(EntityUtil.getNullableCode(person.getCitizenship()));
        }

        if(ClassifierUtil.oneOf(directiveStudent.getDirective().getStatus(), DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD, DirectiveStatus.KASKKIRI_STAATUS_TYHISTATUD)) {
            // for confirmed and canceled directives, get pre-directive status from history
            StudentHistory studentHistory = directiveStudent.getStudentHistory();
            if (studentHistory != null) {
                student = studentHistory;
            }
        }
        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directiveStudent.getDirective().getType()));
        Application application = directiveStudent.getApplication();
        switch(directiveType) {
        case KASKKIRI_AKAD:
            dto.setNewStudyPeriodStart(directiveStudent.getStudyPeriodStart() != null ? AutocompleteResult.of(directiveStudent.getStudyPeriodStart()) : null);
            dto.setNewStudyPeriodEnd(directiveStudent.getStudyPeriodEnd() != null ? AutocompleteResult.of(directiveStudent.getStudyPeriodEnd()) : null);
            dto.setApplicationIsPeriod(application != null ? application.getIsPeriod() : null);
            dto.setApplicationStartDate(application != null ? application.getStartDate() : null);
            dto.setApplicationEndDate(application != null ? application.getEndDate() : null);
            dto.setApplicationStudyPeriodStart(application != null && application.getStudyPeriodStart() != null ? AutocompleteResult.of(application.getStudyPeriodStart()) : null);
            dto.setApplicationStudyPeriodEnd(application != null && application.getStudyPeriodEnd() != null ? AutocompleteResult.of(application.getStudyPeriodEnd()) : null);
            break;
        case KASKKIRI_ENNIST:
            if (student != null) {
                dto.setNewCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
                dto.setStudyForm(EntityUtil.getNullableCode(student.getStudyForm()));
                dto.setStudyLoad(EntityUtil.getNullableCode(student.getStudyLoad()));
                dto.setFin(EntityUtil.getNullableCode(student.getFin()));
                dto.setFinSpecific(EntityUtil.getNullableCode(student.getFinSpecific()));
                dto.setLanguage(EntityUtil.getNullableCode(student.getLanguage()));
            }
            dto.setNewStudentGroup(directiveStudent.getStudentGroup() != null ? directiveStudent.getStudentGroup().getCode() : null);
            break;
        case KASKKIRI_FINM:
            if (student != null) {
                dto.setOldFinSpecific(EntityUtil.getNullableCode(student.getFinSpecific()));
            }
            break;
        case KASKKIRI_INDOK:
            dto.setCurriculumVersionObject(directiveStudent.getCurriculumVersion() != null ? AutocompleteResult.of(directiveStudent.getCurriculumVersion()) : null);
            dto.setStudentGroupObject(directiveStudent.getStudentGroup() != null ? AutocompleteResult.of(directiveStudent.getStudentGroup()) : null);
            dto.setModules(StreamUtil.nullSafeList(directiveStudent.getModules()).stream()
                    .map(m -> DirectiveFormStudentModule.of(m)).collect(Collectors.toList()));
            break;
        case KASKKIRI_INDOKLOP:
            dto.setCurriculumVersionObject(directiveStudent.getCurriculumVersion() != null ? AutocompleteResult.of(directiveStudent.getCurriculumVersion()) : null);
            dto.setStudentGroupObject(directiveStudent.getStudentGroup() != null ? AutocompleteResult.of(directiveStudent.getStudentGroup()) : null);
            dto.setDirectiveStudentObject(directiveStudent.getDirectiveStudent() != null
                    ? ExistingDirectiveStudentDto.of(directiveStudent.getDirectiveStudent())
                    : null);
            break;
        case KASKKIRI_IMMAT:
        case KASKKIRI_IMMATV:
            dto.setNewCurriculumVersion(directiveStudent.getCurriculumVersion() != null ? AutocompleteResult.of(directiveStudent.getCurriculumVersion()) : null);
            dto.setNewStudentGroup(directiveStudent.getStudentGroup() != null ? directiveStudent.getStudentGroup().getCode() : null);
            break;
        case KASKKIRI_KIITUS:
        case KASKKIRI_NOOMI:
            dto.setCurriculumVersionObject(directiveStudent.getCurriculumVersion() != null ? AutocompleteResult.of(directiveStudent.getCurriculumVersion()) : null);
            dto.setStudentGroupObject(directiveStudent.getStudentGroup() != null ? AutocompleteResult.of(directiveStudent.getStudentGroup()) : null);
            break;
        case KASKKIRI_LOPET:
            if (student != null) {
                dto.setNewCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
                CurriculumGrade grade = directiveStudent.getCurriculumGrade();
                if (grade != null) {
                    dto.setCurriculumGrade(AutocompleteResult.of(grade));
                }
            }
            break;
        case KASKKIRI_OKAVA:
            if (student != null) {
                dto.setOldStudyForm(EntityUtil.getNullableCode(student.getStudyForm()));
                dto.setOldCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
            }
            dto.setNewCurriculumVersion(directiveStudent.getCurriculumVersion() != null ? AutocompleteResult.of(directiveStudent.getCurriculumVersion()) : null);
            dto.setNewStudentGroup(directiveStudent.getStudentGroup() != null ? directiveStudent.getStudentGroup().getCode() : null);
            break;
        case KASKKIRI_OTEGEVUS:
            dto.setCurriculumVersionObject(directiveStudent.getCurriculumVersion() != null ? AutocompleteResult.of(directiveStudent.getCurriculumVersion())
                    : student != null ? AutocompleteResult.of(student.getCurriculumVersion()) : null); // CurriculumVersion is must have object for student
            dto.setStudentGroupObject(directiveStudent.getStudentGroup() != null ? AutocompleteResult.of(directiveStudent.getStudentGroup())
                    : student != null && student.getStudentGroup() != null ? AutocompleteResult.of(student.getStudentGroup()) : null); // StudentGroup can be empty
            break;
        case KASKKIRI_OVORM:
            if (student != null) {
                dto.setOldStudyForm(EntityUtil.getNullableCode(student.getStudyForm()));
            }
            dto.setNewStudentGroup(directiveStudent.getStudentGroup() != null ? directiveStudent.getStudentGroup().getCode() : null);
            break;
        case KASKKIRI_PRAKTIK:
            dto.setCurriculumVersionObject(directiveStudent.getCurriculumVersion() != null ? AutocompleteResult.of(directiveStudent.getCurriculumVersion())
                    : student != null ? AutocompleteResult.of(student.getCurriculumVersion()) : null); // CurriculumVersion is must have object for student
            dto.setStudentGroupObject(directiveStudent.getStudentGroup() != null ? AutocompleteResult.of(directiveStudent.getStudentGroup())
                    : student != null && student.getStudentGroup() != null ? AutocompleteResult.of(student.getStudentGroup()) : null); // StudentGroup can be empty
            break;
        case KASKKIRI_STIPTOET:
            ScholarshipApplication sa = directiveStudent.getScholarshipApplication();
            if (sa != null) {
                String nameEt = sa.getScholarshipTerm().getNameEt();
                dto.setScholarshipApplicationObject(new AutocompleteResult(sa.getId(), nameEt, nameEt));
            }
            break;
        case KASKKIRI_STIPTOETL:
            dto.setDirectiveStudentObject(directiveStudent.getDirectiveStudent() != null
                    ? ExistingDirectiveStudentDto.of(directiveStudent.getDirectiveStudent())
                    : null);
            break;
        case KASKKIRI_TUGI:
            dto.setNominalStudyEnd(directiveStudent.getNominalStudyEnd() != null ? directiveStudent.getNominalStudyEnd() : application != null ? application.getStudent().getNominalStudyEnd() : null);
            dto.setStudentGroupObject(directiveStudent.getStudentGroup() != null ? AutocompleteResult.of(directiveStudent.getStudentGroup()) : null);
            dto.setSupportServices(application != null ? application.getSupportServices().stream()
                    .map(service -> new AutocompleteResult(null, ClassifierDto.of(service.getSupportService())))
                    .collect(Collectors.toList()) : Collections.emptyList());
            dto.setSupportModules(application != null ? application.getSupportServices().stream()
                    .filter(service -> ClassifierUtil.equals(SupportServiceType.TUGITEENUS_1, service.getSupportService()))
                    .flatMap(service -> service.getModules().stream()).map(module -> AutocompleteResult.of(module.getModule())).collect(Collectors.toList()) : Collections.emptyList());
            break;
        case KASKKIRI_TUGILOPP:
            dto.setStudentGroupObject(directiveStudent.getStudentGroup() != null ? AutocompleteResult.of(directiveStudent.getStudentGroup()) : null);
            dto.setDirectiveStudentObject(directiveStudent.getDirectiveStudent() != null
                    ? ExistingDirectiveStudentDto.of(directiveStudent.getDirectiveStudent())
                    : null);
            break;
        case KASKKIRI_VALIS:
            dto.setNewStudyPeriodStart(directiveStudent.getStudyPeriodStart() != null ? AutocompleteResult.ofWithYear(directiveStudent.getStudyPeriodStart()) : null);
            dto.setNewStudyPeriodEnd(directiveStudent.getStudyPeriodEnd() != null ? AutocompleteResult.ofWithYear(directiveStudent.getStudyPeriodEnd()) : null);
            break;
        case KASKKIRI_KYLALIS:
            dto.setCurriculumVersionObject(directiveStudent.getCurriculumVersion() != null ? AutocompleteResult.of(directiveStudent.getCurriculumVersion()) : null);
            dto.setStudentGroupObject(directiveStudent.getStudentGroup() != null ? AutocompleteResult.of(directiveStudent.getStudentGroup()) : null);
            dto.setApelSchool(AutocompleteResult.of(directiveStudent.getApelSchool()));
            break;
        default:
            break;
        }

        return dto;
    }
}
