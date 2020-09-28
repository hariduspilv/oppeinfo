package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ee.hitsa.ois.enums.EducationLevel;
import ee.hitsa.ois.util.StreamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.SchoolStudyLevel;
import ee.hitsa.ois.domain.school.StudyYearScheduleLegend;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.SchoolForm;
import ee.hitsa.ois.web.commandobject.SchoolSearchCommand;
import ee.hitsa.ois.web.commandobject.SchoolUpdateStudyLevelsCommand;
import ee.hitsa.ois.web.commandobject.SchoolUpdateStudyYearScheduleLegendsCommand;
import ee.hitsa.ois.web.dto.SchoolDto;
import ee.hitsa.ois.web.dto.StudyYearScheduleLegendDto;

@Transactional
@Service
public class SchoolService {

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;

    public SchoolDto getWithLogo(Long schoolId) {
        SchoolDto dto = EntityUtil.withEntity(schoolId, id -> em.find(School.class, id), school -> SchoolDto.ofWithLogo(school));
        dto.setType(schoolType(schoolId));
        return dto;
    }

    public byte[] getLogo(Long schoolId) {
        List<OisFile> logo = em.createQuery("select s.logo from School s where s.id=?1", OisFile.class)
                .setParameter(1, schoolId)
                .setMaxResults(1).getResultList();

        return logo.isEmpty() ? null : logo.get(0).getFdata();
    }

    public SchoolDto create(SchoolForm schoolForm) {
        return save(new School(), schoolForm);
    }

    public SchoolDto save(School school, SchoolForm schoolForm) {
        if(Boolean.TRUE.equals(schoolForm.getGenerateUserEmail())  && (schoolForm.getEmailDomain() == null || schoolForm.getEmailDomain().isEmpty())) {
            throw new ValidationFailedException("school.missing.emailDomain");
        }

        EntityUtil.bindToEntity(schoolForm, school, classifierRepository);
        OisFile logo = school.getLogo();
        if(Boolean.TRUE.equals(schoolForm.getDeleteCurrentLogo())) {
            if(logo != null) {
                em.remove(logo);
                school.setLogo(null);
            }
        } else if(schoolForm.getLogo() != null) {
            if(logo == null) {
                logo = new OisFile();
            }
            EntityUtil.bindToEntity(schoolForm.getLogo(), logo);
            logo = EntityUtil.save(logo, em);
            school.setLogo(logo);
        }
        // email domain to lowercase
        if(school.getEmailDomain() != null) {
            school.setEmailDomain(school.getEmailDomain().toLowerCase());
        }
        // XXX data duplication
        school.setNameEt(school.getEhisSchool().getNameEt());

        school = EntityUtil.save(school, em);
        em.flush();

        SchoolDto dto = SchoolDto.ofWithLogo(school);
        dto.setType(schoolType(school.getId()));
        return dto;
    }

    public Page<SchoolDto> search(SchoolSearchCommand searchCommand, Pageable pageable) {
        JpaQueryBuilder<School> qb = new JpaQueryBuilder<>(School.class, "s").sort(pageable);
        String nameField = Language.EN.equals(searchCommand.getLang()) ? "nameEn" : "nameEt";
        qb.optionalContains("s." + nameField, "name", searchCommand.getName());

        return JpaQueryUtil.pagingResult(qb, em, pageable).map(SchoolDto::of);
    }

    public void delete(HoisUserDetails user, School school) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(school, em);
    }

    public School updateStudyLevels(HoisUserDetails user, School school, SchoolUpdateStudyLevelsCommand cmd) {
        EntityUtil.setUsername(user.getUsername(), em);
        List<SchoolStudyLevel> storedStudyLevels = school.getStudyLevels();
        if(storedStudyLevels == null) {
            school.setStudyLevels(storedStudyLevels = new ArrayList<>());
        }
        EntityUtil.bindEntityCollection(storedStudyLevels, sl -> EntityUtil.getCode(sl.getStudyLevel()), cmd.getStudyLevels(), studyLevel -> {
            // add new link
            SchoolStudyLevel sl = new SchoolStudyLevel();
            sl.setSchool(school);
            sl.setStudyLevel(EntityUtil.validateClassifier(em.getReference(Classifier.class, studyLevel), MainClassCode.OPPEASTE));
            return sl;
        });

        return EntityUtil.save(school, em);
    }

    public School updateLegends(HoisUserDetails user, School school, SchoolUpdateStudyYearScheduleLegendsCommand cmd) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.bindEntityCollection(school.getStudyYearScheduleLegends(), StudyYearScheduleLegend::getId, cmd.getLegends(), StudyYearScheduleLegendDto::getId, form -> {
            StudyYearScheduleLegend legend = new StudyYearScheduleLegend();
            legend.setSchool(school);
            return EntityUtil.bindToEntity(form, legend);
        }, (form, legend) -> EntityUtil.bindToEntity(form, legend));
        return EntityUtil.save(school, em);
    }

    public SchoolType schoolType(Long schoolId) {
        List<?> data = em.createNativeQuery("select clc.connect_classifier_code from school s " +
                "join school_study_level ssl on ssl.school_id = s.id " +
                "join classifier cl on cl.code = ssl.study_level_code " +
                "join classifier_connect clc on clc.classifier_code = cl.code " +
                "where s.id = :school")
                .setParameter("school", schoolId)
                .getResultList();
        Set<String> educationLevels = StreamUtil.toMappedSet(r -> resultAsString(r, 0), data);

        return new SchoolType(!Collections.disjoint(EducationLevel.BASIC, educationLevels),
                !Collections.disjoint(EducationLevel.SECONDARY, educationLevels),
                !Collections.disjoint(EducationLevel.VOCATIONAL, educationLevels),
                !Collections.disjoint(EducationLevel.HIGHER, educationLevels),
                !Collections.disjoint(EducationLevel.DOCTOR, educationLevels));
    }
    
    public static SchoolType schoolType(Long schoolId, EntityManager em) {
        List<?> data = em.createNativeQuery("select clc.connect_classifier_code from school s " +
                "join school_study_level ssl on ssl.school_id = s.id " +
                "join classifier cl on cl.code = ssl.study_level_code " +
                "join classifier_connect clc on clc.classifier_code = cl.code " +
                "where s.id = :school")
                .setParameter("school", schoolId)
                .getResultList();
        Set<String> educationLevels = StreamUtil.toMappedSet(r -> resultAsString(r, 0), data);

        return new SchoolType(!Collections.disjoint(EducationLevel.BASIC, educationLevels),
                !Collections.disjoint(EducationLevel.SECONDARY, educationLevels),
                !Collections.disjoint(EducationLevel.VOCATIONAL, educationLevels),
                !Collections.disjoint(EducationLevel.HIGHER, educationLevels),
                !Collections.disjoint(EducationLevel.DOCTOR, educationLevels));
    }
    
    /**
     * @param schoolId
     * @return null if schoolId is null
     */
    public String getEhisSchool(Long schoolId) {
        return schoolId != null ? 
                EntityUtil.getCode(em.getReference(School.class, schoolId).getEhisSchool()) : null;
    }

    public static class SchoolType {
        private final boolean basic;
        private final boolean secondary;
        private final boolean vocational;
        private final boolean higher;
        private final boolean doctoral;

        public SchoolType(boolean basic, boolean secondary, boolean vocational, boolean higher, boolean doctoral) {
            this.basic = basic;
            this.secondary = secondary;
            this.vocational = vocational;
            this.higher = higher;
            this.doctoral = doctoral;
        }

        public boolean isBasic() {
            return basic;
        }

        public boolean isSecondary() {
            return secondary;
        }

        public boolean isVocational() {
            return vocational;
        }

        public boolean isHigher() {
            return higher;
        }

        public boolean isDoctoral() {
            return doctoral;
        }
    }
}
