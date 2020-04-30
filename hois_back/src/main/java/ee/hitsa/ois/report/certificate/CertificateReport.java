package ee.hitsa.ois.report.certificate;

import java.util.List;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CertificateReport {
    
    private String school;
    private String schoolGenitive;
    private String schoolEn;
    private Boolean isHigherSchool;
    private CertificateReportStudent student;
    private String gradeSystem;
    private String gradesDescription;
    private List<CertificateReportGrade> grades;
    private String studyYear;
    private List<CertificateReportSession> sessions;
    private CertificateReportSession lastSession;
    private Boolean addOutcomes;
    private AutocompleteResult abroadProgramme;
    
    public static CertificateReport of(Student student) {
        CertificateReport report = new CertificateReport();
        report.setSchool(student.getSchool().getNameEt());
        report.setSchoolGenitive(student.getSchool().getNameGenitiveEt());
        report.setSchoolEn(student.getSchool().getNameEn());
        report.setStudent(CertificateReportStudent.of(student));
        return report;
    }
    
    public static CertificateReport of(Person person, School school) {
        CertificateReport report = new CertificateReport();
        report.setSchool(school.getNameEt());
        report.setSchoolGenitive(school.getNameGenitiveEt());
        report.setSchoolEn(school.getNameEn());
        report.setStudent(CertificateReportStudent.of(person));
        return report;
    }
    
    public static CertificateReport of(School school, String otherName, String otherIdcode) {
        CertificateReport report = new CertificateReport();
        report.setSchool(school.getNameEt());
        report.setSchoolGenitive(school.getNameGenitiveEt());
        report.setSchoolEn(school.getNameEn());
        report.setStudent(CertificateReportStudent.of(otherName, otherIdcode));
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

    public String getSchoolGenitive() {
        return schoolGenitive;
    }

    public void setSchoolGenitive(String schoolGenitive) {
        this.schoolGenitive = schoolGenitive;
    }

    public Boolean getIsHigherSchool() {
        return isHigherSchool;
    }

    public void setIsHigherSchool(Boolean isHigherSchool) {
        this.isHigherSchool = isHigherSchool;
    }

    public CertificateReportStudent getStudent() {
        return student;
    }

    public void setStudent(CertificateReportStudent student) {
        this.student = student;
    }

    public Boolean getAddOutcomes() {
        return addOutcomes;
    }

    public void setAddOutcomes(Boolean addOutcomes) {
        this.addOutcomes = addOutcomes;
    }

    public String getSchoolEn() {
        return schoolEn;
    }

    public void setSchoolEn(String schoolEn) {
        this.schoolEn = schoolEn;
    }

    public String getGradeSystem() {
        return gradeSystem;
    }

    public void setGradeSystem(String gradeSystem) {
        this.gradeSystem = gradeSystem;
    }

    public String getGradesDescription() {
        return gradesDescription;
    }

    public void setGradesDescription(String gradesDescription) {
        this.gradesDescription = gradesDescription;
    }

    public AutocompleteResult getAbroadProgramme() {
        return abroadProgramme;
    }

    public void setAbroadProgramme(AutocompleteResult abroadProgramme) {
        this.abroadProgramme = abroadProgramme;
    }

    public List<CertificateReportGrade> getGrades() {
        return grades;
    }

    public void setGrades(List<CertificateReportGrade> grades) {
        this.grades = grades;
    }
    
}
