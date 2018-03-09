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
import java.util.Map.Entry;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
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
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.JournalRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.MoodleUtil;
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
import ee.hitsa.ois.web.dto.moodle.EnrollResult;
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
import ee.hois.moodle.EnrollResponse;
import ee.hois.moodle.Grade;
import ee.hois.moodle.GradeItem;

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
    private ClassifierService classifierService;
    @Autowired
    private EntityManager em;
    @Autowired
    private Validator validator;
    @Autowired
    private XlsService xlsService;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private MoodleService moodleService;
    
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
        }, pageable).map(j -> JournalSearchDto.of(user, j));
    }

    private static void searchByTeacherCriteria(JournalSearchCommand command, Root<Journal> root,
            CriteriaQuery<?> query, CriteriaBuilder cb, List<Predicate> filters) {
        Subquery<Long> journalTeachersQuery = query.subquery(Long.class);
        Root<Journal> journalTeachersJournalRoot = journalTeachersQuery.from(Journal.class);
        Join<Object, Object> journalTeachersJoin = journalTeachersJournalRoot.join("journalTeachers");
        journalTeachersQuery.select(journalTeachersJournalRoot.get("id")).where(cb.equal(journalTeachersJoin.get("teacher").get("id"), command.getTeacher()));
        
        
        Subquery<Long> studentGroupTeacherQuery = query.subquery(Long.class);
        Root<Journal> studentGroupJournalRoot = studentGroupTeacherQuery.from(Journal.class);
        Join<Object, Object> journalOmoduleThemesJoin = studentGroupJournalRoot.join("journalOccupationModuleThemes", JoinType.INNER);
        studentGroupTeacherQuery.select(studentGroupJournalRoot.get("id"))
                .where(cb.equal(journalOmoduleThemesJoin.get("lessonPlanModule").get("lessonPlan").get("studentGroup")
                        .get("teacher").get("id"), command.getTeacher()));

        filters.add(cb.or(cb.in(root.get("id")).value(journalTeachersQuery), cb.in(root.get("id")).value(studentGroupTeacherQuery)));
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
        journal.setStatus(em.getReference(Classifier.class, JournalStatus.PAEVIK_STAATUS_K.name()));
        return EntityUtil.save(journal, em);
    }

    public Journal unconfirm(Journal journal) {
        journal.setStatus(em.getReference(Classifier.class, JournalStatus.PAEVIK_STAATUS_T.name()));
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
            //TODO: add StudentStatus.OPPURSTAATUS_A
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
                                            root.get("studentGroup").get("id")))
                            
                            );
            filters.add(cb.exists(studentGroupsQuery));

            Journal journal = journalRepository.findOne(journalId);
            Set<Long> modules = StreamUtil.toMappedSet(t -> EntityUtil.getId(
                    t.getCurriculumVersionOccupationModuleTheme().getModule().getCurriculumModule()), journal.getJournalOccupationModuleThemes());
            
            // kellel puudub vastavas moodulis positiivne tulemus.
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

    public Journal saveMoodleCourseLink(HoisUserDetails user, Journal journal, Long courseId) {
        if (!moodleService.courseLinkPossible(user, courseId, getTeachersIdcodes(journal))) {
            throw new ValidationFailedException("main.messages.error.invalidMoodleCourse");
        }
        journal.setMoodleCourseId(courseId);
        EntityUtil.setUsername(user.getUsername(), em);
        return EntityUtil.save(journal, em);
    }

    public EnrollResult moodleEnrollStudents(HoisUserDetails user, Journal journal) {
        List<String> academicianIds = getTeachersIdcodes(journal);
        List<String> studentIds = StreamUtil.toMappedList(js -> js.getStudent().getPerson().getIdcode(), 
                journal.getJournalStudents().stream().filter(js -> js.getIsMoodleRegistered() != Boolean.TRUE));
        if (studentIds.isEmpty()) {
            return MoodleUtil.createEmptyEnrollResult();
        }
        EnrollResponse response = moodleService.enrollStudents(user, journal.getMoodleCourseId(), academicianIds, studentIds);
        Map<String, JournalStudent> studentMap = getMoodleMappedStudents(journal);
        EntityUtil.setUsername(user.getUsername(), em);
        for (String enrolledUser : response.getEnrolled()) {
            studentMap.get(enrolledUser).setIsMoodleRegistered(Boolean.TRUE);
        }
        EnrollResult result = new EnrollResult();
        result.setEnrolled(response.getEnrolled().size());
        result.setFailed(StreamUtil.toMappedList(
                u -> PersonUtil.fullname(studentMap.get(u).getStudent().getPerson()), 
                response.getFailed()));
        result.setMissingUser(StreamUtil.toMappedList(
                u -> PersonUtil.fullname(studentMap.get(u).getStudent().getPerson()), 
                response.getMissingUser()));
        return result;
    }

    public List<GradeItem> moodleImportGradeItems(HoisUserDetails user, Journal journal) {
        List<String> academicianIds = getTeachersIdcodes(journal);
        Map<Long, JournalEntry> entryMap = getMoodleMappedEntries(journal);
        List<GradeItem> items = moodleService.getGradeItems(user, journal.getMoodleCourseId(), academicianIds);
        List<JournalEntry> newEntries = new ArrayList<>();
        ClassifierCache classifiers = new ClassifierCache(classifierService);
        EntityUtil.setUsername(user.getUsername(), em);
        for (GradeItem item : items) {
            JournalEntry entry = entryMap.get(item.getId());
            if (entry == null) {
                entry = new JournalEntry();
                entry.setMoodleGradeItemId(item.getId());
                newEntries.add(entry);
            }
            entry.setEntryType(classifiers.getByCode(
                    MoodleUtil.gradeItemTypeToJournalEntryType(item.getType()).name(), 
                    MainClassCode.SISSEKANNE));
            entry.setNameEt(item.getName());
        }
        for (JournalEntry entry : newEntries) {
            journal.getJournalEntries().add(entry);
        }
        EntityUtil.save(journal, em);
        return items;
    }

    public void moodleImportAllGrades(HoisUserDetails user, Journal journal) {
        List<GradeItem> gradeItems = moodleImportGradeItems(user, journal);
        moodleImportGrades(user, journal, 
                StreamUtil.toMappedList(js -> js.getStudent().getPerson().getIdcode(), 
                        journal.getJournalStudents().stream().filter(js -> js.getIsMoodleRegistered() == Boolean.TRUE)), 
                StreamUtil.toMappedList(GradeItem::getId, gradeItems), 
                StreamUtil.toMap(GradeItem::getId, gradeItems));
    }
    
    public void moodleImportMissingGrades(HoisUserDetails user, Journal journal) {
        List<GradeItem> gradeItems = moodleImportGradeItems(user, journal);
        moodleImportGrades(user, journal, 
                getStudentsWithMissingGrades(journal, gradeItems), 
                getEntriesWithMissingGrades(journal, gradeItems), 
                StreamUtil.toMap(GradeItem::getId, gradeItems));
    }

    private void moodleImportGrades(HoisUserDetails user, Journal journal,
            List<String> studentIds, List<Long> gradeItemIds, Map<Long, GradeItem> gradeItemMap) {
        List<String> academicianIds = getTeachersIdcodes(journal);
        Map<Long, JournalEntry> entryMap = getMoodleMappedEntries(journal);
        Map<String, JournalStudent> studentMap = getMoodleMappedStudents(journal);
        Map<Long, Map<String, JournalEntryStudent>> entryStudentMap = getMoodleMappedEntriesStudents(journal);
        Map<Long, List<Grade>> grades = moodleService.getGradesByItemId(user, journal.getMoodleCourseId(), academicianIds, 
                gradeItemIds, studentIds);
        ClassifierCache classifiers = new ClassifierCache(classifierService);
        EntityUtil.setUsername(user.getUsername(), em);
        for (Entry<Long, List<Grade>> moodleEntry : grades.entrySet()) {
            JournalEntry journalEntry = entryMap.get(moodleEntry.getKey());
            Map<String, JournalEntryStudent> gradeMap = entryStudentMap.get(moodleEntry.getKey());
            List<JournalEntryStudent> newGrades = new ArrayList<>();
            for (Grade grade : moodleEntry.getValue()) {
                JournalEntryStudent journalEntryStudent = gradeMap.get(grade.getStudent());
                if (journalEntryStudent == null) {
                    journalEntryStudent = new JournalEntryStudent();
                    journalEntryStudent.setJournalStudent(studentMap.get(grade.getStudent()));
                    newGrades.add(journalEntryStudent);
                }
                Object points = grade.getPoints();
                if (points == null) {
                    journalEntryStudent.setGrade(null);
                    journalEntryStudent.setAddInfo(null);
                } else {
                    GradeItem gradeItem = gradeItemMap.get(moodleEntry.getKey());
                    journalEntryStudent.setGrade(classifiers.getByCode(
                            MoodleUtil.pointsToGrade(points, gradeItem.getMax().longValue()).name(), 
                            MainClassCode.KUTSEHINDAMINE));
                    journalEntryStudent.setAddInfo(points.toString());
                }
            }
            for (JournalEntryStudent journalEntryStudent : newGrades) {
                journalEntry.getJournalEntryStudents().add(journalEntryStudent);
            }
        }
        EntityUtil.save(journal, em);
    }
    
    private List<String> getTeachersIdcodes(Journal journal) {
        return StreamUtil.toMappedList(jt -> jt.getTeacher().getPerson().getIdcode(), 
                journal.getJournalTeachers());
    }

    private Map<Long, JournalEntry> getMoodleMappedEntries(Journal journal) {
        return StreamUtil.toMap(JournalEntry::getMoodleGradeItemId, 
                journal.getJournalEntries().stream().filter(je -> je.getMoodleGradeItemId() != null));
    }

    private Map<String, JournalStudent> getMoodleMappedStudents(Journal journal) {
        return StreamUtil.toMap(js -> js.getStudent().getPerson().getIdcode(), 
                journal.getJournalStudents());
    }

    private Map<Long, Map<String, JournalEntryStudent>> getMoodleMappedEntriesStudents(Journal journal) {
        return StreamUtil.toMap(JournalEntry::getMoodleGradeItemId, 
                je -> StreamUtil.toMap(jes -> jes.getJournalStudent().getStudent().getPerson().getIdcode(), 
                        je.getJournalEntryStudents()),
                journal.getJournalEntries().stream().filter(je -> je.getMoodleGradeItemId() != null));
    }

    private List<String> getStudentsWithMissingGrades(Journal journal, List<GradeItem> gradeItems) {
        Query q = em.createNativeQuery("select p.idcode"
                + " from (select id, student_id from journal_student where journal_id = ?1 and is_moodle_registered = true) js"
                + " left join (select journal_student_id, grade_code from journal_entry_student where journal_entry_id in ("
                + " select id from journal_entry where moodle_grade_item_id in (?2)))"
                + " jes on jes.journal_student_id = js.id"
                + " inner join student s on s.id = js.student_id"
                + " inner join person p on p.id = s.person_id"
                + " group by p.idcode"
                + " having count(jes.grade_code) < ?3");
        q.setParameter(1, EntityUtil.getId(journal));
        q.setParameter(2, StreamUtil.toMappedList(GradeItem::getId, gradeItems));
        q.setParameter(3, gradeItems.size());
        List<?> result = q.getResultList();
        return StreamUtil.toMappedList(r -> resultAsString(r, 0), result);
    }

    private List<Long> getEntriesWithMissingGrades(Journal journal, List<GradeItem> gradeItems) {
        Query q = em.createNativeQuery("select count(*) from journal_student"
                + " where journal_id = ?1 and is_moodle_registered = true");
        q.setParameter(1, EntityUtil.getId(journal));
        Number moodleStudents = (Number) q.getSingleResult();
        q = em.createNativeQuery("select je.moodle_grade_item_id"
                + " from (select id, moodle_grade_item_id from journal_entry where journal_id = ?1 and moodle_grade_item_id in (?2)) je"
                + " left join journal_entry_student jes on jes.journal_entry_id = je.id"
                + " group by je.moodle_grade_item_id"
                + " having count(jes.grade_code) < ?3");
        q.setParameter(1, EntityUtil.getId(journal));
        q.setParameter(2, StreamUtil.toMappedList(GradeItem::getId, gradeItems));
        q.setParameter(3, moodleStudents);
        List<?> result = q.getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), result);
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
        saveJournalEntryStudents(journalEntryForm, journalEntry);
        return EntityUtil.save(journal, em);
    }

    public void updateJournalEntry(HoisUserDetails user, JournalEntryForm journalEntryForm, Long journalEntrylId) {
        validateJournalEntry(journalEntryForm);
        EntityUtil.setUsername(user.getUsername(), em);
        JournalEntry journalEntry = em.getReference(JournalEntry.class, journalEntrylId);
        EntityUtil.bindToEntity(journalEntryForm, journalEntry, classifierRepository, "journalEntryStudents",
                "journalEntryCapacityTypes");
        saveJournalEntryStudents(journalEntryForm, journalEntry);
        EntityUtil.save(journalEntry, em);
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

    private void saveJournalEntryStudents(JournalEntryForm journalEntryForm,
            JournalEntry journalEntry) {
        for (JournalEntryStudentForm journalEntryStudentForm : journalEntryForm.getJournalEntryStudents()) {
            if (journalEntryStudentForm.getId() != null) {
                updateJournalStudentEntry(journalEntryStudentForm);
            } else {
                saveJournalStudentEntry(journalEntry, journalEntryStudentForm);
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

    private void updateJournalStudentEntry(JournalEntryStudentForm journalEntryStudentForm) {
        JournalEntryStudent journalEntryStudent = em.getReference(JournalEntryStudent.class, journalEntryStudentForm.getId());
        assertJournalEntryStudentRules(journalEntryStudent.getJournalStudent());

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

    private static void assertJournalEntryStudentRules(JournalStudent journaStudent) {

        // cannot change results of not studying students
        if (!StudentUtil.isStudying(journaStudent.getStudent())) {
            throw new ValidationFailedException("journal.messages.changeIsNotAllowedStudentIsNotStudying");
        }
    }

    private void saveJournalStudentEntry(JournalEntry journalEntry,
            JournalEntryStudentForm journalEntryStudentForm) {
        JournalStudent journalStudent = em.getReference(JournalStudent.class, journalEntryStudentForm.getJournalStudent());
        assertJournalEntryStudentRules(journalStudent);

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
        dto.setLessonPlanDates(StreamUtil.toMappedList(r -> resultAsLocalDateTime(r, 0), result));
        return dto;
    }

    public Page<JournalEntryTableDto> journalTableEntries(Long journalId, Pageable pageable) {
        JpaNativeQueryBuilder jeQb = new JpaNativeQueryBuilder("from journal_entry je left join curriculum_module_outcomes cmo on cmo.id = je.curriculum_module_outcomes_id")
                .sort("je.entry_date desc nulls last, lower(je.entry_type_code)='sissekanne_l' asc , lower(je.entry_type_code)='sissekanne_o' asc, cmo.order_nr");

        jeQb.requiredCriteria("je.journal_id=:journalId", "journalId", journalId);

        return JpaQueryUtil.pagingResult(jeQb,
                "je.id, je.entry_type_code, je.entry_date, je.content, je.homework, je.homework_duedate, cmo.order_nr", em,
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
            }
            
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
        
        // order outcome entries by order
        Collections.sort(result, Comparator.comparing(JournalEntryByDateDto::getOutcomeOrderNr, Comparator.nullsFirst(Comparator.naturalOrder())));

        // order entries by entry date
        Collections.sort(result, Comparator.comparing(JournalEntryByDateDto::getEntryDate, Comparator.nullsLast(Comparator.naturalOrder())));
                
        // put final results to the end of the list
        Collections.sort(result, (JournalEntryByDateDto o1, JournalEntryByDateDto o2) -> {
            if(isFinalResult(o1) && !isFinalResult(o2)) {
                return 1;
            } else if(!isFinalResult(o1) && isFinalResult(o2)) {
                return -1;
            }
            return 0;
        });
        return result;
    }

    public List<JournalStudentDto> journalStudents(Journal journal, Boolean allStudents) {
        return journal.getJournalStudents().stream()
                .filter(jt -> Boolean.TRUE.equals(allStudents) || StudentUtil.isStudying(jt.getStudent()))
                .map(JournalStudentDto::of).collect(Collectors.toList());
    }
    
    public Map<String, Integer> usedHours(Journal journal) {
        return Collections.singletonMap("usedHours", Integer.valueOf(journal.getJournalEntries().stream().mapToInt(it -> it.getLessons() == null ? 0 : it.getLessons().intValue()).sum()));
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
        Query q = em.createNativeQuery("select j.id, j.name_et, j.study_year_id, sy.year_code, j.add_module_outcomes,"
                + " count(case jes.absence_code when 'PUUDUMINE_H' then 1 else null end) as puudumine_h,"
                + " count(case jes.absence_code when 'PUUDUMINE_P' then 1 else null end) as puudumine_p,"
                + " count(case when jes.absence_code in ('PUUDUMINE_P', 'PUUDUMINE_V') then 1 else null end) as puudumine_pv from journal_entry je"
                + " inner join journal_entry_student jes on jes.journal_entry_id=je.id"
                + " inner join journal_student js on jes.journal_student_id = js.id"
                + " inner join journal j on j.id = js.journal_id"
                + " inner join study_year sy on j.study_year_id = sy.id"
                + " inner join student s on s.id = js.student_id"
                + " where s.id=?1 group by j.id, sy.year_code");
        q.setParameter(1, studentId);

        List<?> studentJournals = q.getResultList();
        return StreamUtil.toMappedList(r -> new StudentJournalDto((Object[])r, getStudentJournalEntries(studentId, resultAsLong(r, 0))), studentJournals);
    }
    

    private List<StudentJournalEntryDto> getStudentJournalEntries(Long studentId, Long journalId) {
        Query entriesQuery = em.createNativeQuery("select je.id, je.entry_type_code, je.entry_date, je.content,"
                + " ( select value from classifier where code = jes.grade_code ),"
                + " jes.grade_inserted, jes.add_info, je.homework, je.homework_duedate from journal j"
                + " inner join journal_entry je on j.id = je.journal_id"
                + " inner join journal_student js on j.id = js.journal_id"
                + " left join journal_entry_student jes on je.id = jes.journal_entry_id and jes.journal_student_id = js.id"
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
