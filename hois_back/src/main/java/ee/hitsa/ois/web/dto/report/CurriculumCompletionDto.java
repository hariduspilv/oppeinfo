package ee.hitsa.ois.web.dto.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

public class CurriculumCompletionDto {

    private final Long id;
    private final String fullName;
    private final AutocompleteResult curriculumVersion;
    private final String studentGroup;
    private final String studyLoad;
    private final String studyForm;
    private final String status;
    private final String idcode;
    private final LocalDate nominalStudyEnd;
    private final Long participatedInStudyPeriodCount;
    private final Long studyPeriodCount;
    private final Long academicLeaveStudyPeriodCount;
    private final Long abroadStudiesStudyPeriodCount;
    private final Long studyYearCount;
    private final BigDecimal creditsLastStudyPeriod;
    private final BigDecimal beforeCurrentPeriodConsideredCredits;
    private final BigDecimal consideredCredits;
    private final BigDecimal credits;
    private final BigDecimal beforeCurrentPeriodCumulativeCurriculumCompletion;
    private final BigDecimal cumulativeCurriculumCompletion;
    private final BigDecimal curriculumCompletion;
    private final AutocompleteResult lastStudyPeriod;

    public CurriculumCompletionDto(Object record) {
        id = resultAsLong(record, 0);
        fullName = PersonUtil.fullnameTypeSpecific(resultAsString(record, 1), resultAsString(record, 2),
                resultAsString(record, 3));
        String code = resultAsString(record, 4);
        curriculumVersion = new AutocompleteResult(null, CurriculumUtil.versionName(code, resultAsString(record, 5)),
                CurriculumUtil.versionName(code, resultAsString(record, 6)));
        studentGroup = resultAsString(record, 7);
        studyLoad = resultAsString(record, 8);
        studyForm = resultAsString(record, 9);
        status = resultAsString(record, 10);
        idcode = resultAsString(record, 11);
        nominalStudyEnd = resultAsLocalDate(record, 12);
        participatedInStudyPeriodCount = resultAsLong(record, 13);
        studyPeriodCount = resultAsLong(record, 14);
        academicLeaveStudyPeriodCount = resultAsLong(record, 15);
        abroadStudiesStudyPeriodCount = resultAsLong(record, 16);
        studyYearCount = resultAsLong(record, 17);
        creditsLastStudyPeriod = resultAsDecimal(record, 18);
        beforeCurrentPeriodConsideredCredits = resultAsDecimal(record, 19);
        consideredCredits = resultAsDecimal(record, 20);
        credits = resultAsDecimal(record, 21);

        BigDecimal beforeCurrentPeriodCumulative = resultAsDecimal(record, 22);
        beforeCurrentPeriodCumulativeCurriculumCompletion = beforeCurrentPeriodCumulative != null
                ? beforeCurrentPeriodCumulative.setScale(1, RoundingMode.DOWN) : null;
        BigDecimal cumulative = resultAsDecimal(record, 23);
        cumulativeCurriculumCompletion = cumulative != null ? cumulative.setScale(1, RoundingMode.DOWN) : null;
        BigDecimal completion = resultAsDecimal(record, 24);
        curriculumCompletion = completion != null ? completion.setScale(1, RoundingMode.DOWN) : null;

        lastStudyPeriod = new AutocompleteResult(null, resultAsString(record, 25), resultAsString(record, 25));
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

    public String getIdcode() {
        return idcode;
    }

    public LocalDate getNominalStudyEnd() {
        return nominalStudyEnd;
    }

    public Long getParticipatedInStudyPeriodCount() {
        return participatedInStudyPeriodCount;
    }

    public Long getStudyPeriodCount() {
        return studyPeriodCount;
    }

    public Long getAcademicLeaveStudyPeriodCount() {
        return academicLeaveStudyPeriodCount;
    }

    public Long getAbroadStudiesStudyPeriodCount() {
        return abroadStudiesStudyPeriodCount;
    }

    public Long getStudyYearCount() {
        return studyYearCount;
    }

    public BigDecimal getCreditsLastStudyPeriod() {
        return creditsLastStudyPeriod;
    }

    public BigDecimal getBeforeCurrentPeriodConsideredCredits() {
        return beforeCurrentPeriodConsideredCredits;
    }

    public BigDecimal getConsideredCredits() {
        return consideredCredits;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public BigDecimal getBeforeCurrentPeriodCumulativeCurriculumCompletion() {
        return beforeCurrentPeriodCumulativeCurriculumCompletion;
    }

    public BigDecimal getCumulativeCurriculumCompletion() {
        return cumulativeCurriculumCompletion;
    }

    public BigDecimal getCurriculumCompletion() {
        return curriculumCompletion;
    }

    public AutocompleteResult getLastStudyPeriod() {
        return lastStudyPeriod;
    }
}
