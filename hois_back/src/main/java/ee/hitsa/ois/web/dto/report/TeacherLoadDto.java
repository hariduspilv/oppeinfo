package ee.hitsa.ois.web.dto.report;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.List;

import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TeacherLoadDto {

    private final AutocompleteResult studyYear;
    private final AutocompleteResult studyPeriod;
    private final String teacher;
    private final Long plannedHours;
    private final Long actualHours;
    private final List<TeacherLoadSubjectDto> subjects;

    public TeacherLoadDto(Object record, List<Object> subjectRecords) {
        studyYear = new AutocompleteResult(null, resultAsString(record, 0), resultAsString(record, 1));
        studyPeriod = new AutocompleteResult(null, resultAsString(record, 2), resultAsString(record, 3));
        teacher = PersonUtil.fullname(resultAsString(record, 4), resultAsString(record, 5));
        plannedHours = resultAsLong(record, 6);
        actualHours = resultAsLong(record, 7);
        subjects = StreamUtil.toMappedList(TeacherLoadSubjectDto::new, subjectRecords);
    }

    public AutocompleteResult getStudyYear() {
        return studyYear;
    }

    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }

    public String getTeacher() {
        return teacher;
    }

    public Long getPlannedHours() {
        return plannedHours;
    }

    public Long getActualHours() {
        return actualHours;
    }

    public List<TeacherLoadSubjectDto> getSubjects() {
        return subjects;
    }

    public static class TeacherLoadSubjectDto {
        private final AutocompleteResult subject;
        private final String code;

        public TeacherLoadSubjectDto(Object record) {
            subject = new AutocompleteResult(null, resultAsString(record, 0), resultAsString(record, 1));
            code = resultAsString(record, 2);
        }

        public AutocompleteResult getSubject() {
            return subject;
        }

        public String getCode() {
            return code;
        }
    }
}
