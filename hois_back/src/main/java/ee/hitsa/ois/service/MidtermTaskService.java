package ee.hitsa.ois.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
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
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodSubgroup;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.MidtermTaskRepository;
import ee.hitsa.ois.repository.MidtermTaskStudentResultRepository;
import ee.hitsa.ois.repository.SubjectStudyPeriodRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.MidtermTaskUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.MidtermTaskUpdateForm;
import ee.hitsa.ois.web.commandobject.subject.studyperiod.SubjectStudyPeriodMidtermTaskForm;
import ee.hitsa.ois.web.commandobject.subject.studyperiod.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.MidtermTaskDto;
import ee.hitsa.ois.web.dto.MidtermTaskStudentResultDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSearchDto;

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
                        .where(targetRoot.get("teacher").get("id").in(criteria.getTeacher()));
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

    private void updateStudentsResults(SubjectStudyPeriodMidtermTaskForm form, SubjectStudyPeriod subjectStudyPeriod) {
        if (MidtermTaskUtil.isPractice(subjectStudyPeriod)) {
            return;
        }
        
        Set<MidtermTaskStudentResultDto> studentResultsDtos = removeEmptyStudentResults(form.getStudentResults());
        Set<MidtermTaskStudentResult> savedStudentResults = MidtermTaskUtil.getStudentResults(subjectStudyPeriod);
        deleteStudentResults(studentResultsDtos, savedStudentResults);
        
        EntityUtil.bindEntityCollection(savedStudentResults, MidtermTaskStudentResult::getId, 
                studentResultsDtos, MidtermTaskStudentResultDto::getId, 
                this::createStudentResult, 
                this::updateStudentResult);
        
        midtermTaskStudentResultRepository.save(savedStudentResults);
    }
    
    private void updateStudentSubgroups(SubjectStudyPeriodMidtermTaskForm form, SubjectStudyPeriod subjectStudyPeriod) {
        if (subjectStudyPeriod.getSubgroups().isEmpty()) {
            return;
        }
        
        form.getStudents().forEach(midtermStudent -> {
            DeclarationSubject ds = em.getReference(DeclarationSubject.class, midtermStudent.getDeclarationSubject());
            SubjectStudyPeriodSubgroup subgroup = EntityUtil.getOptionalOne(SubjectStudyPeriodSubgroup.class, midtermStudent.getSubgroup(), em);
            if (subgroup != null && !subgroup.equals(ds.getSubgroup())) {
                AssertionFailedException.throwIf(!subgroup.getPeriod().getId().equals(subjectStudyPeriod.getId()), "Subject study period and subgroup are not connected");
                ds.setSubgroup(subgroup);
            }
        });
    }
    
    public void updateStudents(SubjectStudyPeriodMidtermTaskForm form, SubjectStudyPeriod subjectStudyPeriod) {
        updateStudentsResults(form, subjectStudyPeriod);
        updateStudentSubgroups(form, subjectStudyPeriod);
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
    
}
