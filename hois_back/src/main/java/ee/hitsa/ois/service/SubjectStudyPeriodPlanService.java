package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodPlan;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodPlanCapacity;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodPlanCurriculum;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodPlanStudyform;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.repository.SubjectStudyPeriodPlanRepository;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.CurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodPlanSearchCommand;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodPlanUniqueCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanSearchDtoContainer;

@Transactional
@Service
public class SubjectStudyPeriodPlanService {

    @Autowired
    private SubjectStudyPeriodPlanRepository subjectStudyPeriodPlanRepository;
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private StudyPeriodRepository studyPeriodRepository;
    
    @Autowired
    private CurriculumRepository curriculumRepository;
    
    @Autowired
    private ClassifierRepository classifierRepository;

    /**
     * subjectService.search() is not used, because Subject objects need to be acquired in order to get their SubjectStudyPeriodPlans
     */
    public Page<SubjectStudyPeriodPlanSearchDtoContainer> search(Long schoolId, SubjectStudyPeriodPlanSearchCommand criteria,
            Pageable pageable) {

        return subjectRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            filters.add(cb.equal(root.get("status").get("code"), SubjectStatus.AINESTAATUS_K.name()));
            if(criteria.getSubject() != null) {
                filters.add(cb.equal(root.get("id"), criteria.getSubject()));
            }
            Long curriculum = criteria.getCurriculum();
            if (curriculum != null) {
                Subquery<Long> curriculaQuery = query.subquery(Long.class);
                Root<CurriculumVersion> curriculumVersionRoot = curriculaQuery.from(CurriculumVersion.class);
                curriculaQuery = curriculaQuery
                        .select(curriculumVersionRoot.join("modules").join("subjects").get("subject").get("id"))
                        .where(curriculumVersionRoot.get("curriculum").get("id").in(Arrays.asList(curriculum)));
                filters.add(root.get("id").in(curriculaQuery));
            }
            
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(s -> SubjectStudyPeriodPlanSearchDtoContainer.of(s, criteria.getStudyPeriod()));
    }

    public List<AutocompleteResult> curriculums (Long schoolId, CurriculumSearchCommand criteria){
        List<Curriculum> curriculums = curriculumRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            filters.add(cb.equal(root.get("status").get("code"), "OPPEKAVA_STAATUS_K"));
            filters.add(cb.equal(root.get("higher"), Boolean.TRUE));
            
            if(!CollectionUtils.isEmpty(criteria.getSubjects())) {
                Subquery<Long> subjectsQuery = query.subquery(Long.class);
                Root<CurriculumVersionHigherModuleSubject> curriculumSubjectRoot = 
                        subjectsQuery.from(CurriculumVersionHigherModuleSubject.class);
                subjectsQuery.select(curriculumSubjectRoot.join("module")
                        .join("curriculumVersion").get("curriculum").get("id"))
                .where(curriculumSubjectRoot.get("subject").get("id").in(criteria.getSubjects()));
                filters.add(root.get("id").in(subjectsQuery));
            }
            
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        return StreamUtil.toMappedList(AutocompleteResult::of, curriculums);
    }

    public SubjectStudyPeriodPlan create(Long schoolId, SubjectStudyPeriodPlanDto form) {
        SubjectStudyPeriodPlan plan = new SubjectStudyPeriodPlan();

        Subject subject = subjectRepository.getOne(form.getSubject());
        AssertionFailedException.throwIf(!EntityUtil.getId(subject.getSchool()).equals(schoolId),
                "User and subject have different schools!");
        plan.setSubject(subject);

        StudyPeriod studyPeriod = studyPeriodRepository.getOne(form.getStudyPeriod());
        AssertionFailedException.throwIf(!EntityUtil.getId(studyPeriod.getStudyYear().getSchool()).equals(schoolId),
                "User and studyPeriod have different schools!");
        plan.setStudyPeriod(studyPeriod);

        return save(schoolId, plan, form);
    }

    public SubjectStudyPeriodPlan save(Long schoolId, SubjectStudyPeriodPlan plan, SubjectStudyPeriodPlanDto form) {
        StudyPeriod studyPeriod = studyPeriodRepository.getOne(form.getStudyPeriod());
        AssertionFailedException.throwIf(studyPeriod.getEndDate().isBefore(LocalDate.now()) ,
                "Past subjectStudyPeriods cannot be updated or created");

        deleteDuplicates(form);
        updateCurriculums(plan, form.getCurriculums(),schoolId);
        updateStudyForms(plan, form.getStudyForms());
        updateCapacities(plan, form.getCapacities());
        return subjectStudyPeriodPlanRepository.save(plan);
    }
    
    private void deleteDuplicates(SubjectStudyPeriodPlanDto form) {
        List<SubjectStudyPeriodPlan> deletedPlans = getDuplicates(SubjectStudyPeriodPlanUniqueCommand.of(form));
        if(!CollectionUtils.isEmpty(deletedPlans)) {
            subjectStudyPeriodPlanRepository.delete(deletedPlans);  // TODO: Use EntityUtil?
        }
    }

    private void updateCurriculums(SubjectStudyPeriodPlan plan, Set<Long> newCurriculums, Long schoolId) {
        EntityUtil.bindEntityCollection(plan.getCurriculums(), c -> EntityUtil.getId(c), 
        newCurriculums, c -> {
            SubjectStudyPeriodPlanCurriculum newCurriculum = new SubjectStudyPeriodPlanCurriculum();
            
            Curriculum curriculum = curriculumRepository.getOne(c);
            AssertionFailedException.throwIf(!EntityUtil.getId(curriculum.getSchool()).equals(schoolId),
                    "User and Curriculum have different schools!");
            newCurriculum.setCurriculum(curriculum);
            newCurriculum.setPlan(plan);
            return newCurriculum;
        });
    }
    
    private void updateStudyForms(SubjectStudyPeriodPlan plan, Set<String> newStudyForms) {
        EntityUtil.bindEntityCollection(plan.getStudyForms(), sf -> EntityUtil.getCode(sf.getStudyForm()), 
        newStudyForms, sf -> {
            SubjectStudyPeriodPlanStudyform newStudyForm = new SubjectStudyPeriodPlanStudyform();
            newStudyForm.setStudyForm(EntityUtil.validateClassifier(classifierRepository.getOne(sf), MainClassCode.OPPEVORM));
            newStudyForm.setPlan(plan);
            return newStudyForm;
        });
    }
    
    private void updateCapacities(SubjectStudyPeriodPlan plan, Set<SubjectStudyPeriodPlanCapacityDto> dtos) {
        EntityUtil.bindEntityCollection(plan.getCapacities(), SubjectStudyPeriodPlanCapacity::getId, dtos, 
                SubjectStudyPeriodPlanCapacityDto::getId, dto -> {
            SubjectStudyPeriodPlanCapacity capacity = new SubjectStudyPeriodPlanCapacity();
            updateCapacity(plan, dto, capacity);
            return capacity;
        }, (dto, capacity) -> updateCapacity(plan, dto, capacity));
    }
    
    private void updateCapacity(SubjectStudyPeriodPlan plan, SubjectStudyPeriodPlanCapacityDto dto, SubjectStudyPeriodPlanCapacity capacity) {
        EntityUtil.bindToEntity(dto, capacity, classifierRepository, "plan");
        capacity.setPlan(plan);
    }

    public void delete(Long schoolId, SubjectStudyPeriodPlan plan) {
        StudyPeriod studyPeriod = plan.getStudyPeriod();
        AssertionFailedException.throwIf(studyPeriod.getEndDate().isBefore(LocalDate.now()),
                "Past subjectStudyPeriods cannot be deleted");

        AssertionFailedException.throwIf(!EntityUtil.getId(studyPeriod.getStudyYear().getSchool()).equals(schoolId),
                "User and SubjectStudyPeriodsPlan's schools does not match");

        EntityUtil.deleteEntity(subjectStudyPeriodPlanRepository, plan);         
    }

    public Boolean exists(SubjectStudyPeriodPlanUniqueCommand form) {
        return Boolean.valueOf(!getDuplicates(form).isEmpty());
    }

    private List<SubjectStudyPeriodPlan> getDuplicates(SubjectStudyPeriodPlanUniqueCommand form) {
        if(CollectionUtils.isEmpty(form.getCurriculums())) {
            return Collections.emptyList();
        }
        // TODO excess data fetching
        return subjectStudyPeriodPlanRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("subject").get("id"), form.getSubject()));
            filters.add(cb.equal(root.get("studyPeriod").get("id"), form.getStudyPeriod()));
            
            if(form.getId() != null) {
                filters.add(cb.notEqual(root.get("id"), form.getId()));
            }
            if(!CollectionUtils.isEmpty(form.getCurriculums())) {   // Unnecessary check
                Subquery<Long> targetQuery = query.subquery(Long.class);
                Root<SubjectStudyPeriodPlanCurriculum> targetRoot = targetQuery.from(SubjectStudyPeriodPlanCurriculum.class);
                targetQuery = targetQuery.select(targetRoot.get("plan").get("id")).where(targetRoot.get("curriculum").get("id").in(form.getCurriculums()));
                filters.add(root.get("id").in(targetQuery));
            }
            if(!CollectionUtils.isEmpty(form.getStudyForms())) {    // Unnecessary check
                Subquery<Long> targetQuery = query.subquery(Long.class);
                Root<SubjectStudyPeriodPlanStudyform> targetRoot = targetQuery.from(SubjectStudyPeriodPlanStudyform.class);
                targetQuery = targetQuery.select(targetRoot.get("plan").get("id")).where(targetRoot.get("studyForm").get("code").in(form.getStudyForms()));
                filters.add(root.get("id").in(targetQuery));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
    }
}
