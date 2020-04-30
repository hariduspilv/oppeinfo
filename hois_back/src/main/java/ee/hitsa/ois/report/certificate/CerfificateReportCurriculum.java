package ee.hitsa.ois.report.certificate;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CerfificateReportCurriculum {

    private static final int MONTHS_IN_YEAR = 12;

    private final String name;
    private final String nameEn;
    private final String code;
    private final String merCode;
    private final AutocompleteResult studyLevel;
    private final int nominalStudyYears;
    private final int nominalStudyMonths;

    public CerfificateReportCurriculum(Curriculum curriculum) {
        name = curriculum.getNameEt();
        nameEn = curriculum.getNameEn();
        code = curriculum.getCode();
        merCode = curriculum.getMerCode();
        nominalStudyYears = curriculum.getStudyPeriod().intValue() / MONTHS_IN_YEAR;
        nominalStudyMonths = curriculum.getStudyPeriod().intValue() % MONTHS_IN_YEAR;
        studyLevel = new AutocompleteResult(null, curriculum.getOrigStudyLevel().getNameEt(), TranslateUtil.getNonNullableNameEn(curriculum.getOrigStudyLevel()));
    }

    public AutocompleteResult getStudyLevel() {
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

    public String getMerCode() {
        return merCode;
    }

    public String getNameEn() {
        return nameEn;
    }
}
