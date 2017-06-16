package ee.hitsa.ois.web.dto.report;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDate;

import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentSearchDto {

    private final Long id;
    private final String fullName;
    private final String idcode;
    private final LocalDate studyStart;
    private final String studyLevel;
    private final AutocompleteResult curriculumVersion;
    private final String studentGroup;
    private final String studyLoad;
    private final String studyForm;
    private final String status;
    private final String fin;
    private final String finSpecific;
    private final String language;
    private final BigDecimal credits;

    public StudentSearchDto(Object record) {
        id = resultAsLong(record, 0);
        fullName = PersonUtil.fullname(resultAsString(record, 1), resultAsString(record, 2));
        idcode = resultAsString(record, 3);
        studyStart = resultAsLocalDate(record, 4);
        studyLevel = resultAsString(record, 5);
        String code = resultAsString(record, 6);
        curriculumVersion = new AutocompleteResult(null, CurriculumUtil.versionName(code, resultAsString(record, 7)), CurriculumUtil.versionName(code, resultAsString(record, 8)));
        studentGroup = resultAsString(record, 9);
        studyLoad = resultAsString(record, 10);
        studyForm = resultAsString(record, 11);
        status = resultAsString(record, 12);
        fin = resultAsString(record, 13);
        finSpecific = resultAsString(record, 14);
        language = resultAsString(record, 15);
        credits = resultAsDecimal(record, 16);
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIdcode() {
        return idcode;
    }

    public LocalDate getStudyStart() {
        return studyStart;
    }

    public String getStudyLevel() {
        return studyLevel;
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

    public String getFin() {
        return fin;
    }

    public String getFinSpecific() {
        return finSpecific;
    }

    public String getLanguage() {
        return language;
    }

    public BigDecimal getCredits() {
        return credits;
    }
}
