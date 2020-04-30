package ee.hitsa.ois.web.dto.timetable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleCapacity;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.school.StudyYearScheduleLegend;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalOccupationModuleTheme;
import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.domain.timetable.LessonPlan;
import ee.hitsa.ois.domain.timetable.LessonPlanModule;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.LessonPlanUtil;
import ee.hitsa.ois.util.LessonPlanUtil.LessonPlanCapacityMapper;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.StudyPeriodWithWeeksDto;

public class LessonPlanDto extends LessonPlanForm {

    private Long id;
    private String studyYearCode;
    private String studentGroupCode;
    private Short courseNr;
    private String curriculumCode;
    private AutocompleteResult curriculumVersion;
    private Integer studyPeriod;
    private List<StudyPeriodWithWeeksDto> studyPeriods;
    private List<Short> weekNrs;
    private List<LocalDate> weekBeginningDates;
    private List<LessonPlanLegendDto> legends;
    private List<LessonPlanTeacherDto> teachers;
    private List<ClassifierDto> lessonPlanCapacities;

    public static LessonPlanDto of(LessonPlan lessonPlan, Map<Long, Long> weekNrsLegends) {
        LessonPlanDto dto = EntityUtil.bindToDto(lessonPlan, new LessonPlanDto());
        dto.setStudyYearCode(EntityUtil.getCode(lessonPlan.getStudyYear().getYear()));
        
        StudentGroup studentGroup = lessonPlan.getStudentGroup();
        dto.setStudentGroupCode(studentGroup.getCode());
        dto.setCourseNr(studentGroup.getCourse());
        dto.setCurriculumCode(studentGroup.getCurriculum().getCode());
        dto.setCurriculumVersion(AutocompleteResult.of(studentGroup.getCurriculumVersion()));
        dto.setStudyPeriod(studentGroup.getCurriculum().getStudyPeriod());
        
        dto.setStudyPeriods(lessonPlan.getStudyYear().getStudyPeriods().stream().sorted(Comparator.comparing(StudyPeriod::getStartDate))
                .map(StudyPeriodWithWeeksDto::new).collect(Collectors.toList()));
        LessonPlanCapacityMapper capacityMapper = LessonPlanUtil.capacityMapper(lessonPlan.getStudyYear());
        Set<Long> existingModuleIds = StreamUtil.toMappedSet(
                m -> EntityUtil.getId(m.getCurriculumVersionOccupationModule()), 
                lessonPlan.getLessonPlanModules());
        dto.setModules(Stream.concat(
                lessonPlan.getLessonPlanModules().stream()
                    .map(m -> LessonPlanModuleDto.of(m, capacityMapper)), 
                lessonPlan.getCurriculumVersion().getOccupationModules().stream()
                    .filter(m -> !existingModuleIds.contains(EntityUtil.getId(m)))
                    .map(m -> LessonPlanModuleDto.of(m)))
                .sorted(Comparator.comparing(LessonPlanModuleDto::getTypeOrder, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(Comparator.comparing(LessonPlanModuleDto::getOrderNr, Comparator.nullsLast(Comparator.naturalOrder())))
                        .thenComparing(Comparator.comparing(r -> r.getNameEt(), String.CASE_INSENSITIVE_ORDER)))
                .collect(Collectors.toList()));
        dto.setWeekNrs(dto.getStudyPeriods().stream().flatMap(r -> r.getWeekNrs().stream()).collect(Collectors.toList()));
        dto.setWeekBeginningDates(dto.getStudyPeriods().stream().flatMap(r -> r.getWeekBeginningDates().stream()).collect(Collectors.toList()));

        Map<Long, String> colors = StreamUtil.toMap(StudyYearScheduleLegend::getId, StudyYearScheduleLegend::getColor, lessonPlan.getSchool()
                .getStudyYearScheduleLegends());
        dto.setLegends(StreamUtil.toMappedList(e -> new LessonPlanLegendDto(e.getKey(), colors.get(e.getValue())), weekNrsLegends.entrySet()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStudyYearCode() {
        return studyYearCode;
    }

    public void setStudyYearCode(String studyYearCode) {
        this.studyYearCode = studyYearCode;
    }

    public String getStudentGroupCode() {
        return studentGroupCode;
    }

    public void setStudentGroupCode(String studentGroupCode) {
        this.studentGroupCode = studentGroupCode;
    }
    
    public Short getCourseNr() {
        return courseNr;
    }

    public void setCourseNr(Short courseNr) {
        this.courseNr = courseNr;
    }
    
    public String getCurriculumCode() {
        return curriculumCode;
    }

    public void setCurriculumCode(String curriculumCode) {
        this.curriculumCode = curriculumCode;
    }

    public AutocompleteResult getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(AutocompleteResult curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Integer getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Integer studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public List<StudyPeriodWithWeeksDto> getStudyPeriods() {
        return studyPeriods;
    }

    public void setStudyPeriods(List<StudyPeriodWithWeeksDto> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }

    public List<Short> getWeekNrs() {
        return weekNrs;
    }

    public void setWeekNrs(List<Short> weekNrs) {
        this.weekNrs = weekNrs;
    }

    public List<LocalDate> getWeekBeginningDates() {
        return weekBeginningDates;
    }

    public void setWeekBeginningDates(List<LocalDate> weekBeginningDates) {
        this.weekBeginningDates = weekBeginningDates;
    }

    public List<LessonPlanLegendDto> getLegends() {
        return legends;
    }

    public void setLegends(List<LessonPlanLegendDto> legends) {
        this.legends = legends;
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

    public static class LessonPlanModuleDto extends LessonPlanModuleForm {

        private String nameEt;
        private String nameEn;
        private Integer totalHours;
        private Long typeOrder;
        private Long curriculumVersionId;
        private Short orderNr;

        public static LessonPlanModuleDto of(LessonPlanModule lessonPlanModule, LessonPlanCapacityMapper capacityMapper) {
            LessonPlanModuleDto dto = new LessonPlanModuleDto();
            dto.setId(lessonPlanModule.getId());
            setOccupationModuleValues(dto, lessonPlanModule.getCurriculumVersionOccupationModule());
            dto.setTeacher(lessonPlanModule.getTeacher() != null ? AutocompleteResult.of(lessonPlanModule.getTeacher()) : null);
            dto.setJournals(lessonPlanModule.getJournalOccupationModuleThemes().stream().map(r -> r.getJournal()).distinct()
                    .map(r -> LessonPlanModuleJournalDto.of(lessonPlanModule.getLessonPlan(), r, capacityMapper))
                    .sorted(Comparator.comparing(r -> r.getNameEt(), String.CASE_INSENSITIVE_ORDER))
                    .collect(Collectors.toList()));
            dto.setTotalHours(Integer.valueOf(lessonPlanModule.getCurriculumVersionOccupationModule().getCapacities()
                    .stream().mapToInt(CurriculumVersionOccupationModuleCapacity::getHours).sum()));
            dto.setTypeOrder(Long.valueOf(CurriculumUtil.vocationalModuleOrderNr(lessonPlanModule.getCurriculumVersionOccupationModule())));
            dto.setCurriculumVersionId(EntityUtil.getId(lessonPlanModule.getCurriculumVersionOccupationModule().getCurriculumVersion()));
            dto.setOrderNr(lessonPlanModule.getCurriculumVersionOccupationModule().getCurriculumModule().getOrderNr());
            return dto;
        }

        public static LessonPlanModuleDto of(CurriculumVersionOccupationModule occupationModule) {
            LessonPlanModuleDto dto = new LessonPlanModuleDto();
            setOccupationModuleValues(dto, occupationModule);
            dto.setJournals(Collections.emptyList());
            dto.setTotalHours(Integer.valueOf(0));
            dto.setTypeOrder(Long.valueOf(CurriculumUtil.vocationalModuleOrderNr(occupationModule)));
            dto.setCurriculumVersionId(EntityUtil.getId(occupationModule.getCurriculumVersion()));
            dto.setOrderNr(occupationModule.getCurriculumModule().getOrderNr());
            return dto;
        }

        private static void setOccupationModuleValues(LessonPlanModuleDto dto, CurriculumVersionOccupationModule occupationModule) {
            CurriculumModule cm = occupationModule.getCurriculumModule();
            dto.setNameEt(cm.getNameEt());
            dto.setNameEn(cm.getNameEn());
            dto.setOccupationModuleId(EntityUtil.getId(occupationModule));
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

        public Long getTypeOrder() {
            return typeOrder;
        }

        public void setTypeOrder(Long typeOrder) {
            this.typeOrder = typeOrder;
        }

        public Long getCurriculumVersionId() {
            return curriculumVersionId;
        }

        public void setCurriculumVersionId(Long curriculumVersionId) {
            this.curriculumVersionId = curriculumVersionId;
        }

        public Short getOrderNr() {
            return orderNr;
        }

        public void setOrderNr(Short orderNr) {
            this.orderNr = orderNr;
        }

    }

    public static class LessonPlanModuleJournalDto extends LessonPlanModuleJournalForm {

        private String nameEt;
        private String groupProportion;
        private List<LessonPlanModuleJournalThemeDto> themes;
        private List<String> studentGroups;

        public static LessonPlanModuleJournalDto of(LessonPlan lessonPlan, Journal journal, LessonPlanCapacityMapper capacityMapper) {
            LessonPlanModuleJournalDto dto = new LessonPlanModuleJournalDto();
            dto.setId(journal.getId());
            dto.setNameEt(journal.getNameEt());
            dto.setGroupProportion(EntityUtil.getNullableCode(journal.getGroupProportion()));
            
            List<JournalOccupationModuleTheme> lessonPlanThemes = StreamUtil.toFilteredList(
                    r -> EntityUtil.getId(r.getLessonPlanModule().getLessonPlan()).equals(EntityUtil.getId(lessonPlan)),
                    journal.getJournalOccupationModuleThemes());
            dto.setThemes(lessonPlanThemes.stream().map(r -> new LessonPlanModuleJournalThemeDto(r.getCurriculumVersionOccupationModuleTheme()))
                    .sorted(Comparator.comparing(r -> r.getNameEt(), String.CASE_INSENSITIVE_ORDER))
                    .collect(Collectors.toList()));
            dto.setTeachers(journal.getJournalTeachers().stream()
                    .map(jt -> LessonPlanModuleJournalTeacherDto.of(journal, jt, capacityMapper))
                    .sorted(Comparator.comparing(r -> r.getTeacher().getNameEt(), String.CASE_INSENSITIVE_ORDER))
                    .collect(Collectors.toList()));
            List<String> studenGroups = StreamUtil.toMappedList(
                    t -> t.getLessonPlanModule().getLessonPlan().getStudentGroup().getCode(),
                    journal.getJournalOccupationModuleThemes());
            studenGroups = studenGroups.stream().distinct().collect(Collectors.toList());
            Collections.sort(studenGroups, (Comparator.comparing(r -> r, String.CASE_INSENSITIVE_ORDER)));
            dto.setStudentGroups(studenGroups);
            dto.setCapacityDiff(journal.getCapacityDiff());

            // all hours mapped by capacity type and week nr
            dto.setHours(capacityMapper.mapJournalOutput(journal));
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

        public List<String> getStudentGroups() {
            return studentGroups;
        }

        public void setStudentGroups(List<String> studentGroups) {
            this.studentGroups = studentGroups;
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
            dto.setThemes(journal.getJournalOccupationModuleThemes().stream()
                    .map(r -> new LessonPlanModuleJournalThemeDto(r.getCurriculumVersionOccupationModuleTheme()))
                    .sorted(Comparator.comparing(r -> r.getNameEt(), String.CASE_INSENSITIVE_ORDER))
                    .collect(Collectors.toList()));
            dto.setTeachers(journal.getJournalTeachers().stream()
                    .map(jt -> LessonPlanModuleJournalTeacherDto.of(journal, jt, capacityMapper))
                    .sorted(Comparator.comparing(r -> r.getTeacher().getNameEt(), String.CASE_INSENSITIVE_ORDER))
                    .collect(Collectors.toList()));
            Long teacherId = EntityUtil.getId(teacher);
            JournalTeacher currentTeacher = journal.getJournalTeachers().stream()
                    .filter(t -> EntityUtil.getId(t.getTeacher()).equals(teacherId)).findFirst().get();
            dto.setIsConfirmer(currentTeacher.getIsConfirmer());
            dto.setIsFiller(currentTeacher.getIsFiller());
            dto.setStudentGroups(JournalDto.of(journal).getStudentGroups().stream().distinct().collect(Collectors.toList()));
            // all hours mapped by capacity type and week nr
            if (Boolean.TRUE.equals(journal.getCapacityDiff())) {
                dto.setHours(capacityMapper.mapTeacherOutput(journal, currentTeacher));
            } else {
                dto.setHours(capacityMapper.mapJournalOutput(journal));
            }
            dto.setCapacityDiff(journal.getCapacityDiff());
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

        private final Long id;
        private final String nameEt;
        private final BigDecimal credits;
        private final Map<String, Short> hours;

        public LessonPlanModuleJournalThemeDto(CurriculumVersionOccupationModuleTheme theme) {
            id = theme.getId();
            nameEt = theme.getNameEt();
            credits = theme.getCredits();
            hours = StreamUtil.toMap(r -> EntityUtil.getCode(r.getCapacityType()), r -> r.getHours(), theme.getCapacities());
        }
        
        public Long getId() {
            return id;
        }

        public String getNameEt() {
            return nameEt;
        }

        public BigDecimal getCredits() {
            return credits;
        }

        public Map<String, Short> getHours() {
            return hours;
        }
    }

    public static class LessonPlanModuleJournalTeacherDto extends LessonPlanModuleJournalTeacherForm {

        private AutocompleteResult teacher;

        public static LessonPlanModuleJournalTeacherDto of(Journal journal, JournalTeacher journalTeacher,
                LessonPlanCapacityMapper capacityMapper) {
            LessonPlanModuleJournalTeacherDto dto = new LessonPlanModuleJournalTeacherDto();
            dto.setId(EntityUtil.getId(journalTeacher));
            dto.setTeacher(AutocompleteResult.of(journalTeacher.getTeacher()));
            dto.setHours(capacityMapper.mapTeacherOutput(journal, journalTeacher));
            return dto;
        }

        public AutocompleteResult getTeacher() {
            return teacher;
        }

        public void setTeacher(AutocompleteResult teacher) {
            this.teacher = teacher;
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
    
    public static class LessonPlanTeacherDto {
        
        private Long id;
        private Short scheduleLoad;
        private Boolean isStudyPeriodScheduleLoad;
        private Long plannedLessons;
        private Map<String, Long> plannedLessonsByCapacity;
        private List<Long> studyLoadByWeek;
        private Map<String, List<Long>> studyLoadByWeekAndCapacity;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Short getScheduleLoad() {
            return scheduleLoad;
        }

        public void setScheduleLoad(Short scheduleLoad) {
            this.scheduleLoad = scheduleLoad;
        }

        public Boolean getIsStudyPeriodScheduleLoad() {
            return isStudyPeriodScheduleLoad;
        }

        public void setIsStudyPeriodScheduleLoad(Boolean isStudyPeriodScheduleLoad) {
            this.isStudyPeriodScheduleLoad = isStudyPeriodScheduleLoad;
        }

        public Long getPlannedLessons() {
            return plannedLessons;
        }

        public void setPlannedLessons(Long plannedLessons) {
            this.plannedLessons = plannedLessons;
        }

        public Map<String, Long> getPlannedLessonsByCapacity() {
            return plannedLessonsByCapacity;
        }

        public void setPlannedLessonsByCapacity(Map<String, Long> plannedLessonsByCapacity) {
            this.plannedLessonsByCapacity = plannedLessonsByCapacity;
        }

        public List<Long> getStudyLoadByWeek() {
            return studyLoadByWeek;
        }

        public void setStudyLoadByWeek(List<Long> studyLoadByWeek) {
            this.studyLoadByWeek = studyLoadByWeek;
        }

        public Map<String, List<Long>> getStudyLoadByWeekAndCapacity() {
            return studyLoadByWeekAndCapacity;
        }

        public void setStudyLoadByWeekAndCapacity(Map<String, List<Long>> studyLoadByWeekAndCapacity) {
            this.studyLoadByWeekAndCapacity = studyLoadByWeekAndCapacity;
        }

    }
}
