package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.PracticeJournalEvaluation;
import ee.hitsa.ois.domain.enterprise.PracticeEvaluation;
import ee.hitsa.ois.domain.enterprise.PracticeEvaluationCriteria;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.practice.PracticeEvaluationForm;
import ee.hitsa.ois.web.commandobject.practice.PracticeEvaluationSearchCommand;
import ee.hitsa.ois.web.dto.practice.PracticeEvaluationCriteriaDto;
import ee.hitsa.ois.web.dto.practice.PracticeEvaluationDto;
import ee.hitsa.ois.web.dto.practice.PracticeEvaluationSearchDto;

@Transactional
@Service
public class PracticeEvaluationService {

    @Autowired
    private EntityManager em;

    public Page<PracticeEvaluationSearchDto> search(HoisUserDetails user, PracticeEvaluationSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from practice_evaluation pe").sort(pageable);

        qb.requiredCriteria("pe.school_id = :schoolId", "schoolId", user.getSchoolId());
        
        qb.optionalContains("pe.name_et", "nameEt", criteria.getNameEt());
        qb.optionalCriteria("pe.target_code = :target", "target", criteria.getTarget());
        if (Boolean.TRUE.equals(criteria.getIsActive())) {
            qb.filter("pe.is_active");
        }

        return JpaQueryUtil.pagingResult(qb, "pe.id, pe.name_et, pe.add_info, pe.is_active, pe.target_code", em, pageable).map(r -> {
            PracticeEvaluationSearchDto dto = new PracticeEvaluationSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setNameEt(resultAsString(r, 1));
            dto.setAddInfo(resultAsString(r, 2));
            dto.setIsActive(resultAsBoolean(r, 3));
            dto.setTarget(resultAsString(r, 4));
            return dto;
        });
    }

    public PracticeEvaluation create(HoisUserDetails user, PracticeEvaluationForm practiceEvaluationForm) {
        PracticeEvaluation practiceEvaluation = new PracticeEvaluation();
        
        practiceEvaluation.setSchool(em.getReference(School.class, user.getSchoolId()));
        
        return save(user, practiceEvaluation, practiceEvaluationForm);
    }

    public PracticeEvaluation save(HoisUserDetails user, PracticeEvaluation practiceEvaluation, PracticeEvaluationForm form) {
        EntityUtil.setUsername(user.getUsername(), em);
        
        validateEvaluation(user, practiceEvaluation, form);
        
        practiceEvaluation.setNameEt(form.getNameEt());
        practiceEvaluation.setAddInfo(form.getAddInfo());
        practiceEvaluation.setIsActive(form.getIsActive());
        practiceEvaluation.setTarget(em.getReference(Classifier.class, form.getTarget()));
        
        EntityUtil.bindEntityCollection(practiceEvaluation.getCriteria(), criteria -> EntityUtil.getId(criteria),
                form.getCriteria(), PracticeEvaluationCriteriaDto::getCriteriaId, dto -> {
                    PracticeEvaluationCriteria criteria = new PracticeEvaluationCriteria();
                    criteria.setPracticeEvaluation(practiceEvaluation);
                    return updateCriteria(dto, criteria);
                }, this::updateCriteria);
        return EntityUtil.save(practiceEvaluation, em);
    }
    
    private PracticeEvaluationCriteria updateCriteria(PracticeEvaluationCriteriaDto dto, PracticeEvaluationCriteria criteria) {
        criteria.setNameEt(dto.getNameEt());
        criteria.setAddInfo(dto.getAddInfo());
        criteria.setOrderNr(dto.getOrderNr());
        List<PracticeJournalEvaluation> evals = em.createQuery("select pje from PracticeJournalEvaluation pje where "
                + "pje.practiceEvaluationCriteria.id = ?1", PracticeJournalEvaluation.class)
                .setParameter(1, dto.getCriteriaId()).getResultList();
        for (PracticeJournalEvaluation eval : evals) {
            eval.setPracticeEvaluationCriteria(criteria);
            eval.setPracticeEvaluation(criteria.getPracticeEvaluation());
        }
        if (evals.isEmpty()) {
            criteria.setType(em.getReference(Classifier.class, dto.getType()));
        }
        return criteria;
    }
    
    private void validateEvaluation(HoisUserDetails user, PracticeEvaluation practiceEvaluation, PracticeEvaluationForm form) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from practice_evaluation pe");
        qb.requiredCriteria("pe.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("pe.name_et = :nameEt", "nameEt", form.getNameEt());
        qb.optionalCriteria("pe.id != :id", "id", EntityUtil.getId(practiceEvaluation));
        if (!qb.select("pe.id", em).getResultList().isEmpty()) {
            throw new ValidationFailedException("practiceEvaluation.error.evaluationWithNameExists");
        }
    }

    public void delete(HoisUserDetails user, PracticeEvaluation practiceEvaluation) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(practiceEvaluation, em);
    }

    public PracticeEvaluationDto get(PracticeEvaluation practiceEvaluation) {
        PracticeEvaluationDto dto = PracticeEvaluationDto.of(practiceEvaluation);
        List<Long> criteriaIds = dto.getCriteria().stream().map(p -> p.getCriteriaId()).collect(Collectors.toList());
        List<PracticeJournalEvaluation> evals = em.createQuery("select pje from PracticeJournalEvaluation pje where "
                + "pje.practiceEvaluationCriteria.id in ?1", PracticeJournalEvaluation.class)
                .setParameter(1, criteriaIds).getResultList();
        List<Long> relatedCriteriaIds =  evals.stream().map(p -> p.getPracticeEvaluationCriteria().getId()).collect(Collectors.toList());
        for (PracticeEvaluationCriteriaDto criteria : dto.getCriteria()) {
            if (relatedCriteriaIds.contains(criteria.getCriteriaId())) {
                criteria.setDisabled(Boolean.TRUE);
            }
        }
        dto.setCanDelete(Boolean.valueOf(canDelete(practiceEvaluation)));
        return dto;
    }
    
    private boolean canDelete(PracticeEvaluation practiceEvaluation) {
        Long practiceEvaluationId = EntityUtil.getId(practiceEvaluation);
        List<?> result = em.createNativeQuery("select id from contract where practice_evaluation_id = ?1 limit 1")
                .setParameter(1, practiceEvaluationId)
                .getResultList();
        if (!result.isEmpty()) {
            return false;
        }
        result = em.createNativeQuery("select id from practice_journal where practice_evaluation_id = ?1 limit 1")
                .setParameter(1, practiceEvaluationId)
                .getResultList();
        if (!result.isEmpty()) {
            return false;
        }
        return true;
    }

}
