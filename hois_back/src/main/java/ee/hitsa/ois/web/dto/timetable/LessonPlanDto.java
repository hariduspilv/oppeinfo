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
import ee.hitsa.ois.domain.school.StudyYearScheduleLegend;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.domain.timetable.LessonPlan;
import ee.hitsa.ois.domain.timetable.LessonPlanModule;
import ee.hitsa.ois.enums.CapacityType;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.LessonPlanUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.LessonPlanUtil.LessonPlanCapacityMapper;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class LessonPlanDto extends LessonPlanForm {

    private Long id;
    private List<StudyPeriodDto> studyPeriods;
    private List<Integer> weekNrs;
    private List<LessonPlanLegendDto> legends;

    public static LessonPlanDto of(LessonPlan lessonPlan, Map<Long, Long> weekNrsLegends) {
        LessonPlanDto dto = EntityUtil.bindToDto(lessonPlan, new LessonPlanDto());
        dto.setStudyPeriods(lessonPlan.getStudyYear().getStudyPeriods().stream().sorted(Comparator.comparing(StudyPeriod::getStartDate)).map(StudyPeriodDto::new).collect(Collectors.toList()));
        LessonPlanCapacityMapper capacityMapper = LessonPlanUtil.capacityMapper(lessonPlan.getStudyYear());
        dto.setModules(lessonPlan.getLessonPlanModules().stream().map(r -> LessonPlanModuleDto.of(r, capacityMapper)).sorted(Comparator.comparing(r -> r.getNameEt().toLowerCase())).collect(Collectors.toList()));
        dto.setWeekNrs(dto.getStudyPeriods().stream().flatMap(r -> r.getWeekNrs().stream()).collect(Collectors.toList()));

        Map<Long, String> colors = StreamUtil.toMap(StudyYearScheduleLegend::getId, StudyYearScheduleLegend::getColor, lessonPlan.getSchool().getStudyYearScheduleLegends());
        dto.setLegends(StreamUtil.toMappedList(e -> new LessonPlanLegendDto(e.getKey(), colors.get(e.getValue())), weekNrsLegends.entrySet()));
        return dto;
    }

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

    public List<Integer> getWeekNrs() {
        return weekNrs;
    }

    public void setWeekNrs(List<Integer> weekNrs) {
        this.weekNrs = weekNrs;
    }

    public List<LessonPlanLegendDto> getLegends() {
        return legends;
    }

    public void setLegends(List<LessonPlanLegendDto> legends) {
        this.legends = legends;
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
            dto.setJournals(lessonPlanModule.getJournalOccupationModuleThemes().stream().map(r -> r.getJournal()).distinct().map(r -> LessonPlanModuleJournalDto.of(r, capacityMapper)).sorted(Comparator.comparing(r -> r.getNameEt().toLowerCase())).collect(Collectors.toList()));
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

        private String nameEt;
        private String groupProportion;
        private List<LessonPlanModuleJournalThemeDto> themes;
        private List<LessonPlanModuleJournalTeacherDto> teachers;

        public static LessonPlanModuleJournalDto of(Journal journal, LessonPlanCapacityMapper capacityMapper) {
            LessonPlanModuleJournalDto dto = new LessonPlanModuleJournalDto();
            dto.setId(journal.getId());
            dto.setNameEt(journal.getNameEt());
            dto.setGroupProportion(EntityUtil.getNullableCode(journal.getGroupProportion()));
            dto.setThemes(journal.getJournalOccupationModuleThemes().stream().map(r -> new LessonPlanModuleJournalThemeDto(r.getCurriculumVersionOccupationModuleTheme())).sorted(Comparator.comparing(r -> r.getNameEt().toLowerCase())).collect(Collectors.toList()));
            dto.setTeachers(journal.getJournalTeachers().stream().map(LessonPlanModuleJournalTeacherDto::new).sorted(Comparator.comparing(r -> r.getTeacher().getNameEt().toLowerCase())).collect(Collectors.toList()));

            // all hours mapped by capacity type and week nr
            dto.setHours(capacityMapper.mapOutput(journal));
            return dto;
        }

        public String getNameEt() {
            return nameEt;
        }

        public void setNameEt(String nameEt) {
            this.nameEt = nameEt;
        }

        public String getGroupProportion() {
            return groupProportion;
        }

        public void setGroupProportion(String groupProportion) {
            this.groupProportion = groupProportion;
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
    
    public static class LessonPlanModuleJournalForTeacherDto extends LessonPlanModuleJournalDto {
        
        private Boolean isConfirmer;
        private Boolean isFiller;

        public static LessonPlanModuleJournalForTeacherDto of(Journal journal, LessonPlanCapacityMapper capacityMapper, Teacher teacher) {
            LessonPlanModuleJournalForTeacherDto dto = new LessonPlanModuleJournalForTeacherDto();
            dto.setId(journal.getId());
            dto.setNameEt(journal.getNameEt());
            dto.setGroupProportion(EntityUtil.getNullableCode(journal.getGroupProportion()));
            dto.setThemes(journal.getJournalOccupationModuleThemes().stream().map(r -> new LessonPlanModuleJournalThemeDto(r.getCurriculumVersionOccupationModuleTheme())).sorted(Comparator.comparing(r -> r.getNameEt().toLowerCase())).collect(Collectors.toList()));
            dto.setTeachers(journal.getJournalTeachers().stream().map(LessonPlanModuleJournalTeacherDto::new).sorted(Comparator.comparing(r -> r.getTeacher().getNameEt().toLowerCase())).collect(Collectors.toList()));
            List<JournalTeacher> currentTeacher = StreamUtil.nullSafeList(journal.getJournalTeachers().stream().filter(t -> EntityUtil.getId(t.getTeacher()) == EntityUtil.getId(teacher)).collect(Collectors.toList()));
            dto.setIsConfirmer(currentTeacher.get(0).getIsConfirmer());
            dto.setIsFiller(currentTeacher.get(0).getIsFiller());
            // all hours mapped by capacity type and week nr
            dto.setHours(capacityMapper.mapOutput(journal));
            return dto;
        }

        public void setIsConfirmer(Boolean isConfirmer) {
            this.isConfirmer = isConfirmer;
        }

        public Boolean getIsConfirmer() {
            return isConfirmer;
        }

        public void setIsFiller(Boolean isFiller) {
            this.isFiller = isFiller;
        }

        public Boolean getIsFiller() {
            return isFiller;
        }
    }

    public static class LessonPlanModuleJournalThemeDto {

        private final String nameEt;
        private final BigDecimal credits;
        private final String hours;

        public LessonPlanModuleJournalThemeDto(CurriculumVersionOccupationModuleTheme theme) {
            nameEt = theme.getNameEt();
            credits = theme.getCredits();

            Map<String, Integer> capacityHours = StreamUtil.toMap(r -> EntityUtil.getCode(r.getCapacityType()), r -> r.getHours(), theme.getCapacities());
            hours = Arrays.stream(CapacityType.values()).filter(ct -> capacityHours.containsKey(ct.name())).map(ct -> String.format("%s%s", ct.getId(), capacityHours.get(ct.name()))).collect(Collectors.joining("/")); 
        }

        public String getNameEt() {
            return nameEt;
        }

        public BigDecimal getCredits() {
            return credits;
        }

        public String getHours() {
            return hours;
        }
    }

    public static class LessonPlanModuleJournalTeacherDto {

        private final AutocompleteResult teacher;

        public LessonPlanModuleJournalTeacherDto(JournalTeacher journalTeacher) {
            teacher = AutocompleteResult.of(journalTeacher.getTeacher());
        }

        public AutocompleteResult getTeacher() {
            return teacher;
        }

    }

    public static class StudyPeriodDto {

        private final Long id;
        private final String nameEt;
        private final String nameEn;
        private final List<Integer> weekNrs;

        public StudyPeriodDto(StudyPeriod studyPeriod) {
            id = studyPeriod.getId();
            nameEt = studyPeriod.getNameEt();
            nameEn = studyPeriod.getNameEn();
            weekNrs = studyPeriod.getWeekNrs();
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

        public List<Integer> getWeekNrs() {
            return weekNrs;
        }
    }

    public static class LessonPlanLegendDto {

        private final Long weekNr;
        private final String color;

        public LessonPlanLegendDto(Long weekNr, String color) {
            this.weekNr = weekNr;
            this.color = color;
        }

        public Long getWeekNr() {
            return weekNr;
        }

        public String getColor() {
            return color;
        }
    }
}
