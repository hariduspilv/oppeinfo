package ee.hitsa.ois.web.dto.report;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

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

    public CurriculumCompletionDto(Object record) {
        id = resultAsLong(record, 0);
        fullName = PersonUtil.fullname(resultAsString(record, 1), resultAsString(record, 2));
        String code = resultAsString(record, 3);
        curriculumVersion = new AutocompleteResult(null, CurriculumUtil.versionName(code, resultAsString(record, 4)), CurriculumUtil.versionName(code, resultAsString(record, 5)));
        studentGroup = resultAsString(record, 6);
        studyLoad = resultAsString(record, 7);
        studyForm = resultAsString(record, 8);
        status = resultAsString(record, 9);
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
}