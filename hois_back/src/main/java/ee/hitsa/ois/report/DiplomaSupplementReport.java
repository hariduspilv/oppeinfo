package ee.hitsa.ois.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import ee.hitsa.ois.domain.Form;
import ee.hitsa.ois.domain.diploma.DiplomaSupplement;
import ee.hitsa.ois.domain.diploma.DiplomaSupplementStudyResult;
import ee.hitsa.ois.enums.Language;

public class DiplomaSupplementReport {

    private final Language lang;
    
    private final String diplomaNr;
    private final List<String> additionalNrs;
    private final Boolean showSubjectCode;
    private final Boolean showTeacher;
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
    private final List<Map<String, Object>> studyResults = new ArrayList<>();
    private final List<Map<String, Object>> finalResults = new ArrayList<>();
    private final List<Map<String, Object>> finalThesis = new ArrayList<>();
    private final List<Map<String, Object>> apels = new ArrayList<>();
    private BigDecimal totalCredits = BigDecimal.valueOf(0);
    
    public DiplomaSupplementReport(DiplomaSupplement diplomaSupplement, List<String> additionalNrs) {
        this(diplomaSupplement, additionalNrs, Boolean.FALSE, Boolean.FALSE, Language.ET);
    }
    
    public DiplomaSupplementReport(DiplomaSupplement diplomaSupplement, List<String> additionalNrs, 
            Boolean showSubjectCode, Boolean showTeacher, Language lang) {
        this.lang = lang;
        Form diplomaForm = diplomaSupplement.getDiploma().getForm();
        this.diplomaNr = diplomaForm != null ? diplomaForm.getFullCode() : "XXXXXX";
        this.additionalNrs = additionalNrs;
        this.showSubjectCode = showSubjectCode;
        this.showTeacher = showTeacher;
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
        Map<String, Integer> apelFormal = new HashMap<>();
        Map<String, Integer> apelInformal = new HashMap<>();
        Supplier<Integer> numberSupplier = () -> Integer.valueOf(apelFormal.size() + apelInformal.size() + 1);
        for (DiplomaSupplementStudyResult studyResult : diplomaSupplement.getStudyResults()) {
            totalCredits = totalCredits.add(studyResult.getCredits());
            Map<String, Object> resultItem = new HashMap<>();
            if (Boolean.TRUE.equals(showSubjectCode)) {
                resultItem.put("subjectCode", studyResult.getSubjectCode());
            }
            resultItem.put("name", Language.EN.equals(lang) ? studyResult.getNameEn() : studyResult.getNameEt());
            resultItem.put("credits", studyResult.getCredits());
            resultItem.put("grade", studyResult.getGrade());
            resultItem.put("gradeName", Language.EN.equals(lang) ? studyResult.getGradeNameEn() : studyResult.getGradeNameEt());
            resultItem.put("gradeDate", studyResult.getGradeDate());
            if (Boolean.TRUE.equals(showTeacher)) {
                resultItem.put("teacher", studyResult.getTeacher());
            }
            if (Boolean.TRUE.equals(studyResult.getIsFinal())) {
                if (Boolean.TRUE.equals(studyResult.getIsFinalThesis())) {
                    finalThesis.add(resultItem);
                } else {
                    finalResults.add(resultItem);
                }
                continue;
            }
            if (Boolean.TRUE.equals(studyResult.getIsApelFormal())) {
                addApel(resultItem, studyResult, Boolean.TRUE, apelFormal, numberSupplier);
            }
            if (Boolean.TRUE.equals(studyResult.getIsApelInformal())) {
                addApel(resultItem, studyResult, Boolean.FALSE, apelInformal, numberSupplier);
            }
            studyResults.add(resultItem);
        }
    }

    private void addApel(Map<String, Object> resultItem, DiplomaSupplementStudyResult studyResult, Boolean isFormal,
            Map<String, Integer> map, Supplier<Integer> numberSupplier) {
        String apelSchoolName = Language.EN.equals(lang) ? studyResult.getApelSchoolNameEn() : studyResult.getApelSchoolNameEt();
        Integer number = map.get(apelSchoolName);
        if (number == null) {
            map.put(apelSchoolName, number = numberSupplier.get());
            Map<String, Object> apelItem = new HashMap<>();
            apelItem.put("name", apelSchoolName);
            apelItem.put("isFormal", isFormal);
            apels.add(apelItem);
        }
        resultItem.put("name", (Language.EN.equals(lang) ? studyResult.getNameEn() : studyResult.getNameEt()) + " *" + number);
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
    
    public List<Map<String, Object>> getStudyResults() {
        return studyResults;
    }

    public List<Map<String, Object>> getFinalResults() {
        return finalResults;
    }
    
    public List<Map<String, Object>> getFinalThesis() {
        return finalThesis;
    }

    public List<Map<String, Object>> getApels() {
        return apels;
    }

    public BigDecimal getTotalCredits() {
        return totalCredits;
    }
    
}
