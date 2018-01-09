package ee.hitsa.ois.report.certificate;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.student.Student;
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
    private String studyForm;
    private String studyLoad;
    private String nominalStudyEnd;
    private String studyStart;
    private String studyEnd;
    private CerfificateReportCurriculum curriculum;
    private List<CertificateStudentResult> results;
    private boolean hasQuit;
    
    public static CertificateReportStudent of(Student student) {
        CertificateReportStudent reportStudent = new CertificateReportStudent();
        
        Person person = student.getPerson();
        reportStudent.setFirstname(person.getFirstname());
        reportStudent.setLastname(person.getLastname());
        reportStudent.setIdCode(student.getPerson().getIdcode());

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

        reportStudent.setCurriculum(CerfificateReportCurriculum.of(student.getCurriculumVersion().getCurriculum()));
        reportStudent.setHasQuit(StudentUtil.hasQuit(student));
        return reportStudent;
    }

    public static CertificateReportStudent of(Person person) {
        CertificateReportStudent reportStudent = new CertificateReportStudent();
        reportStudent.setFirstname(person.getFirstname());
        reportStudent.setLastname(person.getLastname());
        reportStudent.setIdCode(person.getIdcode());        
        return reportStudent;
    }

    public static CertificateReportStudent of(String otherName, String otherIdcode) {
        CertificateReportStudent reportStudent = new CertificateReportStudent();
        reportStudent.setFullname(otherName);
        reportStudent.setIdCode(otherIdcode);
        return reportStudent;
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
}
