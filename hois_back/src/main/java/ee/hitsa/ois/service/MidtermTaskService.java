package ee.hitsa.ois.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.MidtermTask;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.repository.SubjectStudyPeriodRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.MidtermTaskUpdateForm;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodMidtermTaskForm;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.MidtermTaskDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSearchDto;

@Transactional
@Service
public class MidtermTaskService {
    
    @Autowired
    private SubjectStudyPeriodRepository subjectStudyPeriodRepository;

    /*
     * TODO: check out deletion. 
     * For now SubjectStudyPeriod.midtermTasks orphanRemoval = true, 
     * But they should not be removed when deleting SubjectStudyPeriod, 
     * error must occur instead
     */
    public void updateMidtermTasks(SubjectStudyPeriod subjectStudyPeriod, MidtermTaskUpdateForm form) {
        EntityUtil.bindEntityCollection(subjectStudyPeriod.getMidtermTasks(), MidtermTask::getId, 
                form.getMidtermTasks(), MidtermTaskDto::getId, dto -> {
            MidtermTask midtermTask = new MidtermTask();
            midtermTask.setSubjectStudyPeriod(subjectStudyPeriod);
            updateMidtermTask(dto, midtermTask);
            return midtermTask;
        }, this::updateMidtermTask);
        subjectStudyPeriodRepository.save(subjectStudyPeriod);
    }

    public void updateMidtermTask(MidtermTaskDto dto, MidtermTask midtermTask) {
        EntityUtil.bindToEntity(dto, midtermTask, "subjectStudyPeriod", "studentResults");
        if(dto.getThreshold() == null || Boolean.FALSE.equals(dto.getThreshold())) {
            midtermTask.setThresholdPercentage(null);
        }
    }

    public void updateStudentsResults(SubjectStudyPeriodMidtermTaskForm form) {
        // TODO Auto-generated method stub
        
    }

    public Page<SubjectStudyPeriodSearchDto> searchSubjectStudyPeriods(
            SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        return  subjectStudyPeriodRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("studyPeriod").get("id"), criteria.getStudyPeriod()));

            if(criteria.getSubject() != null) {
                filters.add(cb.equal(root.get("subject").get("id"), criteria.getSubject()));
            }
            if(criteria.getTeacher() != null) {
                Subquery<Long> teacherGroupSubquery = query.subquery(Long.class);
                Root<SubjectStudyPeriodTeacher> targetRoot = teacherGroupSubquery
                        .from(SubjectStudyPeriodTeacher.class);
                teacherGroupSubquery = teacherGroupSubquery.select(targetRoot.get("subjectStudyPeriod").get("id"))
                        .where(cb.equal(targetRoot.get("teacher").get("id"), criteria.getTeacher()));
                filters.add(root.get("id").in(teacherGroupSubquery));
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
            dto.setTeachers(StreamUtil.toMappedSet(t -> t.getTeacher().getPerson().getFullname(), r.getTeachers()));
            dto.setMidtermTasks(StreamUtil.toMappedSet(AutocompleteResult::of, r.getMidtermTasks()));
            return dto;
        });
    }

    public void copyMidtermTasks(SubjectStudyPeriod subjectStudyPeriod, SubjectStudyPeriod copiedSubjectStudyPeriod) {
        List<MidtermTask> copiedMidtermTasks = new ArrayList<>();
        for(MidtermTask m : copiedSubjectStudyPeriod.getMidtermTasks()) {
            MidtermTask newMidtermTask = new MidtermTask();
            newMidtermTask = EntityUtil.bindToEntity(m, newMidtermTask, "subjectStudyPeriod", "studentResults");
            newMidtermTask.setSubjectStudyPeriod(subjectStudyPeriod);
            copiedMidtermTasks.add(newMidtermTask);
        }
        subjectStudyPeriod.getMidtermTasks().addAll(copiedMidtermTasks);
        subjectStudyPeriodRepository.save(subjectStudyPeriod);
    }
}
