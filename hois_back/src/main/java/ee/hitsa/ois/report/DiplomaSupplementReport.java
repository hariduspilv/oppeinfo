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

public class DiplomaSupplementReport {

    private final String diplomaNr;
    private final List<String> additionalNrs;
    private final String schoolNameEt;
    private final String firstname;
    private final String lastname;
    private final String idcode;
    private final LocalDate birthdate;
    private final String curriculumNameEt;
    private final String merCode;
    private final String ekr;
    private final BigDecimal credits;
    private final String vocationalCurriculumType;
    private final String studyFormNameEt;
    private final String studyLanguageNameEt;
    private final String outcomesEt;
    private final String studyCompany;
    private final String signer1Name;
    private final String signer1Position;
    private final Integer pgNrEt;
    private final List<Map<String, Object>> studyResults = new ArrayList<>();
    private final List<Map<String, Object>> finalResults = new ArrayList<>();
    private final List<Map<String, Object>> apels = new ArrayList<>();
    private BigDecimal totalCredits = BigDecimal.valueOf(0);
    
    public DiplomaSupplementReport(DiplomaSupplement diplomaSupplement, List<String> additionalNrs) {
        Form diplomaForm = diplomaSupplement.getDiploma().getForm();
        this.diplomaNr = diplomaForm != null ? diplomaForm.getFullCode() : "XXXXXX";
        this.additionalNrs = additionalNrs;
        this.schoolNameEt = diplomaSupplement.getSchoolNameEt();
        this.firstname = diplomaSupplement.getFirstname();
        this.lastname = diplomaSupplement.getLastname();
        this.idcode = diplomaSupplement.getIdcode();
        this.birthdate = diplomaSupplement.getBirthdate();
        this.curriculumNameEt = diplomaSupplement.getCurriculumNameEt();
        this.merCode = diplomaSupplement.getMerCode();
        this.ekr = diplomaSupplement.getEkr();
        this.credits = diplomaSupplement.getCredits();
        this.vocationalCurriculumType = diplomaSupplement.getVocationalCurriculumType();
        this.studyFormNameEt = diplomaSupplement.getStudyFormNameEt();
        this.studyLanguageNameEt = diplomaSupplement.getStudyLanguageNameEt();
        this.outcomesEt = diplomaSupplement.getOutcomesEt();
        this.studyCompany = diplomaSupplement.getStudyCompany();
        this.signer1Name = diplomaSupplement.getSigner1Name();
        this.signer1Position = diplomaSupplement.getSigner1Position();
        this.pgNrEt = diplomaSupplement.getPgNrEt();
        Map<String, Integer> apelFormal = new HashMap<>();
        Map<String, Integer> apelInformal = new HashMap<>();
        Supplier<Integer> numberSupplier = () -> Integer.valueOf(apelFormal.size() + apelInformal.size() + 1);
        for (DiplomaSupplementStudyResult studyResult : diplomaSupplement.getStudyResults()) {
            totalCredits = totalCredits.add(studyResult.getCredits());
            Map<String, Object> resultItem = new HashMap<>();
            resultItem.put("nameEt", studyResult.getNameEt());
            resultItem.put("credits", studyResult.getCredits());
            resultItem.put("grade", studyResult.getGrade());
            resultItem.put("gradeNameEt", studyResult.getGradeNameEt());
            if (Boolean.TRUE.equals(studyResult.getIsFinal())) {
                finalResults.add(resultItem);
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
        String apelSchoolNameEt = studyResult.getApelSchoolNameEt();
        Integer number = map.get(apelSchoolNameEt);
        if (number == null) {
            map.put(apelSchoolNameEt, number = numberSupplier.get());
            Map<String, Object> apelItem = new HashMap<>();
            apelItem.put("name", apelSchoolNameEt);
            apelItem.put("isFormal", isFormal);
            apels.add(apelItem);
        }
        resultItem.put("nameEt", studyResult.getNameEt() + " *" + number);
    }

    public String getDiplomaNr() {
        return diplomaNr;
    }

    public List<String> getAdditionalNrs() {
        return additionalNrs;
    }

    public String getSchoolNameEt() {
        return schoolNameEt;
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

    public String getCurriculumNameEt() {
        return curriculumNameEt;
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

    public String getStudyFormNameEt() {
        return studyFormNameEt;
    }

    public String getStudyLanguageNameEt() {
        return studyLanguageNameEt;
    }

    public String getOutcomesEt() {
        return outcomesEt;
    }

    public String getStudyCompany() {
        return studyCompany;
    }

    public String getSigner1Name() {
        return signer1Name;
    }

    public String getSigner1Position() {
        return signer1Position;
    }

    public Integer getPgNrEt() {
        return pgNrEt;
    }

    public List<Map<String, Object>> getStudyResults() {
        return studyResults;
    }

    public List<Map<String, Object>> getFinalResults() {
        return finalResults;
    }

    public List<Map<String, Object>> getApels() {
        return apels;
    }

    public BigDecimal getTotalCredits() {
        return totalCredits;
    }
    
}
