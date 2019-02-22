package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.service.curriculum.CurriculumSearchService;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumSearchCommand;
import ee.hitsa.ois.web.dto.PublicDataMapper;
import ee.hitsa.ois.web.dto.StateCurriculumDto;
import ee.hitsa.ois.web.dto.SubjectDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;

@Transactional
@Service
public class PublicDataService {

    @Autowired
    private EntityManager em;
    @Autowired
    private CurriculumSearchService curriculumSearchService;

    public Object curriculum(Long curriculumId) {
        Curriculum curriculum = em.getReference(Curriculum.class, curriculumId);
        assertVisibleToPublic(curriculum);
        return new PublicDataMapper(Language.ET).map(curriculum);
    }

    public Object curriculumVersion(Long curriculumId, Long curriculumVersionId) {
        CurriculumVersion curriculumVersion = em.getReference(CurriculumVersion.class, curriculumVersionId);
        if(curriculumId == null || !curriculumId.equals(EntityUtil.getId(curriculumVersion.getCurriculum()))) {
            throw new EntityNotFoundException();
        }
        if(!CurriculumUtil.isCurriculumVersionConfirmed(curriculumVersion)) {
            throw new EntityNotFoundException();
        }
        return new PublicDataMapper(Language.ET).map(curriculumVersion);
    }

    public Object subject(Long subjectId) {
        Subject s = em.getReference(Subject.class, subjectId);
        if(!SubjectUtil.isActive(s)) {
            throw new EntityNotFoundException();
        }
        return new PublicDataMapper(Language.ET).map(s);
    }

    private static void assertVisibleToPublic(Curriculum curriculum) {
        if(!ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_K, curriculum.getStatus())) {
            throw new EntityNotFoundException();
        }
    }

    @SuppressWarnings("unchecked")
    public Page<CurriculumSearchDto> curriculumSearch(CurriculumSearchCommand criteria, Pageable pageable) {
        return JpaQueryUtil.query(CurriculumSearchDto.class, Curriculum.class, (root, query, cb) -> {
            ((CriteriaQuery<CurriculumSearchDto>)query).select(cb.construct(CurriculumSearchDto.class,
                root.get("id"), root.get("nameEt"), root.get("nameEn"),
                root.get("credits"), root.get("validFrom"), root.get("validThru"), root.get("higher"),
                root.get("status").get("code"), root.get("origStudyLevel").get("code"),
                root.get("school").get("id"), root.get("school").get("nameEt"), root.get("school").get("nameEn"), 
                root.get("ehisStatus").get("code"), root.get("code"), root.get("merCode")));

            List<Predicate> filters = new ArrayList<>();

            // only confirmed
            filters.add(cb.equal(root.get("status").get("code"), CurriculumStatus.OPPEKAVA_STAATUS_K.name()));

            String nameField = Language.EN.equals(criteria.getLang()) ? "nameEn" : "nameEt";
            propertyContains(() -> root.get(nameField), cb, criteria.getName(), filters::add);

            if(!CollectionUtils.isEmpty(criteria.getStudyLevel())) {
                filters.add(root.get("origStudyLevel").get("code").in(criteria.getStudyLevel()));
            }

            List<Long> curriculumSchools = curriculumSearchService.getSchools(null, criteria.getSchool());
            if(!curriculumSchools.isEmpty()) {
                filters.add(curriculumSearchService.filterBySchools(root, query, cb, curriculumSchools, criteria.getIsPartnerSchool()));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable, em);
    }
    
    public StateCurriculumDto stateCurriculum(StateCurriculum stateCurriculum) {
        assertVisibleToPublic(stateCurriculum);
        StateCurriculumDto dto = StateCurriculumDto.of(stateCurriculum);
        dto.setCurricula(StreamUtil.toMappedSet(CurriculumSearchDto::forStateCurriculumForm, 
                stateCurriculum.getCurricula().stream()
                    .filter(c -> ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_K, c.getStatus()))));
        return dto;
    }

    private static void assertVisibleToPublic(StateCurriculum stateCurriculum) {
        if(!ClassifierUtil.equals(CurriculumStatus.OPPEKAVA_STAATUS_K, stateCurriculum.getStatus())) {
            throw new EntityNotFoundException();
        }
    }

    public SubjectDto subjectView(Subject subject) {
        assertVisibleToPublic(subject);
        return SubjectDto.forPublic(subject, em.createQuery("select cvms.module.curriculumVersion from CurriculumVersionHigherModuleSubject cvms"
                + " where cvms.subject.id = ?1 and cvms.module.curriculumVersion.status.code = ?2", CurriculumVersion.class)
                .setParameter(1, subject.getId())
                .setParameter(2, CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K.name())
                .getResultList());
    }

    private static void assertVisibleToPublic(Subject subject) {
        if(!ClassifierUtil.equals(SubjectStatus.AINESTAATUS_K, subject.getStatus())) {
            throw new EntityNotFoundException();
        }
    }

    public Object subjectProgram(SubjectProgram program) {
        return new PublicDataMapper(Language.ET).map(program);
    }

}
