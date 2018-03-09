package ee.hitsa.ois.service.curriculum;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.ClassifierConnect;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumDepartment;
import ee.hitsa.ois.domain.curriculum.CurriculumJointPartner;
import ee.hitsa.ois.domain.curriculum.CurriculumStudyLanguage;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumSearchCommand;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;

@Transactional
@Service
public class CurriculumSearchService {

    @Autowired
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public Page<CurriculumSearchDto> search(HoisUserDetails user, CurriculumSearchCommand criteria, Pageable pageable) {
        if(!CurriculumUtil.canView(user)) {
            criteria.setStatus(Collections.singletonList(CurriculumStatus.OPPEKAVA_STAATUS_K.name()));
        }
        return JpaQueryUtil.query(CurriculumSearchDto.class, Curriculum.class, (root, query, cb) -> {
            ((CriteriaQuery<CurriculumSearchDto>)query).select(cb.construct(CurriculumSearchDto.class,
                root.get("id"), root.get("nameEt"), root.get("nameEn"),
                root.get("credits"), root.get("validFrom"), root.get("validThru"), root.get("higher"),
                root.get("status").get("code"), root.get("origStudyLevel").get("code"),
                root.get("school").get("id"), root.get("school").get("nameEt"), root.get("school").get("nameEn"), 
                root.get("ehisStatus").get("code"), root.get("code"), root.get("merCode")));

            List<Predicate> filters = new ArrayList<>();

            String nameField = Language.EN.equals(criteria.getLang()) ? "nameEn" : "nameEt";
            propertyContains(() -> root.get(nameField), cb, criteria.getName(), filters::add);
            if(criteria.getValidFrom() != null) {
                filters.add(cb.greaterThanOrEqualTo(root.get("validFrom"), criteria.getValidFrom()));
            }
            if(criteria.getValidThru() != null) {
                filters.add(cb.lessThanOrEqualTo(root.get("validThru"), criteria.getValidThru()));
            }
            if(criteria.getCreditsMin() != null) {
                filters.add(cb.greaterThanOrEqualTo(root.get("credits"), criteria.getCreditsMin()));
            }
            if(criteria.getCreditsMax() != null) {
                filters.add(cb.lessThanOrEqualTo(root.get("credits"), criteria.getCreditsMax()));
            }
            if (criteria.getIsJoint() != null) {
                filters.add(cb.equal(root.get("joint"), criteria.getIsJoint()));
            }
            
            if (Boolean.TRUE.equals(criteria.getIsVocational())) {
                filters.add(cb.equal(cb.substring(root.get("origStudyLevel").get("value"), 1, 1), "4"));
            } else if (Boolean.FALSE.equals(criteria.getIsVocational())) {
                filters.add(cb.notEqual(cb.substring(root.get("origStudyLevel").get("value"), 1, 1), "4"));
            }

            propertyContains(() -> root.get("code"), cb, criteria.getCode(), filters::add);
            propertyContains(() -> root.get("merCode"), cb, criteria.getMerCode(), filters::add);

            if(!CollectionUtils.isEmpty(criteria.getStatus())) {
                filters.add(root.get("status").get("code").in(criteria.getStatus()));
            }
            if(!CollectionUtils.isEmpty(criteria.getEhisStatus())) {
                filters.add(root.get("ehisStatus").get("code").in(criteria.getEhisStatus()));
            }
            if(!CollectionUtils.isEmpty(criteria.getIscedClassCode())) {
                filters.add(root.get("iscedClass").get("code").in(criteria.getIscedClassCode()));
            }
            if(!CollectionUtils.isEmpty(criteria.getStudyLevel())) {
                filters.add(root.get("origStudyLevel").get("code").in(criteria.getStudyLevel()));
            }
            if(!CollectionUtils.isEmpty(criteria.getCurriculumGroup())) {
                filters.add(root.get("group").get("code").in(criteria.getCurriculumGroup()));
            }
            if(!CollectionUtils.isEmpty(criteria.getEkrLevel())) {
                Subquery<String> targetQuery = query.subquery(String.class);
                Root<ClassifierConnect> targetRoot = targetQuery.from(ClassifierConnect.class);
                targetQuery = targetQuery.select(targetRoot.get("classifier").get("code")).where(targetRoot.get("connectClassifier").get("code").in(criteria.getEkrLevel()));
                filters.add(root.get("origStudyLevel").get("code").in(targetQuery));
            }
            if(!CollectionUtils.isEmpty(criteria.getIscedSuun())) {
                Subquery<String> targetQuery = query.subquery(String.class);
                Root<ClassifierConnect> targetRoot = targetQuery.from(ClassifierConnect.class);
                targetQuery = targetQuery.select(targetRoot.get("classifier").get("code")).where(targetRoot.get("connectClassifier").get("code").in(criteria.getIscedSuun()));
                /*In case ISCED_RYHM classifier is saved in isced_class_code column (vocational curriculum)*/
                Predicate forVocational = root.get("iscedClass").get("code").in(targetQuery);
                /*In case ISCED_SUUN classifier is saved in isced_class_code column (higher curriculum)*/
                Predicate forHigher = root.get("iscedClass").get("code").in(criteria.getIscedSuun());
                filters.add(cb.or(forVocational, forHigher));
            }

            if(criteria.getIscedVald() != null) {
                // get ISCED_SUUN classifier from isced_class (vocational curriculum)
                // or ISCED_VALD (higher curriculum)
                Subquery<String> getIscedSuun = query.subquery(String.class);
                Root<ClassifierConnect> iscedSuun = getIscedSuun.from(ClassifierConnect.class);
                getIscedSuun = getIscedSuun.select(iscedSuun.get("classifier")
                        .get("code")).where(cb.equal(iscedSuun.get("connectClassifier").get("code"), criteria.getIscedVald()));

                // get ISCED_RYHM classifier from ISCED_SUUN (vocational curriculum)
                Subquery<String> getIscedRyhm = getIscedSuun.subquery(String.class);
                Root<ClassifierConnect> iscedRyhm = getIscedRyhm.from(ClassifierConnect.class);
                getIscedRyhm = getIscedRyhm.select(iscedRyhm.get("classifier")
                        .get("code")).where(iscedRyhm.get("connectClassifier").get("code").in(getIscedSuun));
                /*In case ISCED_RYHM classifier is saved in isced_class_code column (vocational curriculum)*/
                Predicate forVocational = root.get("iscedClass").get("code").in(getIscedRyhm);
                /*In case ISCED_VALD classifier is saved in isced_class_code column (higher curriculum)*/
                Predicate forHigher1 = cb.equal(root.get("iscedClass").get("code"), criteria.getIscedVald());
                /*In case ISCED_SUUN classifier is saved in isced_class_code column (higher curriculum)*/
                Predicate forHigher2 = root.get("iscedClass").get("code").in(getIscedSuun);
                filters.add(cb.or(forVocational, forHigher1, forHigher2));
            }
            if(!CollectionUtils.isEmpty(criteria.getStudyLanguage())) {
                Subquery<Long> targetQuery = query.subquery(Long.class);
                Root<CurriculumStudyLanguage> targetRoot = targetQuery.from(CurriculumStudyLanguage.class);
                targetQuery = targetQuery.select(targetRoot.get("curriculum").get("id")).where(targetRoot.get("studyLang").get("code").in(criteria.getStudyLanguage()));
                filters.add(root.get("id").in(targetQuery));
            }
            if(!CollectionUtils.isEmpty(criteria.getDepartment())) {
                Subquery<Long> targetQuery = query.subquery(Long.class);
                Root<CurriculumDepartment> targetRoot = targetQuery.from(CurriculumDepartment.class);
                targetQuery = targetQuery.select(targetRoot.get("curriculum").get("id")).where(targetRoot.get("schoolDepartment").get("id").in(criteria.getDepartment()));
                filters.add(root.get("id").in(targetQuery));
            }

            List<Long> curriculumSchools = getSchools(user.getSchoolId(), criteria.getSchool());
            if(!curriculumSchools.isEmpty()) {
                filters.add(filterBySchools(root, query, cb, curriculumSchools, criteria.getIsPartnerSchool()));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable, em).map(c -> {
            c.setCanChange(Boolean.valueOf(CurriculumUtil.canBeEdited(user, c.getStatus(), c.getEhisStatus())));
            return c;
        });
    }
    
    /**
     * @return school ids from search criteria or user's school. 
     * Returns empty list if none are present.
     */
    public List<Long> getSchools(Long usersSchool, List<Long> criteriaSchools) {
        List<Long> curriculumSchools = new ArrayList<>();
        if(!CollectionUtils.isEmpty(criteriaSchools)) {
            curriculumSchools.addAll(criteriaSchools);
        } else if(usersSchool != null){
            curriculumSchools.add(usersSchool);
        }
        return curriculumSchools;
    }

    /**
     * Shows curriculums and joint curriculums(!) of defined schools
     */
    public Predicate filterBySchools(Root<Curriculum> root, CriteriaQuery<?> query, CriteriaBuilder cb, List<Long> curriculumSchools, Boolean isPartnerSchool) {
        Predicate mySchool = root.get("school").get("id").in(curriculumSchools);
        
        Subquery<Long> partnerSchools = query.subquery(Long.class);
        Root<CurriculumJointPartner> partnersRoot = partnerSchools.from(CurriculumJointPartner.class);
        
        Subquery<String> schools = partnerSchools.subquery(String.class);
        Root<School> schoolsRoot = schools.from(School.class);
        schools = schools.select(schoolsRoot.get("ehisSchool").get("code")).where(schoolsRoot.get("id").in(curriculumSchools));
        
        if (Boolean.FALSE.equals(isPartnerSchool)) {
            return cb.and(mySchool);
        }
        partnerSchools = partnerSchools.select(partnersRoot.get("curriculum").get("id")).where(partnersRoot.get("ehisSchool").get("code").in(schools));
        Predicate partnerSchool = root.get("id").in(partnerSchools);
        
        return cb.or(mySchool, partnerSchool);
    }
}
