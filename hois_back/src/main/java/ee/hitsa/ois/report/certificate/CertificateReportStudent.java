package ee.hitsa.ois.report.certificate;

import java.time.format.DateTimeFormatter;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.student.Student;

public class CertificateReportStudent {
    
    private String firstname;
    private String lastname;
    private String idCode;
    private String studyForm;
    private String studyLoad;
    private CerfificateReportCurriculum curriculum;
    private String nominalStudyEnd;
    
    
    public static CertificateReportStudent of(Student student) {
        CertificateReportStudent reportStudent = new CertificateReportStudent();
        
        Person person = student.getPerson();
        reportStudent.setFirstname(person.getFirstname());
        reportStudent.setLastname(person.getLastname());
        reportStudent.setIdCode(student.getPerson().getIdcode());
        reportStudent.setCurriculum(CerfificateReportCurriculum.of(student.getCurriculumVersion().getCurriculum()));

        if(student.getStudyForm() != null) {
            reportStudent.setStudyForm(student.getStudyForm().getNameEt().toLowerCase());
        }
        if(student.getStudyLoad() != null) {
            reportStudent.setStudyLoad(student.getStudyLoad().getNameEt().toLowerCase());
        }
        if(student.getNominalStudyEnd() != null) {
            reportStudent.setNominalStudyEnd(student.getNominalStudyEnd().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }
        return reportStudent;
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
}
