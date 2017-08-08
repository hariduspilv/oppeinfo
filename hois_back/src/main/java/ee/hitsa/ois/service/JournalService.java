package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.validation.ConstraintViolation;
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

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.domain.timetable.JournalEntryCapacityType;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalEntryStudentHistory;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.enums.Absence;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.JournalEntryRepository;
import ee.hitsa.ois.repository.JournalEntryStudentRepository;
import ee.hitsa.ois.repository.JournalRepository;
import ee.hitsa.ois.repository.JournalStudentRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.validation.JournalEntryValidation;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.timetable.JournalEndDateCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryStudentForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalStudentsCommand;
import ee.hitsa.ois.web.commandobject.timetable.StudentNameSearchCommand;
import ee.hitsa.ois.web.dto.timetable.JournalDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryByDateDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryLessonInfoDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryStudentResultDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryTableDto;
import ee.hitsa.ois.web.dto.timetable.JournalSearchDto;
import ee.hitsa.ois.web.dto.timetable.JournalStudentDto;
import ee.hitsa.ois.web.dto.timetable.JournalXlsDto;

@Service
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private JournalStudentRepository journalStudentRepository;
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private JournalEntryStudentRepository journalEntryStudentRepository;
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

            if (command.getTeacher() != null) {
                searchByTeacherCriteria(command, root, query, cb, filters);
            }

            if (user.isTeacher()) {
                searchByTeacherPersonCriteria(user, root, query, cb, filters);
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

    private static void searchByTeacherPersonCriteria(HoisUserDetails user, Root<Journal> root, CriteriaQuery<?> query,
            CriteriaBuilder cb, List<Predicate> filters) {
        Subquery<Long> journalTeachersQuery = query.subquery(Long.class);
        Root<Journal> journalRoot = journalTeachersQuery.from(Journal.class);
        Join<Object, Object> journalTeachersJoin = journalRoot.join("journalTeachers");
        journalTeachersQuery.select(journalRoot.get("id")).where(cb.and(cb.equal(journalRoot.get("id"), root.get("id")),
                cb.equal(journalTeachersJoin.get("teacher").get("person").get("id"), user.getPersonId())));
        filters.add(cb.exists(journalTeachersQuery));
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

    public JournalDto get(Journal journal) {
        // TODO: refactor to use native query
        return JournalDto.of(journal);
    }

    public Page<JournalStudentDto> otherStudents(HoisUserDetails user, Long journalId, StudentNameSearchCommand command,
            Pageable pageable) {
        return studentRepository.findAll((root, query, cb) -> {
            root.fetch("person", JoinType.INNER);
            root.fetch("studentGroup", JoinType.LEFT);
            root.fetch("curriculumVersion", JoinType.INNER).fetch("curriculum", JoinType.INNER);

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
            root.fetch("person", JoinType.INNER);
            root.fetch("studentGroup", JoinType.LEFT);
            root.fetch("curriculumVersion", JoinType.INNER).fetch("curriculum", JoinType.INNER);

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
        return journalRepository.save(journal);
    }

    public Journal addStudentsToJournal(Journal journal, JournalStudentsCommand command) {
        List<Long> existingStudents = journal.getJournalStudents().stream().map(js -> EntityUtil.getId(js.getStudent()))
                .collect(Collectors.toList());
        for (Long student : command.getStudents()) {
            if (!existingStudents.contains(student)) {
                journal.getJournalStudents().add(JournalStudent.of(studentRepository.getOne(student)));
            }
        }
        return journalRepository.save(journal);
    }

    public Journal removeStudentsFromJournal(Journal journal, JournalStudentsCommand command) {
        journal.getJournalStudents().removeIf(js -> command.getStudents().contains(EntityUtil.getId(js.getStudent())));
        return journalRepository.save(journal);
    }

    public Journal saveJournalEntry(HoisUserDetails user, Journal journal, JournalEntryForm journalEntryForm) {
        validateJournalEntry(journalEntryForm);
        JournalEntry journalEntry = EntityUtil.bindToEntity(journalEntryForm, new JournalEntry(), classifierRepository,
                "journalEntryStudents", "journalEntryCapacityTypes");
        journal.getJournalEntries().add(journalEntry);
        saveJournalEntryStudents(user, journalEntryForm, journalEntry);
        return journalRepository.save(journal);
    }

    public void updateJournalEntry(HoisUserDetails user, JournalEntryForm journalEntryForm, Long journalEntrylId) {
        validateJournalEntry(journalEntryForm);
        JournalEntry journalEntry = journalEntryRepository.getOne(journalEntrylId);
        EntityUtil.bindToEntity(journalEntryForm, journalEntry, classifierRepository, "journalEntryStudents",
                "journalEntryCapacityTypes");
        saveJournalEntryStudents(user, journalEntryForm, journalEntry);
        journalEntryRepository.save(journalEntry);
    }

    private void validateJournalEntry(JournalEntryForm journalEntryForm) {
        if (StringUtils.hasText(journalEntryForm.getHomework())) {
            Set<ConstraintViolation<JournalEntryForm>> errors = validator.validate(journalEntryForm,
                    JournalEntryValidation.Homework.class);
            if (!errors.isEmpty()) {
                throw new ValidationFailedException(errors);
            }
        }

        if (JournalEntryType.SISSEKANNE_T.name().equals(journalEntryForm.getEntryType())) {
            Set<ConstraintViolation<JournalEntryForm>> errors = validator.validate(journalEntryForm,
                    JournalEntryValidation.Lesson.class);
            if (!errors.isEmpty()) {
                throw new ValidationFailedException(errors);
            }
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
        JournalEntryStudent journalEntryStudent = journalEntryStudentRepository.getOne(journalEntryStudentForm.getId());
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
        if ((user.isTeacher() || user.isSchoolAdmin()) && journalEntryStudentForm.getAbsence() != null
                && Absence.PUUDUMINE_V.name().equals(journalEntryStudentForm.getAbsence())
                && (journalEntryStudent == null
                        || !ClassifierUtil.equals(Absence.PUUDUMINE_V, journalEntryStudent.getAbsence()))) {
            throw new ValidationFailedException("journal.messages.absenceValueIsNotAllowed");
        }

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
        JournalStudent journalStudent = journalStudentRepository.getOne(journalEntryStudentForm.getJournalStudent());
        assertJournalEntryStudentRules(user, journalStudent, null, journalEntryStudentForm);

        JournalEntryStudent journalEntryStudent = EntityUtil.bindToEntity(journalEntryStudentForm,
                new JournalEntryStudent(), classifierRepository, "journalEntryStudentHistories", "gradeInserted");

        journalEntryStudent.setJournalStudent(journalStudent);
        if (journalEntryStudentForm.getGrade() != null) {
            journalEntryStudent.setGradeInserted(LocalDateTime.now());
        }
        List<Long> existingJournalStudentEntries = journalEntry.getJournalEntryStudents().stream()
                .map(it -> EntityUtil.getId(it.getJournalStudent())).collect(Collectors.toList());
        if (!existingJournalStudentEntries.contains(EntityUtil.getId(journalStudent))) {
            journalEntry.getJournalEntryStudents().add(journalEntryStudent);
        } else {
            throw new ValidationFailedException("journal.messages.dublicateJournalStudentInJournalEntry");
        }
    }

    public JournalEntryLessonInfoDto journalEntryLessonInfo(Journal journal) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(
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

        JpaQueryUtil.NativeQueryBuilder jeQb = new JpaQueryUtil.NativeQueryBuilder("from journal_entry je")
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
        JournalEntry journalEntry = journalEntryRepository.getOne(journalEntrylId);
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

}