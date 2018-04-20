package ee.hitsa.ois.service.moodle;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.config.MoodleProperties;
import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.MidtermTask;
import ee.hitsa.ois.domain.MidtermTaskStudentResult;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JournalUtil;
import ee.hitsa.ois.util.MoodleUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.dto.moodle.EnrollResult;
import ee.hois.moodle.MoodleClient;
import ee.hois.moodle.MoodleException;
import ee.hois.moodle.dto.EnrollResponse;
import ee.hois.moodle.dto.Grade;
import ee.hois.moodle.dto.GradeItem;

@Transactional
@Service
public class MoodleService {

    @Autowired
    private EntityManager em;
    @Autowired
    private MoodleProperties properties;
    @Autowired
    private MoodleClient client;
    @Autowired
    private MoodleLogService moodleLogService;
    @Autowired
    private ClassifierService classifierService;
    
    public Journal saveMoodleCourseLink(MoodleContext context, Journal journal, Long courseId) {
        Boolean valid = withResponse(context, () -> client.courseLinkPossible(properties, context.getLog(), 
                getIdcode(context), courseId, getTeachersIdcodes(journal)));
        if (!Boolean.TRUE.equals(valid)) {
            throw new ValidationFailedException("moodle.error.invalidMoodleCourse");
        }
        journal.setMoodleCourseId(courseId);
        EntityUtil.setUsername(context.getUser().getUsername(), em);
        return EntityUtil.save(journal, em);
    }

    public void validateMoodleCourseId(MoodleContext context, SubjectStudyPeriod subjectStudyPeriod, 
            Long courseId) {
        Boolean valid = withResponse(context, () -> client.courseLinkPossible(properties, context.getLog(), 
                getIdcode(context), courseId, getTeachersIdcodes(subjectStudyPeriod)));
        if (!Boolean.TRUE.equals(valid)) {
            throw new ValidationFailedException("moodle.error.invalidMoodleCourse");
        }
    }
    
    public EnrollResult moodleEnrollStudents(MoodleContext context, Journal journal) {
        List<Person> students = StreamUtil.toMappedList(js -> js.getStudent().getPerson(), 
                journal.getJournalStudents().stream()
                    .filter(js -> !Boolean.TRUE.equals(js.getIsMoodleRegistered())));
        Map<String, JournalStudent> studentMap = getMoodleMappedStudents(journal);
        return moodleEnrollStudents(context, students, journal.getMoodleCourseId(), 
                getTeachersIdcodes(journal), 
                enrolled -> enrolled.forEach(idcode -> studentMap.get(idcode).setIsMoodleRegistered(Boolean.TRUE)), 
                idcode -> studentMap.get(idcode).getStudent().getPerson());
    }

    public EnrollResult moodleEnrollStudents(MoodleContext context, SubjectStudyPeriod subjectStudyPeriod) {
        List<Person> students = StreamUtil.toMappedList(ds -> ds.getDeclaration().getStudent().getPerson(), 
                subjectStudyPeriod.getDeclarationSubjects().stream()
                    .filter(ds -> !Boolean.TRUE.equals(ds.getIsMoodleRegistered())));
        Map<String, DeclarationSubject> studentMap = getMoodleMappedStudents(subjectStudyPeriod);
        return moodleEnrollStudents(context, students, subjectStudyPeriod.getMoodleCourseId(), 
                getTeachersIdcodes(subjectStudyPeriod), 
                enrolled -> enrolled.forEach(idcode -> studentMap.get(idcode).setIsMoodleRegistered(Boolean.TRUE)), 
                idcode -> studentMap.get(idcode).getDeclaration().getStudent().getPerson());
    }

    private EnrollResult moodleEnrollStudents(MoodleContext context, List<Person> students, Long courseId, 
            List<String> academicianIds, Consumer<Stream<String>> registered, Function<String, Person> idcodeToPerson) {
        List<String> studentIds = StreamUtil.toMappedList(p -> p.getIdcode(), 
                students.stream()
                    .filter(p -> p.getIdcode() != null));
        if (studentIds.isEmpty()) {
            throw new ValidationFailedException("moodle.error.noStudentsToEnroll");
        }
        return withResponse(context, () -> {
            EnrollResponse response = client.enrollStudents(properties, context.getLog(), getIdcode(context), 
                    courseId, academicianIds, studentIds);
            EntityUtil.setUsername(context.getUser().getUsername(), em);
            registered.accept(Stream.concat(response.getEnrolled().stream(), response.getExists().stream()));
            EnrollResult result = new EnrollResult();
            result.setEnrolled(Integer.valueOf(response.getEnrolled().size()));
            result.setFailed(StreamUtil.toMappedList(
                    u -> PersonUtil.fullname(idcodeToPerson.apply(u)), 
                    response.getFailed()));
            result.setMissingUser(StreamUtil.toMappedList(
                    u -> PersonUtil.fullname(idcodeToPerson.apply(u)), 
                    response.getMissingUser()));
            result.setMissingIdcode(StreamUtil.toMappedList(p -> PersonUtil.fullname(p), 
                    students.stream()
                        .filter(p -> p.getIdcode() == null)));
            return result;
        });
    }

    public List<GradeItem> moodleImportGradeItems(MoodleContext context, Journal journal) {
        return withResponse(context, () -> {
            List<String> academicianIds = getTeachersIdcodes(journal);
            Map<Long, JournalEntry> entryMap = getMoodleMappedEntries(journal);
            List<GradeItem> items = client.getGradeItems(properties, context.getLog(), getIdcode(context), 
                    journal.getMoodleCourseId(), academicianIds);
            List<JournalEntry> newEntries = new ArrayList<>();
            ClassifierCache classifiers = new ClassifierCache(classifierService);
            EntityUtil.setUsername(context.getUser().getUsername(), em);
            for (GradeItem item : items) {
                JournalEntry entry = entryMap.get(item.getId());
                if (entry == null) {
                    if (MoodleUtil.isFinalGradeItemType(item.getType())
                             && JournalUtil.hasFinalEntry(journal)) {
                        continue;
                    }
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
        });
    }

    public List<GradeItem> moodleImportGradeItems(MoodleContext context, SubjectStudyPeriod subjectStudyPeriod) {
        return withResponse(context, () -> {
            List<String> academicianIds = getTeachersIdcodes(subjectStudyPeriod);
            Map<Long, MidtermTask> taskMap = getMoodleMappedTasks(subjectStudyPeriod);
            List<GradeItem> items = client.getGradeItems(properties, context.getLog(), getIdcode(context), 
                    subjectStudyPeriod.getMoodleCourseId(), academicianIds);
            List<MidtermTask> newEntries = new ArrayList<>();
            EntityUtil.setUsername(context.getUser().getUsername(), em);
            for (GradeItem item : items) {
                if (MoodleUtil.isFinalGradeItemType(item.getType())) {
                    continue;
                }
                MidtermTask task = taskMap.get(item.getId());
                if (task == null) {
                    task = new MidtermTask();
                    task.setSubjectStudyPeriod(subjectStudyPeriod);
                    task.setPercentage(Short.valueOf((short) 0));
                    task.setMoodleGradeItemId(item.getId());
                    newEntries.add(task);
                }
                task.setNameEt(item.getName());
                task.setDescriptionEt(item.getName());
                if (item.getMax() != null && item.getMin() != null) {
                    task.setMaxPoints(item.getMax().subtract(item.getMin()));
                } else {
                    task.setMaxPoints(null);
                }
                if (item.getPass() != null && item.getMax() != null && BigDecimal.ZERO.compareTo(item.getPass()) != 0) {
                    task.setThreshold(Boolean.TRUE);
                    task.setThresholdPercentage(Short.valueOf(item.getPass().multiply(BigDecimal.valueOf(100))
                            .divide(item.getMax(), 0, RoundingMode.HALF_UP).shortValue()));
                } else {
                    task.setThreshold(Boolean.FALSE);
                }
            }
            for (MidtermTask task : newEntries) {
                subjectStudyPeriod.getMidtermTasks().add(task);
            }
            EntityUtil.save(subjectStudyPeriod, em);
            return items;
        });
    }

    public void moodleImportAllGrades(MoodleContext context, Journal journal) {
        List<GradeItem> gradeItems = moodleImportGradeItems(context, journal);
        moodleImportGrades(context, journal, 
                StreamUtil.toMappedList(js -> js.getStudent().getPerson().getIdcode(), 
                        journal.getJournalStudents().stream().filter(js -> js.getIsMoodleRegistered() == Boolean.TRUE)), 
                StreamUtil.toMappedList(GradeItem::getId, gradeItems), 
                StreamUtil.toMap(GradeItem::getId, gradeItems));
    }
    
    public void moodleImportMissingGrades(MoodleContext context, Journal journal) {
        List<GradeItem> gradeItems = moodleImportGradeItems(context, journal);
        moodleImportGrades(context, journal, 
                getStudentsWithMissingGrades(journal, gradeItems), 
                getEntriesWithMissingGrades(journal, gradeItems), 
                StreamUtil.toMap(GradeItem::getId, gradeItems));
    }

    private void moodleImportGrades(MoodleContext context, Journal journal,
            List<String> studentIds, List<Long> gradeItemIds, Map<Long, GradeItem> gradeItemMap) {
        if (studentIds.isEmpty() || gradeItemIds.isEmpty()) {
            throw new ValidationFailedException("moodle.error.noMissingGrades");
        }
        List<String> academicianIds = getTeachersIdcodes(journal);
        Map<Long, JournalEntry> entryMap = getMoodleMappedEntries(journal);
        Map<String, JournalStudent> studentMap = getMoodleMappedStudents(journal);
        Map<Long, Map<String, JournalEntryStudent>> entryStudentMap = getMoodleMappedEntriesStudents(journal);
        withResponse(context, () -> {
            Map<Long, List<Grade>> grades = client.getGradesByItemId(properties, context.getLog(), getIdcode(context), 
                    journal.getMoodleCourseId(), academicianIds, gradeItemIds, studentIds);
            ClassifierCache classifiers = new ClassifierCache(classifierService);
            EntityUtil.setUsername(context.getUser().getUsername(), em);
            for (Entry<Long, List<Grade>> moodleEntry : grades.entrySet()) {
                JournalEntry journalEntry = entryMap.get(moodleEntry.getKey());
                Map<String, JournalEntryStudent> gradeMap = entryStudentMap.get(moodleEntry.getKey());
                if (gradeMap == null) {
                    continue;
                }
                List<JournalEntryStudent> newGrades = new ArrayList<>();
                for (Grade grade : moodleEntry.getValue()) {
                    JournalEntryStudent journalEntryStudent = gradeMap.get(grade.getStudent());
                    if (journalEntryStudent == null) {
                        journalEntryStudent = new JournalEntryStudent();
                        journalEntryStudent.setJournalStudent(studentMap.get(grade.getStudent()));
                        journalEntryStudent.setGradeInserted(LocalDateTime.now());
                        newGrades.add(journalEntryStudent);
                    }
                    BigDecimal points = grade.getPoints();
                    if (points == null) {
                        journalEntryStudent.setGrade(null);
                        journalEntryStudent.setAddInfo(null);
                    } else {
                        GradeItem gradeItem = gradeItemMap.get(moodleEntry.getKey());
                        if (gradeItem.getMax() != null && gradeItem.getMin() != null) {
                            journalEntryStudent.setGrade(classifiers.getByCode(
                                    MoodleUtil.pointsToGrade(points, gradeItem.getMax().subtract(gradeItem.getMin())).name(), 
                                    MainClassCode.KUTSEHINDAMINE));
                        }
                        journalEntryStudent.setAddInfo(points.toString());
                    }
                }
                for (JournalEntryStudent journalEntryStudent : newGrades) {
                    journalEntry.getJournalEntryStudents().add(journalEntryStudent);
                }
            }
            return EntityUtil.save(journal, em);
        });
    }

    public void moodleImportAllGrades(MoodleContext context, SubjectStudyPeriod subjectStudyPeriod) {
        List<GradeItem> gradeItems = moodleImportGradeItems(context, subjectStudyPeriod);
        moodleImportGrades(context, subjectStudyPeriod, 
                StreamUtil.toMappedList(ds -> ds.getDeclaration().getStudent().getPerson().getIdcode(), 
                        subjectStudyPeriod.getDeclarationSubjects().stream().filter(ds -> ds.getIsMoodleRegistered() == Boolean.TRUE)), 
                StreamUtil.toMappedList(GradeItem::getId, gradeItems));
    }
    
    public void moodleImportMissingGrades(MoodleContext context, SubjectStudyPeriod subjectStudyPeriod) {
        List<GradeItem> gradeItems = moodleImportGradeItems(context, subjectStudyPeriod);
        moodleImportGrades(context, subjectStudyPeriod, 
                getStudentsWithMissingGrades(subjectStudyPeriod, gradeItems), 
                getTasksWithMissingGrades(subjectStudyPeriod, gradeItems));
    }

    private void moodleImportGrades(MoodleContext context, SubjectStudyPeriod subjectStudyPeriod,
            List<String> studentIds, List<Long> gradeItemIds) {
        if (studentIds.isEmpty() || gradeItemIds.isEmpty()) {
            throw new ValidationFailedException("moodle.error.noMissingGrades");
        }
        withResponse(context, () -> {
            List<String> academicianIds = getTeachersIdcodes(subjectStudyPeriod);
            Map<Long, MidtermTask> taskMap = getMoodleMappedTasks(subjectStudyPeriod);
            Map<String, DeclarationSubject> studentMap = getMoodleMappedStudents(subjectStudyPeriod);
            Map<Long, Map<String, MidtermTaskStudentResult>> entryStudentMap = getMoodleMappedEntriesStudents(subjectStudyPeriod);
            Map<Long, List<Grade>> grades = client.getGradesByItemId(properties, context.getLog(), 
                    getIdcode(context), subjectStudyPeriod.getMoodleCourseId(), academicianIds,
                    gradeItemIds, studentIds);
            EntityUtil.setUsername(context.getUser().getUsername(), em);
            for (Entry<Long, List<Grade>> moodleEntry : grades.entrySet()) {
                MidtermTask task = taskMap.get(moodleEntry.getKey());
                Map<String, MidtermTaskStudentResult> gradeMap = entryStudentMap.get(moodleEntry.getKey());
                if (gradeMap == null) {
                    continue;
                }
                List<MidtermTaskStudentResult> newGrades = new ArrayList<>();
                for (Grade grade : moodleEntry.getValue()) {
                    MidtermTaskStudentResult midtermTaskStudentResult = gradeMap.get(grade.getStudent());
                    if (midtermTaskStudentResult == null) {
                        midtermTaskStudentResult = new MidtermTaskStudentResult();
                        midtermTaskStudentResult.setMidtermTask(task);
                        midtermTaskStudentResult.setDeclarationSubject(studentMap.get(grade.getStudent()));
                        newGrades.add(midtermTaskStudentResult);
                    }
                    BigDecimal points = grade.getPoints();
                    if (points == null) {
                        midtermTaskStudentResult.setPoints(null);
                        midtermTaskStudentResult.setPointsTxt(null);
                    } else {
                        midtermTaskStudentResult.setPoints(points);
                        midtermTaskStudentResult.setPointsTxt(truncateNullable(grade.getComment(), 10));
                    }
                }
                for (MidtermTaskStudentResult midtermTaskStudentResult : newGrades) {
                    task.getStudentResults().add(midtermTaskStudentResult);
                    EntityUtil.save(midtermTaskStudentResult, em);
                }
            }
            return subjectStudyPeriod;
        });
    }
    
    private static String truncateNullable(String input, int length) {
        return input == null ? null : input.substring(0, Math.min(length, input.length())) ;
    }
    
    private static List<String> getTeachersIdcodes(Journal journal) {
        return StreamUtil.toMappedList(p -> p.getIdcode(), 
                journal.getJournalTeachers().stream()
                    .map(jt -> jt.getTeacher().getPerson())
                    .filter(p -> p.getIdcode() != null));
    }

    private static List<String> getTeachersIdcodes(SubjectStudyPeriod subjectStudyPeriod) {
        return StreamUtil.toMappedList(p -> p.getIdcode(), 
                subjectStudyPeriod.getTeachers().stream()
                    .map(sspt -> sspt.getTeacher().getPerson())
                    .filter(p -> p.getIdcode() != null));
    }

    private static Map<Long, JournalEntry> getMoodleMappedEntries(Journal journal) {
        return StreamUtil.toMap(JournalEntry::getMoodleGradeItemId, 
                journal.getJournalEntries().stream().filter(je -> je.getMoodleGradeItemId() != null));
    }

    private static Map<Long, MidtermTask> getMoodleMappedTasks(SubjectStudyPeriod subjectStudyPeriod) {
        return StreamUtil.toMap(MidtermTask::getMoodleGradeItemId, 
                subjectStudyPeriod.getMidtermTasks().stream().filter(mt -> mt.getMoodleGradeItemId() != null));
    }

    private static Map<String, JournalStudent> getMoodleMappedStudents(Journal journal) {
        return StreamUtil.toMap(js -> js.getStudent().getPerson().getIdcode(), 
                journal.getJournalStudents().stream()
                    .filter(js -> js.getStudent().getPerson().getIdcode() != null));
    }

    private static Map<String, DeclarationSubject> getMoodleMappedStudents(SubjectStudyPeriod subjectStudyPeriod) {
        return StreamUtil.toMap(js -> js.getDeclaration().getStudent().getPerson().getIdcode(), 
                subjectStudyPeriod.getDeclarationSubjects());
    }

    private static Map<Long, Map<String, JournalEntryStudent>> getMoodleMappedEntriesStudents(Journal journal) {
        return StreamUtil.toMap(JournalEntry::getMoodleGradeItemId, 
                je -> StreamUtil.toMap(jes -> jes.getJournalStudent().getStudent().getPerson().getIdcode(), 
                        je.getJournalEntryStudents()),
                journal.getJournalEntries().stream().filter(je -> je.getMoodleGradeItemId() != null));
    }

    private static Map<Long, Map<String, MidtermTaskStudentResult>> getMoodleMappedEntriesStudents(SubjectStudyPeriod subjectStudyPeriod) {
        return StreamUtil.toMap(MidtermTask::getMoodleGradeItemId, 
                mt -> StreamUtil.toMap(mtsr -> mtsr.getDeclarationSubject().getDeclaration().getStudent().getPerson().getIdcode(), 
                        mt.getStudentResults()),
                subjectStudyPeriod.getMidtermTasks().stream().filter(mt -> mt.getMoodleGradeItemId() != null));
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
        q.setParameter(3, Integer.valueOf(gradeItems.size()));
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

    private List<String> getStudentsWithMissingGrades(SubjectStudyPeriod subjectStudyPeriod, List<GradeItem> gradeItems) {
        Query q = em.createNativeQuery("select p.idcode"
                + " from (select id, declaration_id from declaration_subject where subject_study_period_id = ?1 and is_moodle_registered = true) ds"
                + " left join (select declaration_subject_id, points from midterm_task_student_result where midterm_task_id in ("
                + " select id from midterm_task where moodle_grade_item_id in (?2)))"
                + " mtsr on mtsr.declaration_subject_id = ds.id"
                + " inner join declaration d on d.id = ds.declaration_id"
                + " inner join student s on s.id = d.student_id"
                + " inner join person p on p.id = s.person_id"
                + " group by p.idcode"
                + " having count(mtsr.points) < ?3");
        q.setParameter(1, EntityUtil.getId(subjectStudyPeriod));
        q.setParameter(2, StreamUtil.toMappedList(GradeItem::getId, gradeItems));
        q.setParameter(3, Integer.valueOf(gradeItems.size()));
        List<?> result = q.getResultList();
        return StreamUtil.toMappedList(r -> resultAsString(r, 0), result);
    }

    private List<Long> getTasksWithMissingGrades(SubjectStudyPeriod subjectStudyPeriod, List<GradeItem> gradeItems) {
        Query q = em.createNativeQuery("select count(*) from declaration_subject"
                + " where subject_study_period_id = ?1 and is_moodle_registered = true");
        q.setParameter(1, EntityUtil.getId(subjectStudyPeriod));
        Number moodleStudents = (Number) q.getSingleResult();
        q = em.createNativeQuery("select mt.moodle_grade_item_id"
                + " from (select id, moodle_grade_item_id from midterm_task where subject_study_period_id = ?1"
                + " and moodle_grade_item_id in (?2)) mt"
                + " left join midterm_task_student_result mtsr on mtsr.midterm_task_id = mt.id"
                + " group by mt.moodle_grade_item_id"
                + " having count(mtsr.points) < ?3");
        q.setParameter(1, EntityUtil.getId(subjectStudyPeriod));
        q.setParameter(2, StreamUtil.toMappedList(GradeItem::getId, gradeItems));
        q.setParameter(3, moodleStudents);
        List<?> result = q.getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), result);
    }

    private String getIdcode(MoodleContext context) {
        return em.getReference(Person.class, context.getUser().getPersonId())
                .getIdcode();
    }

    private <T> T withResponse(MoodleContext context, Supplier<T> handler) {
        try {
            return handler.get();
        } catch (MoodleException e) {
            if (e.getErrorcode() != null) {
                throw new ValidationFailedException("moodle.error." + e.getErrorcode());
            }
            context.getLog().setError(e);
            throw e;
        } catch (Exception e) {
            context.getLog().setError(e);
            throw e;
        } finally {
            moodleLogService.insertLog(context, context.getLog());
        }
    }

}
