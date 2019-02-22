package ee.hitsa.ois.service.curriculum;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.curriculum.CurriculumJointPartner;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionElectiveModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionSpeciality;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HigherModuleCreditsUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.curriculum.HigherModuleSubjectCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionElectiveModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleSubjectDto;

@Transactional
@Service
public class CurriculumVersionHigherModuleService {
    
    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    public CurriculumVersionHigherModule create(CurriculumVersionHigherModuleDto form) {
        CurriculumVersionHigherModule module = createCurriculumVersionHigherModule(form);
        return EntityUtil.save(module, em);
    }
    
    private CurriculumVersionHigherModule createCurriculumVersionHigherModule(CurriculumVersionHigherModuleDto dto) {
        CurriculumVersionHigherModule module = new CurriculumVersionHigherModule();
        module.setCurriculumVersion(em.getReference(CurriculumVersion.class, dto.getCurriculumVersion()));
        return updateCurriculumVersionHigherModule(dto, module);
    }
    
    private CurriculumVersionHigherModule updateCurriculumVersionHigherModule(CurriculumVersionHigherModuleDto dto, CurriculumVersionHigherModule module) {
        EntityUtil.bindToEntity(dto, module, classifierRepository, "subjects", "electiveModules", "specialities");
        updateCurriculumVersionModuleElectiveModules(module, dto.getElectiveModules());
        updateCurriculumVersionModuleSubjects(module, dto.getSubjects());
        if(!Boolean.TRUE.equals(module.getMinorSpeciality())) {
            updateVersionModuleSpecialities(module, dto.getSpecialitiesReferenceNumbers());
        }
        return module;
    }
    
    private static void updateCurriculumVersionModuleElectiveModules(CurriculumVersionHigherModule module,
            Set<CurriculumVersionElectiveModuleDto> electiveModules) {
        Set<CurriculumVersionElectiveModule> newSet = new HashSet<>();
        if(electiveModules != null) {
            for(CurriculumVersionElectiveModuleDto dto : electiveModules) {
                CurriculumVersionElectiveModule newElectiveModule = dto.getId() == null ? new CurriculumVersionElectiveModule() :
                    EntityUtil.find(dto.getId(), module.getElectiveModules()).get();

                newSet.add(EntityUtil.bindToEntity(dto, newElectiveModule, "subjects"));
            }
        }
        module.setElectiveModules(newSet);
    }
    
    private void updateCurriculumVersionModuleSubjects(CurriculumVersionHigherModule module,
            Set<CurriculumVersionHigherModuleSubjectDto> subjects) {
        Set<CurriculumVersionHigherModuleSubject> newSubjects = new HashSet<>();
        if(subjects != null) {
            for(CurriculumVersionHigherModuleSubjectDto dto : subjects) {
                CurriculumVersionHigherModuleSubject subject = dto.getId() == null ? new CurriculumVersionHigherModuleSubject() :
                    EntityUtil.find(dto.getId(), module.getSubjects()).get();

                EntityUtil.bindToEntity(dto, subject, "nameEt", "nameEt", "credits", "school", "subjectId", "electiveModule", "assessment");
                subject.setSubject(em.getReference(Subject.class, dto.getSubjectId()));
                subject.setModule(module);
                
                CurriculumVersionElectiveModule electiveModule = null;
                if(dto.getElectiveModule() != null) {
                    electiveModule = module.getElectiveModules().stream().filter
                            (e -> e.getReferenceNumber().equals(dto.getElectiveModule())).findFirst().orElse(null);
                }
                subject.setElectiveModule(electiveModule);
                newSubjects.add(subject);
            }
        }
        module.setSubjects(newSubjects);
    }
    
    private void updateVersionModuleSpecialities(CurriculumVersionHigherModule module,  Set<Long> specialities) {
        EntityUtil.bindEntityCollection(module.getSpecialities(), versionSpeciality -> 
        EntityUtil.getId(versionSpeciality.getSpeciality()), specialities, versionSpeciality -> {
            CurriculumVersionHigherModuleSpeciality newSpeciality = new CurriculumVersionHigherModuleSpeciality();
            newSpeciality.setModule(module);
            newSpeciality.setSpeciality(em.getReference(CurriculumVersionSpeciality.class, versionSpeciality));
            return newSpeciality;
        });    
    }

    public CurriculumVersionHigherModule update(CurriculumVersionHigherModule module,
            CurriculumVersionHigherModuleDto form) {
        updateCurriculumVersionHigherModule(form, module);
        return EntityUtil.save(module, em);
    }

    public void delete(HoisUserDetails user, CurriculumVersionHigherModule module) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(module, em);
        module.getCurriculumVersion().getModules().remove(module);
    }

    public void deleteSubject(HoisUserDetails user, CurriculumVersionHigherModuleSubject subject) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(subject, em);
        CurriculumVersionHigherModule module = subject.getModule();
        module.getSubjects().remove(subject);
        module.getElectiveModules().forEach(e -> e.getSubjects().remove(subject));
        HigherModuleCreditsUtil.setCreduts(module);
        EntityUtil.save(module, em);
    }

    public List<CurriculumVersionHigherModuleSubjectDto> getSubjectsForHigherModule(HigherModuleSubjectCommand command) {
        CurriculumVersion version = em.getReference(CurriculumVersion.class, command.getCurriculumVersion());
        Long schoolId = EntityUtil.getId(version.getCurriculum().getSchool());
        List<CurriculumJointPartner> estonianJointPartners = 
                StreamUtil.toFilteredList(jp -> jp.getEhisSchool() != null, version.getCurriculum().getJointPartners());
        List<String> pointPartnersEhisSchools = StreamUtil.toMappedList(jp -> EntityUtil.getCode(jp.getEhisSchool()), estonianJointPartners);

        List<Subject> subjects = subjectRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            if(!pointPartnersEhisSchools.isEmpty()) {
                filters.add(
                        cb.or(root.get("school").get("ehisSchool").get("code").in(pointPartnersEhisSchools), 
                        cb.equal(root.get("school").get("id"), schoolId) ));
            } else {
                filters.add(cb.equal(root.get("school").get("id"), schoolId));
            }

            filters.add(cb.equal(root.get("status").get("code"), SubjectStatus.AINESTAATUS_K.name()));
            

            List<Predicate> moduleFilters = new ArrayList<>();
            Subquery<Long> moduleSubjectQuery = query.subquery(Long.class);
            Root<CurriculumVersionHigherModuleSubject> moduleSubjectRoot = moduleSubjectQuery.from(CurriculumVersionHigherModuleSubject.class);
            
            moduleFilters.add(cb.equal(moduleSubjectRoot.get("module").get("curriculumVersion").get("id"), command.getCurriculumVersion()));
            if(command.getModule() != null) {
                moduleFilters.add(cb.notEqual(moduleSubjectRoot.get("module").get("id"), command.getModule()));
            }
            moduleFilters.add(cb.isFalse(moduleSubjectRoot.get("module").get("minorSpeciality")));
            
            Subquery<Long> specialtiesQuery = moduleSubjectQuery.subquery(Long.class);
            Root<CurriculumVersionHigherModuleSpeciality> specialtiesRoot = specialtiesQuery.from(CurriculumVersionHigherModuleSpeciality.class);

            specialtiesQuery = specialtiesQuery.select(specialtiesRoot.get("module").get("id"))
                    .where(specialtiesRoot.get("speciality").get("id").in(command.getSpecialities()));

            moduleFilters.add(moduleSubjectRoot.get("module").get("id").in(specialtiesQuery));
            
            moduleSubjectQuery = moduleSubjectQuery.select(moduleSubjectRoot.get("subject").get("id"))
                    .where(cb.and(moduleFilters.toArray(new Predicate[moduleFilters.size()])));

            // Exclude subjects already added to other modules of the given specialties
            filters.add(cb.not(root.get("id").in(moduleSubjectQuery)));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        return StreamUtil.toMappedList(CurriculumVersionHigherModuleSubjectDto::of, subjects);
    }

    public Set<CurriculumVersionHigherModuleSubjectDto> getSubjectsForMinorSpeciality(HigherModuleSubjectCommand command) {
        List<CurriculumVersionHigherModuleSubject> subjects = em.createQuery("select s from CurriculumVersionHigherModuleSubject s where s.module.curriculumVersion.id =?1", CurriculumVersionHigherModuleSubject.class)
                .setParameter(1, command.getCurriculumVersion())
                .getResultList();
        return StreamUtil.toMappedSet(CurriculumVersionHigherModuleSubjectDto::forOptions, subjects);
    }

    public List<ClassifierSelection> getCurriculumVersionHmoduleTypes(Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_version_hmodule cvm "
                + "join curriculum_version cv on cv.id = cvm.curriculum_version_id "
                + "join curriculum c on c.id = cv.curriculum_id");

        qb.requiredCriteria("cvm.type_code = :typeCode", "typeCode", HigherModuleType.KORGMOODUL_M);
        qb.filter("cvm.type_name_et is not null");

        // school is optional as external expert can view the form
        qb.optionalCriteria("c.school_id = :schoolId", "schoolId", schoolId);

        List<?> data = qb.select("distinct cvm.type_name_et, cvm.type_name_en", em).getResultList();
        return StreamUtil.toMappedList(r ->
             new ClassifierSelection(null, resultAsString(r, 0), resultAsString(r, 1), null, null, null, null, null, null, null, null, null)
        , data);
    }

    public List<AutocompleteResult> getSpecialities(CurriculumVersion version) {
        return StreamUtil.toMappedList(
                s -> new AutocompleteResult(EntityUtil.getId(s), s.getCurriculumSpeciality().getNameEt(), 
                        s.getCurriculumSpeciality().getNameEn()), version.getSpecialities());
    }

    public Short getCurriculumStudyYears(Long curriculumId) {
        List<?> results = em.createNativeQuery("select c.study_period from curriculum c where c.id = ?1 limit 1").setParameter(1, curriculumId).getResultList();
        return results.isEmpty() ? Short.valueOf((short) 0) : Short.valueOf((short) Math.ceil(resultAsInteger(results.get(0), 0).doubleValue() / 12));
    }

}
