package ee.hitsa.ois.service.curriculum;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumAddress;
import ee.hitsa.ois.domain.curriculum.CurriculumDepartment;
import ee.hitsa.ois.domain.curriculum.CurriculumFile;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.curriculum.CurriculumJointPartner;
import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumStudyForm;
import ee.hitsa.ois.domain.curriculum.CurriculumStudyLanguage;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleYearCapacity;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.SchoolDepartment;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.CurriculumConsecution;
import ee.hitsa.ois.enums.CurriculumDraft;
import ee.hitsa.ois.enums.CurriculumEhisStatus;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.ehis.EhisCurriculumService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StateCurriculumUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudyLevelUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.ControllerErrorHandler.ErrorInfo.Error;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumAddressForm;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumFileForm;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumForm;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumSchoolDepartmentCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumStudyLevelCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumFileDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumGradeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumJointPartnerDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSpecialityDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionDto;

@Transactional
@Service
public class CurriculumService {

    @Autowired
    private EntityManager em;
    @Autowired
    private CurriculumRepository curriculumRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EhisCurriculumService ehisCurriculumService;
    @Autowired
    private SchoolService schoolService;

    /**
     * Delete curriculum
     *
     * @param user
     * @param curriculum
     */
    public void delete(HoisUserDetails user, Curriculum curriculum) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(curriculum, em);
    }

    public List<Classifier> getAreasOfStudyByGroupOfStudy(String code) {
        return classifierRepository.findAreasOfStudyByGroupOfStudy(code);
    }

    /**
     * Create new curriculum
     *
     * @param user
     * @param curriculumForm
     * @return
     */
    public Curriculum create(HoisUserDetails user, CurriculumForm curriculumForm) {
        Curriculum curriculum = new Curriculum();
        curriculum.setSchool(em.getReference(School.class, user.getSchoolId()));
        if(Boolean.TRUE.equals(curriculumForm.getHigher())) {
            curriculum.setDraft(em.getReference(Classifier.class, CurriculumDraft.OPPEKAVA_LOOMISE_VIIS_PUUDUB.name()));
            curriculum.setConsecution(em.getReference(Classifier.class, CurriculumConsecution.OPPEKAVA_TYPE_E.name()));
            curriculum.setOccupation(Boolean.FALSE);
            updateGrades(curriculum, curriculumForm.getGrades());
            updateCurriculumSpecialities(curriculum, curriculumForm.getSpecialities());
        } else {
            curriculum.setDraft(em.getReference(Classifier.class, curriculumForm.getDraft()));
        }
        setCurriculumStatus(curriculum, CurriculumStatus.OPPEKAVA_STAATUS_S);
        curriculum.setHigher(curriculumForm.getHigher());
        return save(user, curriculum, curriculumForm);
    }

    /**
     * Update curriculum
     *
     * @param user
     * @param curriculum
     * @param curriculumForm
     * @return
     */
    public Curriculum save(HoisUserDetails user, Curriculum curriculum, CurriculumForm curriculumForm) {
        EntityUtil.setUsername(user.getUsername(), em);
        Integer oldStudyPeriod = curriculum.getStudyPeriod();
        EntityUtil.bindToEntity(curriculumForm, curriculum, classifierRepository, "draft", "higher",
              "versions", "studyLanguages", "studyForms", "addresses", "schoolDepartments", "files",
              "jointPartners", "specialities", "modules", "occupations", "grades", "teacher");

        if(curriculum.getId() != null) {
            updateCurriculumFiles(curriculum, StreamUtil.toMappedSet(CurriculumFileDto::of, curriculumForm.getFiles()));
        }
        updateDepartments(curriculum, curriculumForm.getSchoolDepartments());
        updateLanguages(curriculum, curriculumForm.getStudyLanguages());
        updateAddresses(curriculum, curriculumForm.getAddresses());
        updateJointPartners(curriculum, curriculumForm.getJointPartners());
        curriculum.setTeacher(curriculumForm.getTeacher() == null ? null : em.getReference(Teacher.class, curriculumForm.getTeacher().getId()));
        if(CurriculumUtil.isVocational(curriculum)) {
            updateStudyForms(curriculum, curriculumForm.getStudyForms());
            if(Boolean.TRUE.equals(curriculum.getJoint())) {
                updateVersionsSchoolDepartments(curriculum);
            }
            if(curriculum.getId() != null) {
                // check studyPeriod length change and adjust studyYearNumber-related data
                int oldStudyYears = CurriculumUtil.studyYears(oldStudyPeriod);
                int newStudyYears = CurriculumUtil.studyYears(curriculumForm.getStudyPeriod());
                if(oldStudyYears > newStudyYears) {
                    // new period is shorter, check that there are no themes with given years
                    List<?> data = em.createNativeQuery("select cv.code from curriculum_version_omodule_theme cvot"
                            + " join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id"
                            + " join curriculum_version cv on cvo.curriculum_version_id = cv.id"
                            + " where cv.curriculum_id = ?1 and cvot.study_year_number > ?2")
                        .setParameter(1, curriculum.getId())
                        .setParameter(2, Integer.valueOf(newStudyYears))
                        .getResultList();
                    if(!data.isEmpty()) {
                        // themes with bad study year numbers, fail
                        String cvNames = data.stream().map(r -> resultAsString(r, 0)).distinct().sorted().collect(Collectors.joining(", "));
                        throw new ValidationFailedException(
                                Collections.singletonList(new Error("curriculum.error.vocationalThemesWithBadStudyYearNumberPresent",
                                        Collections.singletonMap("versions", cvNames))));
                    }
                    // delete excess year capacities
                    em.createNativeQuery("delete from curriculum_version_omodule_year_capacity cvomyc"
                            + " where curriculum_version_omodule_id in (select cvo.id from curriculum_version_omodule cvo"
                                + " join curriculum_version cv on cvo.curriculum_version_id = cv.id and cv.curriculum_id = ?1)"
                                + " and study_year_number > ?2")
                        .setParameter(1, curriculum.getId())
                        .setParameter(2, Integer.valueOf(newStudyYears))
                        .executeUpdate();
                } else if(oldStudyYears < newStudyYears) {
                    // new period is longer, add new year capacity entries for modules
                    // watch out for existing legacy values for first 3 years
                    List<?> data = em.createNativeQuery("select cvo.id, cvomyc.study_year_number from curriculum_version_omodule cvo"
                            + " join curriculum_version cv on cvo.curriculum_version_id = cv.id"
                            + " left join curriculum_version_omodule_year_capacity cvomyc on cvomyc.curriculum_version_omodule_id = cvo.id"
                            + " where cv.curriculum_id = ?1")
                        .setParameter(1, curriculum.getId())
                        .getResultList();

                    Map<Long, Set<Long>> yearCapacities = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                            Collectors.mapping(r -> resultAsLong(r, 1), Collectors.toSet())));
                    List<Long> studyYears = StreamUtil.toMappedList(r -> r, IntStream.rangeClosed(1, newStudyYears).mapToObj(i -> Long.valueOf(i)));
                    for(Map.Entry<Long, Set<Long>> cvo : yearCapacities.entrySet()) {
                        CurriculumVersionOccupationModule cvom = em.getReference(CurriculumVersionOccupationModule.class, cvo.getKey());
                        for(Long studyYearNumber : studyYears) {
                            if(!cvo.getValue().contains(studyYearNumber)) {
                                CurriculumVersionOccupationModuleYearCapacity cvomyc = new CurriculumVersionOccupationModuleYearCapacity();
                                cvomyc.setModule(cvom);
                                cvomyc.setStudyYearNumber(Short.valueOf(studyYearNumber.shortValue()));
                                cvomyc.setCredits(BigDecimal.ZERO);
                                cvom.getYearCapacities().add(cvomyc);
                            }
                        }
                    }
                }
            }
        }
        return EntityUtil.save(curriculum, em);
    }

    private void updateJointPartners(Curriculum curriculum, Set<CurriculumJointPartnerDto> jointPartners) {
        EntityUtil.bindEntityCollection(curriculum.getJointPartners(), CurriculumJointPartner::getId, 
                jointPartners, CurriculumJointPartnerDto::getId, dto -> {
            CurriculumJointPartner jointPartner = new CurriculumJointPartner();
            jointPartner.setCurriculum(curriculum);
            return updateJointPartner(dto, jointPartner);
        }, this::updateJointPartner);
    }

    private CurriculumJointPartner updateJointPartner(CurriculumJointPartnerDto dto, CurriculumJointPartner partner) {
        return EntityUtil.bindToEntity(dto, partner, classifierRepository);
    }

    private void updateAddresses(Curriculum curriculum, Set<CurriculumAddressForm> addresses) {
        EntityUtil.bindEntityCollection(curriculum.getAddresses(), CurriculumAddress::getId, 
                addresses, CurriculumAddressForm::getId, dto -> {
            CurriculumAddress address = new CurriculumAddress();
            address.setCurriculum(curriculum);
            return updateAddress(dto, address);
        }, this::updateAddress);
    }

    private CurriculumAddress updateAddress(CurriculumAddressForm form, CurriculumAddress address) {
        return EntityUtil.bindToEntity(form, address, classifierRepository);
    }

    /**
     * Is joint partner is deleted from curriculum, 
     * then its school departments should be deleted from implementation plans
     */
    private void updateVersionsSchoolDepartments(Curriculum curriculum) {
        Set<String> ehisSchools = new HashSet<>();
        ehisSchools.add(EntityUtil.getCode(curriculum.getSchool().getEhisSchool()));
        ehisSchools.addAll(StreamUtil.toMappedSet(p -> EntityUtil.getCode(p.getEhisSchool()), curriculum.getJointPartners().stream().filter(p -> p.getEhisSchool() != null)));

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from school_department sd join school s on s.id = sd.school_id ");

        qb.requiredCriteria("s.ehis_school_code in :ehisSchools", "ehisSchools", ehisSchools);

        List<?> data = qb.select("sd.id", em).getResultList();
        Set<Long> schoolDepartments = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);

        for(CurriculumVersion version: curriculum.getVersions()) {
            if(!schoolDepartments.contains(EntityUtil.getNullableId(version.getSchoolDepartment()))) {
                version.setSchoolDepartment(null);
            }
        }
    }

    private void updateCurriculumSpecialities(Curriculum curriculum, Set<CurriculumSpecialityDto> specialities) {
        EntityUtil.bindEntityCollection(curriculum.getSpecialities(), CurriculumSpeciality::getId, 
                specialities, CurriculumSpecialityDto::getId, dto -> createSpeciality(curriculum, dto), this::updateSpeciality);
    }

    private CurriculumSpeciality createSpeciality(Curriculum curriculum, CurriculumSpecialityDto dto) {
        CurriculumSpeciality speciality = new CurriculumSpeciality();
        speciality.setCurriculum(curriculum);
        return updateSpeciality(dto, speciality);
    }

    private CurriculumSpeciality updateSpeciality(CurriculumSpecialityDto dto, CurriculumSpeciality speciality) {
        return EntityUtil.bindToEntity(dto, speciality, classifierRepository, "curriculum");
    }

    private void updateGrades(Curriculum curriculum, Set<CurriculumGradeDto> gradeDtos) {
        EntityUtil.bindEntityCollection(curriculum.getGrades(), CurriculumGrade::getId, gradeDtos, 
                CurriculumGradeDto::getId, dto -> createGrade(curriculum, dto), this::updateGrade);
    }

    private CurriculumGrade createGrade(Curriculum curriculum, CurriculumGradeDto dto) {
        CurriculumGrade grade = new CurriculumGrade();
        grade.setCurriculum(curriculum);
        return updateGrade(dto, grade);
    }

    private CurriculumGrade updateGrade(CurriculumGradeDto dto, CurriculumGrade grade) {
        return EntityUtil.bindToEntity(dto, grade, classifierRepository);
    }

    void updateDepartments(Curriculum curriculum, Set<Long> newDepartments) {
        EntityUtil.bindEntityCollection(curriculum.getDepartments(), c -> EntityUtil.getId(c.getSchoolDepartment()), newDepartments, d -> {
            CurriculumDepartment cd = new CurriculumDepartment();
            cd.setCurriculum(curriculum);
            cd.setSchoolDepartment(em.getReference(SchoolDepartment.class, d));
            return cd;
        });
    }
    
    /**
     * TODO: no files created here
     */
    private void updateCurriculumFiles(Curriculum curriculum, Set<CurriculumFileDto> newFileDtos) {
        EntityUtil.bindEntityCollection(curriculum.getFiles(), CurriculumFile::getId, 
                newFileDtos, CurriculumFileDto::getId, dto -> {
            CurriculumFile file = new CurriculumFile();
            file.setCurriculum(curriculum);
            file.setOisFile(EntityUtil.bindToEntity(dto.getOisFile(), new OisFile()));
            return updateCurriculumFile(dto, file);
        }, this::updateCurriculumFile);
    }
    
    private CurriculumFile updateCurriculumFile(CurriculumFileDto dto, CurriculumFile file) {
        return EntityUtil.bindToEntity(dto, file, classifierRepository, "oisFile");
    }

    void updateStudyForms(Curriculum curriculum, Set<String> studyForms) {
        EntityUtil.bindEntityCollection(curriculum.getStudyForms(), sf -> EntityUtil.getCode(sf.getStudyForm()), studyForms, studyForm -> {
            // add new link
            Classifier c = EntityUtil.validateClassifier(em.getReference(Classifier.class, studyForm), MainClassCode.OPPEVORM);
            return new CurriculumStudyForm(c);
        });
    }

    void updateLanguages(Curriculum target, Set<String> languageCodes) {
        EntityUtil.bindEntityCollection(target.getStudyLanguages(), e -> EntityUtil.getCode(e.getStudyLang()), languageCodes, code -> {
            CurriculumStudyLanguage csl = new CurriculumStudyLanguage();
            csl.setCurriculum(target);
            csl.setStudyLang(em.getReference(Classifier.class, code));
            return csl;
        });
    }

    public Curriculum closeCurriculum(Curriculum curriculum) {
        setCurriculumStatus(curriculum, CurriculumStatus.OPPEKAVA_STAATUS_C);
        if(!curriculum.getVersions().isEmpty()) {
            Classifier statusClosed = em.getReference(Classifier.class, CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C.name());
            for(CurriculumVersion version : curriculum.getVersions()) {
                version.setStatus(statusClosed);
            }
        }
        return EntityUtil.save(curriculum, em);
    }

    public Curriculum sendToEhis(HoisUserDetails user, Curriculum curriculum, boolean isTest) {
        ehisCurriculumService.sendToEhis(user, curriculum, isTest);
        curriculum.setEhisStatus(em.getReference(Classifier.class, CurriculumEhisStatus.OPPEKAVA_EHIS_STAATUS_A.name()));
        curriculum.setEhisChanged(LocalDate.now());
        return EntityUtil.save(curriculum, em);
    }

    public Curriculum updateFromEhis(HoisUserDetails user, Curriculum curriculum, boolean isTest) {
        ehisCurriculumService.updateFromEhis(user, curriculum, isTest);
        return EntityUtil.save(curriculum, em);
    }

    public CurriculumFile createCurriculumFile(Curriculum curriculum, CurriculumFileForm curriculumFileForm) {
        CurriculumFile curriculumFile = new CurriculumFile();
        EntityUtil.bindToEntity(curriculumFileForm, curriculumFile, classifierRepository, "oisFile");
        curriculumFile.setCurriculum(curriculum);
        curriculumFile.setOisFile(EntityUtil.bindToEntity(curriculumFileForm.getOisFile(), new OisFile()));
        return EntityUtil.save(curriculumFile, em);
    }

    public void deleteCurriculumFile(HoisUserDetails user, CurriculumFile curriculumFile) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(curriculumFile, em);
    }

    public boolean isCodeUnique(Long schoolId, UniqueCommand command) {
        boolean codeExists;
        if(command.getId() == null) {
            codeExists = curriculumRepository.existsBySchoolIdAndCode(schoolId, command.getParamValue());
        } else {
            codeExists = curriculumRepository.existsBySchoolIdAndCodeAndIdNot(schoolId, command.getParamValue(), command.getId());
        }
        return !codeExists;
    }
    
    public CurriculumGrade createCurriculumGrade(Curriculum curriculum, CurriculumGradeDto dto){
        CurriculumGrade grade = EntityUtil.save(createGrade(curriculum, dto), em);
        return grade;
    }

    public CurriculumGrade updateCurriculumGrade(CurriculumGradeDto dto, CurriculumGrade grade){
        return EntityUtil.save(updateGrade(dto, grade), em);
    }

    public void deleteCurriculumGrade(HoisUserDetails user, CurriculumGrade grade) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(grade, em);
    }

    public CurriculumSpeciality createCurriculumSpeciality(Curriculum curriculum, CurriculumSpecialityDto dto) {
        CurriculumSpeciality speciality = EntityUtil.save(createSpeciality(curriculum, dto), em);
        return speciality;
    }

    public CurriculumSpeciality updateCurriculumSpeciality(CurriculumSpeciality speciality,
            CurriculumSpecialityDto dto) {
        return EntityUtil.save(updateSpeciality(dto, speciality), em);
    }

    public void deleteCurriculumSpeciality(HoisUserDetails user, CurriculumSpeciality speciality) {
        if(speciality.isAddedToVersion()) {
            throw new ValidationFailedException("curriculum.error.specAddedToVersion");
        }
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(speciality, em);
    }

    public Curriculum saveAndProceedCurriculum(HoisUserDetails user, Curriculum curriculum, CurriculumForm curriculumForm) {
        setCurriculumStatus(curriculum, CurriculumStatus.OPPEKAVA_STAATUS_M);
        return save(user, curriculum, curriculumForm);
    }
    
    public Curriculum setUnderRevision(HoisUserDetails user, Curriculum curriculum) {
        EntityUtil.setUsername(user.getUsername(), em);
        setCurriculumStatus(curriculum, CurriculumStatus.OPPEKAVA_STAATUS_S);
        return EntityUtil.save(curriculum, em);
    }

    private void setCurriculumStatus(Curriculum curriculum, CurriculumStatus status) {
        curriculum.setStatus(em.getReference(Classifier.class, status.name()));
    }

    public CurriculumDto get(HoisUserDetails user, Curriculum curriculum) {
        CurriculumDto dto = CurriculumDto.of(curriculum);
        String myEhisShool = schoolService.getEhisSchool(user.getSchoolId());
        dto.setCanChange(Boolean.valueOf(CurriculumUtil.canChange(user, myEhisShool, curriculum)));
        dto.setCanConfirm(Boolean.valueOf(CurriculumUtil.canConfirm(user, myEhisShool, curriculum)));
        dto.setCanSetUnderRevision(Boolean.valueOf(CurriculumUtil.canSetUnderRevision(user, curriculum)));
        dto.setCanClose(Boolean.valueOf(CurriculumUtil.canClose(user, myEhisShool, curriculum)));
        dto.setCanDelete(Boolean.valueOf(CurriculumUtil.canDelete(user, myEhisShool, curriculum)));

        dto.setVersions(StreamUtil.toMappedSet(CurriculumVersionDto::forCurriculumForm, curriculum.getVersions()
                .stream().filter(v -> CurriculumUtil.canView(user, myEhisShool, v))));
        if(Boolean.TRUE.equals(curriculum.getHigher())) {
            setJointPartnersDeletePermissions(curriculum, dto.getJointPartners());
        }
        StateCurriculum sc = curriculum.getStateCurriculum();
        if(sc != null && StateCurriculumUtil.canView(user, sc)) {
            dto.setStateCurriculum(AutocompleteResult.of(sc));
        }
        return dto;
    }
    
    private static void setJointPartnersDeletePermissions(Curriculum curriculum,
            Set<CurriculumJointPartnerDto> jointPartners) {

        Set<String> subjectsEhisSchools = new HashSet<>();

        for(CurriculumVersion version : curriculum.getVersions()) {
            for(CurriculumVersionHigherModule module : version.getModules()) {
                for(CurriculumVersionHigherModuleSubject subject : module.getSubjects()) {
                    subjectsEhisSchools.add(EntityUtil.getCode(subject.getSubject().getSchool().getEhisSchool()));
                }
            }
        }
        for(CurriculumJointPartnerDto partner : jointPartners) {
            partner.setHasSubjects(Boolean.valueOf(subjectsEhisSchools.contains(partner.getEhisSchool())));
        }
    }

    public List<AutocompleteResult> getSchoolDepartments(Curriculum curriculum) {
        CurriculumSchoolDepartmentCommand command = new CurriculumSchoolDepartmentCommand();
        command.setCurriculum(EntityUtil.getId(curriculum));
        command.setEhisShools(StreamUtil.toMappedSet(p -> EntityUtil.getCode(p.getEhisSchool()), 
                curriculum.getJointPartners().stream().filter(p -> p.getEhisSchool() != null)));
        return getSchoolDepartments(null, command);
    }

    public List<AutocompleteResult> getSchoolDepartments(Long userSchoolId, CurriculumSchoolDepartmentCommand command) {
        
        School school = command.getCurriculum() != null ? 
                em.getReference(Curriculum.class, command.getCurriculum()).getSchool() : em.getReference(School.class, userSchoolId);
                
        String myEhisSchool = EntityUtil.getCode(school.getEhisSchool());
        command.getEhisShools().add(myEhisSchool);
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from school_department sd join school s on s.id = sd.school_id ");
        
        qb.requiredCriteria("s.ehis_school_code in :ehisSchools", "ehisSchools", command.getEhisShools());
        List<?> data = qb.select("sd.id, sd.name_et, sd.name_en", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            return new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2));
        }, data);
    }

    public List<String> getStudyLevels(Long schoolId, CurriculumStudyLevelCommand command) {
        School school = command.getCurriculum() != null ? 
                em.getReference(Curriculum.class, command.getCurriculum()).getSchool() : 
                    em.getReference(School.class, schoolId);
        List<String> studyLevels = StreamUtil.toMappedList(sl -> EntityUtil.getCode(sl.getStudyLevel()), school.getStudyLevels());
        return StreamUtil.toFilteredList(Boolean.TRUE.equals(command.getIsHigher()) ? StudyLevelUtil::isHigher : StudyLevelUtil::isVocational, studyLevels);
    }
}
