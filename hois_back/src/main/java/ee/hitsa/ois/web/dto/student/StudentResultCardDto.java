package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;
import java.time.LocalDate;

import ee.hitsa.ois.report.diploma.DiplomaSupplementResultReport;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentResultCardDto extends DiplomaSupplementResultReport {

    private Long studentId;
    private String studentStatus;
    private Long regNr;
    private String idcode;
    private LocalDate birthdate;
    private String firstname;
    private String lastname;
    private LocalDate matriculationDate;
    private String studyStartDirectiveNr;
    private LocalDate studyStartDirectiveConfirmDate;
    private LocalDate exmatriculationDate;
    private String studyEndDirectiveNr;
    private LocalDate studyEndDirectiveConfirmDate;
    private AutocompleteResult curriculum;
    private String curriculumName;
    private String curriculumCode;
    private BigDecimal curriculumCredits;
    private String studyForm;
    private String studyLanguage;
    private String diplomaNrs;
    private String supplementNrs;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

    public Long getRegNr() {
        return regNr;
    }

    public void setRegNr(Long regNr) {
        this.regNr = regNr;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
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

    public LocalDate getMatriculationDate() {
        return matriculationDate;
    }

    public void setMatriculationDate(LocalDate matriculationDate) {
        this.matriculationDate = matriculationDate;
    }

    public String getStudyStartDirectiveNr() {
        return studyStartDirectiveNr;
    }

    public void setStudyStartDirectiveNr(String studyStartDirectiveNr) {
        this.studyStartDirectiveNr = studyStartDirectiveNr;
    }

    public LocalDate getStudyStartDirectiveConfirmDate() {
        return studyStartDirectiveConfirmDate;
    }

    public void setStudyStartDirectiveConfirmDate(LocalDate studyStartDirectiveConfirmDate) {
        this.studyStartDirectiveConfirmDate = studyStartDirectiveConfirmDate;
    }

    public LocalDate getExmatriculationDate() {
        return exmatriculationDate;
    }

    public void setExmatriculationDate(LocalDate exmatriculationDate) {
        this.exmatriculationDate = exmatriculationDate;
    }

    public String getStudyEndDirectiveNr() {
        return studyEndDirectiveNr;
    }

    public void setStudyEndDirectiveNr(String studyEndDirectiveNr) {
        this.studyEndDirectiveNr = studyEndDirectiveNr;
    }

    public LocalDate getStudyEndDirectiveConfirmDate() {
        return studyEndDirectiveConfirmDate;
    }

    public void setStudyEndDirectiveConfirmDate(LocalDate studyEndDirectiveConfirmDate) {
        this.studyEndDirectiveConfirmDate = studyEndDirectiveConfirmDate;
    }

    public AutocompleteResult getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(AutocompleteResult curriculum) {
        this.curriculum = curriculum;
    }

    public String getCurriculumName() {
        return curriculumName;
    }

    public void setCurriculumName(String curriculumName) {
        this.curriculumName = curriculumName;
    }

    public String getCurriculumCode() {
        return curriculumCode;
    }

    public void setCurriculumCode(String curriculumCode) {
        this.curriculumCode = curriculumCode;
    }

    public BigDecimal getCurriculumCredits() {
        return curriculumCredits;
    }

    public void setCurriculumCredits(BigDecimal curriculumCredits) {
        this.curriculumCredits = curriculumCredits;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public String getStudyLanguage() {
        return studyLanguage;
    }

    public void setStudyLanguage(String studyLanguage) {
        this.studyLanguage = studyLanguage;
    }

    public String getDiplomaNrs() {
        return diplomaNrs;
    }

    public void setDiplomaNrs(String diplomaNrs) {
        this.diplomaNrs = diplomaNrs;
    }

    public String getSupplementNrs() {
        return supplementNrs;
    }

    public void setSupplementNrs(String supplementNrs) {
        this.supplementNrs = supplementNrs;
    }

}
