package ee.hitsa.ois.service.curriculum;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumDepartment;
import ee.hitsa.ois.domain.curriculum.CurriculumFile;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.curriculum.CurriculumJointPartner;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupationSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumStudyForm;
import ee.hitsa.ois.domain.curriculum.CurriculumStudyLanguage;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionSpeciality;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.SchoolDepartment;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.enums.CurriculumConsecution;
import ee.hitsa.ois.enums.CurriculumDraft;
import ee.hitsa.ois.enums.CurriculumEhisStatus;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.repository.CurriculumVersionSpecialityRepository;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.ehis.EhisCurriculumService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudyLevelUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumFileForm;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumForm;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumSchoolDepartmentCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumStudyLevelCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionHigherModuleForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumFileDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumGradeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumJointPartnerDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumOccupationDto;
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
    private CurriculumVersionSpecialityRepository curriculumVersionSpecialityRepository;
    @Autowired
    private SchoolService schoolService;

    public void delete(Curriculum curriculum) {
        EntityUtil.deleteEntity(curriculum, em);
    }

	public List<Classifier> getAreasOfStudyByGroupOfStudy(String code) {
	    return classifierRepository.findAreasOfStudyByGroupOfStudy(code);
	}

    public Curriculum create(HoisUserDetails user, CurriculumForm curriculumForm) {
        Curriculum curriculum = new Curriculum();
        curriculum.setSchool(em.getReference(School.class, user.getSchoolId()));
        if(Boolean.TRUE.equals(curriculumForm.getHigher())) {
            curriculum.setDraft(classifierRepository.getOne(CurriculumDraft.OPPEKAVA_LOOMISE_VIIS_PUUDUB.name()));
            curriculum.setConsecution(classifierRepository.getOne(CurriculumConsecution.OPPEKAVA_TYPE_E.name()));
            curriculum.setOccupation(Boolean.FALSE);
            updateGrades(curriculum, curriculumForm.getGrades());
            updateCurriculumSpecialities(curriculum, curriculumForm.getSpecialities());
        } else {
            curriculum.setDraft(classifierRepository.getOne(curriculumForm.getDraft()));
            curriculum.setStateCurriculum(EntityUtil.getOptionalOne(StateCurriculum.class, 
                    curriculumForm.getStateCurriculum(), em));
        }
        setCurriculumStatus(curriculum, CurriculumStatus.OPPEKAVA_STAATUS_S);
        curriculum.setHigher(curriculumForm.getHigher());
        return save(curriculum, curriculumForm);
    }

    public Curriculum save(Curriculum curriculum, CurriculumForm curriculumForm) {
      EntityUtil.bindToEntity(curriculumForm, curriculum, classifierRepository, "draft", "higher", 
              "versions", "studyLanguages", "studyForms", "schoolDepartments", "files",
              "jointPartners", "specialities", "modules", "occupations", "grades");
      if(Boolean.FALSE.equals(curriculum.getHigher())) {
          updateOccupations(curriculum, curriculumForm.getOccupations());
          updateStudyForms(curriculum, curriculumForm.getStudyForms());
      }
      if(curriculum.getId() != null) {
          updateCurriculumFiles(curriculum, StreamUtil.toMappedSet(CurriculumFileDto::of, curriculumForm.getFiles()));
      }
      updateDepartments(curriculum, curriculumForm.getSchoolDepartments());
      updateLanguages(curriculum, curriculumForm.getStudyLanguages());
      updateJointPartners(curriculum, curriculumForm.getJointPartners());
      updateModules(curriculum, curriculumForm.getModules());
      return curriculumRepository.save(curriculum);
    }

    private void updateCurriculumVersionSpecialities(CurriculumVersion version, Set<Long> specRefNums) {
      EntityUtil.bindEntityCollection(version.getSpecialities(), cs -> EntityUtil.getId(cs.getCurriculumSpeciality()), specRefNums, s -> {
          CurriculumVersionSpeciality cvs = new CurriculumVersionSpeciality();
          cvs.setCurriculumVersion(version);
          cvs.setCurriculumSpeciality(em.getReference(CurriculumSpeciality.class, s));
          return cvs;
      });
    }

    private void updateModules(Curriculum curriculum, Set<CurriculumModuleDto> modules) {
        EntityUtil.bindEntityCollection(curriculum.getModules(), CurriculumModule::getId, modules, 
           CurriculumModuleDto::getId, dto -> createCurriculumModule(curriculum, dto), this::updateCurriculumModule);
    }

    /**
     * TODO: remove as no module created here
     */
    private CurriculumModule createCurriculumModule(Curriculum curriculum, CurriculumModuleDto dto) {
        CurriculumModule module = new CurriculumModule();
        module.setCurriculum(curriculum);
        curriculum.getModules().add(module);
        return updateCurriculumModule(dto, module);
    }

    /**
     * only needed for updating list of module occupations, if some occupation was removed
     */
    private CurriculumModule updateCurriculumModule(CurriculumModuleDto dto, CurriculumModule module) {
        EntityUtil.bindEntityCollection(module.getOccupations(), o -> EntityUtil.getCode(o.getOccupation()), dto.getOccupations(), occupationCode -> {
            Classifier c = EntityUtil.validateClassifier(classifierRepository.getOne(occupationCode),
                    MainClassCode.OSAKUTSE, MainClassCode.KUTSE, MainClassCode.SPETSKUTSE);
            return new CurriculumModuleOccupation(c);
          });
        return module;
    }

    private void updateOccupations(Curriculum curriculum, Set<CurriculumOccupationDto> occupations) {
        Set<CurriculumOccupation> newOccupations = new HashSet<>();
        if(occupations != null) {
            for(CurriculumOccupationDto dto : occupations) {
                CurriculumOccupation occupation = dto.getId() == null ? new CurriculumOccupation() :
                    EntityUtil.find(dto.getId(), curriculum.getOccupations()).get();

                EntityUtil.bindToEntity(dto, occupation, classifierRepository, "specialities");
                EntityUtil.bindEntityCollection(occupation.getSpecialities(), s -> EntityUtil.getCode(s.getSpeciality()), dto.getSpecialities(), specialityCode -> {
                    Classifier c = EntityUtil.validateClassifier(classifierRepository.getOne(specialityCode), MainClassCode.SPETSKUTSE);
                    return new CurriculumOccupationSpeciality(c);
                });

                newOccupations.add(occupation);
            }
        }
        curriculum.setOccupations(newOccupations);
    }
    
    public void updateJointPartners(Curriculum curriculum, Set<CurriculumJointPartnerDto> jointPartners) {
        EntityUtil.bindEntityCollection(curriculum.getJointPartners(), CurriculumJointPartner::getId, 
                jointPartners, CurriculumJointPartnerDto::getId, dto -> {
            CurriculumJointPartner jointPartner = new CurriculumJointPartner();
            jointPartner.setCurriculum(curriculum);
            updateJointPartner(dto, jointPartner);
            return jointPartner;
        }, this::updateJointPartner);
    }
    
    public void updateJointPartner(CurriculumJointPartnerDto dto, CurriculumJointPartner partner) {
        EntityUtil.bindToEntity(dto, partner, classifierRepository);
    }
    
    private void updateCurriculumSpecialities(Curriculum curriculum, Set<CurriculumSpecialityDto> specialities) {
        EntityUtil.bindEntityCollection(curriculum.getSpecialities(), CurriculumSpeciality::getId, 
                specialities, CurriculumSpecialityDto::getId, dto -> createSpeciality(curriculum, dto), this::updateSpeciality);
    }
    
    public CurriculumSpeciality createSpeciality(Curriculum curriculum, CurriculumSpecialityDto dto) {
        CurriculumSpeciality speciality = new CurriculumSpeciality();
        speciality.setCurriculum(curriculum);
        updateSpeciality(dto, speciality);
        return speciality;  
    }
    
    private CurriculumSpeciality updateSpeciality(CurriculumSpecialityDto dto, CurriculumSpeciality speciality) {
        return EntityUtil.bindToEntity(dto, speciality, classifierRepository, "curriculum");
    }
    
    private void updateGrades(Curriculum curriculum, Set<CurriculumGradeDto> gradeDtos) {
        EntityUtil.bindEntityCollection(curriculum.getGrades(), CurriculumGrade::getId, gradeDtos, 
                CurriculumGradeDto::getId, dto -> createGrade(curriculum, dto), this::updateGrade);
    }
    
    public CurriculumGrade createGrade(Curriculum curriculum, CurriculumGradeDto dto) {
        CurriculumGrade grade = new CurriculumGrade();
        grade.setCurriculum(curriculum);
        updateGrade(dto, grade);
        return grade;
    }

    public CurriculumGrade updateGrade(CurriculumGradeDto dto, CurriculumGrade grade) {
        return EntityUtil.bindToEntity(dto, grade, classifierRepository);
    }

    public void updateDepartments(Curriculum curriculum, Set<Long> newDepartments) {
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
    public void updateCurriculumFiles(Curriculum curriculum, Set<CurriculumFileDto> newFileDtos) {
        EntityUtil.bindEntityCollection(curriculum.getFiles(), CurriculumFile::getId, 
                newFileDtos, CurriculumFileDto::getId, dto -> {
            CurriculumFile file = new CurriculumFile();
            file.setCurriculum(curriculum);
            file.setOisFile(EntityUtil.bindToEntity(dto.getOisFile(), new OisFile()));
            updateCurriculumFile(dto, file);
            return file;
        }, this::updateCurriculumFile);
    }
    
    public void updateCurriculumFile(CurriculumFileDto dto, CurriculumFile file) {
        EntityUtil.bindToEntity(dto, file, classifierRepository, "oisFile");
    }

    public void updateStudyForms(Curriculum curriculum, Set<String> studyForms) {
        EntityUtil.bindEntityCollection(curriculum.getStudyForms(), sf -> EntityUtil.getCode(sf.getStudyForm()), studyForms, studyForm -> {
            // add new link
            Classifier c = EntityUtil.validateClassifier(classifierRepository.getOne(studyForm), MainClassCode.OPPEVORM);
            return new CurriculumStudyForm(c);
        });
    }

    void updateLanguages(Curriculum target, Set<String> languageCodes) {
        EntityUtil.bindEntityCollection(target.getStudyLanguages(), e -> EntityUtil.getCode(e.getStudyLang()), languageCodes, code -> {
            CurriculumStudyLanguage csl = new CurriculumStudyLanguage();
            csl.setCurriculum(target);
            csl.setStudyLang(classifierRepository.getOne(code));
            return csl;
        });
    }

    public Curriculum updateCurriculumSpecialities(Curriculum curriculum,
            CurriculumDto form) {
        updateCurriculumSpecialities(curriculum, form.getSpecialities());
        return EntityUtil.save(curriculum, em);
    }

    public Curriculum updateCurriculumGrades(Curriculum curriculum, CurriculumDto form) {
        updateGrades(curriculum, form.getGrades());
        return EntityUtil.save(curriculum, em);
    }

    public Curriculum closeCurriculum(Curriculum curriculum) {
        setCurriculumStatus(curriculum, CurriculumStatus.OPPEKAVA_STAATUS_C);
        if(!curriculum.getVersions().isEmpty()) {
            Classifier statusClosed = classifierRepository.findOne(CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C.name());
            for(CurriculumVersion version : curriculum.getVersions()) {
                version.setStatus(statusClosed);
            }
        }
        return EntityUtil.save(curriculum, em);
    }

    public Curriculum sendToEhis(HoisUserDetails user, Curriculum curriculum) {
        ehisCurriculumService.sendToEhis(user, curriculum);
        curriculum.setEhisStatus(classifierRepository.findOne(CurriculumEhisStatus.OPPEKAVA_EHIS_STAATUS_A.name()));
        curriculum.setEhisChanged(LocalDate.now());
        return EntityUtil.save(curriculum, em);
    }

    public Curriculum updateFromEhis(HoisUserDetails user, Curriculum curriculum) {
        ehisCurriculumService.updateFromEhis(user, curriculum);
        return EntityUtil.save(curriculum, em);
    }

    public CurriculumFile createCurriculumFile(Curriculum curriculum, CurriculumFileForm curriculumFileForm) {
        CurriculumFile curriculumFile = new CurriculumFile();
        EntityUtil.bindToEntity(curriculumFileForm, curriculumFile, classifierRepository, "oisFile");
        curriculumFile.setCurriculum(curriculum);
        curriculumFile.setOisFile(EntityUtil.bindToEntity(curriculumFileForm.getOisFile(), new OisFile()));
        return EntityUtil.save(curriculumFile, em);
    }

    public void deleteCurriculumFile(CurriculumFile curriculumFile) {
        EntityUtil.deleteEntity(curriculumFile, em);
    }

    public boolean isMerCodeUnique(UniqueCommand command) {
        boolean codeExists;
        if(command.getParamValue() == null) {
            return true;
        } else if(command.getId() == null) {
            codeExists = curriculumRepository.existsByMerCode(command.getParamValue());
        } else {
            codeExists = curriculumRepository.existsByMerCodeAndIdNot(command.getParamValue(), command.getId());
        }
        return !codeExists;
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
        curriculum.getGrades().add(grade);
        return grade;
    }
    
    public CurriculumGrade updateCurriculumGrade(CurriculumGradeDto dto, CurriculumGrade grade){
        return EntityUtil.save(updateGrade(dto, grade), em);
    }

    public void deleteCurriculumGrade(CurriculumGrade grade) {
        EntityUtil.deleteEntity(grade, em);
        grade.getCurriculum().getGrades().remove(grade);
    }
    
    public CurriculumSpeciality createCurriculumSpeciality(Curriculum curriculum, CurriculumSpecialityDto dto) {
        CurriculumSpeciality speciality = EntityUtil.save(createSpeciality(curriculum, dto), em);
        curriculum.getSpecialities().add(speciality);
        return speciality;
    }
    
    public CurriculumSpeciality updateCurriculumSpeciality(CurriculumSpeciality speciality,
            CurriculumSpecialityDto dto) {
        return EntityUtil.save(updateSpeciality(dto, speciality), em);
    }

    public void deleteCurriculumSpeciality(CurriculumSpeciality speciality) {
        if(speciality.isAddedToVersion()) {
            throw new ValidationFailedException("curriculum.error.specAddedToVersion");
        }
        EntityUtil.deleteEntity(speciality, em);
        speciality.getCurriculum().getSpecialities().remove(speciality);
    }

    public void saveCurriculumVersionSpecialities(CurriculumVersion curriculumVersion,
            CurriculumVersionHigherModuleForm form) {
        updateCurriculumVersionSpecialities(curriculumVersion, form.getCurriculumVersionSpecialities());
        curriculumVersionSpecialityRepository.save(curriculumVersion.getSpecialities());
    }

    public Curriculum saveAndProceedCurriculum(Curriculum curriculum, CurriculumForm curriculumForm) {
        setCurriculumStatus(curriculum, CurriculumStatus.OPPEKAVA_STAATUS_M);
        return save(curriculum, curriculumForm);
    }

    private void setCurriculumStatus(Curriculum curriculum, CurriculumStatus status) {
        curriculum.setStatus(classifierRepository.getOne(status.name()));
    }

    public CurriculumDto get(HoisUserDetails user, Curriculum curriculum) {
        CurriculumDto dto = CurriculumDto.of(curriculum);
        String myEhisShool = user.getSchoolId() != null ? 
                em.getReference(School.class, user.getSchoolId()).getEhisSchool().getCode() : null;
        dto.setCanChange(CurriculumUtil.canChange(user, myEhisShool, curriculum));
        dto.setCanConfirm(CurriculumUtil.canConfirm(user, myEhisShool, curriculum));
        dto.setCanClose(CurriculumUtil.canClose(user, myEhisShool, curriculum));
        dto.setCanDelete(CurriculumUtil.canDelete(user, myEhisShool, curriculum));
        
        dto.setVersions(StreamUtil.toMappedSet(CurriculumVersionDto::forCurriculumForm, curriculum.getVersions()
                .stream().filter(v -> CurriculumUtil.canView(user, schoolService.getEhisSchool(user.getSchoolId()), v))));
        if(Boolean.TRUE.equals(curriculum.getHigher())) {
            setJointPartnersDeletePermissions(curriculum, dto.getJointPartners());
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
            if(subjectsEhisSchools.contains(partner.getEhisSchool())) {
                partner.setHasSubjects(Boolean.TRUE);
            } else {
                partner.setHasSubjects(Boolean.FALSE);
            }
        }
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
        if(Boolean.TRUE.equals(command.getIsHigher())) {
            return studyLevels.stream().filter(StudyLevelUtil::isHigher).collect(Collectors.toList());
        } 
        return studyLevels.stream().filter(StudyLevelUtil::isVocational).collect(Collectors.toList());
    }

}
