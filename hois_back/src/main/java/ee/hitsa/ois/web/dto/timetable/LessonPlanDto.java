package ee.hitsa.ois.web.dto.timetable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleCapacity;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.domain.timetable.LessonPlan;
import ee.hitsa.ois.domain.timetable.LessonPlanModule;
import ee.hitsa.ois.enums.CapacityType;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.LessonPlanUtil;
import ee.hitsa.ois.util.LessonPlanUtil.LessonPlanCapacityMapper;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class LessonPlanDto extends LessonPlanForm {

    private Long id;
    private List<StudyPeriodDto> studyPeriods;
    private List<Long> weekNrs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<StudyPeriodDto> getStudyPeriods() {
        return studyPeriods;
    }

    public void setStudyPeriods(List<StudyPeriodDto> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }

    public List<Long> getWeekNrs() {
        return weekNrs;
    }

    public void setWeekNrs(List<Long> weekNrs) {
        this.weekNrs = weekNrs;
    }

    public static LessonPlanDto of(LessonPlan lessonPlan) {
        LessonPlanDto dto = EntityUtil.bindToDto(lessonPlan, new LessonPlanDto());
        dto.setStudyPeriods(lessonPlan.getStudyYear().getStudyPeriods().stream().sorted(Comparator.comparing(StudyPeriod::getStartDate)).map(StudyPeriodDto::of).collect(Collectors.toList()));

        LessonPlanCapacityMapper capacityMapper = LessonPlanUtil.capacityMapper(lessonPlan.getStudyYear());
        dto.setModules(lessonPlan.getLessonPlanModules().stream().map(r -> LessonPlanModuleDto.of(r, capacityMapper)).sorted(Comparator.comparing(LessonPlanModuleDto::getNameEt)).collect(Collectors.toList()));

        dto.setWeekNrs(dto.getStudyPeriods().stream().flatMap(r -> r.getWeekNrs().stream()).collect(Collectors.toList()));
        return dto;
    }

    public static class LessonPlanModuleDto extends LessonPlanModuleForm {

        private String nameEt;
        private String nameEn;
        private Integer totalHours;

        public static LessonPlanModuleDto of(LessonPlanModule lessonPlanModule, LessonPlanCapacityMapper capacityMapper) {
            LessonPlanModuleDto dto = new LessonPlanModuleDto();
            dto.setId(lessonPlanModule.getId());
            CurriculumModule cm = lessonPlanModule.getCurriculumVersionOccupationModule().getCurriculumModule();
            dto.setNameEt(cm.getNameEt());
            dto.setNameEn(cm.getNameEn());
            dto.setTeacher(lessonPlanModule.getTeacher() != null ? AutocompleteResult.of(lessonPlanModule.getTeacher()) : null);
            dto.setJournals(StreamUtil.toMappedList(r -> LessonPlanModuleJournalDto.of(r, lessonPlanModule, capacityMapper), lessonPlanModule.getJournalOccupationModuleThemes().stream().map(r -> r.getJournal()).distinct()));
            dto.setTotalHours(Integer.valueOf(lessonPlanModule.getCurriculumVersionOccupationModule().getCapacities().stream().mapToInt(CurriculumVersionOccupationModuleCapacity::getHours).sum()));
            return dto;
        }

        public String getNameEt() {
            return nameEt;
        }

        public void setNameEt(String nameEt) {
            this.nameEt = nameEt;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public Integer getTotalHours() {
            return totalHours;
        }

        public void setTotalHours(Integer totalHours) {
            this.totalHours = totalHours;
        }
    }

    public static class LessonPlanModuleJournalDto extends LessonPlanModuleJournalForm {

        private Long lessonPlanModule;
        private String nameEt;
        private List<LessonPlanModuleJournalThemeDto> themes;
        private List<LessonPlanModuleJournalTeacherDto> teachers;

        public static LessonPlanModuleJournalDto of(Journal journal, LessonPlanModule lessonPlanModule, LessonPlanCapacityMapper capacityMapper) {
            LessonPlanModuleJournalDto dto = EntityUtil.bindToDto(journal, new LessonPlanModuleJournalDto());
            dto.setLessonPlanModule(EntityUtil.getId(lessonPlanModule));
            dto.setThemes(StreamUtil.toMappedList(r -> LessonPlanModuleJournalThemeDto.of(r.getCurriculumVersionOccupationModuleTheme()), journal.getJournalOccupationModuleThemes()));
            dto.setTeachers(StreamUtil.toMappedList(LessonPlanModuleJournalTeacherDto::of, journal.getJournalTeachers()));

            // all hours mapped by capacity type and week nr
            dto.setHours(capacityMapper.mapOutput(journal));
            return dto;
        }

        public Long getLessonPlanModule() {
            return lessonPlanModule;
        }

        public void setLessonPlanModule(Long lessonPlanModule) {
            this.lessonPlanModule = lessonPlanModule;
        }

        public String getNameEt() {
            return nameEt;
        }

        public void setNameEt(String nameEt) {
            this.nameEt = nameEt;
        }

        public List<LessonPlanModuleJournalThemeDto> getThemes() {
            return themes;
        }

        public void setThemes(List<LessonPlanModuleJournalThemeDto> themes) {
            this.themes = themes;
        }

        public List<LessonPlanModuleJournalTeacherDto> getTeachers() {
            return teachers;
        }

        public void setTeachers(List<LessonPlanModuleJournalTeacherDto> teachers) {
            this.teachers = teachers;
        }
    }

    public static class LessonPlanModuleJournalThemeDto {

        private String nameEt;
        private BigDecimal credits;
        private String hours;

        public static LessonPlanModuleJournalThemeDto of(CurriculumVersionOccupationModuleTheme theme) {
            LessonPlanModuleJournalThemeDto dto = EntityUtil.bindToDto(theme, new LessonPlanModuleJournalThemeDto());
            Map<String, Integer> capacityHours = theme.getCapacities().stream().collect(Collectors.toMap(r -> EntityUtil.getCode(r.getCapacityType()), r -> r.getHours()));
            dto.setHours(Arrays.stream(CapacityType.values()).filter(ct -> capacityHours.containsKey(ct.name())).map(ct -> String.format("%s%s", ct.getId(), capacityHours.get(ct.name()))).collect(Collectors.joining("/"))); 
            return dto;
        }

        public String getNameEt() {
            return nameEt;
        }

        public void setNameEt(String nameEt) {
            this.nameEt = nameEt;
        }

        public BigDecimal getCredits() {
            return credits;
        }

        public void setCredits(BigDecimal credits) {
            this.credits = credits;
        }

        public String getHours() {
            return hours;
        }

        public void setHours(String hours) {
            this.hours = hours;
        }
    }

    public static class LessonPlanModuleJournalTeacherDto {

        private AutocompleteResult teacher;

        public static LessonPlanModuleJournalTeacherDto of(JournalTeacher journalTeacher) {
            LessonPlanModuleJournalTeacherDto dto = EntityUtil.bindToDto(journalTeacher, new LessonPlanModuleJournalTeacherDto());
            return dto;
        }

        public AutocompleteResult getTeacher() {
            return teacher;
        }

        public void setTeacher(AutocompleteResult teacher) {
            this.teacher = teacher;
        }
    }

    public static class StudyPeriodDto {

        private Long id;
        private String nameEt;
        private String nameEn;
        private List<Long> weekNrs;

        public static StudyPeriodDto of(StudyPeriod studyPeriod) {
            StudyPeriodDto dto = EntityUtil.bindToDto(studyPeriod, new StudyPeriodDto());
            dto.setWeekNrs(studyPeriod.getWeekNrs());
            return dto;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNameEt() {
            return nameEt;
        }

        public void setNameEt(String nameEt) {
            this.nameEt = nameEt;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public List<Long> getWeekNrs() {
            return weekNrs;
        }

        public void setWeekNrs(List<Long> weekNrs) {
            this.weekNrs = weekNrs;
        }
    }
}
