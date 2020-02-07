package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.apelapplication.ApelSchool;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelSchoolForm;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelSchoolSearchCommand;
import ee.hitsa.ois.web.dto.apelapplication.ApelSchoolDto;
import ee.hitsa.ois.web.dto.apelapplication.ApelSchoolSearchDto;

@Transactional
@Service
public class ApelSchoolService {

    @Autowired
    private EntityManager em;

    public Page<ApelSchoolSearchDto> search(HoisUserDetails user, ApelSchoolSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from apel_school s").sort(pageable);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        
        qb.optionalContains(Language.EN.equals(criteria.getLang()) ? "s.name_en" : "s.name_et", "name", criteria.getName());
        qb.optionalCriteria("s.country_code in (:country)", "country", criteria.getCountry());
        
        return JpaQueryUtil.pagingResult(qb, "s.id, s.name_et, s.name_en, s.country_code, s.ehis_school_code", em, pageable).map(r -> {
            ApelSchoolSearchDto dto = new ApelSchoolSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setNameEt(resultAsString(r, 1));
            dto.setNameEn(resultAsString(r, 2));
            dto.setCountry(resultAsString(r, 3)); 
            dto.setEhisSchool(resultAsString(r, 4)); 
            return dto;
        });
    }

    public ApelSchoolDto get(ApelSchool school) {
        return ApelSchoolDto.of(school);
    }

    public ApelSchool create(HoisUserDetails user, ApelSchoolForm schoolForm) {
        // To avoid duplicate schools check if school like that already exists
        ApelSchool school = findAlreadyCreatedSchool(schoolForm, user.getSchoolId());
        if (school == null) {
            school = new ApelSchool();
            school.setSchool(em.getReference(School.class, user.getSchoolId()));
            return save(user, school, schoolForm);
        }
        return school;
    }

    private ApelSchool findAlreadyCreatedSchool(ApelSchoolForm schoolForm, Long schoolId) {
        JpaQueryBuilder<ApelSchool> qb = new JpaQueryBuilder<>(ApelSchool.class, "a_s");
        qb.requiredCriteria("a_s.school.id = :schoolId", "schoolId", schoolId);
        if (schoolForm.getEhisSchool() != null) {
            qb.requiredCriteria("a_s.ehisSchool.code = :ehisCode", "ehisCode", schoolForm.getEhisSchool());
        } else {
            qb.requiredCriteria("a_s.nameEt = :nameEt", "nameEt", schoolForm.getNameEt());
            qb.requiredCriteria("a_s.country.code = :countryCode", "countryCode", schoolForm.getCountry());
            qb.filter("a_s.ehisSchool is null");
        }

        List<ApelSchool> school = qb.select(em).setMaxResults(1).getResultList();
        return !school.isEmpty() ? school.get(0) : null;
    }

    public ApelSchool save(HoisUserDetails user, ApelSchool school, ApelSchoolForm schoolForm) {
        EntityUtil.setUsername(user.getUsername(), em);
        
        EntityUtil.bindToEntity(schoolForm, school, "school", "country", "ehisSchool");
        school.setSchool(em.getReference(School.class, user.getSchoolId()));
        school.setCountry(em.getReference(Classifier.class, schoolForm.getCountry()));
        school.setEhisSchool(schoolForm.getEhisSchool() != null ? em.getReference(Classifier.class, schoolForm.getEhisSchool()) : null);

        return EntityUtil.save(school, em);
    }

    public void delete(HoisUserDetails user, ApelSchool school) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(school, em);
    }

    public List<String> usedEhisSchoolCodes(Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from apel_school s");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.filter("s.ehis_school_code is not null");

        List<?> data = qb.select("s.ehis_school_code", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsString(r, 0), data);
    }
}
