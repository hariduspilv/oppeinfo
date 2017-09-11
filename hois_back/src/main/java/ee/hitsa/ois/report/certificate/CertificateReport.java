package ee.hitsa.ois.report.certificate;

import ee.hitsa.ois.domain.student.Student;

public class CertificateReport {
    
    private String studentName;
    private String school;
    private CertificateReportStudent student;
    private String studyYear;
    
    public static CertificateReport of(Student student) {
        CertificateReport report = new CertificateReport();
        report.setSchool(student.getSchool().getNameEt());
        report.setStudent(CertificateReportStudent.of(student));
        return report;
    }
    
    

    public String getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
