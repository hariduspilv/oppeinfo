package ee.hitsa.ois.service.curriculum;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumStudyForm;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionNominalCapacity;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionSpeciality;
import ee.hitsa.ois.domain.school.SchoolDepartment;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionDto;
import ee.hitsa.ois.web.dto.curriculumVersion.CurriculumVersionNominalCapacityDto;

@Transactional
@Service
public class CurriculumVersionService {
    
    @Autowired
    private CurriculumVersionRepository curriculumVersionRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private SchoolService schoolService;

    public CurriculumVersion createVersion(CurriculumVersionDto dto) {
        CurriculumVersion curriculumVersion = new CurriculumVersion();
        curriculumVersion.setCurriculum(em.getReference(Curriculum.class, dto.getCurriculum()));
        setCurriculumVersionStatus(curriculumVersion, CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_S);
        CurriculumVersion updatedCurriculumVersion = updateVersion(curriculumVersion.getCurriculum(), curriculumVersion, dto);
        return EntityUtil.save(updatedCurriculumVersion, em);
    }

    private CurriculumVersion updateVersion(Curriculum curriculum, CurriculumVersion version, CurriculumVersionDto dto) {
        EntityUtil.bindToEntity(dto, version, classifierRepository, "curriculumStudyForm", "modules",
                "specialities", "schoolDepartment", "occupationModules", "status", "nominalCapacities");
        updateCurriculumVersionSpecialities(version, dto.getSpecialitiesReferenceNumbers());
        updateVersionStudyForm(curriculum, version, dto);
        updateSchoolDepartment(version, dto);
        updateCurriculumVersionNominalCapcities(version, dto.getNominalCapacities());
        return version;
    }

    private void updateCurriculumVersionNominalCapcities(CurriculumVersion version,
            List<CurriculumVersionNominalCapacityDto> nominalCapacities) {
        if (nominalCapacities == null) return;
        EntityUtil.bindEntityCollection(version.getNominalCapacities(), cs -> EntityUtil.getId(cs), nominalCapacities, cs -> cs.getId(), s -> {
            CurriculumVersionNominalCapacity capacity = new CurriculumVersionNominalCapacity();
            capacity.setCredits(s.getCredits() != null ? s.getCredits() : BigDecimal.ZERO);
            capacity.setCurriculumVersion(version);
            // must find curriculum_version_speciality with only having curriculum_speciality
            Optional<CurriculumVersionSpeciality> speciality = version.getSpecialities().stream()
                    .filter(p -> s.getSpecialityId().equals(EntityUtil.getId(p.getCurriculumSpeciality()))).findFirst();
            capacity.setCurriculumVersionSpeciality(speciality.get());
            capacity.setPeriodNr(s.getPeriodNr());
            return capacity;
        }, (r, row) -> {
            row.setCredits(r.getCredits() != null ? r.getCredits() : BigDecimal.ZERO);
        });
    }

    private void updateCurriculumVersionSpecialities(CurriculumVersion version, Set<Long> specRefNums) {
        EntityUtil.bindEntityCollection(version.getSpecialities(), cs -> EntityUtil.getId(cs.getCurriculumSpeciality()), specRefNums, s -> {
            CurriculumVersionSpeciality cvs = new CurriculumVersionSpeciality();
            cvs.setCurriculumVersion(version);
            cvs.setCurriculumSpeciality(em.getReference(CurriculumSpeciality.class, s));
            return cvs;
        });
    }

    private static void updateVersionStudyForm(Curriculum curriculum, CurriculumVersion version, CurriculumVersionDto dto) {
        if(curriculum.getId() == null) {
            return;
        }
        String code = dto.getCurriculumStudyForm();
        Optional<CurriculumStudyForm> studyForm = curriculum.getStudyForms().stream()
                .filter(s -> EntityUtil.getCode(s.getStudyForm()).equals(code)).findFirst();
        version.setCurriculumStudyForm(studyForm.orElse(null));
    }

    private void updateSchoolDepartment(CurriculumVersion version, CurriculumVersionDto dto) {
        if (dto.getSchoolDepartment() != null) {
            version.setSchoolDepartment(em.getReference(SchoolDepartment.class, dto.getSchoolDepartment()));
        }
    }

    private void setCurriculumVersionStatus(CurriculumVersion curriculumVersion, CurriculumVersionStatus status) {
        curriculumVersion.setStatus(em.getReference(Classifier.class, status.name()));
    }

    public CurriculumVersion save(HoisUserDetails user, CurriculumVersion curriculumVersion,
            CurriculumVersionDto curriculumVersionDto) {
        EntityUtil.setUsername(user.getUsername(), em);
        CurriculumVersion updatedCurriculumVersion = updateVersion(curriculumVersion.getCurriculum(), curriculumVersion, curriculumVersionDto);
        return EntityUtil.save(updatedCurriculumVersion, em);
    }

    public CurriculumVersion close(CurriculumVersion curriculumVersion) {
        setCurriculumVersionStatus(curriculumVersion, CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C);
        return EntityUtil.save(curriculumVersion, em);
    }

    public CurriculumVersion setUnderRevision(HoisUserDetails user, CurriculumVersion curriculumVersion) {
        EntityUtil.setUsername(user.getUsername(), em);
        setCurriculumVersionStatus(curriculumVersion, CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_S);
        return EntityUtil.save(curriculumVersion, em);
    }

    public CurriculumVersion confirm(CurriculumVersion curriculumVersion) {
        setCurriculumVersionStatus(curriculumVersion, CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K);
        return EntityUtil.save(curriculumVersion, em);
    }

    public CurriculumVersion saveAndConfirm(HoisUserDetails user, CurriculumVersion curriculumVersion,
            CurriculumVersionDto curriculumVersionDto) {
        setCurriculumVersionStatus(curriculumVersion, CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K);
        return save(user, curriculumVersion, curriculumVersionDto);
    }

    public void delete(HoisUserDetails user, CurriculumVersion curriculumVersion) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(curriculumVersion, em);
    }

    public boolean isCodeUnique(Long schoolId, UniqueCommand command) {
        boolean codeExists;
        if(command.getId() == null) {
            codeExists = curriculumVersionRepository.existsByCurriculumSchoolIdAndCode(schoolId, command.getParamValue());
        } else {
            codeExists = curriculumVersionRepository.existsByCurriculumSchoolIdAndCodeAndIdNot(schoolId, command.getParamValue(), command.getId());
        }
        return !codeExists;
    }
    
    public CurriculumVersionDto get(HoisUserDetails user, CurriculumVersion curriculumVersion) {

        CurriculumVersionDto dto = CurriculumVersionDto.of(curriculumVersion);
        Curriculum curriculum = curriculumVersion.getCurriculum();

        String myEhisShool = schoolService.getEhisSchool(user.getSchoolId());
        dto.setCanChange(Boolean.valueOf(CurriculumUtil.canChange(user, myEhisShool, curriculum)));
        dto.setCanConfirm(Boolean.valueOf(CurriculumUtil.canConfirm(user, myEhisShool, curriculum)));
        dto.setCanSetUnderRevision(Boolean.valueOf(CurriculumUtil.canSetUnderRevision(user, curriculumVersion)));
        dto.setCanClose(Boolean.valueOf(CurriculumUtil.canClose(user, myEhisShool, curriculum)));
        dto.setCanDelete(Boolean.valueOf(CurriculumUtil.canDelete(user, myEhisShool, curriculum)));
        dto.setNominalCapacities(getCurriculumVersionNominalCapacities(curriculumVersion));
        return dto;
    }
    

    public List<AutocompleteResult> getSchoolDepartments(Curriculum curriculum) {
        return StreamUtil.toMappedList(d -> AutocompleteResult.of(d.getSchoolDepartment()), curriculum.getDepartments());
    }
    
    public List<CurriculumVersionNominalCapacityDto> getCurriculumVersionNominalCapacities(CurriculumVersion curriculumVersion) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_version_nominal_capacity cvnc "
                + "join curriculum_version_speciality cvs on cvs.id = cvnc.curriculum_version_speciality_id");
        
        qb.requiredCriteria("cvnc.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", EntityUtil.getId(curriculumVersion));
        qb.sort("cvnc.credits");
        List<?> data = qb.select("cvnc.id, cvnc.credits, cvs.curriculum_speciality_id, cvnc.period_nr", em).getResultList();
        
        return StreamUtil.toMappedList(r -> {
                    CurriculumVersionNominalCapacityDto dto = new CurriculumVersionNominalCapacityDto();
                    dto.setId(resultAsLong(r, 0));
                    dto.setCredits(resultAsDecimal(r, 1));
                    dto.setSpecialityId(resultAsLong(r, 2));
                    dto.setPeriodNr(resultAsLong(r, 3));
                    return dto;
                }, data);
    }
}
