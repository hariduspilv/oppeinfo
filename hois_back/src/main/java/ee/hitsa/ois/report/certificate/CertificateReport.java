package ee.hitsa.ois.report.certificate;

import java.util.List;

import ee.hitsa.ois.domain.student.Student;

public class CertificateReport {
    
    private String school;
    private CertificateReportStudent student;
    private String studyYear;
    private List<CertificateReportSession> sessions;
    private CertificateReportSession lastSession;
    
    public static CertificateReport of(Student student) {
        CertificateReport report = new CertificateReport();
        report.setSchool(student.getSchool().getNameEt());
        report.setStudent(CertificateReportStudent.of(student));
        return report;
    }
    
    public CertificateReportSession getLastSession() {
        return lastSession;
    }
    public void setLastSession(CertificateReportSession lastSession) {
        this.lastSession = lastSession;
    }
    public List<CertificateReportSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<CertificateReportSession> sessions) {
        this.sessions = sessions;
    }
    
    public String getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public CertificateReportStudent getStudent() {
        return student;
    }

    public void setStudent(CertificateReportStudent student) {
        this.student = student;
    }
}
