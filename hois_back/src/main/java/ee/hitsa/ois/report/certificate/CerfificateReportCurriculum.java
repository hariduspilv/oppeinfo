package ee.hitsa.ois.report.certificate;

import ee.hitsa.ois.domain.curriculum.Curriculum;

public class CerfificateReportCurriculum {

    private static final int MONTHS_IN_YEAR = 12;

    private final String name;
    private final String code;
    private final String studyLevel;
    private final int nominalStudyYears;
    private final int nominalStudyMonths;

    public CerfificateReportCurriculum(Curriculum curriculum) {
        name = curriculum.getNameEt();
        code = curriculum.getCode();
        nominalStudyYears = curriculum.getStudyPeriod().intValue() / MONTHS_IN_YEAR;
        nominalStudyMonths = curriculum.getStudyPeriod().intValue() % MONTHS_IN_YEAR;
        studyLevel = curriculum.getOrigStudyLevel().getNameEt();
    }

    public String getStudyLevel() {
        return studyLevel;
    }

    public int getNominalStudyYears() {
        return nominalStudyYears;
    }

    public int getNominalStudyMonths() {
        return nominalStudyMonths;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
