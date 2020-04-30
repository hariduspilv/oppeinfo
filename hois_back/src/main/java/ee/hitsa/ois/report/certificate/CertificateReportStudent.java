package ee.hitsa.ois.report.certificate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CertificateReportStudent {

    private String firstname;
    private String lastname;
    /**
     * User for certificates of type "other"
     */
    private String fullname;
    private String idCode;
    private String birthDate;
    private AutocompleteResult studyForm;
    private AutocompleteResult studyLoad;
    private String nominalStudyEnd;
    private String studyStart;
    private String studyEnd;
    private CerfificateReportCurriculum curriculum;
    private List<CertificateEvent> events;
    private List<CertificateStudentResult> results;
    private Map<CertificateStudentResultHeader, Map<CertificateStudentResult, List<CertificateStudentResult>>> mappedResults;
    private List<AutocompleteResult> apelSchools;
    private boolean hasQuit;
    private boolean isGuestStudent;
    private boolean higher = false;
    private boolean isActive = true;
    private String diplomaNr;
    private String occupationText;
    private String partoccupationText;
    private String curriculumGradeNameEt;
    private BigDecimal creditsAll;
    private BigDecimal averageMark;

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static CertificateReportStudent of(Student student) {
        CertificateReportStudent reportStudent = of(student.getPerson());

        if (student.getStudyForm() != null) {
            reportStudent.setStudyForm(new AutocompleteResult(null, student.getStudyForm().getNameEt().toLowerCase(),
                    TranslateUtil.getNonNullableNameEn(student.getStudyForm()).toLowerCase()));
        }
        if (student.getStudyLoad() != null) {
            reportStudent.setStudyLoad(new AutocompleteResult(null, student.getStudyLoad().getNameEt().toLowerCase(),
                    TranslateUtil.getNonNullableNameEn(student.getStudyLoad()).toLowerCase()));
        }
        reportStudent.setNominalStudyEnd(DateUtils.nullableDate(student.getNominalStudyEnd()));
        reportStudent.setStudyStart(DateUtils.nullableDate(student.getStudyStart()));
        reportStudent.setStudyEnd(
                DateUtils.nullableDate(StudentUtil.isStudying(student) ? LocalDate.now() : student.getStudyEnd()));
        if (student.getCurriculumVersion() != null) {
            reportStudent
                    .setCurriculum(new CerfificateReportCurriculum(student.getCurriculumVersion().getCurriculum()));
        }
        reportStudent.setHasQuit(StudentUtil.hasQuit(student));
        reportStudent.setGuestStudent(ClassifierUtil.equals(StudentType.OPPUR_K, student.getType()));
        reportStudent.setIsActive(StudentUtil.isActive(student));
        return reportStudent;
    }

    public static CertificateReportStudent of(Person person) {
        CertificateReportStudent reportStudent = new CertificateReportStudent();
        reportStudent.setFirstname(person.getFirstname());
        reportStudent.setLastname(person.getLastname());
        reportStudent.setIdCode(person.getIdcode());
        if (person.getBirthdate() != null) {
            reportStudent.setBirthDate(person.getBirthdate().format(formatter));
        }
        return reportStudent;
    }

    public static CertificateReportStudent of(String otherName, String otherIdcode) {
        CertificateReportStudent reportStudent = new CertificateReportStudent();
        reportStudent.setFullname(otherName);
        reportStudent.setIdCode(otherIdcode);
        return reportStudent;
    }

    public String getDiplomaNr() {
        return diplomaNr;
    }

    public void setDiplomaNr(String diplomaNr) {
        this.diplomaNr = diplomaNr;
    }

    public String getOccupationText() {
        return occupationText;
    }

    public void setOccupationText(String occupationText) {
        this.occupationText = occupationText;
    }

    public String getPartoccupationText() {
        return partoccupationText;
    }

    public void setPartoccupationText(String partoccupationText) {
        this.partoccupationText = partoccupationText;
    }

    public String getCurriculumGradeNameEt() {
        return curriculumGradeNameEt;
    }

    public void setCurriculumGradeNameEt(String curriculumGradeNameEt) {
        this.curriculumGradeNameEt = curriculumGradeNameEt;
    }

    public boolean isHasQuit() {
        return hasQuit;
    }

    public void setHasQuit(boolean hasQuit) {
        this.hasQuit = hasQuit;
    }

    public List<CertificateEvent> getEvents() {
        return events;
    }

    public void setEvents(List<CertificateEvent> events) {
        this.events = events;
    }

    public List<CertificateStudentResult> getResults() {
        return results;
    }

    public void setResults(List<CertificateStudentResult> results) {
        this.results = results;
    }

    public String getStudyStart() {
        return studyStart;
    }

    public void setStudyStart(String studyStart) {
        this.studyStart = studyStart;
    }

    public String getStudyEnd() {
        return studyEnd;
    }

    public void setStudyEnd(String studyEnd) {
        this.studyEnd = studyEnd;
    }

    public String getNominalStudyEnd() {
        return nominalStudyEnd;
    }

    public void setNominalStudyEnd(String nominalStudyEnd) {
        this.nominalStudyEnd = nominalStudyEnd;
    }

    public AutocompleteResult getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(AutocompleteResult studyForm) {
        this.studyForm = studyForm;
    }

    public AutocompleteResult getStudyLoad() {
        return studyLoad;
    }

    public void setStudyLoad(AutocompleteResult studyLoad) {
        this.studyLoad = studyLoad;
    }

    public CerfificateReportCurriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(CerfificateReportCurriculum curriculum) {
        this.curriculum = curriculum;
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

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isGuestStudent() {
        return isGuestStudent;
    }

    public void setGuestStudent(boolean isGuestStudent) {
        this.isGuestStudent = isGuestStudent;
    }

    public boolean isHigher() {
        return higher;
    }

    public void setHigher(boolean higher) {
        this.higher = higher;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Map<CertificateStudentResultHeader, Map<CertificateStudentResult, List<CertificateStudentResult>>> getMappedResults() {
        return mappedResults;
    }

    public void setMappedResults(
            Map<CertificateStudentResultHeader, Map<CertificateStudentResult, List<CertificateStudentResult>>> mappedResults) {
        this.mappedResults = mappedResults;
    }

    public BigDecimal getCreditsAll() {
        return creditsAll;
    }

    public void setCreditsAll(BigDecimal creditsAll) {
        this.creditsAll = creditsAll;
    }

    public BigDecimal getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(BigDecimal averageMark) {
        this.averageMark = averageMark;
    }

    public List<AutocompleteResult> getApelSchools() {
        return apelSchools;
    }

    public void setApelSchools(List<AutocompleteResult> apelSchools) {
        this.apelSchools = apelSchools;
    }
}
