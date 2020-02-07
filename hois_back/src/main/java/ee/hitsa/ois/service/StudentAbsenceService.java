package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.domain.student.StudentAbsenceLesson;
import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalEntryStudentLessonAbsence;
import ee.hitsa.ois.enums.Absence;
import ee.hitsa.ois.repository.JournalEntryStudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentAbsenceUtil;
import ee.hitsa.ois.web.commandobject.student.StudentAbsenceLessonsForm;
import ee.hitsa.ois.web.commandobject.student.StudentAbsenceSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.student.StudentAbsenceDto;

@Transactional
@Service
public class StudentAbsenceService {

    @Autowired
    private EntityManager em;
    @Autowired 
    private JournalEntryStudentRepository journalEntryStudentRepository;
    @Autowired
    private StudyYearService studyYearService;

    private static final String SELECT = "sa.id as absenceId, s.id as studentId, p.firstname, p.lastname, sg.code, sa.valid_from, "
            + "sa.valid_thru, sa.is_accepted, sa.is_rejected, sa.cause, sa.inserted_by, sa.accepted_by, sa.changed_by, sa.is_lesson_absence, sa.reject_reason, s.type_code studentType ";
    private static final String FROM =
              "from student_absence sa "
            + "join student s on s.id = sa.student_id "
            + "left join student_group sg on sg.id = s.student_group_id "
            + "join person p on p.id = s.person_id ";

    private static final String ABSENCE_ENTRY_FROM = "from journal_entry_student jes "
            + "join journal_student js on js.id = jes.journal_student_id "
            + "join journal_entry je on je.id = jes.journal_entry_id";
    
    private static final String ABSENCE_LESSON_ENTRY_FROM = "from journal_entry_student jes "
            + "join journal_entry_student_lesson_absence jesla on jesla.journal_entry_student_id = jes.id "
            + "join journal_student js on js.id = jes.journal_student_id "
            + "join journal_entry je on je.id = jes.journal_entry_id "
            + "join student_absence sa on sa.student_id = js.student_id "
            + "join student_absence_lesson sal on sal.student_absence_id = sa.id "
            + "and sal.absence = je.entry_date and sal.lesson_nr = jesla.lesson_nr + coalesce(je.start_lesson_nr - 1, 0)";
    
    private static final String FILTER_BY_STUDY_PERIOD = " exists("
            + "select sp.id "
            + "from study_period sp "
            + "where sp.id = :studyPeriod "
            + "and sp.start_date <= sa.valid_from "
            + "and (case when sa.valid_thru is null then sp.end_date >= sa.valid_from else sp.end_date >= sa.valid_thru end)) ";

    private static final String FILTER_BY_STUDY_YEAR = " exists("
            + "select sy.id "
            + "from study_year sy "
            + "where sy.id = :studyYear "
            + "and sy.start_date <= sa.valid_from "
            + "and (case when sa.valid_thru is null then sa.valid_from >= sy.start_date and sa.valid_from <= sy.end_date else sy.start_date <= sa.valid_thru and sy.end_date >= sa.valid_from end)) ";

    private static final String FILTER_BY_LEADING_TEACHER_CURRICULUMS = " exists("
            + "select c.id from curriculum_version cv "
            + "join curriculum c on c.id = cv.curriculum_id "
            + "where s.curriculum_version_id = cv.id and c.id in (:userCurriculumIds))";

    private static final String PRACTICE_JOURNAL_CAUSE = "Praktikal (leping)";
    private static final String DIRECTIVE_CAUSE = "Õppetegevus (käskiri)";

    public Page<StudentAbsenceDto> search(HoisUserDetails user, StudentAbsenceSearchCommand criteria,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(FROM).sort(pageable);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria(FILTER_BY_LEADING_TEACHER_CURRICULUMS, "userCurriculumIds", user.getCurriculumIds());
        }

        qb.optionalCriteria("s.curriculum_version_id in :curriculumVersions", "curriculumVersions", criteria.getCurriculumVersions());
        qb.optionalContains("sg.code", "studentGroupCode", criteria.getStudentGroupCode());
        qb.optionalCriteria("sg.id = :groupId", "groupId", criteria.getStudentGroupId());
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getStudentName());
        qb.optionalCriteria("sa.is_accepted = :isAccepted", "isAccepted", criteria.getIsAccepted());

        if (Boolean.TRUE.equals(criteria.getIsRejected())) {
            qb.optionalCriteria("(sa.is_rejected = :isRejected or (sa.is_rejected is null and sa.is_accepted != :isRejected))", "isRejected", Boolean.TRUE);
        } else if (Boolean.FALSE.equals(criteria.getIsRejected())){
            qb.optionalCriteria("sa.is_rejected = :isRejected", "isRejected", Boolean.FALSE);
        }
        qb.optionalCriteria(FILTER_BY_STUDY_PERIOD, "studyPeriod", criteria.getStudyPeriod());
        qb.requiredCriteria(FILTER_BY_STUDY_YEAR, "studyYear", criteria.getStudyYear());

        if(user.isTeacher()) {
            qb.requiredCriteria("sg.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        }

        Page<Object[]> results = JpaQueryUtil.pagingResult(qb, SELECT, em, pageable);
        boolean hasPermissionToChangeStatus = StudentAbsenceUtil.hasPermissionToChangeStatus(user);
        return results.map(r -> rowToDto(hasPermissionToChangeStatus, r));
    }

    private static StudentAbsenceDto rowToDto(boolean hasPermissionToChangeStatus,  Object[] row) {
        StudentAbsenceDto dto = new StudentAbsenceDto();
        dto.setId(resultAsLong(row, 0));
        String fullname = PersonUtil.fullnameOptionalGuest(resultAsString(row, 2), resultAsString(row, 3), resultAsString(row, 15));
        dto.setStudent(new AutocompleteResult(resultAsLong(row, 1), fullname, fullname));
        dto.setStudentGroup(resultAsString(row, 4));
        dto.setValidFrom(resultAsLocalDate(row, 5));
        dto.setValidThru(resultAsLocalDate(row, 6));
        dto.setIsAccepted(resultAsBoolean(row, 7));
        dto.setIsRejected(resultAsBoolean(row, 8));
        dto.setCause(resultAsString(row, 9));
        dto.setApplicant(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(row, 10)));
        if(Boolean.TRUE.equals(dto.getIsAccepted()) || Boolean.TRUE.equals(dto.getIsRejected())) {
            dto.setAcceptor(resultAsString(row, 11) != null ? resultAsString(row, 11)
                    : PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(row, 12)));
        }
        dto.setCanAccept(Boolean.valueOf(hasPermissionToChangeStatus && Boolean.FALSE.equals(dto.getIsAccepted())
                && (dto.getIsRejected() != null ? Boolean.FALSE.equals(dto.getIsRejected()) : true)));
        dto.setCanReject(Boolean.valueOf(hasPermissionToChangeStatus
                && (Boolean.FALSE.equals(dto.getIsAccepted())
                        || StudentAbsenceUtil.validTodayOrInFuture(dto.getValidFrom(), dto.getValidThru()))
                && (dto.getIsRejected() != null ? Boolean.FALSE.equals(dto.getIsRejected()) : true)));
        dto.setIsLessonAbsence(resultAsBoolean(row, 13));
        dto.setRejectReason(resultAsString(row, 14));
        return dto;
    }

    public StudentAbsenceDto get(HoisUserDetails user, StudentAbsence studentAbsence) {
        StudentAbsenceDto dto = StudentAbsenceDto.of(studentAbsence);
        dto.setCanAccept(Boolean.valueOf(StudentAbsenceUtil.canAccept(user, studentAbsence)));
        dto.setCanReject(Boolean.valueOf(StudentAbsenceUtil.canReject(user, studentAbsence)));
        return dto;
    }

    public StudentAbsence accept(HoisUserDetails user, StudentAbsence studentAbsence) {
        updateJournalEntryStudentAbsences(studentAbsence, Absence.PUUDUMINE_V);
        studentAbsence.setIsAccepted(Boolean.TRUE);
        studentAbsence.setIsLessonAbsence(Boolean.FALSE);
        studentAbsence.setAcceptedBy(PersonUtil.fullname(em.getReference(Person.class, user.getPersonId())));
        return EntityUtil.save(studentAbsence, em);
    }
    
    public StudentAbsence acceptByLessons(HoisUserDetails user, StudentAbsence studentAbsence,
            StudentAbsenceLessonsForm form) {
        studentAbsence.setIsAccepted(Boolean.TRUE);
        studentAbsence.setIsLessonAbsence(Boolean.TRUE);
        studentAbsence.setAcceptedBy(PersonUtil.fullname(em.getReference(Person.class, user.getPersonId())));

        Map<LocalDate, Map<Long, Boolean>> lessonsByDate = form.getLessonsByDate();
        lessonsByDate.entrySet().removeIf(set -> set.getValue() == null);
        for (LocalDate date : lessonsByDate.keySet()) {
            for (Long lessonNr : lessonsByDate.get(date).keySet()) {
                if (Boolean.TRUE.equals(lessonsByDate.get(date).get(lessonNr))) {
                    StudentAbsenceLesson absenceLesson = new StudentAbsenceLesson();
                    absenceLesson.setStudentAbsence(studentAbsence);
                    absenceLesson.setAbsence(date);
                    absenceLesson.setLessonNr(lessonNr);
                    EntityUtil.save(absenceLesson, em);
                }
            }
        }
        updateJournalEntryStudentAbsenceLessons(studentAbsence);
        return EntityUtil.save(studentAbsence, em);
    }

    public StudentAbsence reject(HoisUserDetails user, StudentAbsence studentAbsence, String rejectReason) {
        return reject(PersonUtil.fullname(em.getReference(Person.class, user.getPersonId())), studentAbsence,
                rejectReason);
    }

    public StudentAbsence reject(String rejecter, StudentAbsence studentAbsence, String rejectReason) {
        studentAbsence.setIsRejected(Boolean.TRUE);
        studentAbsence.setIsAccepted(Boolean.FALSE);
        studentAbsence.setAcceptedBy(rejecter);
        studentAbsence.setRejectReason(StringUtils.hasText(rejectReason) ? rejectReason : null);
        return EntityUtil.save(studentAbsence, em);
    }

    private void updateJournalEntryStudentAbsences(StudentAbsence studentAbsence, Absence absence) {
        Set<Long> absences = getAbsenceEntries(studentAbsence);
        if(!absences.isEmpty()) {
            journalEntryStudentRepository.acceptAbsences(absence.name(), absences);
        }
    }

    private void updateJournalEntryStudentAbsenceLessons(StudentAbsence studentAbsence) {
        createAbsenceLessonsResultingFromOverlap(studentAbsence);
        Set<Long> absences = getAbsenceLessonsEntries(studentAbsence);
        if(!absences.isEmpty()) {
            journalEntryStudentRepository.acceptAbsenceLessons(Absence.PUUDUMINE_V.name(), absences);
        }
    }

    private void createAbsenceLessonsResultingFromOverlap(StudentAbsence studentAbsence) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal_entry_student jes " +
                "join journal_student js on js.id = jes.journal_student_id " +
                "join journal_entry je on je.id = jes.journal_entry_id " +
                "join student_absence sa on sa.student_id = js.student_id " +
                "join student_absence_lesson sal on sal.student_absence_id = sa.id and sal.absence = je.entry_date");
        qb.requiredCriteria("js.student_id = :studentId", "studentId", EntityUtil.getId(studentAbsence.getStudent()));
        qb.requiredCriteria("jes.absence_code = :noReason", "noReason", Absence.PUUDUMINE_P);
        qb.requiredCriteria("sa.id = :studentAbsenceId", "studentAbsenceId", EntityUtil.getId(studentAbsence));
        qb.filter("(je.entry_date = sa.valid_from or (je.entry_date >= sa.valid_from and je.entry_date <= sa.valid_thru))");
        qb.filter("je.start_lesson_nr is not null and sal.lesson_nr >= je.start_lesson_nr " +
                "and sal.lesson_nr <= je.start_lesson_nr + coalesce(je.lessons - 1, 0)");

        List<?> data = qb.select("jes.id", em).getResultList();
        Set<Long> entriesWithOverlap = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);

        if (!entriesWithOverlap.isEmpty()) {
            List<Object[]> entryData = em.createQuery("select jes, jes.journalEntry from JournalEntryStudent jes " +
                    "where jes.id in (:jesIds)", Object[].class)
                    .setParameter("jesIds", entriesWithOverlap)
                    .getResultList();

            for (Object[] row : entryData) {
                JournalEntryStudent jes = (JournalEntryStudent) row[0];
                JournalEntry je = (JournalEntry) row[1];

                int lessons = je.getLessons() != null ? je.getLessons().intValue() : 1;
                for (int i = 1; i <= lessons; i++) {
                    JournalEntryStudentLessonAbsence jesla = new JournalEntryStudentLessonAbsence();
                    jesla.setJournalEntryStudent(jes);
                    jesla.setLessonNr(Long.valueOf(i));
                    jesla.setAbsence(jes.getAbsence());
                    jesla.setAbsenceInserted(jes.getAbsenceInserted());
                    jes.getJournalEntryStudentLessonAbsences().add(jesla);
                }
                jes.setAbsence(null);
                jes.setAbsenceInserted(null);
                jes.setIsLessonAbsence(Boolean.TRUE);
                EntityUtil.save(jes, em);
            }
        }
    }

    private Set<Long> getAbsenceEntries(StudentAbsence studentAbsence) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(ABSENCE_ENTRY_FROM);
        qb.requiredCriteria("js.student_id = :studentId", "studentId", EntityUtil.getId(studentAbsence.getStudent()));
        qb.requiredCriteria("jes.absence_code = :noReason", "noReason", Absence.PUUDUMINE_P);
        if(studentAbsence.getValidThru() != null) {
            qb.requiredCriteria("je.entry_date >= :absenceFrom", "absenceFrom", studentAbsence.getValidFrom());
            qb.requiredCriteria("je.entry_date <= :absenceThru", "absenceThru", studentAbsence.getValidThru());
        } else {
            qb.requiredCriteria("je.entry_date = :absenceFrom", "absenceFrom", studentAbsence.getValidFrom());
        }
        List<?> result = qb.select("jes.id", em).getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), result);
    }

    private Set<Long> getAbsenceLessonsEntries(StudentAbsence studentAbsence) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(ABSENCE_LESSON_ENTRY_FROM);
        qb.requiredCriteria("js.student_id = :studentId", "studentId", EntityUtil.getId(studentAbsence.getStudent()));
        qb.requiredCriteria("jesla.absence_code = :noReason", "noReason", Absence.PUUDUMINE_P);
        qb.requiredCriteria("sa.id = :studentAbsenceId", "studentAbsenceId", EntityUtil.getId(studentAbsence));
        List<?> result = qb.select("jesla.id", em).getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), result);
    }
    

    public boolean hasUnaccepted(HoisUserDetails user) {
        StudyYear currentYear = studyYearService.getCurrentStudyYear(user.getSchoolId());
        if(currentYear == null) {
            return false;
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_absence sa join student s on s.id = sa.student_id");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.filter("sa.is_accepted = false");
        qb.filter("sa.is_rejected = false");
        qb.requiredCriteria(FILTER_BY_STUDY_YEAR, "studyYear", EntityUtil.getId(currentYear));
        if(user.isTeacher()) {
            qb.requiredCriteria("s.student_group_id in (select sg.id from student_group sg where sg.teacher_id = :teacherId)", "teacherId", user.getTeacherId());
        }
        List<?> data = qb.select("sa.id", em).setMaxResults(1).getResultList();
        return !data.isEmpty();
    }

    public void createContractAbsence(Contract contract) {
        StudentAbsence studentAbsence = studentAbsence(contract.getStudent(), contract.getStartDate(),
                contract.getEndDate(), PRACTICE_JOURNAL_CAUSE);
        studentAbsence.setContract(contract);
        EntityUtil.save(studentAbsence, em);
        Absence absenceCode = Boolean.TRUE.equals(contract.getIsPracticeAbsence()) ? Absence.PUUDUMINE_PR
                : Absence.PUUDUMINE_V;
        updateJournalEntryStudentAbsences(studentAbsence, absenceCode);
        EntityUtil.save(contract, em);
    }

    public void createDirectiveAbsence(DirectiveStudent directiveStudent) {
        StudentAbsence studentAbsence = studentAbsence(directiveStudent.getStudent(), directiveStudent.getStartDate(),
                directiveStudent.getEndDate(), DIRECTIVE_CAUSE);
        studentAbsence.setDirectiveStudent(directiveStudent);
        updateJournalEntryStudentAbsences(studentAbsence, Absence.PUUDUMINE_V);
        EntityUtil.save(studentAbsence, em);
    }

    private StudentAbsence studentAbsence(Student student, LocalDate startDate, LocalDate endDate, String cause) {
        StudentAbsence studentAbsence = new StudentAbsence();
        studentAbsence.setStudent(student);
        studentAbsence.setValidFrom(startDate);
        studentAbsence.setValidThru(endDate);
        studentAbsence.setCause(cause);
        studentAbsence.setIsAccepted(Boolean.TRUE);
        studentAbsence.setIsRejected(Boolean.FALSE);
        studentAbsence.setIsLessonAbsence(Boolean.FALSE);
        return studentAbsence;
    }
}
