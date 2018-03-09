package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.MidtermTask;
import ee.hitsa.ois.domain.MidtermTaskStudentResult;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.repository.MidtermTaskRepository;
import ee.hitsa.ois.repository.MidtermTaskStudentResultRepository;
import ee.hitsa.ois.repository.SubjectStudyPeriodRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.MidtermTaskUtil;
import ee.hitsa.ois.util.MoodleUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.MidtermTaskUpdateForm;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodMidtermTaskForm;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.MidtermTaskDto;
import ee.hitsa.ois.web.dto.MidtermTaskStudentResultDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSearchDto;
import ee.hitsa.ois.web.dto.moodle.EnrollResult;
import ee.hois.moodle.EnrollResponse;
import ee.hois.moodle.Grade;
import ee.hois.moodle.GradeItem;

@Transactional
@Service
public class MidtermTaskService {

    @Autowired
    private EntityManager em;
    @Autowired
    private SubjectStudyPeriodRepository subjectStudyPeriodRepository;
    @Autowired
    private MidtermTaskStudentResultRepository midtermTaskStudentResultRepository;
    @Autowired
    private MidtermTaskRepository midtermTaskRepository;
    @Autowired
    private MoodleService moodleService;

    public void updateMidtermTasks(SubjectStudyPeriod subjectStudyPeriod, MidtermTaskUpdateForm form) {
        EntityUtil.bindEntityCollection(subjectStudyPeriod.getMidtermTasks(), MidtermTask::getId, 
                form.getMidtermTasks(), MidtermTaskDto::getId, 
                dto -> createMidtermTask(dto, subjectStudyPeriod), 
                this::updateMidtermTask);
        EntityUtil.save(subjectStudyPeriod, em);
    }
    
    public MidtermTask createMidtermTask(MidtermTaskDto dto, SubjectStudyPeriod subjectStudyPeriod) {
        MidtermTask midtermTask = new MidtermTask();
        midtermTask.setSubjectStudyPeriod(subjectStudyPeriod);
        updateMidtermTask(dto, midtermTask);
        return midtermTask;
    }

    public void updateMidtermTask(MidtermTaskDto dto, MidtermTask midtermTask) {
        EntityUtil.bindToEntity(dto, midtermTask, "subjectStudyPeriod", "studentResults");
        if(dto.getThreshold() == null || Boolean.FALSE.equals(dto.getThreshold())) {
            midtermTask.setThresholdPercentage(null);
        }
    }

    public Page<SubjectStudyPeriodSearchDto> searchSubjectStudyPeriods(
            Long subjectStudyPeriodId, SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        return  subjectStudyPeriodRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("studyPeriod").get("id"), criteria.getStudyPeriod()));
            filters.add(cb.notEqual(root.get("id"), subjectStudyPeriodId));

            if(criteria.getSubject() != null) {
                filters.add(cb.equal(root.get("subject").get("id"), criteria.getSubject()));
            }
            if(criteria.getTeacher() != null) {
                Subquery<Long> teacherSubquery = query.subquery(Long.class);
                Root<SubjectStudyPeriodTeacher> targetRoot = teacherSubquery
                        .from(SubjectStudyPeriodTeacher.class);
                teacherSubquery = teacherSubquery.select(targetRoot.get("subjectStudyPeriod").get("id"))
                        .where(cb.equal(targetRoot.get("teacher").get("id"), criteria.getTeacher()));
                filters.add(root.get("id").in(teacherSubquery));
            }
            
            /*
             * Show only those subjectStudyPeriods, which have any midtermTasks
             */
            Subquery<Long> midtermTaskSubquery = query.subquery(Long.class);
            Root<MidtermTask> targetRoot = midtermTaskSubquery
                    .from(MidtermTask.class);
            midtermTaskSubquery = midtermTaskSubquery.select(targetRoot.get("subjectStudyPeriod").get("id"));
            filters.add(root.get("id").in(midtermTaskSubquery));
            
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(r -> {
            SubjectStudyPeriodSearchDto dto = new SubjectStudyPeriodSearchDto();
            dto.setId(EntityUtil.getId(r));
            dto.setSubject(AutocompleteResult.of(r.getSubject()));
            dto.setTeachers(PersonUtil.sorted(r.getTeachers().stream().map(t -> t.getTeacher().getPerson())));
            dto.setMidtermTasks(StreamUtil.toMappedSet(AutocompleteResult::of, r.getMidtermTasks()));
            return dto;
        });
    }
    
    public List<MidtermTask> copyMidtermTasks(SubjectStudyPeriod subjectStudyPeriod, SubjectStudyPeriod copiedSubjectStudyPeriod) {
        List<MidtermTask> copiedMidtermTasks = new ArrayList<>();
        for(MidtermTask m : copiedSubjectStudyPeriod.getMidtermTasks()) {
            copiedMidtermTasks.add(getCopyOfMidtermTask(m, subjectStudyPeriod));
        }
        subjectStudyPeriod.getMidtermTasks().addAll(copiedMidtermTasks);
        return midtermTaskRepository.save(copiedMidtermTasks);
    }

    public MidtermTask getCopyOfMidtermTask(MidtermTask midtermTask, SubjectStudyPeriod subjectStudyPeriod) {
        MidtermTask newMidtermTask = new MidtermTask();
        newMidtermTask = EntityUtil.bindToEntity(midtermTask, newMidtermTask, "subjectStudyPeriod", "studentResults");
        newMidtermTask.setSubjectStudyPeriod(subjectStudyPeriod);
        return newMidtermTask;
    }

    public void updateStudentsResults(SubjectStudyPeriodMidtermTaskForm form, SubjectStudyPeriod subjectStudyPeriod) {
        Set<MidtermTaskStudentResultDto> studentResultsDtos = removeEmptyStudentResults(form.getStudentResults());
        Set<MidtermTaskStudentResult> savedStudentResults = MidtermTaskUtil.getStudentResults(subjectStudyPeriod);
        deleteStudentResults(studentResultsDtos, savedStudentResults);
        
        EntityUtil.bindEntityCollection(savedStudentResults, MidtermTaskStudentResult::getId, 
                studentResultsDtos, MidtermTaskStudentResultDto::getId, 
                this::createStudentResult, 
                this::updateStudentResult);
        midtermTaskStudentResultRepository.save(savedStudentResults);
    }

    private Set<MidtermTaskStudentResultDto> removeEmptyStudentResults(Set<MidtermTaskStudentResultDto> studentResults) {
        return studentResults.stream()
                .filter(r -> {
                    MidtermTask task = em.getReference(MidtermTask.class, r.getMidtermTask());
                    if(MidtermTaskUtil.resultIsText(task)) {
                        return r.getPointsTxt() != null && !r.getPointsTxt().isEmpty();
                    }
                    return r.getPoints() != null;
                }).collect(Collectors.toSet());
    }

    private void deleteStudentResults(Set<MidtermTaskStudentResultDto> studentResultsDtos, 
            Set<MidtermTaskStudentResult> savedStudentResults) {
        Set<Long> updatedStudentResultsIds = studentResultsDtos.stream()
                .filter(r -> r.getId() != null).map(r -> r.getId()).collect(Collectors.toSet());
        Set<MidtermTaskStudentResult> deletedStudentResults = new HashSet<>();
        Iterator<MidtermTaskStudentResult> iterator = savedStudentResults.iterator();
        while(iterator.hasNext()) {
            MidtermTaskStudentResult studentResult = iterator.next();
            if(!updatedStudentResultsIds.contains(studentResult.getId())) {
                
                MidtermTaskUtil.checkIfStudentResultCanBeChanged(studentResult.getDeclarationSubject());
                
                deletedStudentResults.add(studentResult);
                iterator.remove();
                studentResult.getDeclarationSubject().getMidtermTaskStudentResults().remove(studentResult);
            }
        }
        midtermTaskStudentResultRepository.delete(deletedStudentResults);
    }

    public MidtermTaskStudentResult createStudentResult(MidtermTaskStudentResultDto dto) {
        MidtermTaskStudentResult studentResult = new MidtermTaskStudentResult();
        studentResult.setMidtermTask(em.getReference(MidtermTask.class, dto.getMidtermTask()));
        DeclarationSubject declarationSubject = em.getReference(DeclarationSubject.class, dto.getDeclarationSubject());
        studentResult.setDeclarationSubject(declarationSubject);
        updateStudentResult(dto, studentResult);
        declarationSubject.getMidtermTaskStudentResults().add(studentResult);

        MidtermTaskUtil.checkIfStudentResultCanBeChanged(studentResult.getDeclarationSubject());

        return studentResult;
    }

    public void updateStudentResult(MidtermTaskStudentResultDto dto, MidtermTaskStudentResult studentResult) {
        if(!MidtermTaskUtil.studentResultCanBeChanged(studentResult.getDeclarationSubject())) {
            return;
        }
        if(MidtermTaskUtil.resultIsText(studentResult.getMidtermTask())) {
            studentResult.setPoints(null);
            studentResult.setPointsTxt(dto.getPointsTxt());
        } else {
            studentResult.setPoints(dto.getPoints());
            studentResult.setPointsTxt(null);
            MidtermTaskUtil.checkStudentResultsPoints(studentResult);
        }
    }
    
    public EnrollResult moodleEnrollStudents(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod) {
        List<String> academicianIds = getTeachersIdcodes(subjectStudyPeriod);
        List<String> studentIds = StreamUtil.toMappedList(ds -> ds.getDeclaration().getStudent().getPerson().getIdcode(), 
                subjectStudyPeriod.getDeclarationSubjects().stream().filter(ds -> ds.getIsMoodleRegistered() != Boolean.TRUE));
        if (studentIds.isEmpty()) {
            return MoodleUtil.createEmptyEnrollResult();
        }
        EnrollResponse response = moodleService.enrollStudents(user, subjectStudyPeriod.getMoodleCourseId(), academicianIds, studentIds);
        Map<String, DeclarationSubject> studentMap = getMoodleMappedStudents(subjectStudyPeriod);
        EntityUtil.setUsername(user.getUsername(), em);
        for (String enrolledUser : response.getEnrolled()) {
            studentMap.get(enrolledUser).setIsMoodleRegistered(Boolean.TRUE);
        }
        EnrollResult result = new EnrollResult();
        result.setEnrolled(response.getEnrolled().size());
        result.setFailed(StreamUtil.toMappedList(
                u -> PersonUtil.fullname(studentMap.get(u).getDeclaration().getStudent().getPerson()), 
                response.getFailed()));
        result.setMissingUser(StreamUtil.toMappedList(
                u -> PersonUtil.fullname(studentMap.get(u).getDeclaration().getStudent().getPerson()), 
                response.getMissingUser()));
        return result;
    }

    public List<GradeItem> moodleImportGradeItems(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod) {
        List<String> academicianIds = getTeachersIdcodes(subjectStudyPeriod);
        Map<Long, MidtermTask> taskMap = getMoodleMappedTasks(subjectStudyPeriod);
        List<GradeItem> items = moodleService.getGradeItems(user, subjectStudyPeriod.getMoodleCourseId(), academicianIds);
        List<MidtermTask> newEntries = new ArrayList<>();
        EntityUtil.setUsername(user.getUsername(), em);
        for (GradeItem item : items) {
            MidtermTask task = taskMap.get(item.getId());
            if (task == null) {
                task = new MidtermTask();
                task.setSubjectStudyPeriod(subjectStudyPeriod);
                task.setPercentage((short) 0);
                task.setMoodleGradeItemId(item.getId());
                newEntries.add(task);
            }
            task.setNameEt(item.getName());
            task.setDescriptionEt(item.getName());
            task.setMaxPoints(BigDecimal.valueOf(item.getMax()));
            if (item.getPass() != 0.0) {
                task.setThreshold(Boolean.TRUE);
                task.setThresholdPercentage((short) (item.getPass() * 100 / item.getMax()));
            }
        }
        for (MidtermTask task : newEntries) {
            subjectStudyPeriod.getMidtermTasks().add(task);
        }
        EntityUtil.save(subjectStudyPeriod, em);
        return items;
    }

    public void moodleImportAllGrades(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod) {
        List<GradeItem> gradeItems = moodleImportGradeItems(user, subjectStudyPeriod);
        moodleImportGrades(user, subjectStudyPeriod, 
                StreamUtil.toMappedList(ds -> ds.getDeclaration().getStudent().getPerson().getIdcode(), 
                        subjectStudyPeriod.getDeclarationSubjects().stream().filter(ds -> ds.getIsMoodleRegistered() == Boolean.TRUE)), 
                StreamUtil.toMappedList(GradeItem::getId, gradeItems));
    }
    
    public void moodleImportMissingGrades(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod) {
        List<GradeItem> gradeItems = moodleImportGradeItems(user, subjectStudyPeriod);
        moodleImportGrades(user, subjectStudyPeriod, 
                getStudentsWithMissingGrades(subjectStudyPeriod, gradeItems), 
                getTasksWithMissingGrades(subjectStudyPeriod, gradeItems));
    }

    private void moodleImportGrades(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod,
            List<String> studentIds, List<Long> gradeItemIds) {
        List<String> academicianIds = getTeachersIdcodes(subjectStudyPeriod);
        Map<Long, MidtermTask> taskMap = getMoodleMappedTasks(subjectStudyPeriod);
        Map<String, DeclarationSubject> studentMap = getMoodleMappedStudents(subjectStudyPeriod);
        Map<Long, Map<String, MidtermTaskStudentResult>> entryStudentMap = getMoodleMappedEntriesStudents(subjectStudyPeriod);
        Map<Long, List<Grade>> grades = moodleService.getGradesByItemId(user, subjectStudyPeriod.getMoodleCourseId(), academicianIds, 
                gradeItemIds, studentIds);
        EntityUtil.setUsername(user.getUsername(), em);
        for (Entry<Long, List<Grade>> moodleEntry : grades.entrySet()) {
            MidtermTask task = taskMap.get(moodleEntry.getKey());
            Map<String, MidtermTaskStudentResult> gradeMap = entryStudentMap.get(moodleEntry.getKey());
            List<MidtermTaskStudentResult> newGrades = new ArrayList<>();
            for (Grade grade : moodleEntry.getValue()) {
                MidtermTaskStudentResult midtermTaskStudentResult = gradeMap.get(grade.getStudent());
                if (midtermTaskStudentResult == null) {
                    midtermTaskStudentResult = new MidtermTaskStudentResult();
                    midtermTaskStudentResult.setMidtermTask(task);
                    midtermTaskStudentResult.setDeclarationSubject(studentMap.get(grade.getStudent()));
                    newGrades.add(midtermTaskStudentResult);
                }
                Object points = grade.getPoints();
                if (points == null) {
                    midtermTaskStudentResult.setPoints(null);
                    midtermTaskStudentResult.setPointsTxt(null);
                } else {
                    midtermTaskStudentResult.setPoints(BigDecimal.valueOf(MoodleUtil.pointsToNumber(points).longValue()));
                    midtermTaskStudentResult.setPointsTxt(points.toString());
                }
            }
            for (MidtermTaskStudentResult midtermTaskStudentResult : newGrades) {
                task.getStudentResults().add(midtermTaskStudentResult);
                EntityUtil.save(midtermTaskStudentResult, em);
            }
        }
    }
    
    private List<String> getTeachersIdcodes(SubjectStudyPeriod subjectStudyPeriod) {
        return StreamUtil.toMappedList(sspt -> sspt.getTeacher().getPerson().getIdcode(), 
                subjectStudyPeriod.getTeachers());
    }

    private Map<Long, MidtermTask> getMoodleMappedTasks(SubjectStudyPeriod subjectStudyPeriod) {
        return StreamUtil.toMap(MidtermTask::getMoodleGradeItemId, 
                subjectStudyPeriod.getMidtermTasks().stream().filter(mt -> mt.getMoodleGradeItemId() != null));
    }

    private Map<String, DeclarationSubject> getMoodleMappedStudents(SubjectStudyPeriod subjectStudyPeriod) {
        return StreamUtil.toMap(js -> js.getDeclaration().getStudent().getPerson().getIdcode(), 
                subjectStudyPeriod.getDeclarationSubjects());
    }

    private Map<Long, Map<String, MidtermTaskStudentResult>> getMoodleMappedEntriesStudents(SubjectStudyPeriod subjectStudyPeriod) {
        return StreamUtil.toMap(MidtermTask::getMoodleGradeItemId, 
                mt -> StreamUtil.toMap(mtsr -> mtsr.getDeclarationSubject().getDeclaration().getStudent().getPerson().getIdcode(), 
                        mt.getStudentResults()),
                subjectStudyPeriod.getMidtermTasks().stream().filter(mt -> mt.getMoodleGradeItemId() != null));
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
        q.setParameter(3, gradeItems.size());
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
    
}
