package ee.hitsa.ois.web.dto.report;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.util.List;

import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TeacherLoadDto {

    private final AutocompleteResult studyYear;
    private final AutocompleteResult studyPeriod;
    private final String teacher;
    private final Long plannedHours;
    private final Long actualHours;
    private final BigDecimal coefficientHours;
    private final List<TeacherLoadSubjectDto> subjects;
    private final List<TeacherLoadModuleDto> modules;

    public TeacherLoadDto(Object record, List<Object> subjectRecords, List<Object> moduleRecords, Long actualLoad, BigDecimal coefficientLoad) {
        studyYear = new AutocompleteResult(null, resultAsString(record, 0), resultAsString(record, 1));
        studyPeriod = new AutocompleteResult(null, resultAsString(record, 2), resultAsString(record, 3));
        teacher = PersonUtil.fullname(resultAsString(record, 4), resultAsString(record, 5));
        plannedHours = resultAsLong(record, 6);
        actualHours = actualLoad;
        coefficientHours = coefficientLoad;
        subjects = StreamUtil.toMappedList(TeacherLoadSubjectDto::new, subjectRecords);
        modules = StreamUtil.toMappedList(TeacherLoadModuleDto::new, moduleRecords);
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
    
    public BigDecimal getCoefficientHours() {
        return coefficientHours;
    }

    public List<TeacherLoadSubjectDto> getSubjects() {
        return subjects;
    }

    public List<TeacherLoadModuleDto> getModules() {
        return modules;
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

    public static class TeacherLoadModuleDto {
        private final AutocompleteResult module;

        public TeacherLoadModuleDto(Object record) {
            String curriculumCode = resultAsString(record, 4);
            module = new AutocompleteResult(null, CurriculumUtil.moduleName(resultAsString(record, 0), resultAsString(record, 2), curriculumCode),
                    CurriculumUtil.moduleName(resultAsString(record, 1), resultAsString(record, 3), curriculumCode));
        }

        public AutocompleteResult getModule() {
            return module;
        }
    }
}
