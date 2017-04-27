package ee.hitsa.ois.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodPlan;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodPlanCapacity;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodPlanCurriculum;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodPlanStudyform;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.repository.SubjectStudyPeriodPlanRepository;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodPlanSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanSearchDtoContainer;

/**
 * This is not final version of service
 *
 */
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

    public Page<SubjectStudyPeriodPlanSearchDtoContainer> search(Long schoolId, SubjectStudyPeriodPlanSearchCommand criteria,
            Pageable pageable) {
        
        return subjectRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            if(criteria.getSubject() != null) {
                filters.add(cb.equal(root.get("id"), criteria.getSubject()));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(SubjectStudyPeriodPlanSearchDtoContainer::of);
    }
    
    
/**
 *     public Page<SubjectStudyPeriodPlanDto> search(SubjectStudyPeriodPlanSearchCommand criteria,
            Pageable pageable) {
        return subjectStudyPeriodPlanRepository.findAll((root, query, cb) -> {
          List<Predicate> filters = new ArrayList<>();
          
          if(criteria.getStudyPeriod() != null) {
              filters.add(cb.equal(root.get("studyPeriod").get("id"), criteria.getStudyPeriod()));
          }
          if(criteria.getSubject() != null) {
              filters.add(cb.equal(root.get("subject").get("id"), criteria.getSubject()));
          }
          if(criteria.getCurriculum() != null) {
              Subquery<Long> targetQuery = query.subquery(Long.class);
              Root<SubjectStudyPeriodPlanCurriculum> targetRoot = targetQuery.from(SubjectStudyPeriodPlanCurriculum.class);
              targetQuery = targetQuery.select(targetRoot.get("plan").get("id"))
                      .where(targetRoot.get("curriculum").get("id").in(criteria.getCurriculum()));
              filters.add(root.get("id").in(targetQuery));
          }
          return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(SubjectStudyPeriodPlanDto::of);
    }    
 */
    
    public List<StudyPeriodDto> studyPeriods(Long schoolId) {
        return StreamUtil.toMappedList(sp -> {
            StudyPeriodDto dto = new StudyPeriodDto();
            dto.setId(sp.getId());
            dto.setNameEt(sp.getNameEt());
            dto.setNameEn(sp.getNameEn());
            dto.setStartDate(sp.getStartDate());
            dto.setEndDate(sp.getEndDate());
            return dto;
        }, studyPeriodRepository.findAll((root, query, cb) -> {
            return cb.equal(root.get("studyYear").get("school").get("id"), schoolId);
        }));
    }
    
    public List<AutocompleteResult> curriculums (Long schoolId){
        List<Curriculum> curriculums = curriculumRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            filters.add(cb.equal(root.get("status").get("code"), "OPPEKAVA_STAATUS_K"));
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
        updateCurriculums(plan, form.getCurriculums(),schoolId);
        updateStudyForms(plan, form.getStudyForms());
        updateCapacities(plan, form.getCapacities());
        return subjectStudyPeriodPlanRepository.save(plan);
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

    public void delete(SubjectStudyPeriodPlan plan) {
        EntityUtil.deleteEntity(subjectStudyPeriodPlanRepository, plan);        
    }

}
