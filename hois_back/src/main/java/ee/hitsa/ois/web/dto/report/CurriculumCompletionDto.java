package ee.hitsa.ois.web.dto.report;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;

import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumCompletionDto {

    private final Long id;
    private final String fullName;
    private final AutocompleteResult curriculumVersion;
    private final String studentGroup;
    private final String studyLoad;
    private final String studyForm;
    private final String status;
    private BigDecimal creditsLastStudyPeriod = BigDecimal.ZERO;
    private final BigDecimal credits;
    private final BigDecimal curriculumCompletion;
    private final Long studyPeriodCount;
    private final Long studyYearCount;

    public CurriculumCompletionDto(Object record) {
        id = resultAsLong(record, 0);
        fullName = PersonUtil.fullnameTypeSpecific(resultAsString(record, 1), resultAsString(record, 2), resultAsString(record, 14));
        String code = resultAsString(record, 3);
        curriculumVersion = new AutocompleteResult(null, CurriculumUtil.versionName(code, resultAsString(record, 4)), CurriculumUtil.versionName(code, resultAsString(record, 5)));
        studentGroup = resultAsString(record, 6);
        studyLoad = resultAsString(record, 7);
        studyForm = resultAsString(record, 8);
        status = resultAsString(record, 9);
        credits = resultAsDecimal(record, 10);
        curriculumCompletion = resultAsDecimal(record, 11);
        studyPeriodCount = resultAsLong(record, 12);
        studyYearCount = resultAsLong(record, 13);
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public AutocompleteResult getCurriculumVersion() {
        return curriculumVersion;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public String getStudyLoad() {
        return studyLoad;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getCreditsLastStudyPeriod() {
        return creditsLastStudyPeriod;
    }

    public void setCreditsLastStudyPeriod(BigDecimal creditsLastStudyPeriod) {
        this.creditsLastStudyPeriod = creditsLastStudyPeriod;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public BigDecimal getCurriculumCompletion() {
        return curriculumCompletion;
    }

    public Long getStudyPeriodCount() {
        return studyPeriodCount;
    }

    public Long getStudyYearCount() {
        return studyYearCount;
    }
    
}
