package ee.hitsa.ois.web.dto.directive;

import java.time.LocalDate;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.sais.SaisApplication;
import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.domain.scholarship.ScholarshipTerm;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.FinSource;
import ee.hitsa.ois.enums.FinSpecific;
import ee.hitsa.ois.enums.StudyLoad;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.SaisAdmissionUtil;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class DirectiveStudentDto extends DirectiveForm.DirectiveFormStudent {

    private String fullname;
    private String oldStudyForm;
    private String oldStudyLoad;
    private AutocompleteResult oldCurriculumVersion;
    private String oldFin;
    private String oldFinSpecific;
    private String oldLanguage;
    private Boolean isCumLaude;
    private Boolean isOccupationExamPassed;
    private String curriculumGrade;
    private String bankAccount;
    private Boolean applicationIsPeriod;
    private LocalDate applicationStartDate;
    private LocalDate applicationEndDate;
    private AutocompleteResult applicationStudyPeriodStart;
    private AutocompleteResult applicationStudyPeriodEnd;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getOldStudyForm() {
        return oldStudyForm;
    }

    public void setOldStudyForm(String oldStudyForm) {
        this.oldStudyForm = oldStudyForm;
    }

    public String getOldStudyLoad() {
        return oldStudyLoad;
    }

    public void setOldStudyLoad(String oldStudyLoad) {
        this.oldStudyLoad = oldStudyLoad;
    }

    public AutocompleteResult getOldCurriculumVersion() {
        return oldCurriculumVersion;
    }

    public void setOldCurriculumVersion(AutocompleteResult oldCurriculumVersion) {
        this.oldCurriculumVersion = oldCurriculumVersion;
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

    public String getOldLanguage() {
        return oldLanguage;
    }

    public void setOldLanguage(String oldLanguage) {
        this.oldLanguage = oldLanguage;
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

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
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

    public static DirectiveStudentDto of(ScholarshipApplication application, DirectiveType directiveType) {
        DirectiveStudentDto dto = of(application.getStudent(), directiveType);
        ScholarshipTerm term = application.getScholarshipTerm();
        if("STIPTOETUS_ERI".equals(EntityUtil.getCode(term.getType()))) {
            dto.setStartDate(application.getScholarshipFrom());
            dto.setEndDate(application.getScholarshipThru());
        } else {
            dto.setStartDate(term.getPaymentStart());
            dto.setEndDate(term.getPaymentEnd());
        }
        dto.setBankAccount(application.getBankAccount());
        dto.setAmountPaid(term.getAmountPaid());
        dto.setScholarshipApplication(application.getId());
        return dto;
    }

    public static DirectiveStudentDto of(Application application, DirectiveType directiveType) {
        DirectiveStudentDto dto = of(application.getStudent(), directiveType);
        dto.setApplication(application.getId());
        // FIXME should copy old* values from application?
        switch(directiveType) {
        case KASKKIRI_AKAD:
            dto.setReason(EntityUtil.getNullableCode(application.getReason()));
            dto.setIsPeriod(application.getIsPeriod());
            dto.setStartDate(application.getStartDate());
            dto.setEndDate(application.getEndDate());
            dto.setStudyPeriodStart(EntityUtil.getNullableId(application.getStudyPeriodStart()));
            dto.setStudyPeriodEnd(EntityUtil.getNullableId(application.getStudyPeriodEnd()));
            dto.setApplicationIsPeriod(application.getIsPeriod());
            dto.setApplicationStartDate(application.getStartDate());
            dto.setApplicationEndDate(application.getEndDate());
            dto.setApplicationStudyPeriodStart(application.getStudyPeriodStart() != null ? AutocompleteResult.of(application.getStudyPeriodStart()) : null);
            dto.setApplicationStudyPeriodEnd(application.getStudyPeriodEnd() != null ? AutocompleteResult.of(application.getStudyPeriodEnd()) : null);
            break;
        case KASKKIRI_AKADK:
            dto.setStartDate(application.getStartDate());
            break;
        case KASKKIRI_EKSMAT:
            dto.setReason(EntityUtil.getNullableCode(application.getReason()));
            break;
        case KASKKIRI_FINM:
            dto.setFinSpecific(EntityUtil.getNullableCode(application.getNewFinSpecific()));
            break;
        case KASKKIRI_OKAVA:
            dto.setCurriculumVersion(EntityUtil.getNullableId(application.getNewCurriculumVersion()));
            dto.setStudyForm(EntityUtil.getNullableCode(application.getNewStudyForm()));
            break;
        case KASKKIRI_OVORM:
            dto.setStudyForm(EntityUtil.getNullableCode(application.getNewStudyForm()));
            break;
        case KASKKIRI_VALIS:
            dto.setIsAbroad(application.getIsAbroad());
            dto.setAbroadSchool(application.getAbroadSchool());
            dto.setEhisSchool(EntityUtil.getNullableCode(application.getEhisSchool()));
            dto.setCountry(!Boolean.TRUE.equals(application.getIsAbroad()) ? ClassifierUtil.COUNTRY_ESTONIA : EntityUtil.getNullableCode(application.getCountry()));
            dto.setIsPeriod(application.getIsPeriod());
            dto.setStartDate(application.getStartDate());
            dto.setEndDate(application.getEndDate());
            dto.setStudyPeriodStart(EntityUtil.getNullableId(application.getStudyPeriodStart()));
            dto.setStudyPeriodEnd(EntityUtil.getNullableId(application.getStudyPeriodEnd()));
            dto.setAbroadPurpose(EntityUtil.getNullableCode(application.getAbroadPurpose()));
            dto.setAbroadProgramme(EntityUtil.getNullableCode(application.getAbroadProgramme()));
            break;
        default:
            break;
        }
        return dto;
    }

    public static DirectiveStudentDto of(DirectiveStudent directiveStudent) {
        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directiveStudent.getDirective().getType()));
        Application app = directiveStudent.getApplication();
        Student student = directiveStudent.getStudent();
        DirectiveStudentDto dto;
        if(app != null) {
            dto = of(app, directiveType);
        } else if(student != null) {
            dto = of(student, directiveType);
        } else {
            // without student: KASKKIRI_IMMAT, KASKKIRI_IMMATV
            dto = new DirectiveStudentDto();
            Person person = directiveStudent.getPerson();
            if(person != null) {
                setPersonData(person, dto);
            }
        }
        return EntityUtil.bindToDto(directiveStudent, dto);
    }

    public static DirectiveStudentDto of(SaisApplication application) {
        DirectiveStudentDto dto = EntityUtil.bindToDto(application, new DirectiveStudentDto());
        CurriculumVersion cv = application.getSaisAdmission().getCurriculumVersion();
        dto.setCurriculumVersion(cv.getId());
        if(!application.getGraduatedSchools().isEmpty()) {
            dto.setPreviousStudyLevel(EntityUtil.getCode(application.getGraduatedSchools().stream().findFirst().get().getStudyLevel()));
        }
        dto.setSaisApplication(application.getId());

        // finSpecific default value
        boolean higher = SaisAdmissionUtil.isHigher(application.getSaisAdmission());
        FinSpecific s;
        if(FinSource.isFree(dto.getFin())) {
            s = higher ? FinSpecific.FINTAPSUSTUS_Y : FinSpecific.FINTAPSUSTUS_R;
        } else {
            s = higher ? FinSpecific.FINTAPSUSTUS_X : FinSpecific.FINTAPSUSTUS_T;
        }
        dto.setFinSpecific(s.name());
        return dto;
    }

    public static DirectiveStudentDto of(Student student, DirectiveType directiveType) {
        DirectiveStudentDto dto = new DirectiveStudentDto();
        dto.setStudent(student.getId());
        setPersonData(student.getPerson(), dto);

        switch(directiveType) {
        case KASKKIRI_AKAD:
            dto.setOldCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
            break;
        case KASKKIRI_ENNIST:
            dto.setStudentGroup(EntityUtil.getNullableId(student.getStudentGroup()));
            dto.setOldCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
            dto.setOldStudyForm(EntityUtil.getNullableCode(student.getStudyForm()));
            dto.setOldStudyLoad(EntityUtil.getNullableCode(student.getStudyLoad()));
            dto.setOldFin(EntityUtil.getNullableCode(student.getFin()));
            dto.setOldFinSpecific(EntityUtil.getNullableCode(student.getFinSpecific()));
            dto.setOldLanguage(EntityUtil.getNullableCode(student.getLanguage()));
            break;
        case KASKKIRI_FINM:
            dto.setOldFinSpecific(EntityUtil.getNullableCode(student.getFinSpecific()));
            dto.setFin(FinSource.isFree(EntityUtil.getNullableCode(student.getFin())) ? FinSource.FINALLIKAS_REV.name() : FinSource.FINALLIKAS_RE.name());
            break;
        case KASKKIRI_LOPET:
            dto.setOldCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
            // TODO
            // dto.setIsCumLaude(isCumLaude);
            // dto.setIsOccupationExamPassed(isOccupationExamPassed);
            // dto.setCurriculumGrade(curriculumGrade);
            break;
        case KASKKIRI_OKAVA:
            dto.setOldStudyForm(EntityUtil.getNullableCode(student.getStudyForm()));
            dto.setOldCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
            break;
        case KASKKIRI_OKOORM:
            dto.setOldFinSpecific(EntityUtil.getNullableCode(student.getFinSpecific()));
            // calculate new study load and fin from existing values
            boolean partial = ClassifierUtil.equals(StudyLoad.OPPEKOORMUS_OSA, student.getStudyLoad());
            dto.setStudyLoad(partial ? StudyLoad.OPPEKOORMUS_TAIS.name() : StudyLoad.OPPEKOORMUS_OSA.name());
            dto.setFin(partial ? FinSource.FINALLIKAS_RE.name() : FinSource.FINALLIKAS_REV.name());
            break;
        case KASKKIRI_OVORM:
            dto.setOldStudyForm(EntityUtil.getNullableCode(student.getStudyForm()));
            dto.setOldCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
            dto.setStudentGroup(EntityUtil.getNullableId(student.getStudentGroup()));
            break;
        default:
            break;
        }
        return dto;
    }

    private static void setPersonData(Person person, DirectiveStudentDto dto) {
        dto.setIdcode(person.getIdcode());
        dto.setFirstname(person.getFirstname());
        dto.setLastname(person.getLastname());
        dto.setFullname(person.getFullname());
    }
}
