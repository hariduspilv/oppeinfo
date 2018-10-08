package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.base.Objects;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalCapacity;
import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.domain.timetable.JournalEntryCapacityType;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalEntryStudentHistory;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.enums.Absence;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.message.StudentRemarkCreated;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.JournalRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JournalUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.validation.JournalEntryValidation;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.SchoolCapacityTypeCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalEndDateCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryStudentForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalReviewForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalStudentsCommand;
import ee.hitsa.ois.web.commandobject.timetable.OtherStudentsSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;
import ee.hitsa.ois.web.dto.studymaterial.CapacityHoursDto;
import ee.hitsa.ois.web.dto.studymaterial.JournalLessonHoursDto;
import ee.hitsa.ois.web.dto.timetable.JournalDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryByDateDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryLessonInfoDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryStudentHistoryDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryStudentResultDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryTableDto;
import ee.hitsa.ois.web.dto.timetable.JournalSearchDto;
import ee.hitsa.ois.web.dto.timetable.JournalStudentDto;
import ee.hitsa.ois.web.dto.timetable.JournalXlsDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalAbsenceDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalEntryDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalEntryPreviousResultDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalResultDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalStudyDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalStudyListDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalTaskDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalTaskListDto;

@Transactional
@Service
public class JournalService {

    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private Validator validator;
    @Autowired
    private XlsService xlsService;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private AutomaticMessageService automaticMessageService;
    
    private static final List<String> journalAbsenceCodes = EnumUtil.toNameList(Absence.PUUDUMINE_P, Absence.PUUDUMINE_H);
    private static final List<String> testEntryTypeCodes = EnumUtil.toNameList(JournalEntryType.SISSEKANNE_H, JournalEntryType.SISSEKANNE_L,
            JournalEntryType.SISSEKANNE_E, JournalEntryType.SISSEKANNE_I, JournalEntryType.SISSEKANNE_R, JournalEntryType.SISSEKANNE_P);
    
    private static final String JOURNAL_LIST_FROM = "from journal j " +
            "join journal_omodule_theme jot on j.id=jot.journal_id " +
            "join lesson_plan_module lpm on jot.lesson_plan_module_id=lpm.id " +
            "join lesson_plan lp on lpm.lesson_plan_id=lp.id " +
            "join student_group sg on lp.student_group_id=sg.id " +
            "join curriculum_version_omodule_theme cvot on jot.curriculum_version_omodule_theme_id=cvot.id " +
            "join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id=cvo.id " +
            "join curriculum_module cm on cvo.curriculum_module_id=cm.id " +
            "join curriculum_version cv on cvo.curriculum_version_id=cv.id " +
            "join curriculum c on cm.curriculum_id=c.id " +
            "join classifier cl on cm.module_code=cl.code " +
            "left join journal_teacher jt on j.id=jt.journal_id " +
            "left join teacher t on jt.teacher_id=t.id " +
            "left join person p on t.person_id=p.id";
    
    private static final String JOURNAL_LIST_SELECT = "j.id, string_agg(distinct sg.code, ', ') as student_groups, j.name_et, " +
            "string_agg(distinct p.firstname || ' ' || p.lastname, ', ') as teachers, " +
            "string_agg(distinct cm.name_et || ' - ' || cl.name_et || ' (' || cv.code || ')', ', ') as modules_et, " +
            "string_agg(distinct cm.name_en || ' - ' || cl.name_en || ' (' || cv.code || ')', ', ') as modules_en, " +
            "j.status_code, string_agg(distinct c.code, ', '), j.is_review_ok, j.review_date";
    
    public Page<JournalSearchDto> search(HoisUserDetails user, JournalSearchCommand command, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(JOURNAL_LIST_FROM).sort(pageable);
        
        qb.requiredCriteria("j.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("j.study_year_id = :studyYear", "studyYear", command.getStudyYear());
        
        
        if (user.isTeacher()) {
            command.setTeacher(user.getTeacherId());
        }
        if (command.getTeacher() != null) {
            qb.filter("j.id in (select j.id from journal j " + 
                    "join journal_omodule_theme jot on j.id=jot.journal_id " + 
                    "join lesson_plan_module lpm on jot.lesson_plan_module_id=lpm.id " + 
                    "join lesson_plan lp on lpm.lesson_plan_id=lp.id " + 
                    "join student_group sg on lp.student_group_id=sg.id " +
                    "left join journal_teacher jt on j.id=jt.journal_id " + 
                    "where lpm.teacher_id=" + command.getTeacher() + " or sg.teacher_id=" + command.getTeacher() +
                    " or jt.teacher_id=" + command.getTeacher() + ")");
        }
        
        qb.optionalContains("j.name_et", "name", command.getJournalName());
        
        if (command.getStudentGroup() != null) {
            qb.filter("j.id in (select j.id from journal j " +
                    "join journal_omodule_theme jot on j.id=jot.journal_id " + 
                    "join lesson_plan_module lpm on jot.lesson_plan_module_id=lpm.id " + 
                    "join lesson_plan lp on lpm.lesson_plan_id=lp.id " + 
                    "join student_group sg on lp.student_group_id=sg.id " + 
                    "where sg.id=" + command.getStudentGroup() + ")");
        }
        
        if (command.getModule() != null) {
            String modules = command.getModule().stream().map(m -> String.valueOf(m)).collect(Collectors.joining(","));
            qb.filter("j.id in (select j.id from journal j " +
            "join journal_omodule_theme jot on j.id=jot.journal_id " +
            "join curriculum_version_omodule_theme cvot on jot.curriculum_version_omodule_theme_id=cvot.id " +
            "join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id=cvo.id " +
            "where cvo.id in (" + modules + "))"); 
        }
        
        qb.optionalCriteria("j.status_code = :status", "status", command.getStatus());
        
        qb.groupBy("j.id");
        
        return JpaQueryUtil.pagingResult(qb, JOURNAL_LIST_SELECT, em, pageable).map(r -> {
            JournalSearchDto dto = new JournalSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setStudentGroups(resultAsString(r, 1));
            dto.setNameEt(resultAsString(r, 2));
            dto.setTeachers(resultAsString(r, 3));
            dto.setModules(new AutocompleteResult(null, resultAsString(r, 4), resultAsString(r, 5)));
            dto.setStatus(resultAsString(r, 6));
            dto.setCurriculums(resultAsString(r, 7));
            dto.setIsReviewOk(resultAsBoolean(r, 8));
            dto.setReviewDate(resultAsLocalDate(r, 9));
            
            Journal journal = em.getReference(Journal.class, resultAsLong(r, 0));
            dto.setPlannedHours(Integer.valueOf(journal.getJournalCapacities().stream().mapToInt(it -> it.getHours() == null ? 0 : it.getHours().intValue()).sum()));
            dto.setUsedHours(Integer.valueOf(journal.getJournalEntries().stream().mapToInt(it -> it.getLessons() == null ? 0 : it.getLessons().intValue()).sum()));
            dto.setCanEdit(Boolean.valueOf(JournalUtil.hasPermissionToChange(user, journal)));
            return dto;
        });
    }
    
    public JournalDto get(HoisUserDetails user, Journal journal) {
        JournalDto dto = JournalDto.of(journal);
        dto.setCanBeConfirmed(Boolean.valueOf(JournalUtil.canConfirm(user, journal)));
        dto.setCanBeUnconfirmed(Boolean.valueOf(JournalUtil.canUnconfirm(user, journal)));
        dto.setCanEdit(Boolean.valueOf(JournalUtil.hasPermissionToChange(user, journal)));
        dto.setCanReview(Boolean.valueOf(JournalUtil.hasPermissionToReview(user, journal)));
        dto.setLessonHours(usedHours(journal));
        return dto;
    }

    public Journal confirm(Journal journal) {
        journal.setStatus(em.getReference(Classifier.class, JournalStatus.PAEVIK_STAATUS_K.name()));
        return EntityUtil.save(journal, em);
    }

    public Journal unconfirm(Journal journal) {
        journal.setStatus(em.getReference(Classifier.class, JournalStatus.PAEVIK_STAATUS_T.name()));
        return EntityUtil.save(journal, em);
    }

    public Page<JournalStudentDto> otherStudents(HoisUserDetails user, Long journalId, OtherStudentsSearchCommand command,
            Pageable pageable) {
        return studentRepository.findAll((root, query, cb) -> {
            root.join("person", JoinType.INNER);
            root.join("studentGroup", JoinType.LEFT);
            root.join("curriculumVersion", JoinType.INNER).join("curriculum", JoinType.INNER);

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), user.getSchoolId()));
            filters.add(cb.or(cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_A.name()),
                    cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_O.name()),
                    cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_V.name())));
            filters.add(cb.equal(root.get("curriculumVersion").get("curriculum").get("higher"), Boolean.FALSE));

            if (StringUtils.hasText(command.getStudentName())) {
                List<Predicate> name = new ArrayList<>();
                propertyContains(() -> root.get("person").get("firstname"), cb, command.getStudentName(), name::add);
                propertyContains(() -> root.get("person").get("lastname"), cb, command.getStudentName(), name::add);
                name.add(cb.like(
                        cb.concat(cb.upper(root.get("person").get("firstname")),
                                cb.concat(" ", cb.upper(root.get("person").get("lastname")))),
                        JpaQueryUtil.toContains(command.getStudentName())));
                if (!name.isEmpty()) {
                    filters.add(cb.or(name.toArray(new Predicate[name.size()])));
                }
            }
            
            if(!CollectionUtils.isEmpty(command.getStudentId())) {
                filters.add(cb.not(root.get("id").in(command.getStudentId())));
            }
            
            if (command.getStudentGroupId() != null) {
                filters.add(cb.equal(root.get("studentGroup").get("id"), command.getStudentGroupId()));
            }

            Subquery<Long> studentsQuery = query.subquery(Long.class);
            Root<Journal> journalRoot = studentsQuery.from(Journal.class);
            Join<Object, Object> journalStudentsJoin = journalRoot.join("journalStudents", JoinType.LEFT);
            studentsQuery.select(journalStudentsJoin.get("student").get("id"))
                    .where(cb.and(cb.equal(journalRoot.get("id"), journalId),
                            cb.equal(journalStudentsJoin.get("student").get("id"), root.get("id"))));
            filters.add(cb.not(cb.exists(studentsQuery)));

            
            // who has no positive result in given module
            
            Journal journal = journalRepository.findOne(journalId);
            Set<Long> omodules = StreamUtil.toMappedSet(t -> EntityUtil.getId(
                    t.getCurriculumVersionOccupationModuleTheme().getModule()), journal.getJournalOccupationModuleThemes());
            
            Subquery<Long> protocolStudentsQuery = query.subquery(Long.class);
            Root<Protocol> protocolRoot = protocolStudentsQuery.from(Protocol.class);
            Join<Object, Object> protocolStudentsJoin = protocolRoot.join("protocolStudents", JoinType.LEFT);
            protocolStudentsQuery.select(protocolStudentsJoin.get("student").get("id")).where(
                    cb.and(cb.equal(protocolStudentsJoin.get("student").get("id"), root.get("id"))),
                    protocolStudentsJoin.get("grade").get("code").in(OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE),
                    protocolRoot.get("protocolVdata").get("curriculumVersionOccupationModule").get("id").in(omodules));
            filters.add(cb.not(cb.exists(protocolStudentsQuery)));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(JournalStudentDto::of);
    }

    public List<JournalStudentDto> suitedStudents(HoisUserDetails user, Long journalId) {
        return StreamUtil.toMappedList(JournalStudentDto::of, studentRepository.findAll((root, query, cb) -> {
            root.join("person", JoinType.INNER);
            root.join("studentGroup", JoinType.LEFT);
            root.join("curriculumVersion", JoinType.INNER).join("curriculum", JoinType.INNER);

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), user.getSchoolId()));
            filters.add(cb.or(cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_A.name()),
                    cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_O.name()),
                    cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_V.name())));

            Subquery<Long> studentGroupsQuery = query.subquery(Long.class);
            Root<Journal> journalRoot = studentGroupsQuery.from(Journal.class);
            Join<Object, Object> journalOmoduleThemesJoin = journalRoot.join("journalOccupationModuleThemes",
                    JoinType.INNER);

            studentGroupsQuery.select(
                    journalOmoduleThemesJoin.get("lessonPlanModule").get("lessonPlan").get("studentGroup").get("id"))
                    .where(cb
                            .and(cb.equal(journalRoot.get("id"), journalId),
                                    cb.equal(
                                            journalOmoduleThemesJoin.get("lessonPlanModule").get("lessonPlan")
                                                    .get("studentGroup").get("id"),
                                            root.get("studentGroup").get("id")))
                            
                            );
            filters.add(cb.exists(studentGroupsQuery));

            // who has no positive result in given module
            Journal journal = journalRepository.findOne(journalId);
            Set<Long> modules = StreamUtil.toMappedSet(t -> EntityUtil.getId(
                    t.getCurriculumVersionOccupationModuleTheme().getModule().getCurriculumModule()), journal.getJournalOccupationModuleThemes());
            
            Subquery<Long> protocolStudentsQuery = query.subquery(Long.class);
            Root<Protocol> protocolRoot = protocolStudentsQuery.from(Protocol.class);
            Join<Object, Object> protocolStudentsJoin = protocolRoot.join("protocolStudents", JoinType.LEFT);
            protocolStudentsQuery.select(protocolStudentsJoin.get("student").get("id")).where(
                    cb.and(cb.equal(protocolStudentsJoin.get("student").get("id"), root.get("id"))),
                    protocolStudentsJoin.get("grade").get("code").in(OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE),
                    protocolRoot.get("protocolVdata").get("curriculumVersionOccupationModule").get("curriculumModule").get("id").in(modules));
            filters.add(cb.not(cb.exists(protocolStudentsQuery)));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }));
    }

    public Journal saveEndDate(Journal journal, JournalEndDateCommand command) {
        if (command.getEndDate() != null) {
            journal.setEndDate(command.getEndDate());
        }
        return EntityUtil.save(journal, em);
    }

    public Journal saveJournalReview(Journal journal, JournalReviewForm journalReviewForm) {
        if (Objects.equal(journal.getReviewOk(), journalReviewForm.getIsReviewOk())
                && Objects.equal(journal.getReviewInfo(), journalReviewForm.getReviewInfo())) {
            throw new ValidationFailedException("journal.messages.reviewIsNotChanged");
        }
        journal.setReviewOk(journalReviewForm.getIsReviewOk());
        journal.setReviewInfo(journalReviewForm.getReviewInfo());
        journal.setReviewDate(LocalDate.now());
        return EntityUtil.save(journal, em);
    }

    public Journal addStudentsToJournal(Journal journal, JournalStudentsCommand command) {
        Set<Long> existingStudents = journal.getJournalStudents().stream().map(js -> EntityUtil.getId(js.getStudent()))
                .collect(Collectors.toSet());
        for (Long student : command.getStudents()) {
            if (!existingStudents.contains(student)) {
                journal.getJournalStudents().add(JournalStudent.of(em.getReference(Student.class, student)));
            }
        }
        return EntityUtil.save(journal, em);
    }

    public Journal removeStudentsFromJournal(HoisUserDetails user, Journal journal, JournalStudentsCommand command) {
        EntityUtil.setUsername(user.getUsername(), em);
        journal.getJournalStudents().removeIf(js -> command.getStudents().contains(EntityUtil.getId(js.getStudent())));
        return EntityUtil.save(journal, em);
    }

    public Journal saveJournalEntry(HoisUserDetails user, Journal journal, JournalEntryForm journalEntryForm) {
        validateJournalEntry(journalEntryForm);
        EntityUtil.setUsername(user.getUsername(), em);
        JournalEntry journalEntry = EntityUtil.bindToEntity(journalEntryForm, new JournalEntry(), classifierRepository,
                "journalEntryStudents", "journalEntryCapacityTypes");
        journal.getJournalEntries().add(journalEntry);
        saveJournalEntryStudents(user, journalEntryForm, journalEntry);
        journal = EntityUtil.save(journal, em);
        sendRemarkMessages(getStudentsWithComments(journalEntry).values());
        return journal;
    }

    public void updateJournalEntry(HoisUserDetails user, JournalEntryForm journalEntryForm, Long journalEntrylId) {
        validateJournalEntry(journalEntryForm);
        EntityUtil.setUsername(user.getUsername(), em);
        JournalEntry journalEntry = em.getReference(JournalEntry.class, journalEntrylId);
        Map<Long, Student> existingComments = getStudentsWithComments(journalEntry);
        EntityUtil.bindToEntity(journalEntryForm, journalEntry, classifierRepository, "journalEntryStudents",
                "journalEntryCapacityTypes");
        saveJournalEntryStudents(user, journalEntryForm, journalEntry);
        journalEntry = EntityUtil.save(journalEntry, em);
        Map<Long, Student> commentStudents = getStudentsWithComments(journalEntry);
        commentStudents.keySet().removeAll(existingComments.keySet());
        sendRemarkMessages(commentStudents.values());
    }

    private void sendRemarkMessages(Collection<Student> students) {
        for (Student student : students) {
            StudentRemarkCreated data = new StudentRemarkCreated(student);
            automaticMessageService.sendMessageToStudent(MessageType.TEATE_LIIK_OP_MARKUS, student, data);
            StudentGroup studentGroup = student.getStudentGroup();
            if (studentGroup != null) {
                Teacher studentGroupTeacher = studentGroup.getTeacher();
                if (studentGroupTeacher != null) {
                    automaticMessageService.sendMessageToTeacher(MessageType.TEATE_LIIK_OP_MARKUS, studentGroupTeacher, data);
                }
            }
        }
    }

    private static Map<Long, Student> getStudentsWithComments(JournalEntry journalEntry) {
        return StreamUtil.toMap(EntityUtil::getId, 
                journalEntry.getJournalEntryStudents().stream()
                .filter(jes -> StringUtils.hasText(jes.getAddInfo()))
                .map(jes -> jes.getJournalStudent().getStudent()));
    }
    
    public void deleteJournalEntry(HoisUserDetails user, JournalEntry entry) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(entry, em);
    }

    private void validateJournalEntry(JournalEntryForm journalEntryForm) {
        if (StringUtils.hasText(journalEntryForm.getHomework())) {
            ValidationFailedException.throwOnError(validator.validate(journalEntryForm, JournalEntryValidation.Homework.class));
        }

        if (JournalEntryType.SISSEKANNE_T.name().equals(journalEntryForm.getEntryType())) {
            ValidationFailedException.throwOnError(validator.validate(journalEntryForm, JournalEntryValidation.Lesson.class));
        }
    }

    private void saveJournalEntryStudents(HoisUserDetails user, JournalEntryForm journalEntryForm,
            JournalEntry journalEntry) {
        for (JournalEntryStudentForm journalEntryStudentForm : journalEntryForm.getJournalEntryStudents()) {
            if (journalEntryStudentForm.getId() != null) {
                JournalEntryStudent journalEntryStudent = em.getReference(JournalEntryStudent.class, journalEntryStudentForm.getId());
                assertJournalEntryStudentRules(journalEntryStudent.getJournalStudent());
                
                updateJournalStudentEntry(user, journalEntryStudent, journalEntryStudentForm);
                if (Boolean.TRUE.equals(journalEntryStudentForm.getRemoveStudentHistory())) {
                    removeStudentGradeHistory(user, journalEntryStudent);
                }
            } else {
                saveJournalStudentEntry(user, journalEntry, journalEntryStudentForm);
            }
        }
        EntityUtil.bindEntityCollection(journalEntry.getJournalEntryCapacityTypes(),
                type -> EntityUtil.getCode(type.getCapacityType()), journalEntryForm.getJournalEntryCapacityTypes(),
                it -> {
                    JournalEntryCapacityType type = new JournalEntryCapacityType();
                    type.setCapacityType(em.getReference(Classifier.class, it));
                    return type;
                });
    }

    private void updateJournalStudentEntry(HoisUserDetails user, JournalEntryStudent journalEntryStudent, JournalEntryStudentForm journalEntryStudentForm) {
        if (Boolean.FALSE.equals(journalEntryStudentForm.getRemoveStudentHistory()) && journalEntryStudent.getGrade() != null
                && !EntityUtil.getCode(journalEntryStudent.getGrade()).equals(journalEntryStudentForm.getGrade())) {
            JournalEntryStudentHistory journalEntryStudentHistory = new JournalEntryStudentHistory();
            journalEntryStudentHistory.setGrade(journalEntryStudent.getGrade());
            journalEntryStudentHistory.setGradeInserted(journalEntryStudent.getGradeInserted());
            
            String insertedBy;
            if (journalEntryStudent.getGradeInsertedBy() != null) {
                insertedBy = journalEntryStudent.getGradeInsertedBy();
            } else if (journalEntryStudent.getChangedBy() != null) {
                insertedBy = journalEntryStudent.getChangedBy();
            } else {
                insertedBy = journalEntryStudent.getInsertedBy();
            }
            journalEntryStudentHistory.setGradeInsertedBy(insertedBy);
            
            journalEntryStudent.getJournalEntryStudentHistories().add(journalEntryStudentHistory);
        }
        if (journalEntryStudentForm.getGrade() != null && !journalEntryStudentForm.getGrade()
                .equals(EntityUtil.getNullableCode(journalEntryStudent.getGrade()))) {
            journalEntryStudent.setGradeInserted(LocalDateTime.now());
            journalEntryStudent.setGradeInsertedBy(user.getUsername());
        }
        EntityUtil.bindToEntity(journalEntryStudentForm, journalEntryStudent, classifierRepository,
                "journalEntryStudentHistories", "gradeInserted");
    }

    private static void assertJournalEntryStudentRules(JournalStudent journaStudent) {
        if (!StudentUtil.isActive(journaStudent.getStudent())) {
            throw new ValidationFailedException("journal.messages.changeIsNotAllowedStudentIsNotStudying");
        }
    }

    private void removeStudentGradeHistory(HoisUserDetails user, JournalEntryStudent journalEntryStudent) {
        EntityUtil.setUsername(user.getUsername(), em);
        journalEntryStudent.getJournalEntryStudentHistories().clear();
    }

    private void saveJournalStudentEntry(HoisUserDetails user, JournalEntry journalEntry,
            JournalEntryStudentForm journalEntryStudentForm) {
        JournalStudent journalStudent = em.getReference(JournalStudent.class, journalEntryStudentForm.getJournalStudent());
        assertJournalEntryStudentRules(journalStudent);

        JournalEntryStudent journalEntryStudent = EntityUtil.bindToEntity(journalEntryStudentForm,
                new JournalEntryStudent(), classifierRepository, "journalEntryStudentHistories", "gradeInserted");

        journalEntryStudent.setJournalStudent(journalStudent);
        if (journalEntryStudentForm.getGrade() != null) {
            journalEntryStudent.setGradeInserted(LocalDateTime.now());
            journalEntryStudent.setGradeInsertedBy(user.getUsername());
        }
        // TODO remove check, use database unique constraint
        Long id = EntityUtil.getId(journalStudent);
        if(journalEntry.getJournalEntryStudents().stream().anyMatch(it -> id.equals(EntityUtil.getId(it.getJournalStudent())))) {
            throw new ValidationFailedException("journal.messages.dublicateJournalStudentInJournalEntry");
        }
        journalEntry.getJournalEntryStudents().add(journalEntryStudent);
    }

    public JournalEntryLessonInfoDto journalEntryLessonInfo(Journal journal) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from timetable_event_time tet " + "inner join timetable_event te on te.id = tet.timetable_event_id "
                        + "inner join timetable_object tob on tob.id = te.timetable_object_id");
        qb.requiredCriteria("tob.journal_id = :journalId", "journalId", EntityUtil.getId(journal));
        List<?> result = qb.select("tet.start", em).getResultList();
        JournalEntryLessonInfoDto dto = new JournalEntryLessonInfoDto();
        dto.setLessonPlanDates(StreamUtil.toMappedList(r -> resultAsLocalDateTime(r, 0), result));
        return dto;
    }

    public Page<JournalEntryTableDto> journalTableEntries(Long journalId, Pageable pageable) {
        JpaNativeQueryBuilder jeQb = new JpaNativeQueryBuilder("from journal_entry je left join curriculum_module_outcomes cmo on cmo.id = je.curriculum_module_outcomes_id")
                .sort("je.entry_date desc nulls last, lower(je.entry_type_code)='sissekanne_l' asc , lower(je.entry_type_code)='sissekanne_o' asc, cmo.order_nr");

        jeQb.requiredCriteria("je.journal_id=:journalId", "journalId", journalId);

        return JpaQueryUtil.pagingResult(jeQb,
                "je.id, je.entry_type_code, je.entry_date, je.lessons, je.name_et, je.content, je.homework, je.homework_duedate, je.moodle_grade_item_id, cmo.order_nr", em,
                pageable).map(r -> {
                    JournalEntryTableDto dto = new JournalEntryTableDto();
                    dto.setId(resultAsLong(r, 0));
                    dto.setEntryType(resultAsString(r, 1));
                    dto.setEntryDate(resultAsLocalDate(r, 2));
                    dto.setLessons(resultAsLong(r, 3));
                    dto.setNameEt(resultAsString(r, 4));
                    dto.setContent(resultAsString(r, 5));
                    dto.setHomework(resultAsString(r, 6));
                    dto.setHomeworkDuedate(resultAsLocalDate(r, 7));
                    dto.setMoodleGradeItemId(resultAsLong(r, 8));
                    return dto;
                });
    }

    public JournalEntryDto journalEntry(Long journalId, Long journalEntrylId) {
        JournalEntry journalEntry = em.getReference(JournalEntry.class, journalEntrylId);
        if (EntityUtil.getId(journalEntry.getJournal()).equals(journalId)) {
            return JournalEntryDto.of(journalEntry);
        }
        return null;
    }
    
    private static boolean isFinalResult(JournalEntryByDateDto dto) {
        return JournalEntryType.SISSEKANNE_L.name().equals(dto.getEntryType());
    }

    public List<JournalEntryByDateDto> journalEntriesByDate(Journal journal, Boolean allStudents) {
        List<JournalEntryByDateDto> result = new ArrayList<>();

        int outcomeWithoutOrderNr = 0;
        for (JournalEntry journalEntry : journal.getJournalEntries()) {
            JournalEntryByDateDto journalEntryByDateDto = EntityUtil.bindToDto(journalEntry,
                    new JournalEntryByDateDto());
            journalEntryByDateDto.setTeacher(PersonUtil.stripIdcodeFromFullnameAndIdcode(journalEntry.getInsertedBy()));
            journalEntryByDateDto.setEntryDate(journalEntry.getEntryDate());
            if (journalEntry.getCurriculumModuleOutcomes() != null) {
                if (journalEntry.getCurriculumModuleOutcomes().getOrderNr() != null) {
                    journalEntryByDateDto.setOutcomeOrderNr(journalEntry.getCurriculumModuleOutcomes().getOrderNr());
                } else {
                    journalEntryByDateDto.setOutcomeOrderNr(Long.valueOf(outcomeWithoutOrderNr++));
                }
                journalEntryByDateDto.setCurriculumModule(EntityUtil.getId(journalEntry.getCurriculumModuleOutcomes().getCurriculumModule()));
            }
            
            for (JournalEntryStudent journalEntryStudent : journalEntry.getJournalEntryStudents()) {
                if (Boolean.TRUE.equals(allStudents)
                        || StudentUtil.isActive(journalEntryStudent.getJournalStudent().getStudent())) {
                    List<JournalEntryStudentResultDto> studentresults = journalEntryByDateDto.getJournalStudentResults()
                            .computeIfAbsent(EntityUtil.getId(journalEntryStudent.getJournalStudent()),
                                    id -> new ArrayList<>());
                    JournalEntryStudentResultDto dto = EntityUtil.bindToDto(journalEntryStudent,
                            new JournalEntryStudentResultDto(), "gradeInsertedBy", "journalEntryStudentHistories");
                    
                    String insertedBy;
                    if (journalEntryStudent.getGradeInsertedBy() != null) {
                        insertedBy = journalEntryStudent.getGradeInsertedBy();
                    } else if (journalEntryStudent.getChangedBy() != null) {
                        insertedBy = journalEntryStudent.getChangedBy();
                    } else {
                        insertedBy = journalEntryStudent.getInsertedBy();
                    }
                    
                    dto.setGradeInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(insertedBy));
                    dto.setJournalEntryStudentHistories(StreamUtil.toMappedList(JournalEntryStudentHistoryDto::new,
                            journalEntryStudent.getJournalEntryStudentHistories()));
                    studentresults.add(dto);
                }
            }
            result.add(journalEntryByDateDto);
        }
        setOutcomeEntriesUnqiueOrderNrs(result);
        orderJournalEntriesByDate(result);
        return result;
    }
    
    private static void setOutcomeEntriesUnqiueOrderNrs(List<JournalEntryByDateDto> entries) {
        // order outcomes by curriculum module id and their order nr and then give outcomes from different modules a unique outcome order nr
        Collections.sort(entries, Comparator.comparing(JournalEntryByDateDto::getCurriculumModule, Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(JournalEntryByDateDto::getOutcomeOrderNr, Comparator.nullsFirst(Comparator.naturalOrder())));
        List<Long> orderNrs = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            Long entryOrderNr = entries.get(i) != null && entries.get(i).getOutcomeOrderNr() != null ? entries.get(i).getOutcomeOrderNr() : null;
            if (entryOrderNr != null) {
                if (!orderNrs.contains(entryOrderNr)) {
                    orderNrs.add(entryOrderNr);
                } else {
                    Long newOrderNr = Long.valueOf(orderNrs.stream().max(Comparator.comparing(nr -> nr)).get().longValue() + 1);
                    entries.get(i).setOutcomeOrderNr(newOrderNr);
                    orderNrs.add(newOrderNr);
                }
            }
        }
    }
    
    private static void orderJournalEntriesByDate(List<JournalEntryByDateDto> entries) {
        // order day entries by lesson nr
        Collections.sort(entries, Comparator.comparing(JournalEntryByDateDto::getEntryDate, Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(JournalEntryByDateDto::getStartLessonNr, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(JournalEntryByDateDto::getLessons, Comparator.nullsFirst(Comparator.naturalOrder())));
        
        // outcome entries that don't have a date are ordered last among entries without date, all other entries are ordered by date
        Collections.sort(entries, Comparator.comparing(JournalEntryByDateDto::getOutcomeOrderNr, Comparator.nullsFirst(Comparator.naturalOrder())));
        Collections.sort(entries, Comparator.comparing(JournalEntryByDateDto::getEntryDate, Comparator.nullsFirst(Comparator.naturalOrder())));

        // put final results to the end of the list
        Collections.sort(entries, (JournalEntryByDateDto o1, JournalEntryByDateDto o2) -> {
            if (isFinalResult(o1) && !isFinalResult(o2)) {
                return 1;
            } else if (!isFinalResult(o1) && isFinalResult(o2)) {
                return -1;
            }
            return 0;
        });
    }

    public List<JournalStudentDto> journalStudents(Journal journal, Boolean allStudents) {
        List<JournalStudent> students = journal.getJournalStudents().stream()
                .filter(jt -> Boolean.TRUE.equals(allStudents) || StudentUtil.isActive(jt.getStudent()))
                .collect(Collectors.toList());
        students.sort(Comparator
                .comparing(js -> ((JournalStudent) js).getStudent().getStudentGroup() != null ? ((JournalStudent) js).getStudent().getStudentGroup().getCode(): null, 
                        Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(js -> ((JournalStudent) js).getStudent().getPerson().getLastname(), String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Comparator.comparing(js -> ((JournalStudent) js).getStudent().getPerson().getFirstname(), String.CASE_INSENSITIVE_ORDER)));
        
        return students.stream().map(JournalStudentDto::of).collect(Collectors.toList());
    }
    
    public JournalLessonHoursDto usedHours(Journal journal) {
        List<JournalCapacity> capacities = journal.getJournalCapacities();
        Set<JournalEntry> entries = journal.getJournalEntries();
        
        JournalLessonHoursDto dto = new JournalLessonHoursDto();
        dto.setTotalPlannedHours(calculatePlannedHours(capacities, null));
        dto.setTotalUsedHours(calculateUsedHours(entries, null));
        
        List<CapacityHoursDto> capacityHours = new ArrayList<>();
        SchoolCapacityTypeCommand capacityCommand = new SchoolCapacityTypeCommand();
        capacityCommand.setJournalId(journal.getId());
        capacityCommand.setEntryTypes(Boolean.TRUE);
        List<Classifier> capacityTypes = autocompleteService.schoolCapacityTypes(EntityUtil.getId(journal.getSchool()), capacityCommand);
        for (Classifier type : capacityTypes) {
            if (type.isVocational()) {
                CapacityHoursDto typeHours = new CapacityHoursDto();
                String typeCode = EntityUtil.getCode(type);
                typeHours.setCapacity(typeCode);
                typeHours.setPlannedHours(calculatePlannedHours(capacities, typeCode));
                typeHours.setUsedHours(calculateUsedHours(entries, typeCode));
                capacityHours.add(typeHours);
            }
        }
        dto.setCapacityHours(capacityHours);
        
        return dto;
    }
    
    private static Integer calculatePlannedHours(List<JournalCapacity> capacities, String typeCode) {
        return Integer.valueOf(capacities.stream()
                .filter(it -> typeCode == null
                        || typeCode.equals(EntityUtil.getCode(it.getJournalCapacityType().getCapacityType())))
                .mapToInt(it -> it.getHours() == null ? 0 : it.getHours().intValue()).sum());
    }
    
    private static Integer calculateUsedHours(Set<JournalEntry> entries, String typeCode) {
        return Integer.valueOf(entries.stream()
                .filter(it -> typeCode == null || it.getJournalEntryCapacityTypes().stream()
                        .anyMatch(ct -> typeCode.equals(EntityUtil.getCode(ct.getCapacityType()))))
                .mapToInt(it -> it.getLessons() == null ? 0 : it.getLessons().intValue()).sum());
    }

    public byte[] journalAsExcel(Journal journal) {
        JournalXlsDto dto = JournalXlsDto.of(journal);
        dto.setLessonHours(usedHours(journal));
        return xlsService.generate("journal.xls", Collections.singletonMap("journal", dto));
    }

    public Set<Long> journalStudentsWithAcceptedAbsence(Journal journal, LocalDate entryDate) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(" from journal_student js ");
        qb.requiredCriteria("js.journal_id = :journalId", "journalId", EntityUtil.getId(journal));
        qb.requiredCriteria(
                "exists("
                + "select sa.id "
                + "from student_absence sa "
                + "where sa.is_accepted "
                + "and sa.student_id = js.student_id "
                + "and sa.valid_from <= :entryDate "
                + "and case "
                    +   "when sa.valid_thru is null "
                        +  "then sa.valid_from = :entryDate "
                    +  "else "
                        + "sa.valid_thru >= :entryDate "
                +  "end ) ", "entryDate", entryDate);
        List<?> result = qb.select("js.id", em).getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), result);
    }
    
    
    /**
     * Get study years for view
     * @param studentId
     * @return list of study years in which student has journals
     */
    public List<StudyYearSearchDto> studentJournalStudyYears(Long studentId) {
        String from = "from journal j"
                + " join journal_student js on j.id = js.journal_id"
                + " join study_year sy on j.study_year_id = sy.id "
                + " join classifier c on sy.year_code = c.code";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("js.student_id = :studentId", "studentId", studentId);
        
        List<?> data = qb.select("distinct c.code, c.name_et, c.name_en, sy.id, sy.start_date, sy.end_date, 0 as count", em).getResultList();
        return StreamUtil.toMappedList(r -> new StudyYearSearchDto((Object[])r), data);
    }
    
    /**
     * Get student journals for view
     * @param studentId
     * @return list of student journals with student's absences
     */
    public List<StudentJournalDto> studentJournals(Long studentId, Long studyYearId) {
        Query q = em.createNativeQuery("select j.id, j.name_et, j.study_year_id, sy.year_code, j.add_module_outcomes,"
                + " string_agg(distinct p.firstname || ' ' || p.lastname, ', ') as teachers,"
                + " string_agg(distinct cm.name_et || ' - ' || mcl.name_et || ' (' || c.code || ')', ', ') as module_et,"
                + " string_agg(distinct cm.name_en || ' - ' || mcl.name_en || ' (' || c.code || ')', ', ') as module_en"
                + " from journal_student js"
                + " join journal j on j.id = js.journal_id"
                + " join study_year sy on j.study_year_id = sy.id"
                + " join student s on s.id = js.student_id"
                + " left join journal_teacher jt on j.id = jt.journal_id"
                + " left join teacher t on jt.teacher_id = t.id"
                + " left join person p on t.person_id = p.id"
                + " left join journal_omodule_theme jot on j.id = jot.journal_id"
                + " left join curriculum_version_omodule_theme cvot on jot.curriculum_version_omodule_theme_id = cvot.id"
                + " left join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id"
                + " left join curriculum_module cm on cvo.curriculum_module_id = cm.id"
                + " left join curriculum c on cm.curriculum_id = c.id"
                + " left join classifier mcl on cm.module_code = mcl.code"
                + " where s.id=?1 and j.study_year_id=?2 group by j.id, sy.year_code");

        q.setParameter(1, studentId);
        q.setParameter(2, studyYearId);

        List<?> result = q.getResultList();
        List<StudentJournalDto> journals = StreamUtil.toMappedList(r -> {
            StudentJournalDto dto = new StudentJournalDto();
            dto.setId(resultAsLong(r, 0));
            dto.setNameEt(resultAsString(r, 1));
            dto.setStudyYearId(resultAsLong(r, 2));
            dto.setYearCode(resultAsString(r, 3));
            dto.setModuleOutcomesAsEntries(resultAsBoolean(r, 4));
            dto.setTeachers(resultAsString(r, 5));
            dto.setModules(new AutocompleteResult(null, resultAsString(r, 6), resultAsString(r, 7)));
            dto.setAbsenceH(Long.valueOf(0));
            dto.setAbsenceP(Long.valueOf(0));
            dto.setAbsencePV(Long.valueOf(0));
            return dto;
        }, result);
        
        setStudentJournalAbsences(studentId, journals);
        return getStudentJournalsWithEntries(studentId, journals);
    }
    
    private void setStudentJournalAbsences(Long studentId, List<StudentJournalDto> journals) {
        List<Long> journalIds = StreamUtil.toMappedList(StudentJournalDto::getId, journals);
        
        if (!journalIds.isEmpty()) {
            Query absencesQuery = em.createNativeQuery("select j.id, jes.absence_code from journal_entry je"
                    + " join journal_entry_student jes on jes.journal_entry_id=je.id"
                    + " join journal_student js on jes.journal_student_id = js.id "
                    + " join journal j  on j.id = js.journal_id "
                    + " where j.id in (?1) and js.student_id =?2 and jes.absence_code is not null");
            absencesQuery.setParameter(1, journalIds);
            absencesQuery.setParameter(2, studentId);
            List<?> data = absencesQuery.getResultList();
            
            if (!data.isEmpty()) {
                Map<Long, List<String>> absencesByJournal = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                        Collectors.mapping(r -> resultAsString(r, 1), Collectors.toList())));
                
                for (StudentJournalDto journal : journals) {
                    List<String> journalAbsences = absencesByJournal.get(journal.getId());
                    
                    if (journalAbsences != null) {
                        journal.setAbsenceH(Long.valueOf(
                                journalAbsences.stream().filter(a -> a.equals(Absence.PUUDUMINE_H.name())).count()));
                        journal.setAbsenceP(Long.valueOf(
                                journalAbsences.stream().filter(a -> a.equals(Absence.PUUDUMINE_P.name())).count()));
                        journal.setAbsencePV(Long.valueOf(journalAbsences.stream()
                                .filter(a -> a.equals(Absence.PUUDUMINE_P.name()) || a.equals(Absence.PUUDUMINE_V.name())).count()));
                    }
                }
            }
        }
    }

    private List<StudentJournalDto> getStudentJournalsWithEntries(Long studentId, List<StudentJournalDto> journals) {
        List<Long> journalIds = StreamUtil.toMappedList(StudentJournalDto::getId, journals);
        
        if (!journalIds.isEmpty()) {
            Query entriesQuery = em.createNativeQuery("select je.id, je.journal_id, je.entry_type_code, je.entry_date, je.content,"
                    + " ( select value from classifier where code = jes.grade_code ),"
                    + " jes.grade_inserted, coalesce(jes.grade_inserted_by, jes.changed_by, jes.inserted_by) as grade_inserted_by,"
                    + " jes.add_info, je.homework, je.homework_duedate, jes.absence_code from journal j"
                    + " inner join journal_entry je on j.id = je.journal_id"
                    + " inner join journal_student js on j.id = js.journal_id"
                    + " left join journal_entry_student jes on je.id = jes.journal_entry_id and jes.journal_student_id = js.id"
                    + " inner join student s on js.student_id = s.id"
                    + " where j.id in (?1) and s.id = ?2"
                    + " order by je.entry_date is null, je.entry_date desc");
            entriesQuery.setParameter(1, journalIds);
            entriesQuery.setParameter(2, studentId);
            List<?> data = entriesQuery.getResultList();
            
            List<StudentJournalEntryDto> entries = StreamUtil.toMappedList(r -> new StudentJournalEntryDto(resultAsLong(r, 0), resultAsLong(r, 1),
                    resultAsString(r, 2), resultAsLocalDate(r, 3), resultAsString(r, 4), resultAsString(r, 5), resultAsLocalDateTime(r, 6), 
                    PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 7)), resultAsString(r, 8), resultAsString(r, 9), 
                    resultAsLocalDate(r, 10), resultAsString(r, 11)), data);
            entries = getEntriesWithPreviousResults(entries, studentId);
            Map<Long, List<StudentJournalEntryDto>> journalEntries = entries.stream().collect(Collectors.groupingBy(r -> r.getJournalId()));
            
            for (StudentJournalDto journal : journals) {
                journal.setJournalEntries(journalEntries.containsKey(journal.getId()) ? journalEntries.get(journal.getId()) : new ArrayList<>());
            }
        }
        
        return journals;
    }
    
    private List<StudentJournalEntryDto> getEntriesWithPreviousResults(List<StudentJournalEntryDto> entries, Long studentId) {
        Set<Long> entriesIds = StreamUtil.toMappedSet(StudentJournalEntryDto::getId, entries.stream());
        if(!entriesIds.isEmpty()) {
            Query previousResultQuerys = em.createNativeQuery("select je.id, ( select value from classifier where code = jesh.grade_code ),"
                    + " jesh.grade_inserted, coalesce(jesh.grade_inserted_by, jesh.changed_by, jesh.inserted_by) as grade_inserted_by from journal j"
                    + " inner join journal_entry je on je.journal_id = j.id"
                    + " inner join journal_student js on js.journal_id = j.id"
                    + " inner join student s on s.id=js.student_id"
                    + " inner join journal_entry_student jes on je.id=jes.journal_entry_id and jes.journal_student_id=js.id"
                    + " inner join journal_entry_student_history jesh on jesh.journal_entry_student_id=jes.id"
                    + " where je.id in(:entries) and s.id=:studentId"
                    + " order by je.entry_date is null, je.entry_date desc");
            previousResultQuerys.setParameter("entries", entriesIds);
            previousResultQuerys.setParameter("studentId", studentId);
            
            List<?> previousResultQueryResult = previousResultQuerys.getResultList();
            Map<Long, List<StudentJournalEntryPreviousResultDto>> previousResults = previousResultQueryResult.stream().collect(
                    Collectors.groupingBy(r -> resultAsLong(r, 0), 
                    Collectors.mapping(r -> new StudentJournalEntryPreviousResultDto(resultAsString(r, 1), 
                            resultAsLocalDateTime(r, 2), PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 3))),
                    Collectors.toList())));
            for(StudentJournalEntryDto dto : entries) {
                dto.setPreviousResults(previousResults.get(dto.getId()));
            }
        }
        return entries;
    }
    
    /**
     * Get student's journal tasks for view.
     * @param schoolId
     * @param studentId
     * @return study year info and a list of student's journal tasks, or null if there is no current study year
     */
    public StudentJournalTaskListDto studentJournalTasks(Long schoolId, Long studentId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
        if(studyYear == null) {
            return null;
        }

        Query q = em.createNativeQuery("select je.id, je.entry_type_code, j.name_et, coalesce(je.homework_duedate, je.entry_date) as task_date,"
                + " coalesce(je.homework, je.content) as task_content"
                + " from journal_entry je inner join journal j on j.id=je.journal_id"
                + " inner join journal_student js on j.id=js.journal_id"
                + " inner join student s on js.student_id=s.id"
                + " where j.study_year_id=?1 and s.id=?2 and"
                + " (je.homework_duedate is not null and coalesce(je.homework,'x')!='x' or"
                + " je.entry_type_code in (:testEntryTypes)"
                + " and coalesce(je.content,'x')!='x' and je.entry_date is not null)"
                + " order by task_date desc");
        q.setParameter(1, studyYear.getId());
        q.setParameter(2, studentId);
        q.setParameter("testEntryTypes", testEntryTypeCodes);
        List<?> data = q.getResultList();

        return new StudentJournalTaskListDto(studyYear, StreamUtil.toMappedList(r -> new StudentJournalTaskDto((Object[]) r), data));
    }

    /**
     * Get student's study entries for view.
     * @param schoolId
     * @param studentId
     * @return study year info and a list of student's study entries, or null if there is no current study year
     */
    public StudentJournalStudyListDto studentJournalStudy(Long schoolId, Long studentId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
        if(studyYear == null) {
            return null;
        }

        Query q = em.createNativeQuery("select je.id, je.entry_date, j.name_et, je.content as content from journal_entry je"
                + " inner join journal j on j.id=je.journal_id"
                + " inner join journal_student js on j.id=js.journal_id"
                + " inner join student s on js.student_id=s.id"
                + " where j.study_year_id=?1 and s.id=?2 and je.entry_date is not null and coalesce(je.content,'x')!='x' order by je.entry_date desc");
        q.setParameter(1, studyYear.getId());
        q.setParameter(2, studentId);
        List<?> data = q.getResultList();

        return new StudentJournalStudyListDto(studyYear, StreamUtil.toMappedList(r -> new StudentJournalStudyDto((Object[]) r), data));
    }

    /**
     * Get student's last 30 absences for view.
     * @param schoolId
     * @param studentId
     * @return list of student's absences, or null if there is no current study year
     */
    public List<StudentJournalAbsenceDto> studentAbsences(Long schoolId, Long studentId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
        if(studyYear == null) {
            return null;
        }

        Query q = em.createNativeQuery("select je.id, je.entry_date, j.name_et, jes.absence_code, je.start_lesson_nr,"
                + " STRING_AGG(p.firstname || ' ' || p.lastname, ', ') as teachers from journal j"
                + " inner join journal_entry je on je.journal_id=j.id"
                + " inner join journal_teacher jt on jt.journal_id=j.id"
                + " inner join teacher t on t.id=jt.teacher_id"
                + " inner join person p on p.id=t.person_id"
                + " inner join journal_student js on js.journal_id=j.id"
                + " inner join journal_entry_student jes on jes.journal_entry_id=je.id and jes.journal_student_id=js.id"
                + " inner join student s on s.id=js.student_id"
                + " where j.study_year_id=?1 and s.id=?2 and jes.absence_code in (:absenceCodes)"
                + " group by je.id, jes.absence_code, j.name_et"
                + " order by je.entry_date limit 30");
        q.setParameter(1, studyYear.getId());
        q.setParameter(2, studentId);
        q.setParameter("absenceCodes", journalAbsenceCodes);
        List<?> data = q.getResultList();
        
        return StreamUtil.toMappedList(r -> new StudentJournalAbsenceDto(resultAsLong(r, 0), resultAsLocalDate(r, 1), resultAsString(r, 2),
                resultAsString(r, 3), resultAsShort(r, 4), resultAsString(r, 5)), data);
    }
    
    /**
     * Get student's last 10 results for view.
     * @param schoolId
     * @param studentId
     * @return list of student's last results, or null if there is no current study year
     */
    public List<StudentJournalResultDto> studentLastResults(Long schoolId, Long studentId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
        if(studyYear == null) {
            return null;
        }

        Query q = em.createNativeQuery("select distinct je.id, je.entry_type_code, j.name_et, null as name_en, je.content,"
                + " c.value as grade, jes.grade_inserted, jes.add_info from journal j"
                + " inner join journal_entry je on je.journal_id=j.id"
                + " inner join journal_teacher jt on jt.journal_id=j.id"
                + " inner join teacher t on t.id=jt.teacher_id"
                + " inner join person p on p.id=t.person_id"
                + " inner join journal_student js on js.journal_id=j.id"
                + " inner join journal_entry_student jes on jes.journal_entry_id=je.id and jes.journal_student_id=js.id"
                + " inner join classifier c on c.code=jes.grade_code"
                + " inner join student s on s.id=js.student_id"
                + " where j.study_year_id=?1 and s.id=?2"
                + " union"
                + " select p.id, null as entry_type_code, cm.name_et, cm.name_en, null as content, c.value as grade,"
                + " ps.changed as grade_inserted, ps.add_info from protocol p"
                + " inner join protocol_student ps on ps.protocol_id=p.id"
                + " inner join student s on s.id=ps.student_id"
                + " inner join classifier c on c.code=ps.grade_code"
                + " inner join protocol_vdata pvd on pvd.protocol_id=p.id"
                + " inner join curriculum_version_omodule cvm on cvm.id=pvd.curriculum_version_omodule_id"
                + " inner join curriculum_module cm on cm.id=cvm.curriculum_module_id"
                + " where s.id=?2"
                + " order by grade_inserted desc limit 10");
        q.setParameter(1, studyYear.getId());
        q.setParameter(2, studentId);
        List<?> data = q.getResultList();
        
        return StreamUtil.toMappedList(r -> new StudentJournalResultDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2),
                resultAsString(r, 3), resultAsString(r, 4), resultAsString(r, 5), resultAsLocalDateTime(r, 6), resultAsString(r, 7)), data);
    }
    
    /**
     * Confirms journals which meet following criteria:
     *  - status = PAEVIK_STAATUS_T
     *  - has any students added
     *  - all student with status OPPURSTAATUS_O have final results
     */
    public Integer confirmAll(HoisUserDetails user) {
        Long currentStudyYear = EntityUtil.getId(studyYearService.getCurrentStudyYear(user.getSchoolId()));
        
        Query q = em.createNativeQuery("update journal "
                + "set status_code = :journalStatusConfirmed "
                + "where id in ("
                    + "select id "
                    + "from journal j "
                    + "where j.study_year_id = :currentStudyYear "
                    + "and status_code = :journalStatusInWork "
                    + "and exists ("
                        + "select 1 from "
                        + "journal_student js2 "
                        + "where js2.journal_id = j.id) "
                    + "and not exists("
                        + "select 1 "
                        + "from journal_student js "
                        + "join student s on s.id = js.student_id and s.status_code = :studentStatus "
                        + "where js.journal_id = j.id "
                        + "and not exists("
                            + "select je.id "
                            + "from journal_entry je "
                            + "join journal_entry_student jes on jes.journal_student_id = js.id "
                            + "where je.entry_type_code = :finalResult "
                            + "and jes.grade_code is not null )))");

        q.setParameter("currentStudyYear", currentStudyYear);
        q.setParameter("journalStatusConfirmed", JournalStatus.PAEVIK_STAATUS_K.name());
        q.setParameter("journalStatusInWork", JournalStatus.PAEVIK_STAATUS_T.name());
        q.setParameter("studentStatus", StudentStatus.OPPURSTAATUS_O.name());
        q.setParameter("finalResult", JournalEntryType.SISSEKANNE_L.name());
        
        return Integer.valueOf(q.executeUpdate());
    }

}
