package ee.hitsa.ois.report.certificate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.StudentUtil;

public class CertificateReportStudent {

    private String firstname;
    private String lastname;
    /**
     * User for certificates of type "other"
     */
    private String fullname;
    private String idCode;
    private String birthDate;
    private String studyForm;
    private String studyLoad;
    private String nominalStudyEnd;
    private String studyStart;
    private String studyEnd;
    private CerfificateReportCurriculum curriculum;
    private List<CertificateStudentResult> results;
    private boolean hasQuit;
    private boolean isGuestStudent;
    private boolean higher = false;
    private String diplomaNr;
    private String occupationText;
    private String partoccupationText;
    private String curriculumGradeNameEt;
    
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static CertificateReportStudent of(Student student) {
        CertificateReportStudent reportStudent = of(student.getPerson());

        if(student.getStudyForm() != null) {
            reportStudent.setStudyForm(student.getStudyForm().getNameEt().toLowerCase());
        }
        if(student.getStudyLoad() != null) {
            reportStudent.setStudyLoad(student.getStudyLoad().getNameEt().toLowerCase());
        }
        reportStudent.setNominalStudyEnd(DateUtils.nullableDate(student.getNominalStudyEnd()));
        reportStudent.setStudyStart(DateUtils.nullableDate(student.getStudyStart()));
        reportStudent.setStudyEnd(DateUtils.nullableDate(
                StudentUtil.isStudying(student) ? LocalDate.now() : student.getStudyEnd()));
        if (student.getCurriculumVersion() != null) {
            reportStudent.setCurriculum(new CerfificateReportCurriculum(student.getCurriculumVersion().getCurriculum()));
        }
        reportStudent.setHasQuit(StudentUtil.hasQuit(student));
        reportStudent.setGuestStudent(ClassifierUtil.equals(StudentType.OPPUR_K, student.getType()));
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
}
