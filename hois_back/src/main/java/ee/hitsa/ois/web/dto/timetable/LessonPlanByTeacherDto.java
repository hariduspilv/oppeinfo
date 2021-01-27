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

public class LessonPlanByTeacherDto {

    private final String studyYearCode;
    private final String teacherName;
    private final List<StudyPeriodWithWeeksDto> studyPeriods;
    private final List<Short> weekNrs;
    private final List<LessonPlanModuleJournalDto> journals;
    private final List<LessonPlanByTeacherSubjectDto> subjects;
    private final Map<Long, Map<String, Long>> subjectTotals;
    private final Map<Long, Map<String, Long>> subjectContactTotals;
    private final List<LocalDate> weekBeginningDates;
    private List<ClassifierDto> lessonPlanCapacities;
    private List<String> vocationalContactCapacities;

    public LessonPlanByTeacherDto(StudyYear studyYear, List<Journal> journals, List<LessonPlanByTeacherSubjectDto> subjects,
                                  Map<Long, Map<String, Long>> subjectTotals, Map<Long, Map<String, Long>> subjectContactTotals,
                                  Teacher teacher) {
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
        this.subjectContactTotals = subjectContactTotals;
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

    public List<ClassifierDto> getLessonPlanCapacities() {
        return lessonPlanCapacities;
    }

    public void setLessonPlanCapacities(List<ClassifierDto> lessonPlanCapacities) {
        this.lessonPlanCapacities = lessonPlanCapacities;
    }

    public List<String> getVocationalContactCapacities() {
        return vocationalContactCapacities;
    }

    public void setVocationalContactCapacities(List<String> vocationalContactCapacities) {
        this.vocationalContactCapacities = vocationalContactCapacities;
    }

    public Map<Long, Map<String, Long>> getSubjectContactTotals() {
        return subjectContactTotals;
    }

    public static class LessonPlanByTeacherSubjectDto {
        private final Long id;
        private final String nameEt;
        private final String nameEn;
        private String groupProportion;
        private Map<Long, Map<String, Long>> hours = new HashMap<>();
        private Map<Long, Map<String, Long>> contactHours = new HashMap<>();
        private final List<LessonPlanByTeacherSubjectStudentGroupDto> studentGroups = new ArrayList<>();
        private Map<Long, Map<String, Long>> capacityTotals = new HashMap<>();
        private Map<Long, Map<String, Long>> contactCapacityTotals = new HashMap<>();

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

        public Map<Long, Map<String, Long>> getContactHours() {
            return contactHours;
        }

        public void setContactHours(Map<Long, Map<String, Long>> contactHours) {
            this.contactHours = contactHours;
        }

        public Map<Long, Map<String, Long>> getContactCapacityTotals() {
            return contactCapacityTotals;
        }

        public void setContactCapacityTotals(Map<Long, Map<String, Long>> contactCapacityTotals) {
            this.contactCapacityTotals = contactCapacityTotals;
        }
    }

    public static class LessonPlanByTeacherSubjectStudentGroupDto {
        private final List<String> studentGroups;
        private final Integer subgroups;
        // {study period: {capacity type: hours}}
        private final Map<Long, Map<String, Long>> hours;
        private final Map<Long, Map<String, Long>> contactHours;

        public LessonPlanByTeacherSubjectStudentGroupDto(
                List<String> studentGroups,
                Map<Long, Map<String, Long>> hours,
                Map<Long, Map<String, Long>> contactHours,
                Integer subgroups) {
            this.studentGroups = studentGroups;
            this.hours = hours;
            this.contactHours = contactHours;
            this.subgroups = subgroups;
        }

        public List<String> getStudentGroups() {
            return studentGroups;
        }

        public Integer getSubgroups() {
            return subgroups;
        }

        public Map<Long, Map<String, Long>> getHours() {
            return hours;
        }

        public Map<Long, Map<String, Long>> getContactHours() {
            return contactHours;
        }
    }
}
