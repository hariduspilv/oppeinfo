package ee.hitsa.ois.web.dto.timetable;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.util.LessonPlanUtil;
import ee.hitsa.ois.util.LessonPlanUtil.LessonPlanCapacityMapper;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto.LessonPlanModuleJournalDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto.LessonPlanModuleJournalForTeacherDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto.StudyPeriodDto;

public class LessonPlanByTeacherDto {

    private final List<StudyPeriodDto> studyPeriods;
    private final List<Integer> weekNrs;
    private final List<LessonPlanModuleJournalDto> journals;
    private final List<LessonPlanByTeacherSubjectDto> subjects;

    public LessonPlanByTeacherDto(StudyYear studyYear, List<Journal> journals, List<LessonPlanByTeacherSubjectDto> subjects, Teacher teacher) {
        studyPeriods = studyYear.getStudyPeriods().stream().sorted(Comparator.comparing(StudyPeriod::getStartDate)).map(StudyPeriodDto::new).collect(Collectors.toList());
        weekNrs = studyPeriods.stream().flatMap(r -> r.getWeekNrs().stream()).collect(Collectors.toList());

        LessonPlanCapacityMapper capacityMapper = LessonPlanUtil.capacityMapper(studyYear);
        this.journals = journals.stream().map(r -> LessonPlanModuleJournalForTeacherDto.of(r, capacityMapper, teacher)).sorted(Comparator.comparing(r -> r.getNameEt().toLowerCase())).collect(Collectors.toList());
        this.subjects = subjects;
    }

    public List<StudyPeriodDto> getStudyPeriods() {
        return studyPeriods;
    }

    public List<Integer> getWeekNrs() {
        return weekNrs;
    }

    public List<LessonPlanModuleJournalDto> getJournals() {
        return journals;
    }

    public List<LessonPlanByTeacherSubjectDto> getSubjects() {
        return subjects;
    }

    public static class LessonPlanByTeacherSubjectDto {
        private final Long id;
        private final String nameEt;
        private final String nameEn;
        private List<LessonPlanByTeacherSubjectStudentGroupDto> studentGroups;

        public LessonPlanByTeacherSubjectDto(Long id, String nameEt, String nameEn) {
            this.id = id;
            this.nameEt = nameEt;
            this.nameEn = nameEn;
        }

        public Long getId() {
            return id;
        }

        public String getNameEt() {
            return nameEt;
        }

        public String getNameEn() {
            return nameEn;
        }

        public List<LessonPlanByTeacherSubjectStudentGroupDto> getStudentGroups() {
            return studentGroups;
        }
    }

    public static class LessonPlanByTeacherSubjectStudentGroupDto {
        private final String studentGroups;
        // {study period: {capacity type: hours}}
        private final Map<Long, Map<String, Long>> hours;

        LessonPlanByTeacherSubjectStudentGroupDto(String studentGroups, Map<Long, Map<String, Long>> hours) {
            this.studentGroups = studentGroups;
            this.hours = hours;
        }

        public String getStudentGroups() {
            return studentGroups;
        }

        public Map<Long, Map<String, Long>> getHours() {
            return hours;
        }
    }
}
