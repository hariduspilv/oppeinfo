package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
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

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
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
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.SchoolCapacityTypeCommand;
import ee.hitsa.ois.web.commandobject.StudentGroupAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanCreateForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm.LessonPlanModuleForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm.LessonPlanModuleJournalForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanJournalForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanJournalForm.LessonPlanGroupForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanSearchTeacherCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
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
        setLessonPlanTeachers(lessonPlan, dto);
        setLessonPlanCapacities(lessonPlan, dto);
        return dto;
    }
    
    public void setLessonPlanTeachers(LessonPlan lessonPlan, LessonPlanDto dto) {
        List<LessonPlanTeacherDto> teacherDtos = new ArrayList<>();
        
        List<?> teachers = em.createNativeQuery("select t.id from lesson_plan lp "
                    + "join lesson_plan_module lpm on lp.id = lpm.lesson_plan_id "
                    + "join curriculum_version_omodule cvo on lpm.curriculum_version_omodule_id = cvo.id "
                    + "join curriculum_version_omodule_theme cvot on cvo.id = cvot.curriculum_version_omodule_id "
                    + "join journal_omodule_theme jot on cvot.id = jot.curriculum_version_omodule_theme_id "
                    + "join journal_teacher jt on jot.journal_id = jt.journal_id "
                    + "join teacher t on jt.teacher_id = t.id " 
                    + "where lp.id = ?1")
                .setParameter(1, dto.getId())
                .getResultList();
        
        if (!teachers.isEmpty()) {
            List<?> teacherScheduleLoads =  em.createNativeQuery("select t.id, t.schedule_load, t.is_study_period_schedule_load, sum(jc.hours) from teacher t " +
                    "join journal_teacher jt on t.id = jt.teacher_id " +
                    "join journal j on jt.journal_id = j.id " +
                    "join journal_capacity jc on j.id = jc.journal_id " +
                    "where t.id in (:teacherIds) and j.study_year_id = :studyYearId " +
                    "group by t.id")
                .setParameter("teacherIds", StreamUtil.toMappedList(r -> resultAsLong(r, 0), teachers))
                .setParameter("studyYearId", EntityUtil.getId(lessonPlan.getStudyYear()))
                .getResultList();
            
            teacherDtos = StreamUtil.toMappedList(r -> new LessonPlanTeacherDto(resultAsLong(r, 0), resultAsShort(r, 1),
                    resultAsBoolean(r, 2), resultAsLong(r, 3)), teacherScheduleLoads);
        }
        dto.setTeachers(teacherDtos);
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
        // XXX for higher this is optional in student group
        CurriculumVersion curriculumVersion = studentGroup.getCurriculumVersion();
        lessonPlan.setCurriculumVersion(curriculumVersion);
        return EntityUtil.save(lessonPlan, em);
    }

    public LessonPlan save(LessonPlan lessonPlan, LessonPlanForm form) {
        EntityUtil.bindToEntity(form, lessonPlan);

        Map<Long, LessonPlanModule> modules = StreamUtil.toMap(LessonPlanModule::getId, lessonPlan.getLessonPlanModules());
        List<? extends LessonPlanModuleForm> formModules = form.getModules();
        if(formModules != null) {
            LessonPlanCapacityMapper capacityMapper = LessonPlanUtil.capacityMapper(lessonPlan.getStudyYear());

            for(LessonPlanModuleForm formModule : formModules) {
                if (formModule.getId() == null) {
                    continue;
                }
                LessonPlanModule lpm = modules.remove(formModule.getId());
                if(lpm == null) {
                    throw new AssertionFailedException("Unknown lessonplan module");
                }
                EntityUtil.bindToEntity(formModule, lpm);
                lpm.setTeacher(EntityUtil.getOptionalOne(Teacher.class, formModule.getTeacher(), em));
                // store journal capacities
                List<? extends LessonPlanModuleJournalForm> formJournals = formModule.getJournals();
                if(formJournals != null) {
                    for(LessonPlanModuleJournalForm formJournal : formJournals) {
                        Journal journal = em.getReference(Journal.class, formJournal.getId());
                        // TODO better checks - is journal related to this module
                        assertSameSchool(journal, lessonPlan.getSchool());
                        capacityMapper.mapInput(journal, formJournal.getHours());
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
        qb.requiredCriteria("j.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        if (user.isTeacher()) {
            qb.filter("j.id in (select jot.journal_id"
                    + " from journal_omodule_theme jot"
                    + " join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                    + " join lesson_plan lp on lp.id = lpm.lesson_plan_id"
                    + " where lp.is_usable = true)");
        }
        String journalQuery = qb.querySql("sum(jc.hours)", false);
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
                + ", coalesce((" + journalQuery + "), 0) as j_hours"
                + ", coalesce((" + subjectQuery + "), 0) as ssp_hours", false);
        queryParameters.putAll(qb.queryParameters());
        
        qb = new JpaNativeQueryBuilder("from (" + hoursQuery + ") hours"
                + " join person p on hours.person_id = p.id ").sort(pageable);
        qb.filter("(hours.j_hours + hours.ssp_hours) > 0");
        

        return JpaQueryUtil.pagingResult(qb, "hours.id, p.firstname, p.lastname, hours.j_hours + hours.ssp_hours as total_hours", 
                queryParameters, em, pageable).map(r -> {
            return new LessonPlanSearchTeacherDto(resultAsLong(r, 0), PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)), resultAsLong(r, 3), criteria.getStudyYear());
        });
    }

    public LessonPlanByTeacherDto getByTeacher(Teacher teacher, StudyYear studyYear) {
        Long studyYearId = EntityUtil.getId(studyYear);
        Long teacherId = EntityUtil.getId(teacher);

        List<Journal> journals = getTeacherJournals(teacherId, studyYearId);

        List<LessonPlanByTeacherSubjectDto> subjects = getTeacherSubjects(teacherId, studyYearId).values().stream()
                .sorted(Comparator.comparing(LessonPlanByTeacherSubjectDto::getNameEt, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        
        LessonPlanByTeacherDto dto = new LessonPlanByTeacherDto(studyYear, journals, subjects, getSubjectTotals(subjects), teacher);
        setTeacherLessonPlanCapacities(dto, EntityUtil.getId(teacher.getSchool()), journals, subjects);
        return dto;
    }

    private List<Journal> getTeacherJournals(Long teacherId, Long studyYearId) {
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
        return dto;
    }

    public LessonPlanJournalDto getJournal(Journal journal, Long lessonPlanModuleId) {
        LessonPlanModule lessonPlanModule = em.getReference(LessonPlanModule.class, lessonPlanModuleId);
        return LessonPlanJournalDto.of(journal, lessonPlanModule);
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
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> resultAsLong(r, 1), Collectors.toList())));
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

    public byte[] lessonplanByTeacherAsExcel(Teacher teacher, StudyYear studyYear) {
        LessonPlanByTeacherDto dto = getByTeacher(teacher, studyYear);
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
