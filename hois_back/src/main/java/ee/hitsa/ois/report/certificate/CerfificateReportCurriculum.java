package ee.hitsa.ois.report.certificate;

import ee.hitsa.ois.domain.curriculum.Curriculum;

public class CerfificateReportCurriculum {
    
    private String name;
    private String code;
    private int nominalStudyYears;
    private int nominalStudyMonths;
    
    private final static int MONTHS_IN_YEAR = 12;


    public static CerfificateReportCurriculum of(Curriculum curriculum) {
        CerfificateReportCurriculum curriculumReport = new CerfificateReportCurriculum();
        curriculumReport.setName(curriculum.getNameEt());
        curriculumReport.setCode(curriculum.getCode());
        curriculumReport.setNominalStudyYears(curriculum.getStudyPeriod() / MONTHS_IN_YEAR);
        curriculumReport.setNominalStudyMonths(curriculum.getStudyPeriod() % MONTHS_IN_YEAR);
        return curriculumReport;
    }
    
    public int getNominalStudyYears() {
        return nominalStudyYears;
    }

    public void setNominalStudyYears(int nominalStudyYears) {
        this.nominalStudyYears = nominalStudyYears;
    }

    public int getNominalStudyMonths() {
        return nominalStudyMonths;
    }

    public void setNominalStudyMonths(int nominalStudyMonths) {
        this.nominalStudyMonths = nominalStudyMonths;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
