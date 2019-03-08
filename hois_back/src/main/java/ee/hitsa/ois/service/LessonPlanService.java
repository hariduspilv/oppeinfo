package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalCapacity;
import ee.hitsa.ois.domain.timetable.JournalCapacityType;
import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.domain.timetable.JournalOccupationModuleTheme;
import ee.hitsa.ois.domain.timetable.JournalRoom;
import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.domain.timetable.LessonPlan;
import ee.hitsa.ois.domain.timetable.LessonPlanModule;
import ee.hitsa.ois.domain.timetable.TimetableObject;
import ee.hitsa.ois.domain.timetable.TimetableObjectStudentGroup;
import ee.hitsa.ois.enums.GroupProportion;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.exception.EntityRemoveException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.JournalRepository;
import ee.hitsa.ois.repository.LessonPlanModuleRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.LessonPlanUtil;
import ee.hitsa.ois.util.LessonPlanUtil.LessonPlanCapacityMapper;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UntisCodeUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.SchoolCapacityTypeCommand;
import ee.hitsa.ois.web.commandobject.StudentGroupAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanCreateForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm.LessonPlanModuleForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm.LessonPlanModuleJournalForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm.LessonPlanModuleJournalTeacherCapacitiesForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm.LessonPlanModuleJournalTeacherForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanJournalForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanJournalForm.LessonPlanGroupForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanSearchTeacherCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeResult;
import ee.hitsa.ois.web.dto.timetable.LessonPlanByTeacherDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanByTeacherDto.LessonPlanByTeacherSubjectDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanByTeacherDto.LessonPlanByTeacherSubjectStudentGroupDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanCreatedJournalDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto.LessonPlanModuleDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto.LessonPlanModuleJournalDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto.LessonPlanTeacherDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto.StudyPeriodDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanJournalDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanSearchDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanSearchTeacherDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanTeacherLoadDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanXlsDto.LessonPlanXlsJournalDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanXlsDto.LessonPlanXlsModuleDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanXlsDto.LessonPlanXlsStudyPeriodDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanXlsDto.LessonPlanXlsTotalsDto;

@Transactional
@Service
public class LessonPlanService {

    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private LessonPlanModuleRepository lessonPlanModuleRepository;
    @Autowired
    private XlsService xlsService;

    public LessonPlanDto get(LessonPlan lessonPlan) {
        LessonPlanDto dto = LessonPlanDto.of(lessonPlan, scheduleLegends(lessonPlan));
        setLessonPlanCapacities(lessonPlan, dto);

        Set<Long> journalTeachers = lessonPlanJournalTeachers(dto.getId());
        dto.setTeachers(setLessonPlanTeachers(EntityUtil.getId(lessonPlan.getStudyYear()), journalTeachers));
        return dto;
    }

    private Set<Long> lessonPlanJournalTeachers(Long lessonPlanId) {
        List<?> teachers = em.createNativeQuery("select t.id from lesson_plan lp "
                    + "join lesson_plan_module lpm on lp.id = lpm.lesson_plan_id "
                    + "join curriculum_version_omodule cvo on lpm.curriculum_version_omodule_id = cvo.id "
                    + "join curriculum_version_omodule_theme cvot on cvo.id = cvot.curriculum_version_omodule_id "
                    + "join journal_omodule_theme jot on cvot.id = jot.curriculum_version_omodule_theme_id "
                    + "join journal_teacher jt on jot.journal_id = jt.journal_id "
                    + "join teacher t on jt.teacher_id = t.id " 
                    + "where lp.id = ?1")
                .setParameter(1, lessonPlanId)
                .getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), teachers);
    }

    public List<LessonPlanTeacherDto> setLessonPlanTeachers(Long studyYearId, Set<Long> teacherIds) {
        List<LessonPlanTeacherDto> teacherDtos = new ArrayList<>();

        if (!teacherIds.isEmpty()) {
            List<?> teacherScheduleLoads = em.createNativeQuery(
                    "select t.id, t.schedule_load, t.is_study_period_schedule_load from teacher t " +
                    "join journal_teacher jt on t.id = jt.teacher_id " +
                    "join journal j on jt.journal_id = j.id " +
                    "where t.id in (:teacherIds) and j.study_year_id = :studyYearId " +
                    "group by t.id")
                .setParameter("teacherIds", teacherIds)
                .setParameter("studyYearId", studyYearId)
                    .getResultList();

            Map<Long, List<Long>> teacherStudyLoadByWeek = teacherStudyLoadByWeek(teacherIds, studyYearId);

            teacherDtos = StreamUtil.toMappedList(r -> {
                LessonPlanTeacherDto teacherDto = new LessonPlanTeacherDto();
                Long teacherId = resultAsLong(r, 0);
                teacherDto.setId(teacherId);
                teacherDto.setScheduleLoad(resultAsShort(r, 1));
                teacherDto.setIsStudyPeriodScheduleLoad(resultAsBoolean(r, 2));
                List<Long> teacherStudyLoad = teacherStudyLoadByWeek.get(teacherId);
                Long plannedLessons = teacherStudyLoad != null
                        ? Long.valueOf(teacherStudyLoad.stream().filter(l -> l != null).mapToInt(Long::intValue).sum())
                        : null;
                teacherDto.setStudyLoadByWeek(teacherStudyLoad);
                teacherDto.setPlannedLessons(plannedLessons);
                return teacherDto;
            }, teacherScheduleLoads);
        }
        return teacherDtos;
    }

    public Map<Long, List<Long>> teacherStudyLoadByWeek(Set<Long> teacherIds, Long studyYearId) {
        Map<Long, List<Long>> result = new HashMap<>();
        
        Map<Long, List<LessonPlanTeacherLoadDto>> loads = new HashMap<>();
        List<?> teacherStudyLoads = em.createNativeQuery(
                "select teacher_id, week_nr, study_period_id, sum(hours) from (" +
                "select jt.teacher_id, week_nr, jc.study_period_id, sum(jc.hours) as hours from journal_teacher jt " +
                    "join journal j on jt.journal_id = j.id " +
                    "join journal_capacity jc on jc.journal_id = j.id and (j.is_capacity_diff is null or j.is_capacity_diff=false) " +
                "where jt.teacher_id in (:teacherIds) and j.study_year_id = :studyYearId " +
                    "group by teacher_id, week_nr, study_period_id " +
                "union all " +
                "select jt2.teacher_id, jtc.week_nr, jtc.study_period_id, sum(jtc.hours) as hours from journal_teacher jt2 " +
                    "join journal j2 on jt2.journal_id = j2.id " +
                    "join journal_teacher_capacity jtc on jt2.id = jtc.journal_teacher_id and j2.is_capacity_diff=true " +
                "where jt2.teacher_id in (:teacherIds) and j2.study_year_id = :studyYearId " +
                    "group by teacher_id, week_nr, study_period_id) as hours " +
                "group by teacher_id, week_nr, study_period_id")
            .setParameter("teacherIds", teacherIds)
            .setParameter("studyYearId", studyYearId)
            .getResultList();

        if (!teacherStudyLoads.isEmpty()) {
            loads = teacherStudyLoads.stream()
                    .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(
                            r -> new LessonPlanTeacherLoadDto(resultAsShort(r, 1), resultAsLong(r, 2), resultAsLong(r, 3)),
                            Collectors.toList())));

            StudyYear studyYear = em.getReference(StudyYear.class, studyYearId);
            LessonPlanCapacityMapper capacityMapper = LessonPlanUtil.capacityMapper(studyYear);

            for (Long teacher : teacherIds) {
                List<LessonPlanTeacherLoadDto> teacherLoads = loads.get(teacher);
                result.put(teacher, capacityMapper.mapTeacherLoads(teacherLoads));
            }
        }
        return result;
    }

    private void setLessonPlanCapacities(LessonPlan lessonPlan, LessonPlanDto dto) {
        List<ClassifierDto> schoolCapacities = lessonPlanSchoolCapacityTypeDtos(EntityUtil.getId(lessonPlan.getSchool()), Boolean.FALSE);
        
        if (dto.getModules() != null) {
            List<LessonPlanModuleJournalForm> journals = new ArrayList<>();
            journals = dto.getModules().stream().filter(m -> m.getJournals() != null).map(m -> m.getJournals())
                    .flatMap(m -> m.stream()).collect(Collectors.toList());

            Set<String> journalCapacities = new HashSet<>();
            journalCapacities = journals.stream().filter(j -> j.getHours() != null).map(j -> j.getHours().keySet())
                    .flatMap(j -> j.stream()).collect(Collectors.toSet());
            addMissingJournalCapacities(schoolCapacities, journalCapacities);
        }
        
        schoolCapacities.sort(Comparator.comparing(ClassifierDto::getNameEt, String.CASE_INSENSITIVE_ORDER));
        dto.setLessonPlanCapacities(schoolCapacities);
    }
    
    private void setTeacherLessonPlanCapacities(LessonPlanByTeacherDto dto, Long schoolId,
            List<Journal> journals, List<LessonPlanByTeacherSubjectDto> subjects) {
        List<ClassifierDto> schoolCapacities = lessonPlanSchoolCapacityTypeDtos(schoolId, null);
        
        if (journals != null) {
            Set<String> journalCapacities = new HashSet<>();
            journalCapacities.addAll(journals.stream().map(j -> j.getJournalCapacityTypes())
                    .flatMap(j -> j.stream()).map(jct -> jct.getCapacityType().getCode()).collect(Collectors.toSet()));
            addMissingJournalCapacities(schoolCapacities, journalCapacities);
        }
        
        if (subjects != null) {
            Set<String> subjectCapacities = new HashSet<>();
            subjectCapacities.addAll(subjects.stream().map(s -> s.getCapacityTotals()).flatMap(s -> s.values().stream())
                    .flatMap(s -> s.keySet().stream()).collect(Collectors.toSet()));
            addMissingJournalCapacities(schoolCapacities, subjectCapacities);
        }

        schoolCapacities.sort(Comparator.comparing(ClassifierDto::getNameEt, String.CASE_INSENSITIVE_ORDER));
        dto.setLessonPlanCapacities(schoolCapacities);
    }
    
    private List<ClassifierDto> lessonPlanSchoolCapacityTypeDtos(Long schoolId, Boolean isHigher) {
        SchoolCapacityTypeCommand command = new SchoolCapacityTypeCommand();
        command.setIsHigher(isHigher);
        command.setIsTimetable(Boolean.TRUE);
        return autocompleteService.schoolCapacityTypeDtos(schoolId, command);
    }
    
    private void addMissingJournalCapacities(List<ClassifierDto> schoolCapacities, Set<String> journalCapacities) {
        Set<String> schoolCapacityCodes = StreamUtil.toMappedSet(sc -> sc.getCode(), schoolCapacities);
        for (String code : journalCapacities) {
            if (!schoolCapacityCodes.contains(code)) {
                schoolCapacities.add(ClassifierDto.of(em.getReference(Classifier.class, code)));
            }
        }
    }

    public LessonPlan create(HoisUserDetails user, LessonPlanCreateForm form) {
        StudentGroup studentGroup = em.getReference(StudentGroup.class, form.getStudentGroup());
        UserUtil.assertSameSchool(user, studentGroup.getSchool());
        StudyYear studyYear = em.getReference(StudyYear.class, form.getStudyYear());
        UserUtil.assertSameSchool(user, studyYear.getSchool());

        LessonPlan lessonPlan = new LessonPlan();
        lessonPlan.setSchool(em.getReference(School.class, user.getSchoolId()));
        lessonPlan.setStudentGroup(studentGroup);
        lessonPlan.setStudyYear(studyYear);
        lessonPlan.setIsUsable(Boolean.FALSE);
        lessonPlan.setShowWeeks(Boolean.FALSE);
        CurriculumVersion curriculumVersion = studentGroup.getCurriculumVersion();
        lessonPlan.setCurriculumVersion(curriculumVersion);

        EntityUtil.save(lessonPlan, em);
        if (form.getPreviousLessonplan() != null) {
            copyPreviousLessonPlan(form, lessonPlan);
        }
        return lessonPlan;
    }

    private void copyPreviousLessonPlan(LessonPlanCreateForm form, LessonPlan lessonPlan) {
        LessonPlan previousLessonPlan = em.getReference(LessonPlan.class, form.getPreviousLessonplan());

        for (LessonPlanModule lpm : previousLessonPlan.getLessonPlanModules()) {
            LessonPlanModule lessonPlanModuleCopy = new LessonPlanModule();
            lessonPlanModuleCopy.setLessonPlan(lessonPlan);
            
            CurriculumVersionOccupationModule module = lessonPlanCopyModule(previousLessonPlan, lessonPlan, lpm);
            if (module == null) {
                continue;
            }
            lessonPlanModuleCopy.setCurriculumVersionOccupationModule(module);
            
            if (lpm.getTeacher() != null && Boolean.TRUE.equals(lpm.getTeacher().getIsActive())) {
                lessonPlanModuleCopy.setTeacher(lpm.getTeacher());
            }
            lessonPlan.getLessonPlanModules().add(lessonPlanModuleCopy);
            EntityUtil.save(lessonPlanModuleCopy, em);
            
            Map<Long, CurriculumVersionOccupationModuleTheme> themeEquivalents = occupationalModuleThemeEquivalents(lpm, module);
            List<Journal> journals = lessonPlanModuleJournals(lpm);
            for (Journal j : journals) {
                List<CurriculumVersionOccupationModuleTheme> occupationModuleThemes = StreamUtil.toMappedList(
                jt -> jt.getCurriculumVersionOccupationModuleTheme(), j.getJournalOccupationModuleThemes());
                List<CurriculumVersionOccupationModuleTheme> journalThemeEquivalents = journalCopyOcupationModuleThemes(
                lessonPlanModuleCopy, occupationModuleThemes, themeEquivalents);
                
                if (!journalThemeEquivalents.isEmpty()) {
                    copyPreviousLessonPlanJournal(form, j, journalThemeEquivalents, lessonPlan, lessonPlanModuleCopy);
                }
            }
        }
    }
    
    private CurriculumVersionOccupationModule lessonPlanCopyModule(LessonPlan previousLessonPlan,
            LessonPlan lessonPlan, LessonPlanModule lessonPlanModule) {
        CurriculumVersionOccupationModule module = null;
        if (EntityUtil.getId(lessonPlan.getCurriculumVersion()).equals(EntityUtil.getId(previousLessonPlan.getCurriculumVersion()))) {
            module = lessonPlanModule.getCurriculumVersionOccupationModule();
        } else {
            List<CurriculumVersionOccupationModule> data = em.createQuery("select cvom from CurriculumVersionOccupationModule cvom "
                    + "where cvom.curriculumVersion.id = ?1 and cvom.curriculumModule.id = ?2", CurriculumVersionOccupationModule.class)
                    .setParameter(1, EntityUtil.getId(lessonPlan.getCurriculumVersion()))
                    .setParameter(2, EntityUtil.getId(lessonPlanModule.getCurriculumVersionOccupationModule().getCurriculumModule()))
                    .getResultList();
            module = data.isEmpty() ? null : data.get(0);
        }
        return module;
    }
    
    private Map<Long, CurriculumVersionOccupationModuleTheme> occupationalModuleThemeEquivalents(LessonPlanModule lessonPlanModule,
            CurriculumVersionOccupationModule copiedLessonPlanModuleOccupationModule) {
        List<?> data = em.createNativeQuery(
                "select distinct on (cvot.id) cvot.id as theme_id, cvot2.id as equivalent_theme_id from curriculum_version_omodule_theme cvot "
                        + "join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id "
                        + "join curriculum_version_omodule cvo2 on cvo2.id = ?1 "
                        + "join curriculum_version_omodule_theme cvot2 on "
                            + "(cvot2.name_et ilike '%' || cvot.name_et || '%' or cvot.name_et ilike '%' || cvot2.name_et || '%') "
                            + "and cvo2.id = cvot2.curriculum_version_omodule_id "
                        + "where cvot.curriculum_version_omodule_id = ?2")
                .setParameter(1, EntityUtil.getId(copiedLessonPlanModuleOccupationModule))
                .setParameter(2, EntityUtil.getId(lessonPlanModule.getCurriculumVersionOccupationModule()))
                .getResultList();
        
        List<Long> equivalentThemeIds = StreamUtil.toMappedList(r -> resultAsLong(r, 1), data);
        List<CurriculumVersionOccupationModuleTheme> equivalentThemes = new ArrayList<>();
        if (!equivalentThemeIds.isEmpty()) {
            equivalentThemes = em.createQuery("select cvomt from CurriculumVersionOccupationModuleTheme cvomt "
                    + "where cvomt.id in (?1)", CurriculumVersionOccupationModuleTheme.class)
                    .setParameter(1, equivalentThemeIds).getResultList();
        }
        Map<Long, CurriculumVersionOccupationModuleTheme> equivalentThemesMap = StreamUtil.toMap(t -> t.getId(), t -> t, equivalentThemes);
        
        return StreamUtil.toMap(r -> resultAsLong(r, 0), r -> equivalentThemesMap.get(resultAsLong(r, 1)), data);
    }
    
    private List<CurriculumVersionOccupationModuleTheme> journalCopyOcupationModuleThemes(LessonPlanModule lessonPlanModuleCopy,
            List<CurriculumVersionOccupationModuleTheme> occupationModuleThemes,
            Map<Long, CurriculumVersionOccupationModuleTheme> journalThemeEquivalents) {
        List<CurriculumVersionOccupationModuleTheme> themes = new ArrayList<>();
        //if there are any equivalents then those are added to journal
        for (CurriculumVersionOccupationModuleTheme cvomt : occupationModuleThemes) {
            Long cvomtId = EntityUtil.getId(cvomt);
            if (journalThemeEquivalents.containsKey(cvomtId)) {
                themes.add(journalThemeEquivalents.get(cvomtId));
            }
        }
        
        //if there are no equivalents then all of curriculum version modules themes are added
        if (themes.isEmpty()) {
            themes = em.createQuery(
                    "select cvomt from CurriculumVersionOccupationModuleTheme cvomt where cvomt.module.id = ?1",
                        CurriculumVersionOccupationModuleTheme.class)
                    .setParameter(1, EntityUtil.getId(lessonPlanModuleCopy.getCurriculumVersionOccupationModule()))
                    .getResultList();
        }
        
        return themes;
    }
    
    private List<Journal> lessonPlanModuleJournals(LessonPlanModule lessonPlanModule) {
        return em.createQuery("select jomt.journal from JournalOccupationModuleTheme jomt "
                + "where jomt.lessonPlanModule.id = ?1", Journal.class)
                .setParameter(1, EntityUtil.getId(lessonPlanModule))
                .getResultList().stream().distinct().collect(Collectors.toList());
    }

    private void copyPreviousLessonPlanJournal(LessonPlanCreateForm form, Journal journal,
            List<CurriculumVersionOccupationModuleTheme> journalThemeEquivalents, LessonPlan lessonPlan,
            LessonPlanModule lessonPlanModuleCopy) {
        Journal journalCopy = new Journal();
        journalCopy = new Journal();
        journalCopy.setSchool(lessonPlan.getSchool());
        journalCopy.setAssessment(journal.getAssessment());
        journalCopy.setNameEt(journal.getNameEt());
        journalCopy.setStudyYear(lessonPlan.getStudyYear());
        journalCopy.setGroupProportion(journal.getGroupProportion());
        journalCopy.setStatus(journal.getStatus());
        journalCopy.setAddModuleOutcomes(journal.getAddModuleOutcomes());

        copyPreviousLessonPlanJournalCapacities(journal, journalCopy);
        copyPreviousLessonPlanJournalTeachers(journal, journalCopy);
        copyPreviousLessonPlanJournalRooms(journal, journalCopy);
        copyPreviousLessonPlanJournalModuleThemes(journalCopy, journalThemeEquivalents, lessonPlanModuleCopy);
        
        if (Boolean.TRUE.equals(form.getCopyLessons())) {
            copyPreviousLessonPlanJournalLessons(journal, journalCopy, lessonPlan);
        }
        
        if (Boolean.TRUE.equals(journalCopy.getAddModuleOutcomes())) {
            List<Long> curriculumVersionOmoduleThemeIds = StreamUtil.toMappedList(
                    jt -> EntityUtil.getId(jt.getCurriculumVersionOccupationModuleTheme()),
                    journalCopy.getJournalOccupationModuleThemes());
            addJournalOutcomeEntries(journalCopy, curriculumVersionOmoduleThemeIds);
        }
        EntityUtil.save(journalCopy, em);
    }

    private static void copyPreviousLessonPlanJournalRooms(Journal journal, Journal journalCopy) {
        for (JournalRoom jr : journal.getJournalRooms()) {
            JournalRoom journalRoomCopy = new JournalRoom();
            journalRoomCopy.setJournal(journalCopy);
            journalRoomCopy.setRoom(jr.getRoom());
            journalCopy.getJournalRooms().add(journalRoomCopy);
        }
    }

    private static void copyPreviousLessonPlanJournalTeachers(Journal journal, Journal journalCopy) {
        for (JournalTeacher jt : journal.getJournalTeachers()) {
            if (Boolean.TRUE.equals(jt.getTeacher().getIsActive())) {
                JournalTeacher journalTeacherCopy = new JournalTeacher();
                journalTeacherCopy.setJournal(journalCopy);
                journalTeacherCopy.setTeacher(jt.getTeacher());
                journalTeacherCopy.setIsConfirmer(jt.getIsConfirmer());
                journalTeacherCopy.setIsFiller(jt.getIsFiller());
                journalCopy.getJournalTeachers().add(journalTeacherCopy);
            }
        }
    }

    private static void copyPreviousLessonPlanJournalCapacities(Journal journal, Journal journalCopy) {
        for (JournalCapacityType jct : journal.getJournalCapacityTypes()) {
            JournalCapacityType journalCapacityTypeCopy = new JournalCapacityType();
            journalCapacityTypeCopy.setJournal(journalCopy);
            journalCapacityTypeCopy.setCapacityType(jct.getCapacityType());
            journalCopy.getJournalCapacityTypes().add(journalCapacityTypeCopy);
        }
    }

    private static void copyPreviousLessonPlanJournalLessons(Journal journal, Journal journalCopy, LessonPlan lessonPlan) {
        List<Short> lessonPlanWeekNrs = lessonPlan.getStudyYear().getStudyPeriods().stream()
                .flatMap(r -> r.getWeekNrs().stream()).collect(Collectors.toList());
        Map<StudyPeriod, List<Short>> weekNrsByStudyPeriod = lessonPlan.getStudyYear().getStudyPeriods()
                .stream().collect(Collectors.toMap(r -> r, r -> r.getWeekNrs()));

        for (JournalCapacity jc : journal.getJournalCapacities()) {
            if (lessonPlanWeekNrs.contains(jc.getWeekNr())) {
                StudyPeriod studyPeriod = weekNrsByStudyPeriod.entrySet().stream()
                        .filter(sp -> sp.getValue().stream().anyMatch(nr -> nr == jc.getWeekNr()))
                        .map(sp -> sp.getKey()).findFirst().orElse(null);
                
                JournalCapacity journalCapacityCopy = new JournalCapacity();
                journalCapacityCopy.setJournal(journalCopy);
                journalCapacityCopy.setStudyPeriod(studyPeriod);
                journalCapacityCopy.setJournalCapacityType(jc.getJournalCapacityType());
                journalCapacityCopy.setWeekNr(jc.getWeekNr());
                journalCapacityCopy.setHours(jc.getHours());
                journalCopy.getJournalCapacities().add(journalCapacityCopy);
            }
        }
    }

    private static void copyPreviousLessonPlanJournalModuleThemes(Journal journalCopy,
            List<CurriculumVersionOccupationModuleTheme> journalThemeEquivalents,
            LessonPlanModule lessonPlanModuleCopy) {
        List<Long> addedThemes = new ArrayList<>();
        for (CurriculumVersionOccupationModuleTheme cvomt : journalThemeEquivalents) {
            // added themes are kept track of because there can be multiples of equivalent themes
            Long themeId = EntityUtil.getId(cvomt);
            if (!addedThemes.contains(themeId)) {
                JournalOccupationModuleTheme journalOModuleThemeCopy = new JournalOccupationModuleTheme();
                journalOModuleThemeCopy.setJournal(journalCopy);
                journalOModuleThemeCopy.setLessonPlanModule(lessonPlanModuleCopy);
                journalOModuleThemeCopy.setCurriculumVersionOccupationModuleTheme(cvomt);
                journalCopy.getJournalOccupationModuleThemes().add(journalOModuleThemeCopy);
                addedThemes.add(themeId); 
            }
        }
    }

    public LessonPlan save(LessonPlan lessonPlan, LessonPlanForm form) {
        EntityUtil.bindToEntity(form, lessonPlan);

        Map<Long, LessonPlanModule> modules = StreamUtil.toMap(LessonPlanModule::getId,
                lessonPlan.getLessonPlanModules());
        List<? extends LessonPlanModuleForm> formModules = form.getModules();
        if (formModules != null) {
            LessonPlanCapacityMapper capacityMapper = LessonPlanUtil.capacityMapper(lessonPlan.getStudyYear());

            for (LessonPlanModuleForm formModule : formModules) {
                LessonPlanModule lpm = null;
                if (formModule.getId() == null) {
                    // if teacher responsible for teacher is added to a lesson
                    // plan module that has never had any journals, the module
                    // needs to be created
                    if (formModule.getTeacher() != null) {
                        lpm = new LessonPlanModule();
                        lpm.setLessonPlan(lessonPlan);
                        lpm.setCurriculumVersionOccupationModule(em.getReference(CurriculumVersionOccupationModule.class,
                                formModule.getOccupationModuleId()));
                        lessonPlan.getLessonPlanModules().add(lpm);
                    } else {
                        continue;
                    }
                } else {
                    lpm = modules.remove(formModule.getId());
                    if (lpm == null) {
                        throw new AssertionFailedException("Unknown lessonplan module");
                    }
                }
                EntityUtil.bindToEntity(formModule, lpm);
                lpm.setTeacher(EntityUtil.getOptionalOne(Teacher.class, formModule.getTeacher(), em));
                // store journal capacities
                List<? extends LessonPlanModuleJournalForm> formJournals = formModule.getJournals();
                if (formJournals != null) {
                    for (LessonPlanModuleJournalForm formJournal : formJournals) {
                        Journal journal = em.getReference(Journal.class, formJournal.getId());
                        // TODO better checks - is journal related to this
                        // module
                        assertSameSchool(journal, lessonPlan.getSchool());
                        capacityMapper.mapJournalInput(journal, formJournal.getHours());
                        EntityUtil.save(journal, em);
                    }
                }
            }
        }
        AssertionFailedException.throwIf(!modules.isEmpty(), "Unhandled lessonplan module");
        return EntityUtil.save(lessonPlan, em);
    }
    
    public void delete(HoisUserDetails user, LessonPlan lessonPlan) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(lessonPlan, em);
    }

    public void saveJournalTeacherCapacities(LessonPlanModuleJournalTeacherCapacitiesForm form) {
        Journal journal = em.getReference(Journal.class, form.getId());
        journal.setCapacityDiff(form.getCapacityDiff());

        if (Boolean.TRUE.equals(form.getCapacityDiff())) {
            LessonPlanCapacityMapper capacityMapper = LessonPlanUtil.capacityMapper(journal.getStudyYear());
            for (LessonPlanModuleJournalTeacherForm teacherForm : form.getTeachers()) {
                JournalTeacher journalTeacher = em.getReference(JournalTeacher.class, teacherForm.getId());
                capacityMapper.mapTeacherInput(journal, journalTeacher, teacherForm.getHours());
            }
        } else {
            // Remove previously saved teacher capacities
            List<JournalTeacher> teachers = journal.getJournalTeachers();
            for (JournalTeacher journalTeacher : teachers) {
                journalTeacher.getJournalTeacherCapacities().clear();
            }
        }
        EntityUtil.save(journal, em);
    }

    public Page<LessonPlanSearchDto> search(Long schoolId, LessonPlanSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from lesson_plan lp inner join student_group sg on lp.student_group_id = sg.id " +
                "inner join curriculum_version cv on lp.curriculum_version_id = cv.id").sort(pageable);

        qb.requiredCriteria("lp.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("cv.school_department_id = :schoolDepartmentId", "schoolDepartmentId", criteria.getSchoolDepartment());
        qb.optionalCriteria("lp.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", criteria.getCurriculumVersion());
        qb.optionalCriteria("lp.student_group_id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        
        if (criteria.getTeacher() != null) {
            qb.requiredCriteria("lp.id in (select lp.id from lesson_plan lp "
                    + "join lesson_plan_module lpm on lp.id = lpm.lesson_plan_id "
                    + "join journal_omodule_theme jot on lpm.id = jot.lesson_plan_module_id "
                    + "join journal j on jot.journal_id = j.id "
                    + "join journal_teacher jt on j.id = jt.journal_id "
                    + "where jt.teacher_id = :teacherId)", "teacherId", criteria.getTeacher());
        }
        
        String select = "lp.id, sg.code as student_group_code, cv.code, (select coalesce(sum(jc.hours * (1 / cast(c.value as numeric))), 0) "
                + "from journal_capacity jc "
                + "join journal j on jc.journal_id = j.id "
                + "join classifier c on j.group_proportion_code = c.code "
                + "where jc.journal_id in "
                + "(select j.id from journal j join journal_omodule_theme jot on j.id = jot.journal_id "
                + "join lesson_plan_module lpm on jot.lesson_plan_module_id = lpm.id "
                + "join lesson_plan lp2 on lpm.lesson_plan_id = lp2.id "
                + "where lp.id = lp2.id)) as hours";

        return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new LessonPlanSearchDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2), resultAsDecimal(r, 3));
        });
    }

    public Page<LessonPlanSearchTeacherDto> search(HoisUserDetails user, LessonPlanSearchTeacherCommand criteria, Pageable pageable) {
        Map<String, Object> queryParameters = new HashMap<>();
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal_teacher jt"
                + " join journal j on j.id = jt.journal_id "
                + " join journal_capacity jc on j.id = jc.journal_id");
        qb.filter("jt.teacher_id = t.id");
        qb.filter("(j.is_capacity_diff is null or j.is_capacity_diff = false)");
        qb.requiredCriteria("j.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        if (user.isTeacher()) {
            qb.filter("j.id in (select jot.journal_id"
                    + " from journal_omodule_theme jot"
                    + " join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                    + " join lesson_plan lp on lp.id = lpm.lesson_plan_id"
                    + " where lp.is_usable = true)");
        }
        String journalCapacityQuery = qb.querySql("sum(jc.hours)", false);
        queryParameters.putAll(qb.queryParameters());

        qb = new JpaNativeQueryBuilder("from journal_teacher jt2"
                + " join journal j2 on j2.id = jt2.journal_id "
                + " join journal_teacher_capacity jtc on jt2.id = jtc.journal_teacher_id");
        qb.filter("jt2.teacher_id = t.id");
        qb.filter("j2.is_capacity_diff = true");
        qb.requiredCriteria("j2.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        if (user.isTeacher()) {
            qb.filter("j2.id in (select jot.journal_id"
                    + " from journal_omodule_theme jot"
                    + " join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                    + " join lesson_plan lp on lp.id = lpm.lesson_plan_id"
                    + " where lp.is_usable = true)");
        }
        String journalTeacherCapacityQuery = qb.querySql("sum(jtc.hours)", false);
        queryParameters.putAll(qb.queryParameters());
        
        qb = new JpaNativeQueryBuilder("from subject_study_period_teacher sspt"
                + " join subject_study_period ssp on ssp.id = sspt.subject_study_period_id"
                + " join subject_study_period_capacity sspc on sspc.subject_study_period_id = ssp.id"
                + " join study_period sp on sp.id = ssp.study_period_id");
        qb.filter("sspt.teacher_id = t.id");
        qb.requiredCriteria("sp.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        String subjectQuery = qb.querySql("sum(sspc.hours)", false);
        queryParameters.putAll(qb.queryParameters());
        
        qb = new JpaNativeQueryBuilder("from teacher t");
        if (user.isTeacher()) {
            qb.requiredCriteria("t.id = :teacherId", "teacherId", user.getTeacherId());
        } else {
            qb.optionalCriteria("t.id = :teacherId", "teacherId", criteria.getTeacher());
        }
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());
        String hoursQuery = qb.querySql("t.id, t.person_id"
                + ", coalesce((" + journalCapacityQuery + "), 0) as jc_hours"
                + ", coalesce((" + journalTeacherCapacityQuery + "), 0) as jtc_hours"
                + ", coalesce((" + subjectQuery + "), 0) as ssp_hours", false);
        queryParameters.putAll(qb.queryParameters());
        
        qb = new JpaNativeQueryBuilder("from (" + hoursQuery + ") hours"
                + " join person p on hours.person_id = p.id ").sort(pageable);
        qb.filter("(hours.jc_hours + hours.jtc_hours + hours.ssp_hours) > 0");
        

        return JpaQueryUtil.pagingResult(qb, "hours.id, p.firstname, p.lastname, hours.jc_hours + hours.jtc_hours + hours.ssp_hours as total_hours", 
                queryParameters, em, pageable).map(r -> {
            return new LessonPlanSearchTeacherDto(resultAsLong(r, 0), PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)), resultAsLong(r, 3), criteria.getStudyYear());
        });
    }

    public LessonPlanByTeacherDto getByTeacher(HoisUserDetails user, Teacher teacher, StudyYear studyYear) {
        Long studyYearId = EntityUtil.getId(studyYear);
        Long teacherId = EntityUtil.getId(teacher);

        List<Journal> journals = getTeacherJournals(user, teacherId, studyYearId);

        List<LessonPlanByTeacherSubjectDto> subjects = getTeacherSubjects(teacherId, studyYearId).values().stream()
                .sorted(Comparator.comparing(LessonPlanByTeacherSubjectDto::getNameEt, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());

        LessonPlanByTeacherDto dto = new LessonPlanByTeacherDto(studyYear, journals, subjects,
                getSubjectTotals(subjects), teacher);
        setTeacherLessonPlanCapacities(dto, EntityUtil.getId(teacher.getSchool()), journals, subjects);

        Set<Long> journalTeachers = lessonPlanByTeacherJournalTeachers(
                StreamUtil.toMappedList(j -> EntityUtil.getId(j), journals));
        dto.setTeachers(setLessonPlanTeachers(studyYearId, journalTeachers));
        return dto;
    }

    private Set<Long> lessonPlanByTeacherJournalTeachers(List<Long> journalIds) {
        Set<Long> teacherIds = new HashSet<>();
        if (!journalIds.isEmpty()) {
            List<?> teachers = em.createNativeQuery("select t.id from journal_teacher jt "
                    + "join teacher t on jt.teacher_id = t.id " 
                    + "where jt.journal_id in (?1)")
                    .setParameter(1, journalIds)
                    .getResultList();
            teacherIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), teachers); 
        }
        return teacherIds;
    }

    private List<Journal> getTeacherJournals(HoisUserDetails user, Long teacherId, Long studyYearId) {
        return journalRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("studyYear").get("id"), studyYearId));

            Subquery<Long> journalTeachersQuery = query.subquery(Long.class);
            Root<Journal> journalRoot = journalTeachersQuery.from(Journal.class);
            Join<Object, Object> journalTeachersJoin = journalRoot.join("journalTeachers");
            journalTeachersQuery.select(journalRoot.get("id")).where(
                cb.and(
                    cb.equal(journalRoot.get("id"), root.get("id")),
                    cb.equal(journalTeachersJoin.get("teacher").get("id"), teacherId))
                );
            filters.add(cb.exists(journalTeachersQuery));

            if (user.isTeacher()) {
                Subquery<Long> usableLessonPlanQuery = query.subquery(Long.class);
                journalRoot = usableLessonPlanQuery.from(Journal.class);
                Join<Object, Object> lessonPlanJoin = journalRoot.join("journalOccupationModuleThemes")
                        .join("lessonPlanModule").join("lessonPlan");
                usableLessonPlanQuery.select(journalRoot.get("id")).where(
                    cb.and(
                        cb.equal(journalRoot.get("id"), root.get("id")),
                        cb.equal(lessonPlanJoin.get("isUsable"), Boolean.TRUE))
                    );
                filters.add(cb.exists(usableLessonPlanQuery));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
    }

    private Map<Long, LessonPlanByTeacherSubjectDto> getTeacherSubjects(Long teacherId, Long studyYearId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp "+
                "inner join study_period sp on ssp.study_period_id = sp.id " +
                "inner join subject_study_period_teacher sspt on ssp.id = sspt.subject_study_period_id " +
                "inner join subject s on ssp.subject_id = s.id " +
                "left join (select sspsg.subject_study_period_id, sspsg.student_group_id, sg.code from subject_study_period_student_group sspsg " +
                    "join student_group sg on sspsg.student_group_id = sg.id) sg on ssp.id = sg.subject_study_period_id");

        qb.requiredCriteria("sp.study_year_id = :studyYearId", "studyYearId", studyYearId);
        qb.requiredCriteria("sspt.teacher_id = :teacherId", "teacherId", teacherId);
        
        qb.sort("s.name_et");

        List<?> teacherSubjects = qb.select("distinct s.id, s.name_et, s.name_en, sg.student_group_id, sg.code, ssp.id as ssp_id, ssp.group_proportion_code", em).getResultList();
        
        Query capacityQuery = em.createNativeQuery("select ssp.study_period_id, sspc.capacity_type_code, sspc.hours"
                + " from subject_study_period_capacity sspc"
                + " join subject_study_period ssp on ssp.id = sspc.subject_study_period_id"
                + " where sspc.subject_study_period_id = ?1");

        Map<Long, LessonPlanByTeacherSubjectDto> subjects = new HashMap<>();
        Map<Long, Long> subjectStudyPeriodToSubject = new HashMap<>();
        Map<Long, List<String>> studentGroups = new HashMap<>();
        Map<Long, Map<Long, Map<String, Long>>> studentGroupHours = new HashMap<>();
        for(Object r : teacherSubjects) {
            Long subjectId = resultAsLong(r, 0);
            LessonPlanByTeacherSubjectDto subject = subjects.computeIfAbsent(subjectId, 
                    k -> new LessonPlanByTeacherSubjectDto(subjectId, resultAsString(r, 1), resultAsString(r, 2)));
            subject.setGroupProportion(resultAsString(r, 6));
            String studentGroupCode = resultAsString(r, 4);
            Long subjectStudyPeriodId = resultAsLong(r, 5);
            if (studentGroupCode != null) {
                subjectStudyPeriodToSubject.put(subjectStudyPeriodId, subjectId);
                studentGroups.computeIfAbsent(subjectStudyPeriodId, k -> new ArrayList<>()).add(studentGroupCode);
            }
            Map<Long, Map<String, Long>> periodCapacityHours = new HashMap<>();
            List<?> capacities = capacityQuery
                    .setParameter(1, subjectStudyPeriodId)
                    .getResultList();
            for (Object cr : capacities) {
                Long studyPeriodId = resultAsLong(cr, 0);
                String capacityTypeCode = resultAsString(cr, 1);
                Long hours = resultAsLong(cr, 2);
                periodCapacityHours.computeIfAbsent(studyPeriodId, k -> new HashMap<>())
                    .put(capacityTypeCode, hours);
            }
            if (studentGroupCode != null) {
                studentGroupHours.put(subjectStudyPeriodId, periodCapacityHours);
            } else {
                subject.setHours(periodCapacityHours);
            }
        }
        for (Entry<Long, Long> entry : subjectStudyPeriodToSubject.entrySet()) {
            subjects.get(entry.getValue()).getStudentGroups().add(new LessonPlanByTeacherSubjectStudentGroupDto(
                    studentGroups.get(entry.getKey()), studentGroupHours.get(entry.getKey())));
        }
        for (LessonPlanByTeacherSubjectDto subject : subjects.values()) {
            Map<Long, Map<String, Long>> totals = subject.getCapacityTotals();
            addSubjectHours(subject.getHours(), totals);
            for (LessonPlanByTeacherSubjectStudentGroupDto studentGroup : subject.getStudentGroups()) {
                addSubjectHours(studentGroup.getHours(), totals);
            }
        }
        return subjects;
    }

    private static void addSubjectHours(Map<Long, Map<String, Long>> hours, Map<Long, Map<String, Long>> totals) {
        if (hours == null) {
            return;
        }
        for (Entry<Long, Map<String, Long>> periodEntry : hours.entrySet()) {
            Map<String, Long> periodTotals = totals.computeIfAbsent(periodEntry.getKey(), k -> new HashMap<>());
            for (Entry<String, Long> entry : periodEntry.getValue().entrySet()) {
                periodTotals.put(entry.getKey(), Long.valueOf(periodTotals.computeIfAbsent(entry.getKey(), k -> Long.valueOf(0))
                        .longValue() + entry.getValue().longValue()));
            }
        }
    }
    
    private static Map<Long, Map<String, Long>> getSubjectTotals(List<LessonPlanByTeacherSubjectDto> subjects) {
        Map<Long, Map<String, Long>> result = new HashMap<>();
        for (LessonPlanByTeacherSubjectDto subject : subjects) {
            addSubjectHours(subject.getCapacityTotals(), result);
        }
        return result;
    }

    public Map<String, ?> searchFormData(Long schoolId) {
        Map<String, Object> data = new HashMap<>();
        data.put("studyYears", autocompleteService.studyYears(schoolId));
        StudentGroupAutocompleteCommand studentGroupLookup = new StudentGroupAutocompleteCommand();
        studentGroupLookup.setHigher(Boolean.FALSE);
        data.put("studentGroups", autocompleteService.studentGroups(schoolId, studentGroupLookup));
        data.put("studentGroupMapping", studentgroupsWithLessonPlans(schoolId));
        data.put("curriculumLessonPlans", curriculumLessonPlans(schoolId));
        CurriculumVersionAutocompleteCommand curriculumVersionLookup = new CurriculumVersionAutocompleteCommand();
        curriculumVersionLookup.setHigher(Boolean.FALSE);
        curriculumVersionLookup.setValid(Boolean.TRUE);
        data.put("curriculumVersions", autocompleteService.curriculumVersions(schoolId, curriculumVersionLookup));
        return data;
    }

    /**
     * New record for insertion with default values filled
     *
     * @param user
     * @param lessonPlanId
     * @param occupationModuleId
     * @param lessonPlanModuleId
     * @return
     */
    public LessonPlanJournalDto newJournal(HoisUserDetails user, Long lessonPlanId,
            Long occupationModuleId, Long lessonPlanModuleId) {
        LessonPlanModule lessonPlanModule = EntityUtil.getOptionalOne(LessonPlanModule.class, lessonPlanModuleId, em);
        CurriculumVersionOccupationModule occupationModule;
        LessonPlan lessonPlan;
        if (lessonPlanModule == null) {
            occupationModule = em.getReference(CurriculumVersionOccupationModule.class, occupationModuleId);
            lessonPlan = em.getReference(LessonPlan.class, lessonPlanId);
        } else {
            occupationModule = lessonPlanModule.getCurriculumVersionOccupationModule();
            lessonPlan = lessonPlanModule.getLessonPlan();
        }
        UserUtil.assertIsSchoolAdmin(user, lessonPlan.getSchool());

        Journal journal = new Journal();
        // default values filled
        Set<CurriculumVersionOccupationModuleTheme> themes = occupationModule.getThemes();
        if(themes.size() == 1) {
            CurriculumVersionOccupationModuleTheme theme = themes.iterator().next();
            journal.setAssessment(theme.getAssessment());
            journal.setNameEt(theme.getNameEt());
        }

        LessonPlanJournalDto dto = lessonPlanModule == null ?
                LessonPlanJournalDto.of(journal, lessonPlan, occupationModule) :
                LessonPlanJournalDto.of(journal, lessonPlanModule);
        dto.setGroupProportion(GroupProportion.PAEVIK_GRUPI_JAOTUS_1.name());  // by default 1/1
        
        Map<Long, CurriculumVersionOccupationModuleThemeResult> dtoThemes = StreamUtil.toMap(t -> t.getId(), t -> t,
                dto.getThemes());
        autocompleteService.setThemesInOtherJournals(dtoThemes, dto.getStudentGroupId(), dto.getId());
        return dto;
    }

    public LessonPlanJournalDto getJournal(Journal journal, Long lessonPlanModuleId) {
        LessonPlanModule lessonPlanModule = em.getReference(LessonPlanModule.class, lessonPlanModuleId);
        LessonPlanJournalDto dto = LessonPlanJournalDto.of(journal, lessonPlanModule);
        
        Map<Long, CurriculumVersionOccupationModuleThemeResult> themes = StreamUtil.toMap(t -> t.getId(), t -> t,
                dto.getThemes());
        autocompleteService.setThemesInOtherJournals(themes, EntityUtil.getId(lessonPlanModule.getLessonPlan().getStudentGroup()),
                EntityUtil.getId(journal));
        return dto;
    }

    public LessonPlanCreatedJournalDto createJournal(HoisUserDetails user, LessonPlanJournalForm form) {
        LessonPlan lessonPlan = em.getReference(LessonPlan.class, form.getLessonPlan());
        UserUtil.assertIsSchoolAdmin(user, lessonPlan.getSchool());
        LessonPlanModule lessonPlanModule;
        if (form.getLessonPlanModuleId() == null) {
            lessonPlanModule = new LessonPlanModule();
            lessonPlanModule.setLessonPlan(lessonPlan);
            lessonPlanModule.setCurriculumVersionOccupationModule(
                    em.getReference(CurriculumVersionOccupationModule.class, form.getOccupationModuleId()));
            lessonPlan.getLessonPlanModules().add(lessonPlanModule);
        } else {
            lessonPlanModule = em.getReference(LessonPlanModule.class, form.getLessonPlanModuleId());
        }
        Journal journal = new Journal();
        journal.setStudyYear(lessonPlan.getStudyYear());
        journal.setSchool(lessonPlan.getSchool());
        journal.setStatus(em.getReference(Classifier.class, JournalStatus.PAEVIK_STAATUS_T.name()));
        journal = saveJournal(journal, form, user, lessonPlanModule);
        return new LessonPlanCreatedJournalDto(EntityUtil.getId(journal), EntityUtil.getId(lessonPlanModule));
    }

    private Journal saveJournal(Journal journal, LessonPlanJournalForm form, HoisUserDetails user, LessonPlanModule lessonPlanModule) {
        assertSameSchool(journal, lessonPlanModule.getLessonPlan().getSchool());
        
        EntityUtil.setUsername(user.getUsername(), em);

        List<JournalCapacityType> capacityTypes = journal.getJournalCapacityTypes();
        if(capacityTypes != null) {
            // try to delete capacity types first to catch foreign reference errors
            List<String> formJournalCapacityTypes = form.getJournalCapacityTypes();
            capacityTypes.removeIf(type -> !formJournalCapacityTypes.contains(EntityUtil.getCode(type.getCapacityType())));
            try {
                em.flush();
            } catch (PersistenceException e) {
                Throwable cause = e.getCause();
                if(cause instanceof ConstraintViolationException) {
                    throw new EntityRemoveException("lessonplan.journal.capacityTypeReferenced", cause);
                }
                throw e;
            }
        } else {
            journal.setJournalCapacityTypes(capacityTypes = new ArrayList<>());
        }
        UntisCodeUtil untisCodeUtil = new UntisCodeUtil();
        Long studyYear = EntityUtil.getId(journal.getStudyYear());
        List<LessonPlan> lessonplan = em.createQuery("select l from LessonPlan l "
    			+ "where l.school.id = ?1 "
    			+ "and l.id = ?2",LessonPlan.class)
            .setParameter(1, user.getSchoolId())
            .setParameter(2, form.getLessonPlan())
            .getResultList();
        List<StudentGroup> studentGroups = lessonplan.stream().map(p->p.getStudentGroup()).collect(Collectors.toList());
        if (StringUtils.isEmpty(form.getUntisCode())) {
        	List<Journal> journals = em.createQuery("select j from Journal j "
        			+ "where j.school.id = ?1 "
        			+ "and j.studyYear.id = ?2",Journal.class)
                .setParameter(1, user.getSchoolId())
                .setParameter(2, studyYear)
                .getResultList();
    		form.setUntisCode(untisCodeUtil.generateJournalCode(form, journals, studentGroups, journal));
        } else {
        	List<Journal> journalsWithSameCode = em.createQuery("select j from Journal j "
        			+ "where j.school.id = ?1 "
        			+ "and j.untisCode = ?2 "
        			+ "and j.studyYear.id = ?3", Journal.class)
                .setParameter(1, user.getSchoolId())
                .setParameter(2, form.getUntisCode())
                .setParameter(3, studyYear)
                .getResultList();
        	if (journalsWithSameCode.size() > 1) {
        		List<Journal> journals = em.createQuery("select j from Journal j "
            			+ "where j.school.id = ?1 "
            			+ "and j.studyYear.id = ?2",Journal.class)
                    .setParameter(1, user.getSchoolId())
                    .setParameter(2, studyYear)
                    .getResultList();
        		form.setUntisCode(untisCodeUtil.generateJournalCode(form, journals, studentGroups, journal));
        		
        	}
        }

        EntityUtil.bindToEntity(form, journal, classifierRepository, "journalCapacityTypes", "journalTeachers", "journalOccupationModuleThemes", "groups", "journalRooms");
        EntityUtil.bindEntityCollection(capacityTypes, c -> EntityUtil.getCode(c.getCapacityType()), form.getJournalCapacityTypes(), ct -> {
            JournalCapacityType jct = new JournalCapacityType();
            jct.setJournal(journal);
            jct.setCapacityType(EntityUtil.validateClassifier(em.getReference(Classifier.class, ct), MainClassCode.MAHT));
            return jct;
        });
        
        List<JournalRoom> journalRooms = journal.getJournalRooms();
        if(journalRooms == null) {
            journal.setJournalRooms(journalRooms = new ArrayList<>());
        }
        EntityUtil.bindEntityCollection(journalRooms, r -> EntityUtil.getId(r.getRoom()), form.getJournalRooms(), jr -> jr.getId(), jrf -> {
            JournalRoom jr = new JournalRoom();
            jr.setJournal(journal);
            jr.setRoom(em.getReference(Room.class, jrf.getId()));
            return jr;
        });
        
        List<JournalTeacher> teachers = journal.getJournalTeachers();
        if(teachers == null) {
            journal.setJournalTeachers(teachers = new ArrayList<>());
        }
        EntityUtil.bindEntityCollection(teachers, EntityUtil::getId, form.getJournalTeachers(), jt -> jt.getId(), jtf -> {
            JournalTeacher jt = EntityUtil.bindToEntity(jtf, new JournalTeacher());
            jt.setJournal(journal);
            jt.setTeacher(EntityUtil.getOptionalOne(Teacher.class, jtf.getTeacher(), em));
            assertSameSchool(journal, jt.getTeacher().getSchool());
            return jt;
        }, (jtf, jt) -> {
            EntityUtil.bindToEntity(jtf, jt);
        });

        List<JournalOccupationModuleTheme> oldThemes = journal.getJournalOccupationModuleThemes();
        List<JournalOccupationModuleThemeHolder> fromForm = StreamUtil.toMappedList(id -> new JournalOccupationModuleThemeHolder(journal, lessonPlanModule, id), form.getJournalOccupationModuleThemes());

        if(form.getGroups() != null && !form.getGroups().isEmpty()) {
            Set<Long> groupIds = StreamUtil.toMappedSet(LessonPlanGroupForm::getStudentGroup, form.getGroups());
            Map<Long, Long> lessonPlanIds = findLessonPlanByStudyYearAndStudentGroup(journal.getStudyYear(), groupIds);
            createMissingPlansAndAdd(groupIds, lessonPlanIds, EntityUtil.getId(journal.getStudyYear()), user);

            List<LessonPlanModule> lessonPlanModules = lessonPlanModuleRepository.findAll((root, query, cb) -> {
                List<Predicate> filters = new ArrayList<>();
                filters.add(root.get("lessonPlan").get("id").in(lessonPlanIds.values()));
                filters.add(root.get("curriculumVersionOccupationModule").get("id").in(StreamUtil.toMappedList(LessonPlanGroupForm::getCurriculumVersionOccupationModule, form.getGroups())));
                return cb.and(filters.toArray(new Predicate[filters.size()]));
            });
            for(LessonPlanGroupForm lpg : form.getGroups()) {
                Long lessonPlanId = lessonPlanIds.get(lpg.getStudentGroup());
                LessonPlan lp;
                if(lessonPlanId == null) {
                    LessonPlanCreateForm lpCreateForm = new LessonPlanCreateForm();
                    lpCreateForm.setStudentGroup(lpg.getStudentGroup());
                    lpCreateForm.setStudyYear(EntityUtil.getId(journal.getStudyYear()));
                    lp = create(user, lpCreateForm);
                } else {
                    lp = em.getReference(LessonPlan.class, lessonPlanId);
                }
                LessonPlanModule lpm = getLessonPlanModule(lessonPlanModules, EntityUtil.getId(lp), lpg.getCurriculumVersionOccupationModule());
                if(lpm == null) {
                    lpm = new LessonPlanModule();
                    lpm.setLessonPlan(lp);
                    CurriculumVersionOccupationModule module = em.getReference(CurriculumVersionOccupationModule.class, lpg.getCurriculumVersionOccupationModule());
                    lpm.setCurriculumVersionOccupationModule(module);
                    lp.getLessonPlanModules().add(lpm);
                    EntityUtil.save(lpm, em);
                }
                final LessonPlanModule lessonPlanModuleForSave = lpm;
                fromForm.addAll(StreamUtil.toMappedList(cvomt -> new JournalOccupationModuleThemeHolder(journal, lessonPlanModuleForSave, cvomt), lpg.getCurriculumVersionOccupationModuleThemes()));
            }
        }
        
        setJournalOccupationModuleThemes(journal, oldThemes, fromForm);
        
        setTimetableObjectStudentGroups(journal, form, lessonPlanModule);
        
        if (Boolean.TRUE.equals(form.getAddModuleOutcomes())) {
            List<Long> curriculumVersionOmoduleThemeIds = form.getJournalOccupationModuleThemes();
            if (form.getGroups() != null) {
                for (LessonPlanGroupForm group : form.getGroups()) {
                    curriculumVersionOmoduleThemeIds.addAll(group.getCurriculumVersionOccupationModuleThemes());
                }
            }
            addJournalOutcomeEntries(journal, curriculumVersionOmoduleThemeIds);
        } else if (journal.getId() != null) {
            removeJournalOutcomeEntries(journal);
        }
        
        return EntityUtil.save(journal, em);
    }
    
    public Journal saveJournal(Journal journal, LessonPlanJournalForm form, HoisUserDetails user) {
        return saveJournal(journal, form, user, em.getReference(LessonPlanModule.class, form.getLessonPlanModuleId()));
    }
    
    private void setJournalOccupationModuleThemes(Journal journal, List<JournalOccupationModuleTheme> oldThemes, List<JournalOccupationModuleThemeHolder> fromForm) {
        if (!fromForm.isEmpty()) {
            List<JournalOccupationModuleTheme> savedFormThemes = new ArrayList<>();
            
            for (JournalOccupationModuleThemeHolder jmth : fromForm) {
                JournalOccupationModuleTheme oldTheme = oldThemes.stream()
                        .filter(o -> EntityUtil.getId(o.getCurriculumVersionOccupationModuleTheme()).equals(jmth.getCvomt()) 
                                && o.getLessonPlanModule().equals(jmth.getLessonPlanModule()))
                        .findFirst().orElse(null);
                if (oldTheme != null) {
                    savedFormThemes.add(oldTheme);
                } else {
                    JournalOccupationModuleTheme jmt = new JournalOccupationModuleTheme();
                    jmt.setJournal(jmth.getJournal());
                    jmt.setLessonPlanModule(jmth.getLessonPlanModule());
                    jmt.setCurriculumVersionOccupationModuleTheme(em.getReference(CurriculumVersionOccupationModuleTheme.class, jmth.getCvomt()));
                    journal.getJournalOccupationModuleThemes().add(jmt);
                    savedFormThemes.add(jmt);
                }
            }
            oldThemes.removeIf(o -> !savedFormThemes.contains(o));
        }
    }
    
    private void setTimetableObjectStudentGroups(Journal journal, LessonPlanJournalForm form, LessonPlanModule lessonPlanModule) {
        // Remove previously connected groups from timetable objects
        Set<Long> connectedGroups = StreamUtil.toMappedSet(LessonPlanGroupForm::getStudentGroup, form.getGroups());
        connectedGroups.add(EntityUtil.getId(lessonPlanModule.getLessonPlan().getStudentGroup()));
        List<TimetableObjectStudentGroup> leftOverTimetableGroups = em.createQuery(
                "select tosg from TimetableObjectStudentGroup tosg join tosg.timetableObject to where to.journal.id = ?1 and tosg.studentGroup.id not in ?2",
                TimetableObjectStudentGroup.class)
                .setParameter(1, EntityUtil.getId(journal))
                .setParameter(2, connectedGroups)
                .getResultList();
        
        for (TimetableObjectStudentGroup group : leftOverTimetableGroups) {
            em.remove(group);
            em.flush();
        }
        
        // Add student groups to existing timetable objects
        List<TimetableObject> timetableObjects = em.createQuery("select to from TimetableObject to where to.journal.id = ?1", TimetableObject.class)
                .setParameter(1, EntityUtil.getId(journal))
                .getResultList();
        for (TimetableObject object : timetableObjects) {
            List<Long> groups = StreamUtil.toMappedList(tosg -> EntityUtil.getId(tosg.getStudentGroup()), object.getTimetableObjectStudentGroups());
            for (Long connectedGroup : connectedGroups) {
                if (!groups.contains(connectedGroup)) {
                    TimetableObjectStudentGroup tosg = new TimetableObjectStudentGroup();
                    tosg.setTimetableObject(object);
                    tosg.setStudentGroup(em.getReference(StudentGroup.class, connectedGroup));
                    object.getTimetableObjectStudentGroups().add(tosg);
                }
            }
        }
    }
    
    private void addJournalOutcomeEntries(Journal journal, List<Long> curriculumVersionOmoduleThemeIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_module_outcomes cmo "
                + "join curriculum_version_omodule_outcomes cvoo on cmo.id = cvoo.curriculum_module_outcomes_id");
        qb.requiredCriteria("cvoo.curriculum_version_omodule_theme_id in (:curriculumVersionOmoduleThemes)",
                "curriculumVersionOmoduleThemes", curriculumVersionOmoduleThemeIds);

        List<?> moduleOutcomesResult = qb.select("distinct cmo.id, cmo.outcome_et", em).getResultList();
        Map<Long, String> moduleOutcomes = StreamUtil.toMap(r -> resultAsLong(r, 0), r -> resultAsString(r, 1), moduleOutcomesResult);
        
        Map<Long, JournalEntry> oldEntries = getOldJournalEntriesFromOutcomes(journal);
        
        moduleOutcomes.forEach((id, name) -> {
            JournalEntry entry = oldEntries != null ? oldEntries.remove(id) : null;

            if (entry == null) {
                entry = new JournalEntry();
            }
            entry.setJournal(journal);
            if (name != null && name.length() > 100) {
                entry.setNameEt(name.substring(0, 99));
            } else {
                entry.setNameEt(name);
            }
            entry.setEntryType(em.getReference(Classifier.class, JournalEntryType.SISSEKANNE_O.name()));
            entry.setCurriculumModuleOutcomes(em.getReference(CurriculumModuleOutcome.class, id));
            journal.getJournalEntries().add(entry);
        });
        
        // entries left over from previously connected group themes
        if (oldEntries != null && !oldEntries.isEmpty()) {
            removePreviouslyConnectedGroupThemeOutcomeEntries(journal, oldEntries);
        }
        
    }
    
    private void removeJournalOutcomeEntries(Journal journal) {
        Query q = em.createNativeQuery("select jes.id from journal_entry_student jes "
                + "inner join journal_entry je on je.id = jes.journal_entry_id "
                + "where jes.journal_entry_id in (select je.id from journal_entry je where je.entry_type_code = 'SISSEKANNE_O' and je.journal_id = ?1)");
        q.setParameter(1, journal.getId());
       
        List<?> data = q.getResultList();
        if (!data.isEmpty()) {
            throw new ValidationFailedException("lessonplan.journal.outcomesReferenced");
        }
        
        Map<Long, JournalEntry> oldEntries = getOldJournalEntriesFromOutcomes(journal);
        oldEntries.forEach((id, entry) -> {
            journal.getJournalEntries().remove(entry);
        });
    }
    
    private Map<Long, JournalEntry> getOldJournalEntriesFromOutcomes(Journal journal) {
        if (journal.getId() != null) {
            List<JournalEntry> data = em.createQuery("select je from JournalEntry je where je.journal = ?1 and je.entryType.code = ?2", JournalEntry.class)
                    .setParameter(1, journal).setParameter(2, JournalEntryType.SISSEKANNE_O.name()).getResultList();
            return StreamUtil.toMap(d -> d.getCurriculumModuleOutcomes().getId(), data);
        }
        return null;
    }
    
    private void removePreviouslyConnectedGroupThemeOutcomeEntries(Journal journal, Map<Long, JournalEntry> oldEntries) {
        Query q = em.createNativeQuery("select je.id from journal_entry je "
                + "join journal_entry_student jes on je.id=jes.journal_entry_id "
                + "where je.entry_type_code = 'SISSEKANNE_O' and je.journal_id = ?1 and jes.journal_entry_id in (?2)");
        q.setParameter(1, journal.getId());
        q.setParameter(2, StreamUtil.toMappedList(e -> e.getId(), oldEntries.values()));
        
        List<?> data = q.getResultList();
        Set<Long> referencedEntries = StreamUtil.toMappedSet(d -> resultAsLong(d, 0), data);

        oldEntries.forEach((id, entry) -> {
            if (!referencedEntries.contains(EntityUtil.getId(entry))) {
                journal.getJournalEntries().remove(entry);
            }
        });
    }

    private void createMissingPlansAndAdd(Collection<Long> newGroupIds, Map<Long, Long> lessonPlanIds, Long studyYearId, HoisUserDetails user) {
        newGroupIds.removeAll(lessonPlanIds.keySet());
        // add lesson plans for student groups not already present
        for(Long missingGroupId : newGroupIds) {
            LessonPlanCreateForm lpCreateForm = new LessonPlanCreateForm();
            lpCreateForm.setStudentGroup(missingGroupId);
            lpCreateForm.setStudyYear(studyYearId);
            LessonPlan lp = create(user, lpCreateForm);
            lessonPlanIds.put(missingGroupId, EntityUtil.getId(lp));
        }
    }

    private static LessonPlanModule getLessonPlanModule(List<LessonPlanModule> lessonPlanModules, Long lessonPlan, Long cvom) {
        return lessonPlanModules.stream().filter(p ->
            EntityUtil.getId(p.getLessonPlan()).equals(lessonPlan) &&
            EntityUtil.getId(p.getCurriculumVersionOccupationModule()).equals(cvom)).findFirst().orElse(null);
    }

    public void deleteJournal(HoisUserDetails user, Journal journal) {
        if (!journal.getJournalStudents().isEmpty()) {
            throw new ValidationFailedException("lessonplan.journal.hasStudents");
        }
        
        //  remove timetable objects and groups that do not have any connecting events
        Query objectsQuery = em.createNativeQuery("select tto.id from timetable_object tto where tto.journal_id=?1 " + 
                "and tto.id not in (select tto.id from timetable_object tto join timetable_event te on te.timetable_object_id=tto.id " +
                "where tto.journal_id=?1)");
        objectsQuery.setParameter(1, EntityUtil.getId(journal));
        List<?> objectsData = objectsQuery.getResultList();
        
        if (!objectsData.isEmpty()) {
            List<Long> objects = StreamUtil.toMappedList(r -> resultAsLong(r, 0), objectsData);
            List<TimetableObjectStudentGroup> groups = em
                    .createQuery("select tosg from TimetableObjectStudentGroup tosg where tosg.timetableObject.id in (?1)", TimetableObjectStudentGroup.class)
                    .setParameter(1, objects).getResultList();
            
            for (TimetableObjectStudentGroup group: groups) {
                EntityUtil.deleteEntity(group, em);
            }
            for (Long objectId : objects) {
                EntityUtil.deleteEntity(em.getReference(TimetableObject.class, objectId), em);
            }
        }
        
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(journal, em);
    }

    private Map<Long, Long> findLessonPlanByStudyYearAndStudentGroup(StudyYear studyYear, Collection<Long> studentGroup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from lesson_plan lp");

        qb.requiredCriteria("lp.study_year_id = :studyYear", "studyYear", EntityUtil.getId(studyYear));
        qb.requiredCriteria("lp.student_group_id in (:studentGroup)", "studentGroup", studentGroup);

        List<?> result = qb.select("lp.student_group_id, lp.id", em).getResultList();
        return StreamUtil.toMap(r -> resultAsLong(r, 0),  r -> resultAsLong(r, 1), result);
    }

    private Map<Long, List<Long>> studentgroupsWithLessonPlans(Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from study_year sy inner join lesson_plan lp on sy.id = lp.study_year_id inner join student_group sg on lp.student_group_id = sg.id");

        qb.requiredCriteria("sg.school_id = :schoolId and sy.school_id = :schoolId", "schoolId", schoolId);

        List<?> data = qb.select("sy.id, sg.id as sg_id", em).getResultList();
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> resultAsLong(r, 1), Collectors.toList())));
    }

    private Map<Long, List<Object>> curriculumLessonPlans(Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from lesson_plan lp join curriculum_version cv on lp.curriculum_version_id = cv.id "
                        + "join curriculum c on cv.curriculum_id = c.id "
                        + "join student_group sg on lp.student_group_id = sg.id "
                        + "join study_year sy on lp.study_year_id = sy.id "
                        + "join classifier cl on sy.year_code = cl.code");

        qb.requiredCriteria("sg.school_id = :schoolId and sy.school_id = :schoolId", "schoolId", schoolId);
        qb.sort("sy.end_date desc, sg.code");

        List<?> data = qb.select(
                "c.id as curriculum_id, sy.id as study_year_id, sy.end_date, lp.id as lesson_plan_id, cl.name_et || ' ' || sg.code as name",
                em).getResultList();
        return data.stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), LinkedHashMap::new, Collectors.mapping(r -> {
                    Map<String, Object> curriculumLessonplan = new HashMap<>();
                    curriculumLessonplan.put("studyYear", resultAsLong(r, 1));
                    curriculumLessonplan.put("studyYearEndDate", resultAsLocalDate(r, 2));
                    curriculumLessonplan.put("lessonplan", resultAsLong(r, 3));
                    curriculumLessonplan.put("name", resultAsString(r, 4));
                    return curriculumLessonplan;
                }, Collectors.toList())));
    }

    private Map<Long, Long> scheduleLegends(LessonPlan lessonPlan) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from lesson_plan lp inner join study_year sy on lp.study_year_id = sy.id"
                + " inner join study_period sp on sy.id = sp.study_year_id"
                + " inner join study_year_schedule sys on sp.id = sys.study_period_id");

        qb.requiredCriteria("lp.id = :lessonPlanId", "lessonPlanId", lessonPlan.getId());
        qb.requiredCriteria("sys.school_id = :schoolId", "schoolId", EntityUtil.getId(lessonPlan.getSchool()));
        qb.requiredCriteria("sys.student_group_id = :studentGroupId", "studentGroupId", EntityUtil.getId(lessonPlan.getStudentGroup()));

        List<?> data = qb.select("sys.week_nr, sys.study_year_schedule_legend_id", em).getResultList();
        return data.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> resultAsLong(r, 1), (o, n) -> o));
    }

    private static void assertSameSchool(Journal journal, School school) {
        if(school != null && !EntityUtil.getId(journal.getSchool()).equals(EntityUtil.getId(school))) {
            throw new AssertionFailedException("School mismatch");
        }
    }
    
    public byte[] lessonplanAsExcel(LessonPlan lessonPlan) {
        LessonPlanDto dto = get(lessonPlan);
        List<LessonPlanXlsStudyPeriodDto> studyPeriods = lessonplanExcelStudyPeriods(dto.getStudyPeriods());
        List<LessonPlanXlsModuleDto> modules = lessonplanExcelModules(dto);
        LessonPlanXlsTotalsDto totals = lessonplanExcelTotals(modules, dto.getWeekNrs(), true);

        Map<String, Object> data = new HashMap<>();
        data.put("capacities", dto.getLessonPlanCapacities());

        data.put("studyYearCode", dto.getStudyYearCode());
        data.put("studentGroupCode", dto.getStudentGroupCode());
        data.put("courseNr", dto.getCourseNr());
        data.put("curriculumCode", dto.getCurriculumCode());
        data.put("curriculumVersion", dto.getCurriculumVersion());
        data.put("studyPeriodYears", Integer.valueOf(dto.getStudyPeriod().intValue() / 12));
        data.put("studyPeriodMonths", Integer.valueOf(dto.getStudyPeriod().intValue() % 12));

        data.put("studyPeriods", studyPeriods);
        data.put("weekNrs", dto.getWeekNrs());
        data.put("modules", modules);
        data.put("totals", totals);
        
        return xlsService.generate("lessonplan.xls", data);
    }
    
    private static List<LessonPlanXlsStudyPeriodDto> lessonplanExcelStudyPeriods(List<StudyPeriodDto> inputPeriods) {
        List<LessonPlanXlsStudyPeriodDto> studyPeriods = new ArrayList<>();
        for (StudyPeriodDto sp : inputPeriods) {
            LessonPlanXlsStudyPeriodDto studyPeriod = new LessonPlanXlsStudyPeriodDto();
            studyPeriod.setId(sp.getId());
            studyPeriod.setNameEt(sp.getNameEt());
            studyPeriod.setNameEn(sp.getNameEn());
             
            List<Short> colspanColumns = sp.getWeekNrs().size() > 0
                    ? sp.getWeekNrs().subList(0, sp.getWeekNrs().size() - 1)
                    : sp.getWeekNrs();
            studyPeriod.setColspanColumns(colspanColumns);
            studyPeriods.add(studyPeriod);
        }
        return studyPeriods;
    }
    
    private List<LessonPlanXlsModuleDto> lessonplanExcelModules(LessonPlanDto dto) {
        List<LessonPlanXlsModuleDto> modules = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<LessonPlanModuleDto> dtoModules = (List<LessonPlanModuleDto>) dto.getModules();
        for (LessonPlanModuleDto m : dtoModules) {
            LessonPlanXlsModuleDto module = new LessonPlanXlsModuleDto();
            module.setNameEt(m.getNameEt());
            module.setNameEn(m.getNameEn());
            
            AutocompleteResult teacher = m.getTeacher() != null
                    ? AutocompleteResult.of(em.getReference(Teacher.class, m.getTeacher().getId()))
                    : null;
            module.setTeacher(teacher != null ? teacher.getNameEn() : null);
        
            List<LessonPlanXlsJournalDto> journals =  lessonplanExcelJournals(m, dto.getWeekNrs());
            module.setJournals(journals);
            setLessonplanExcelModuleTotals(module, journals, dto.getWeekNrs(), true);
            modules.add(module);
        }
        return modules;
    }
    
    private static List<LessonPlanXlsJournalDto> lessonplanExcelJournals(LessonPlanModuleDto module, List<Short> weekNrs) {
        List<LessonPlanXlsJournalDto> journals = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<LessonPlanModuleJournalDto> dtoJournals = (List<LessonPlanModuleJournalDto>) module.getJournals();
        for (LessonPlanModuleJournalDto j : dtoJournals) {
            LessonPlanXlsJournalDto journal = new LessonPlanXlsJournalDto();
            journal.setNameEt(j.getNameEt());
            journal.setTeachers(StreamUtil.toMappedList(t -> t.getTeacher().getNameEt(), j.getTeachers()));
            journal.setGroupProportion(j.getGroupProportion());
            journal.setHours(sortCapacities(j.getHours()));
            
            List<Short> totalHours = grandTotals(j.getHours(), weekNrs);
            journal.setTotalHours(totalHours);
            journals.add(journal);
        }
        return journals;
    }
    
    private void setLessonplanExcelModuleTotals(LessonPlanXlsModuleDto module, List<LessonPlanXlsJournalDto> journals,
            List<Short> weekNrs, boolean useGroupProportion) {
        Map<String, List<Double>> hours = new HashMap<>();
        journals.forEach(journal -> {
            double groupProportion = useGroupProportion ? 1 / Double
                    .valueOf(em.getReference(Classifier.class, journal.getGroupProportion()).getValue()).doubleValue()
                    : 1;
            for (String capacity : journal.getHours().keySet()) {
                if (!hours.containsKey(capacity)) {
                    List<Double> weekHours = new ArrayList<>();
                    for (Short hour : journal.getHours().get(capacity)) {
                        weekHours.add(hour != null ? Double.valueOf(hour.shortValue() * groupProportion) : Double.valueOf(0));
                    }
                    hours.put(capacity, weekHours);
                } else {
                    List<Double> capacityHours = hours.get(capacity);
                    List<Short> journalCapacityHours = journal.getHours().get(capacity);
                    for (int i = 0; i < capacityHours.size(); i++) {
                        double weekHours = capacityHours.get(i) != null ? capacityHours.get(i).doubleValue() : 0;
                        double journalWeekHours = journalCapacityHours.get(i) != null ? journalCapacityHours.get(i).doubleValue() * groupProportion : 0;
                        capacityHours.set(i, Double.valueOf(weekHours + journalWeekHours));
                    }
                }
            }
        });
        module.setHours(sortTotalHourCapacities(hours));
        
        List<Double> totalHours = grandProportionTotals(module.getHours(), weekNrs);
        module.setTotalHours(totalHours);
    }
    
    private LessonPlanXlsTotalsDto lessonplanExcelTotals(List<LessonPlanXlsModuleDto> modules, List<Short> weekNrs, boolean useGroupProportion) {
        LessonPlanXlsTotalsDto totals = new LessonPlanXlsTotalsDto();
        
        Map<String, List<Double>> hours = new HashMap<>();
        modules.forEach(module -> module.getJournals().forEach(journal -> {
            double groupProportion = useGroupProportion ? 1 / Double
                    .valueOf(em.getReference(Classifier.class, journal.getGroupProportion()).getValue()).doubleValue()
                    : 1;
            for (String capacity : journal.getHours().keySet()) {
                if (!hours.containsKey(capacity)) {
                    List<Double> weekHours = new ArrayList<>();
                    for (Short hour : journal.getHours().get(capacity)) {
                        weekHours.add(hour != null ? Double.valueOf(hour.shortValue() * groupProportion) : Double.valueOf(0));
                    }
                    hours.put(capacity, weekHours);
                } else {
                    List<Double> capacityHours = hours.get(capacity);
                    List<Short> journalCapacityHours = journal.getHours().get(capacity);
                    for (int i = 0; i < capacityHours.size(); i++) {
                        double weekHours = capacityHours.get(i) != null ? capacityHours.get(i).doubleValue() : 0;
                        double journalWeekHours = journalCapacityHours.get(i) != null ? journalCapacityHours.get(i).doubleValue() * groupProportion : 0;
                        capacityHours.set(i, Double.valueOf(weekHours + journalWeekHours));
                    }
                }
            }
        }));
        totals.setHours(sortTotalHourCapacities(hours));
        
        List<Double> totalHours = grandProportionTotals(totals.getHours(), weekNrs);
        totals.setTotalHours(totalHours);
        
        return totals;
    }
    
    private static List<Short> grandTotals(Map<String, List<Short>> capacityHours, List<Short> weekNrs) {
        List<Short> totalHours = new ArrayList<>();
        for (int i = 0; i < weekNrs.size(); i++) {
            totalHours.add(Short.valueOf((short) 0));
        }
        
        for (String capacity : capacityHours.keySet()) {
            List<Short> hours = capacityHours.get(capacity);
            for (int i = 0; i < hours.size() ; i++) {
                Short weekHours = hours.get(i) != null ? hours.get(i) : Short.valueOf((short) 0);
                totalHours.set(i, Short.valueOf((short) (totalHours.get(i).shortValue() + weekHours.shortValue())));
            }
        }
        return totalHours;
    }
    
    private static List<Double> grandProportionTotals(Map<String, List<Double>> capacityHours, List<Short> weekNrs) {
        List<Double> totalHours = new ArrayList<>();
        for (int i = 0; i < weekNrs.size(); i++) {
            totalHours.add(Double.valueOf(0));
        }
        
        for (String capacity : capacityHours.keySet()) {
            List<Double> hours = capacityHours.get(capacity);
            for (int i = 0; i < hours.size() ; i++) {
                double weekHours = hours.get(i) != null ? hours.get(i).doubleValue() : 0;
                totalHours.set(i, Double.valueOf(totalHours.get(i).doubleValue() + weekHours));
            }
        }
        return totalHours;
    }
    
    private static Map<String, List<Short>> sortCapacities(Map<String, List<Short>> hours) {
        Map<String, List<Short>> sorted = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (String capacity : hours.keySet()) {
            List<Short> capacityHours = new ArrayList<>();
            for (Short hour : hours.get(capacity) ) {
                capacityHours.add(hour != null ? Short.valueOf(hour.shortValue()) : null);
            }
            sorted.put(capacity, capacityHours);
        }
        return sorted;
    }
    
    private static Map<String, List<Double>> sortTotalHourCapacities(Map<String, List<Double>> hours) {
        Map<String, List<Double>> sorted = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (String capacity : hours.keySet()) {
            sorted.put(capacity, hours.get(capacity));
        }
        return sorted;
    }

    public byte[] lessonplanByTeacherAsExcel(HoisUserDetails user, Teacher teacher, StudyYear studyYear) {
        LessonPlanByTeacherDto dto = getByTeacher(user, teacher, studyYear);
        List<LessonPlanXlsStudyPeriodDto> studyPeriods = lessonplanExcelStudyPeriods(dto.getStudyPeriods());
        List<LessonPlanXlsJournalDto> journals = lessonplanExcelJournals(dto);

        LessonPlanXlsModuleDto totalModule = new LessonPlanXlsModuleDto();
        totalModule.setJournals(journals);
        LessonPlanXlsTotalsDto totals = lessonplanExcelTotals(Collections.singletonList(totalModule), dto.getWeekNrs(), false);
        
        Map<String, Object> data = new HashMap<>();
        data.put("studyYearCode", dto.getStudyYearCode());
        data.put("teacherName", dto.getTeacherName());
        data.put("capacities", dto.getLessonPlanCapacities());
        data.put("studyPeriods", studyPeriods);
        data.put("weekNrs", dto.getWeekNrs());
        data.put("journals", journals);
        data.put("totals", totals);
        data.put("subjects", dto.getSubjects());
        data.put("subjectTotals", dto.getSubjectTotals());
        
        return xlsService.generate("lessonplanbyteacher.xls", data);
    }

    private static List<LessonPlanXlsJournalDto> lessonplanExcelJournals(LessonPlanByTeacherDto dto) {
        List<LessonPlanXlsJournalDto> journals = new ArrayList<>();
        for (LessonPlanModuleJournalDto j : dto.getJournals()) {
            LessonPlanXlsJournalDto journal = new LessonPlanXlsJournalDto();
            journal.setNameEt(j.getNameEt());
            journal.setTeachers(StreamUtil.toMappedList(t -> t.getTeacher().getNameEt(), j.getTeachers()));
            journal.setStudentGroups(j.getStudentGroups().stream().collect(Collectors.joining(" ")));
            journal.setGroupProportion(j.getGroupProportion());
            journal.setHours(sortCapacities(j.getHours()));
            
            List<Short> totalHours = grandTotals(j.getHours(), dto.getWeekNrs());
            journal.setTotalHours(totalHours);
            journals.add(journal);
        }
        return journals;
    }

    private static class JournalOccupationModuleThemeHolder {
        private Journal journal;
        private LessonPlanModule lessonPlanModule;
        private Long cvomt;

        public JournalOccupationModuleThemeHolder(Journal journal, LessonPlanModule lpm, Long cvomt) {
            this.journal = journal;
            this.lessonPlanModule = lpm;
            this.cvomt = cvomt;
        }

        public Journal getJournal() {
            return journal;
        }

        public LessonPlanModule getLessonPlanModule() {
            return lessonPlanModule;
        }

        public Long getCvomt() {
            return cvomt;
        }
    }
}
