package ee.hitsa.ois.report.diploma;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.domain.Form;
import ee.hitsa.ois.domain.diploma.Diploma;
import ee.hitsa.ois.domain.diploma.DiplomaSupplement;
import ee.hitsa.ois.enums.Language;

public class DiplomaSupplementReport extends DiplomaSupplementResultReport {

    private final Language lang;
    
    private final String diplomaNr;
    private final List<String> additionalNrs;
    private final Boolean showSubjectCode;
    private final Boolean showTeacher;
    private final Boolean isLetterGrades;
    private final String schoolName;
    private final String schoolNameEn;
    private final String firstname;
    private final String lastname;
    private final String idcode;
    private final LocalDate birthdate;
    private final String curriculumName;
    private final String merCode;
    private final String ekr;
    private final BigDecimal credits;
    private final String vocationalCurriculumType;
    private final LocalDate curriculumMerRegDate;
    private final String curriculumCompletion;
    private final BigDecimal averageMark;
    private final Integer studyYears;
    private final Integer studyMonths;
    private final String studyLoadName;
    private final String studyFormName;
    private final String studyLanguageName;
    private final String outcomes;
    private final String studyCompany;
    private final String final21;
    private final String final31;
    private final String final33;
    private final String final51;
    private final String final52;
    private final String final61;
    private final String final62;
    private final String schoolType;
    private final String signer1Name;
    private final String signer1Position;
    private final String signer2Name;
    private final String signer2Position;
    private final LocalDate printed;

    public DiplomaSupplementReport(DiplomaSupplement diplomaSupplement, List<String> additionalNrs) {
        this(diplomaSupplement, additionalNrs, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Language.ET);
    }

    public DiplomaSupplementReport(DiplomaSupplement diplomaSupplement, List<String> additionalNrs, 
            Boolean showSubjectCode, Boolean showTeacher, Boolean isLetterGrades, Language lang) {
        this.lang = lang;
        
        Diploma diploma = diplomaSupplement.getDiploma();
        Form diplomaForm = diploma != null ? diploma.getForm() : null;
        this.diplomaNr = diplomaForm != null ? diplomaForm.getFullCode() : "XXXXXX";
        
        this.additionalNrs = additionalNrs;
        this.showSubjectCode = showSubjectCode;
        this.showTeacher = showTeacher;
        this.isLetterGrades = isLetterGrades;
        this.schoolName = diplomaSupplement.getSchoolNameEt();
        this.schoolNameEn = diplomaSupplement.getSchoolNameEn();
        this.firstname = diplomaSupplement.getFirstname();
        this.lastname = diplomaSupplement.getLastname();
        this.idcode = diplomaSupplement.getIdcode();
        this.birthdate = diplomaSupplement.getBirthdate();
        this.merCode = diplomaSupplement.getMerCode();
        this.ekr = diplomaSupplement.getEkr();
        this.credits = diplomaSupplement.getCredits();
        this.vocationalCurriculumType = diplomaSupplement.getVocationalCurriculumType();
        this.curriculumMerRegDate = diplomaSupplement.getCurriculumMerRegDate();
        this.averageMark = diplomaSupplement.getAverageMark();
        int studyPeriod = diplomaSupplement.getStudyPeriod().intValue();
        this.studyYears = Integer.valueOf(studyPeriod / 12);
        this.studyMonths = Integer.valueOf(studyPeriod % 12);
        this.studyCompany = diplomaSupplement.getStudyCompany();
        this.signer1Name = diplomaSupplement.getSigner1Name();
        this.signer2Name = diplomaSupplement.getSigner2Name();
        this.printed = diplomaSupplement.getPrinted();
        if (Language.EN.equals(lang)) {
            this.curriculumName = diplomaSupplement.getCurriculumNameEn();
            this.studyLoadName = diplomaSupplement.getStudyLoadNameEn();
            this.studyFormName = diplomaSupplement.getStudyFormNameEn();
            this.studyLanguageName = diplomaSupplement.getStudyLanguageNameEn();
            this.outcomes = diplomaSupplement.getOutcomesEn();
            this.curriculumCompletion = diplomaSupplement.getCurriculumCompletionEn();
            this.final21 = diplomaSupplement.getFinalEn21();
            this.final31 = diplomaSupplement.getFinalEn31();
            this.final33 = diplomaSupplement.getFinalEn33();
            this.final51 = diplomaSupplement.getFinalEn51();
            this.final52 = diplomaSupplement.getFinalEn52();
            this.final61 = diplomaSupplement.getFinalEn61();
            this.final62 = diplomaSupplement.getFinalEn62();
            this.schoolType = diplomaSupplement.getSchoolTypeEn();
            this.signer1Position = diplomaSupplement.getSigner1PositionEn();
            this.signer2Position = diplomaSupplement.getSigner2PositionEn();
        } else {
            this.curriculumName = diplomaSupplement.getCurriculumNameEt();
            this.studyLoadName = diplomaSupplement.getStudyLoadNameEt();
            this.studyFormName = diplomaSupplement.getStudyFormNameEt();
            this.studyLanguageName = diplomaSupplement.getStudyLanguageNameEt();
            this.outcomes = diplomaSupplement.getOutcomesEt();
            this.curriculumCompletion = diplomaSupplement.getCurriculumCompletion();
            this.final21 = diplomaSupplement.getFinal21();
            this.final31 = diplomaSupplement.getFinal31();
            this.final33 = diplomaSupplement.getFinal33();
            this.final51 = diplomaSupplement.getFinal51();
            this.final52 = diplomaSupplement.getFinal52();
            this.final61 = diplomaSupplement.getFinal61();
            this.final62 = diplomaSupplement.getFinal62();
            this.schoolType = diplomaSupplement.getSchoolType();
            this.signer1Position = diplomaSupplement.getSigner1Position();
            this.signer2Position = diplomaSupplement.getSigner2Position();
        }
    }

    public Language getLang() {
        return lang;
    }

    public String getDiplomaNr() {
        return diplomaNr;
    }

    public List<String> getAdditionalNrs() {
        return additionalNrs;
    }

    public Boolean getShowSubjectCode() {
        return showSubjectCode;
    }

    public Boolean getShowTeacher() {
        return showTeacher;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public Boolean getIsLetterGrades() {
        return isLetterGrades;
    }

    public String getSchoolNameEn() {
        return schoolNameEn;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getIdcode() {
        return idcode;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String getCurriculumName() {
        return curriculumName;
    }

    public String getMerCode() {
        return merCode;
    }

    public String getEkr() {
        return ekr;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public String getVocationalCurriculumType() {
        return vocationalCurriculumType;
    }

    public LocalDate getCurriculumMerRegDate() {
        return curriculumMerRegDate;
    }

    public String getCurriculumCompletion() {
        return curriculumCompletion;
    }

    public BigDecimal getAverageMark() {
        return averageMark;
    }

    public Integer getStudyYears() {
        return studyYears;
    }

    public Integer getStudyMonths() {
        return studyMonths;
    }

    public String getStudyLoadName() {
        return studyLoadName;
    }

    public String getStudyFormName() {
        return studyFormName;
    }

    public String getStudyLanguageName() {
        return studyLanguageName;
    }

    public String getOutcomes() {
        return outcomes;
    }

    public String getStudyCompany() {
        return studyCompany;
    }

    public String getFinal21() {
        return final21;
    }

    public String getFinal31() {
        return final31;
    }

    public String getFinal33() {
        return final33;
    }

    public String getFinal51() {
        return final51;
    }

    public String getFinal52() {
        return final52;
    }

    public String getFinal61() {
        return final61;
    }

    public String getFinal62() {
        return final62;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public String getSigner1Name() {
        return signer1Name;
    }

    public String getSigner1Position() {
        return signer1Position;
    }

    public String getSigner2Name() {
        return signer2Name;
    }

    public String getSigner2Position() {
        return signer2Position;
    }

    public LocalDate getPrinted() {
        return printed;
    }

}
