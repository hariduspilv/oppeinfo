package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.LessonPlanUtil;
import ee.hitsa.ois.util.LessonPlanUtil.LessonPlanCapacityMapper;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.StudyPeriodWithWeeksDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto.LessonPlanModuleJournalDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto.LessonPlanModuleJournalForTeacherDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto.LessonPlanTeacherDto;

public class LessonPlanByTeacherDto {

    private final String studyYearCode;
    private final String teacherName;
    private final List<StudyPeriodWithWeeksDto> studyPeriods;
    private final List<Short> weekNrs;
    private final List<LessonPlanModuleJournalDto> journals;
    private final List<LessonPlanByTeacherSubjectDto> subjects;
    private final Map<Long, Map<String, Long>> subjectTotals;
    private final List<LocalDate> weekBeginningDates;
    private List<LessonPlanTeacherDto> teachers;
    private List<ClassifierDto> lessonPlanCapacities;

    public LessonPlanByTeacherDto(StudyYear studyYear, List<Journal> journals, List<LessonPlanByTeacherSubjectDto> subjects,
            Map<Long, Map<String, Long>> subjectTotals, Teacher teacher) {
        studyYearCode = EntityUtil.getCode(studyYear.getYear());
        teacherName = teacher.getPerson().getFullname();
        studyPeriods = studyYear.getStudyPeriods().stream().sorted(Comparator.comparing(StudyPeriod::getStartDate))
                .map(StudyPeriodWithWeeksDto::new).collect(Collectors.toList());
        weekNrs = studyPeriods.stream().flatMap(r -> r.getWeekNrs().stream()).collect(Collectors.toList());
        weekBeginningDates = studyPeriods.stream().flatMap(r -> r.getWeekBeginningDates().stream())
                .collect(Collectors.toList());

        LessonPlanCapacityMapper capacityMapper = LessonPlanUtil.capacityMapper(studyYear);
        this.journals = journals.stream().map(r -> LessonPlanModuleJournalForTeacherDto.of(r, capacityMapper, teacher))
                .sorted(Comparator.comparing(r -> r.getNameEt(), String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        this.subjects = subjects;
        this.subjectTotals = subjectTotals;
    }

    public String getStudyYearCode() {
        return studyYearCode;
    }
    
    public String getTeacherName() {
        return teacherName;
    }

    public List<StudyPeriodWithWeeksDto> getStudyPeriods() {
        return studyPeriods;
    }

    public List<Short> getWeekNrs() {
        return weekNrs;
    }

    public List<LessonPlanModuleJournalDto> getJournals() {
        return journals;
    }

    public List<LessonPlanByTeacherSubjectDto> getSubjects() {
        return subjects;
    }
    
    public Map<Long, Map<String, Long>> getSubjectTotals() {
        return subjectTotals;
    }
    
    public List<LocalDate> getWeekBeginningDates() {
        return weekBeginningDates;
    }
    
    public List<LessonPlanTeacherDto> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<LessonPlanTeacherDto> teachers) {
        this.teachers = teachers;
    }

    public List<ClassifierDto> getLessonPlanCapacities() {
        return lessonPlanCapacities;
    }

    public void setLessonPlanCapacities(List<ClassifierDto> lessonPlanCapacities) {
        this.lessonPlanCapacities = lessonPlanCapacities;
    }


    public static class LessonPlanByTeacherSubjectDto {
        private final Long id;
        private final String nameEt;
        private final String nameEn;
        private String groupProportion;
        private Map<Long, Map<String, Long>> hours = new HashMap<>();
        private final List<LessonPlanByTeacherSubjectStudentGroupDto> studentGroups = new ArrayList<>();
        private Map<Long, Map<String, Long>> capacityTotals = new HashMap<>();

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
        
        public Map<Long, Map<String, Long>> getHours() {
            return hours;
        }
        
        public void setHours(Map<Long, Map<String, Long>> hours) {
            this.hours = hours;
        }

        public List<LessonPlanByTeacherSubjectStudentGroupDto> getStudentGroups() {
            return studentGroups;
        }
        
        public String getGroupProportion() {
            return this.groupProportion;
        }
        
        public void setGroupProportion(String groupProportion) {
            this.groupProportion = groupProportion;
        }

        public Map<Long, Map<String, Long>> getCapacityTotals() {
            return capacityTotals;
        }

        public void setCapacityTotals(Map<Long, Map<String, Long>> capacityTotals) {
            this.capacityTotals = capacityTotals;
        }
        
    }

    public static class LessonPlanByTeacherSubjectStudentGroupDto {
        private final List<String> studentGroups;
        // {study period: {capacity type: hours}}
        private final Map<Long, Map<String, Long>> hours;

        public LessonPlanByTeacherSubjectStudentGroupDto(List<String> studentGroups, Map<Long, Map<String, Long>> hours) {
            this.studentGroups = studentGroups;
            this.hours = hours;
        }

        public List<String> getStudentGroups() {
            return studentGroups;
        }

        public Map<Long, Map<String, Long>> getHours() {
            return hours;
        }
    }
}
