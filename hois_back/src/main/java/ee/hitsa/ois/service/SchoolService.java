package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

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
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.util.EntityUtil;
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
    @Autowired
    private SchoolRepository schoolRepository;

    public SchoolDto getWithLogo(Long schoolId) {
        return EntityUtil.withEntity(schoolId, id -> em.find(School.class, id), school -> SchoolDto.ofWithLogo(school));
    }

    public byte[] getLogo(Long schoolId) {
        List<OisFile> logo = em.createQuery("select s.logo from School s where s.id=?1",OisFile.class)
                .setParameter(1, schoolId).getResultList();
        if (logo.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return logo.get(0).getFdata();
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
        return SchoolDto.ofWithLogo(EntityUtil.save(school, em));
    }

    public Page<SchoolDto> search(SchoolSearchCommand searchCommand, Pageable pageable) {
        return schoolRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            String nameField = Language.EN.equals(searchCommand.getLang()) ? "nameEn" : "nameEt";
            propertyContains(() -> root.get(nameField), cb, searchCommand.getName(), filters::add);

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(SchoolDto::of);
    }

    public void delete(School school) {
        EntityUtil.deleteEntity(school, em);
    }

    public School updateStudyLevels(School school, SchoolUpdateStudyLevelsCommand cmd) {
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

    public School updateLegends(School school, SchoolUpdateStudyYearScheduleLegendsCommand cmd) {
        EntityUtil.bindEntityCollection(school.getStudyYearScheduleLegends(), StudyYearScheduleLegend::getId, cmd.getLegends(), StudyYearScheduleLegendDto::getId, form -> {
            StudyYearScheduleLegend legend = new StudyYearScheduleLegend();
            legend.setSchool(school);
            return EntityUtil.bindToEntity(form, legend);
        }, (form, legend) -> EntityUtil.bindToEntity(form, legend));
        return EntityUtil.save(school, em);
    }

    public SchoolType schoolType(Long schoolId) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("");
        qb.parameter("school", schoolId);
        Object type = qb.select(
            "case when exists(select 1 from classifier c inner join school_study_level ssl on ssl.school_id = :school and ssl.study_level_code = c.code and c.value ~ '^[5-9].*$') " +
                    "then true else false end as higher, " +
            "case when exists(select 1 from classifier c inner join school_study_level ssl on ssl.school_id = :school and ssl.study_level_code = c.code and c.value ~ '^[0-4].*$') " +
                    "then true else false end as vocational", em).getSingleResult();

        return new SchoolType(resultAsBoolean(type, 0).booleanValue(), resultAsBoolean(type, 1).booleanValue());
    }

    public static class SchoolType {
        private final boolean higher;
        private final boolean vocational;

        public SchoolType(boolean higher, boolean vocational) {
            this.higher = higher;
            this.vocational = vocational;
        }

        public boolean isHigher() {
            return higher;
        }

        public boolean isVocational() {
            return vocational;
        }
    }
}
