package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.domain.timetable.JournalEntryCapacityType;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalEntryStudentHistory;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.enums.Absence;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.JournalRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.validation.JournalEntryValidation;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.timetable.JournalEndDateCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryStudentForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalStudentsCommand;
import ee.hitsa.ois.web.commandobject.timetable.StudentNameSearchCommand;
import ee.hitsa.ois.web.dto.timetable.JournalEntryByDateDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryLessonInfoDto;
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
    
    private static final List<String> journalAbsenceCodes = EnumUtil.toNameList(Absence.PUUDUMINE_P, Absence.PUUDUMINE_H);
    private static final List<String> testEntryTypeCodes = EnumUtil.toNameList(JournalEntryType.SISSEKANNE_H, JournalEntryType.SISSEKANNE_L,
            JournalEntryType.SISSEKANNE_E, JournalEntryType.SISSEKANNE_I);

    public Page<JournalSearchDto> search(HoisUserDetails user, JournalSearchCommand command, Pageable pageable) {
        return journalRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), user.getSchoolId()));

            if (command.getStudentGroup() != null) {
                searchByStudentGroupCriteria(command, root, query, cb, filters);
            }

            if (!CollectionUtils.isEmpty(command.getModule())) {
                searchByModuleCriteria(command, root, query, cb, filters);
            }

            if (user.isTeacher()) {
                command.setTeacher(user.getTeacherId());
            }
            if (command.getTeacher() != null) {
                searchByTeacherCriteria(command, root, query, cb, filters);
            }

            if (command.getJournal() != null) {
                filters.add(cb.equal(root.get("id"), command.getJournal()));
            }

            if (command.getStatus() != null) {
                filters.add(cb.equal(root.get("status").get("code"), command.getStatus()));
            }

            if (command.getStudyYear() != null) {
                filters.add(cb.equal(root.get("studyYear").get("id"), command.getStudyYear()));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(JournalSearchDto::of);
    }

    private static void searchByTeacherCriteria(JournalSearchCommand command, Root<Journal> root,
            CriteriaQuery<?> query, CriteriaBuilder cb, List<Predicate> filters) {
        Subquery<Long> journalTeachersQuery = query.subquery(Long.class);
        Root<Journal> journalRoot = journalTeachersQuery.from(Journal.class);
        Join<Object, Object> journalTeachersJoin = journalRoot.join("journalTeachers");
        journalTeachersQuery.select(journalRoot.get("id")).where(cb.and(cb.equal(journalRoot.get("id"), root.get("id")),
                cb.equal(journalTeachersJoin.get("teacher").get("id"), command.getTeacher())));
        filters.add(cb.exists(journalTeachersQuery));
    }

    private static void searchByModuleCriteria(JournalSearchCommand command, Root<Journal> root, CriteriaQuery<?> query,
            CriteriaBuilder cb, List<Predicate> filters) {
        Subquery<Long> curriculumModulesQuery = query.subquery(Long.class);
        Root<Journal> journalRoot = curriculumModulesQuery.from(Journal.class);
        Join<Object, Object> journalOmoduleThemesJoin = journalRoot.join("journalOccupationModuleThemes",
                JoinType.INNER);
        curriculumModulesQuery.select(journalRoot.get("id"))
                .where(cb.and(cb.equal(journalRoot.get("id"), root.get("id")),
                        journalOmoduleThemesJoin.get("curriculumVersionOccupationModuleTheme").get("module")
                                .get("curriculumModule").get("id").in(command.getModule())));
        filters.add(cb.exists(curriculumModulesQuery));
    }

    private static void searchByStudentGroupCriteria(JournalSearchCommand command, Root<Journal> root,
            CriteriaQuery<?> query, CriteriaBuilder cb, List<Predicate> filters) {
        Subquery<Long> studentGroupsQuery = query.subquery(Long.class);
        Root<Journal> journalRoot = studentGroupsQuery.from(Journal.class);
        Join<Object, Object> journalOmoduleThemesJoin = journalRoot.join("journalOccupationModuleThemes",
                JoinType.INNER);
        studentGroupsQuery.select(journalRoot.get("id"))
                .where(cb.and(cb.equal(journalRoot.get("id"), root.get("id")),
                        cb.equal(journalOmoduleThemesJoin.get("lessonPlanModule").get("lessonPlan").get("studentGroup")
                                .get("id"), command.getStudentGroup())));
        filters.add(cb.exists(studentGroupsQuery));
    }
    
    public Journal confirm(Journal journal) {
        journal.setStatus(classifierRepository.getOne(JournalStatus.PAEVIK_STAATUS_K.name()));
        return EntityUtil.save(journal, em);
    }
    
    public Journal unconfirm(Journal journal) {
        journal.setStatus(classifierRepository.getOne(JournalStatus.PAEVIK_STAATUS_T.name()));
        return EntityUtil.save(journal, em);
    }

    public Page<JournalStudentDto> otherStudents(HoisUserDetails user, Long journalId, StudentNameSearchCommand command,
            Pageable pageable) {
        return studentRepository.findAll((root, query, cb) -> {
            root.join("person", JoinType.INNER);
            root.join("studentGroup", JoinType.LEFT);
            root.join("curriculumVersion", JoinType.INNER).join("curriculum", JoinType.INNER);

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), user.getSchoolId()));
            filters.add(cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_O.name()));

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

            Subquery<Long> studentsQuery = query.subquery(Long.class);
            Root<Journal> journalRoot = studentsQuery.from(Journal.class);
            Join<Object, Object> journalStudentsJoin = journalRoot.join("journalStudents", JoinType.LEFT);
            studentsQuery.select(journalStudentsJoin.get("student").get("id"))
                    .where(cb.and(cb.equal(journalRoot.get("id"), journalId),
                            cb.equal(journalStudentsJoin.get("student").get("id"), root.get("id"))));
            filters.add(cb.not(cb.exists(studentsQuery)));

            // kellel puudub vastavas moodulis positiivne tulemus.
            Subquery<Long> protocolStudentsQuery = query.subquery(Long.class);
            Root<Protocol> protocolRoot = protocolStudentsQuery.from(Protocol.class);
            Join<Object, Object> protocolStudentsJoin = protocolRoot.join("protocolStudents", JoinType.LEFT);
            protocolStudentsQuery.select(protocolStudentsJoin.get("student").get("id")).where(
                    cb.and(cb.equal(protocolStudentsJoin.get("student").get("id"), root.get("id"))),
                    protocolStudentsJoin.get("grade").get("code").in(OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE),
                    cb.equal(protocolRoot.get("protocolVdata").get("curriculumVersion").get("id"),
                            root.get("curriculumVersion").get("id")));
            filters.add(cb.not(cb.exists(protocolStudentsQuery)));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(JournalStudentDto::of);
    }

    public List<JournalStudentDto> suitedStudents(HoisUserDetails user, Long journalId) {
        return studentRepository.findAll((root, query, cb) -> {
            root.join("person", JoinType.INNER);
            root.join("studentGroup", JoinType.LEFT);
            root.join("curriculumVersion", JoinType.INNER).join("curriculum", JoinType.INNER);

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), user.getSchoolId()));
            filters.add(cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_O.name()));

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
                                            root.get("studentGroup").get("id"))));
            filters.add(cb.exists(studentGroupsQuery));

            // kellel puudub vastavas moodulis positiivne tulemus.
            Subquery<Long> protocolStudentsQuery = query.subquery(Long.class);
            Root<Protocol> protocolRoot = protocolStudentsQuery.from(Protocol.class);
            Join<Object, Object> protocolStudentsJoin = protocolRoot.join("protocolStudents", JoinType.LEFT);
            protocolStudentsQuery.select(protocolStudentsJoin.get("student").get("id")).where(
                    cb.and(cb.equal(protocolStudentsJoin.get("student").get("id"), root.get("id"))),
                    protocolStudentsJoin.get("grade").get("code").in(OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE),
                    cb.equal(protocolRoot.get("protocolVdata").get("curriculumVersion").get("id"),
                            root.get("curriculumVersion").get("id")));
            filters.add(cb.not(cb.exists(protocolStudentsQuery)));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }).stream().map(JournalStudentDto::of).collect(Collectors.toList());
    }

    public Journal saveEndDate(Journal journal, JournalEndDateCommand command) {
        if (command.getEndDate() != null) {
            journal.setEndDate(command.getEndDate());
        }
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

    public Journal removeStudentsFromJournal(Journal journal, JournalStudentsCommand command) {
        journal.getJournalStudents().removeIf(js -> command.getStudents().contains(EntityUtil.getId(js.getStudent())));
        return EntityUtil.save(journal, em);
    }

    public Journal saveJournalEntry(HoisUserDetails user, Journal journal, JournalEntryForm journalEntryForm) {
        validateJournalEntry(journalEntryForm);
        JournalEntry journalEntry = EntityUtil.bindToEntity(journalEntryForm, new JournalEntry(), classifierRepository,
                "journalEntryStudents", "journalEntryCapacityTypes");
        journal.getJournalEntries().add(journalEntry);
        saveJournalEntryStudents(user, journalEntryForm, journalEntry);
        return EntityUtil.save(journal, em);
    }

    public void updateJournalEntry(HoisUserDetails user, JournalEntryForm journalEntryForm, Long journalEntrylId) {
        validateJournalEntry(journalEntryForm);
        JournalEntry journalEntry = em.getReference(JournalEntry.class, journalEntrylId);
        EntityUtil.bindToEntity(journalEntryForm, journalEntry, classifierRepository, "journalEntryStudents",
                "journalEntryCapacityTypes");
        saveJournalEntryStudents(user, journalEntryForm, journalEntry);
        EntityUtil.save(journalEntry, em);
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
                updateJournalStudentEntry(user, journalEntryStudentForm);
            } else {
                saveJournalStudentEntry(user, journalEntry, journalEntryStudentForm);
            }
        }
        EntityUtil.bindEntityCollection(journalEntry.getJournalEntryCapacityTypes(),
                type -> EntityUtil.getCode(type.getCapacityType()), journalEntryForm.getJournalEntryCapacityTypes(),
                it -> {
                    JournalEntryCapacityType type = new JournalEntryCapacityType();
                    type.setCapacityType(classifierRepository.getOne(it));
                    return type;
                });
    }

    private void updateJournalStudentEntry(HoisUserDetails user, JournalEntryStudentForm journalEntryStudentForm) {
        JournalEntryStudent journalEntryStudent = em.getReference(JournalEntryStudent.class, journalEntryStudentForm.getId());
        assertJournalEntryStudentRules(user, journalEntryStudent.getJournalStudent(), journalEntryStudent,
                journalEntryStudentForm);

        if (journalEntryStudent.getGrade() != null
                && !EntityUtil.getCode(journalEntryStudent.getGrade()).equals(journalEntryStudentForm.getGrade())) {
            JournalEntryStudentHistory journalEntryStudentHistory = new JournalEntryStudentHistory();
            journalEntryStudentHistory.setGrade(journalEntryStudent.getGrade());
            journalEntryStudentHistory.setGradeInserted(journalEntryStudent.getGradeInserted());
            journalEntryStudent.getJournalEntryStudentHistories().add(journalEntryStudentHistory);
        }
        if (journalEntryStudentForm.getGrade() != null && !journalEntryStudentForm.getGrade()
                .equals(EntityUtil.getNullableCode(journalEntryStudent.getGrade()))) {
            journalEntryStudent.setGradeInserted(LocalDateTime.now());
        }
        EntityUtil.bindToEntity(journalEntryStudentForm, journalEntryStudent, classifierRepository,
                "journalEntryStudentHistories", "gradeInserted");
    }

    private static void assertJournalEntryStudentRules(HoisUserDetails user, JournalStudent journaStudent,
            JournalEntryStudent journalEntryStudent, JournalEntryStudentForm journalEntryStudentForm) {

        // Mitteõppivate, kuid päevikuga seotud õppurite õppetulemusi muuta ei
        // saa
        if (!StudentUtil.isStudying(journaStudent.getStudent())) {
            throw new ValidationFailedException("journal.messages.changeIsNotAllowedStudentIsNotStudying");
        }

        // Õpetaja ja administratiivne töötaja saavad märkida ainult põhjuseta
        // puudumist
        // new requirement: they can select PUUDUMINE_V if student has accepted absence
        // TODO: validation here can be enhanced actually
//        if ((user.isTeacher() || user.isSchoolAdmin()) && journalEntryStudentForm.getAbsence() != null
//                && Absence.PUUDUMINE_V.name().equals(journalEntryStudentForm.getAbsence())
//                && (journalEntryStudent == null
//                        || !ClassifierUtil.equals(Absence.PUUDUMINE_V, journalEntryStudent.getAbsence()))) {
//            throw new ValidationFailedException("journal.messages.absenceValueIsNotAllowed");
//        }

        // Kui puudumine on rühmajuhataja poolt muudetud põhjendatuks, siis
        // hiljem hilinemise ega puudumise andmeid vastava sissekande juures
        // muuta ei saa
        if (journalEntryStudent != null && journalEntryStudent.getAbsence() != null
                && ClassifierUtil.equals(Absence.PUUDUMINE_V, journalEntryStudent.getAbsence())
                && !Absence.PUUDUMINE_V.name().equals(journalEntryStudentForm.getAbsence())) {
            throw new ValidationFailedException("journal.messages.changingAbsenceIsNotAllowed");
        }
    }

    private void saveJournalStudentEntry(HoisUserDetails user, JournalEntry journalEntry,
            JournalEntryStudentForm journalEntryStudentForm) {
        JournalStudent journalStudent = em.getReference(JournalStudent.class, journalEntryStudentForm.getJournalStudent());
        assertJournalEntryStudentRules(user, journalStudent, null, journalEntryStudentForm);

        JournalEntryStudent journalEntryStudent = EntityUtil.bindToEntity(journalEntryStudentForm,
                new JournalEntryStudent(), classifierRepository, "journalEntryStudentHistories", "gradeInserted");

        journalEntryStudent.setJournalStudent(journalStudent);
        if (journalEntryStudentForm.getGrade() != null) {
            journalEntryStudent.setGradeInserted(LocalDateTime.now());
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
        dto.setLessonPlanDates(result.stream().map(r -> resultAsLocalDateTime(r, 0)).collect(Collectors.toList()));
        return dto;
    }

    public Page<JournalEntryTableDto> journalTableEntries(Long journalId, Pageable pageable) {
        // TODO: If we make NULLS_LAST default, we can remove this hacky line
        PageRequest sortedByEntryDateNullsLast = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                new Sort(new Order(Direction.DESC, "entryDate", NullHandling.NULLS_LAST)));

        JpaNativeQueryBuilder jeQb = new JpaNativeQueryBuilder("from journal_entry je")
                .sort(sortedByEntryDateNullsLast);

        jeQb.requiredCriteria("je.journal_id=:journalId", "journalId", journalId);

        return JpaQueryUtil.pagingResult(jeQb,
                "je.id, je.entry_type_code, je.entry_date, " + "je.content, je.homework, je.homework_duedate", em,
                pageable).map(r -> {
                    JournalEntryTableDto dto = new JournalEntryTableDto();
                    dto.setId(resultAsLong(r, 0));
                    dto.setEntryType(resultAsString(r, 1));
                    dto.setEntryDate(resultAsLocalDate(r, 2));
                    dto.setContent(resultAsString(r, 3));
                    dto.setHomework(resultAsString(r, 4));
                    dto.setHomeworkDuedate(resultAsLocalDate(r, 5));
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

    public List<JournalEntryByDateDto> journalEntriesByDate(Journal journal, Boolean allStudents) {
        List<JournalEntryByDateDto> result = new ArrayList<>();

        for (JournalEntry journalEntry : journal.getJournalEntries()) {
            JournalEntryByDateDto journalEntryByDateDto = EntityUtil.bindToDto(journalEntry,
                    new JournalEntryByDateDto());
            journalEntryByDateDto.setTeacher(PersonUtil.stripIdcodeFromFullnameAndIdcode(journalEntry.getInsertedBy()));
            journalEntryByDateDto.setEntryDate(journalEntry.getEntryDate());

            for (JournalEntryStudent journalEntryStudent : journalEntry.getJournalEntryStudents()) {
                if (Boolean.TRUE.equals(allStudents)
                        || StudentUtil.isStudying(journalEntryStudent.getJournalStudent().getStudent())) {
                    List<JournalEntryStudentResultDto> studentresults = journalEntryByDateDto.getJournalStudentResults()
                            .computeIfAbsent(EntityUtil.getId(journalEntryStudent.getJournalStudent()),
                                    id -> new ArrayList<>());
                    JournalEntryStudentResultDto dto = EntityUtil.bindToDto(journalEntryStudent,
                            new JournalEntryStudentResultDto());
                    studentresults.add(dto);
                }
            }
            result.add(journalEntryByDateDto);
        }

        Collections.sort(result, Comparator.comparing(JournalEntryByDateDto::getEntryDate, Comparator.nullsLast(Comparator.naturalOrder())));
        return result;
    }

    public List<JournalStudentDto> journalStudents(Journal journal, Boolean allStudents) {
        return journal.getJournalStudents().stream()
                .filter(jt -> Boolean.TRUE.equals(allStudents) || StudentUtil.isStudying(jt.getStudent()))
                .map(JournalStudentDto::of).collect(Collectors.toList());
    }

    public byte[] journalAsExcel(Journal journal) {
        return xlsService.generate("journal.xls", Collections.singletonMap("journal", JournalXlsDto.of(journal)));
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
     * Get student journals for view
     * @param studentId
     * @return list of student journals with student's absences
     */
    public List<StudentJournalDto> studentJournals(Long studentId) {
        Query q = em.createNativeQuery("select j.id, j.name_et, j.study_year_id, sy.year_code,"
                + " count(case jes.absence_code when 'PUUDUMINE_H' then 1 else null end) as puudumine_h,"
                + " count(case jes.absence_code when 'PUUDUMINE_P' then 1 else null end) as puudumine_p,"
                + " count(case jes.absence_code when 'PUUDUMINE_V' then 1 else null end) as puudumine_v from journal j"
                + " inner join journal_student js on j.id=js.journal_id"
                + " inner join student s on js.student_id=s.id"
                + " inner join study_year sy on j.study_year_id=sy.id"
                + " inner join journal_entry je on je.journal_id=j.id"
                + " inner join journal_entry_student jes on jes.journal_entry_id=je.id"
                + " where s.id=?1 group by j.id, sy.year_code");
        q.setParameter(1, studentId);

        List<?> data = q.getResultList();
        return StreamUtil.toMappedList(r -> new StudentJournalDto((Object[])r), data);
    }
    
    
    /**
     * Get student journal for view
     * @param studentId
     * @param journalId
     * @return student journal with student's absences
     */
    public StudentJournalDto studentJournal(Long studentId, Long journalId) {
        Query q = em.createNativeQuery("select j.id, j.name_et, j.study_year_id, sy.year_code,"
                + " count(case jes.absence_code when 'PUUDUMINE_H' then 1 else null end) as puudumine_h,"
                + " count(case jes.absence_code when 'PUUDUMINE_P' then 1 else null end) as puudumine_p,"
                + " count(case jes.absence_code when 'PUUDUMINE_V' then 1 else null end) as puudumine_v from journal j"
                + " inner join journal_student js on j.id=js.journal_id"
                + " inner join student s on js.student_id=s.id"
                + " inner join study_year sy on j.study_year_id=sy.id"
                + " inner join journal_entry je on je.journal_id=j.id"
                + " inner join journal_entry_student jes on jes.journal_entry_id=je.id"
                + " where s.id=?1 and j.id=?2 group by j.id, sy.year_code");
        q.setParameter(1, studentId);
        q.setParameter(2, journalId);
        Object studentJournal = q.getSingleResult();

        List<StudentJournalEntryDto> journalEntries = getStudentJournalEntries(studentId, journalId);
        return new StudentJournalDto((Object[]) studentJournal, journalEntries);
    }

    private List<StudentJournalEntryDto> getStudentJournalEntries(Long studentId, Long journalId) {
        Query entriesQuery = em.createNativeQuery("select je.id, je.entry_type_code, je.entry_date, je.content,"
                + " ( select value from classifier where code = jes.grade_code ),"
                + " jes.grade_inserted, jes.add_info, je.homework, je.homework_duedate from journal j"
                + " inner join journal_entry je on j.id = je.journal_id"
                + " inner join journal_student js on j.id = js.journal_id"
                + " inner join journal_entry_student jes on je.id = jes.journal_entry_id and jes.journal_student_id = js.id"
                + " inner join student s on js.student_id = s.id"
                + " where j.id = ?1 and s.id = ?2"
                + " order by je.entry_date is null, je.entry_date desc");
        entriesQuery.setParameter(1, journalId);
        entriesQuery.setParameter(2, studentId);
        List<?> data = entriesQuery.getResultList();
        
        List<StudentJournalEntryDto> entries = StreamUtil.toMappedList(r -> new StudentJournalEntryDto(resultAsLong(r, 0), 
                resultAsString(r, 1), resultAsLocalDate(r, 2), resultAsString(r, 3), resultAsString(r, 4), resultAsLocalDateTime(r, 5), 
                resultAsString(r, 6), resultAsString(r, 7), resultAsLocalDate(r, 8)), data);
        
        return getEntriesWithPreviousResults(entries, studentId);
    }
    
    private List<StudentJournalEntryDto> getEntriesWithPreviousResults(List<StudentJournalEntryDto> entries, Long studentId) {
        Set<Long> entriesIds = StreamUtil.toMappedSet(StudentJournalEntryDto::getId, entries.stream());
        if(!entriesIds.isEmpty()) {
            Query previousResultQuerys = em.createNativeQuery("select je.id, ( select value from classifier where code = jesh.grade_code ),"
                    + " jesh.grade_inserted from journal j"
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
                    Collectors.mapping(r -> new StudentJournalEntryPreviousResultDto(resultAsString(r, 1), resultAsLocalDateTime(r, 2)),
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
     * @return study year info and a list of student's journal tasks
     */
    public StudentJournalTaskListDto studentJournalTasks(Long schoolId, Long studentId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);

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
     * @return study year info and a list of student's study entries
     */
    public StudentJournalStudyListDto studentJournalStudy(Long schoolId, Long studentId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);

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
     * @return list of student's absences
     */
    public List<StudentJournalAbsenceDto> studentAbsences(Long schoolId, Long studentId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);

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
    
    //StudentJournalResultDto
    
    /**
     * Get student's last 10 results for view.
     * @param schoolId
     * @param studentId
     * @return list of student's last results
     */
    public List<StudentJournalResultDto> studentLastResults(Long schoolId, Long studentId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);

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
    
}
