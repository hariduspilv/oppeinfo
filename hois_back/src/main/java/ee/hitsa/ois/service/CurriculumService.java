package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
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
import ee.hitsa.ois.domain.curriculum.CurriculumModuleCompetence;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupationSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumStudyForm;
import ee.hitsa.ois.domain.curriculum.CurriculumStudyLanguage;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionElectiveModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleCapacity;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleOutcome;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleThemeCapacity;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleYearCapacity;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionSpeciality;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.SchoolDepartment;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.CurriculumConsecution;
import ee.hitsa.ois.enums.CurriculumDraft;
import ee.hitsa.ois.enums.CurriculumEhisStatus;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.CurriculumVersionSpecialityRepository;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.service.ehis.EhisCurriculumService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.CurriculumFileForm;
import ee.hitsa.ois.web.commandobject.CurriculumForm;
import ee.hitsa.ois.web.commandobject.CurriculumModuleForm;
import ee.hitsa.ois.web.commandobject.CurriculumVersionHigherModuleForm;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumFileDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumGradeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumJointPartnerDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleOutcomeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumOccupationDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSpecialityDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionElectiveModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleSubjectDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleCapacityDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeCapacityDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleYearCapacityDto;


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
    private CurriculumVersionRepository curriculumVersionRepository;
    @Autowired
    private EhisCurriculumService ehisCurriculumService;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private CurriculumVersionSpecialityRepository curriculumVersionSpecialityRepository;

    public void delete(Curriculum curriculum) {
        EntityUtil.deleteEntity(curriculum, em);
    }

	public List<Classifier> getAreasOfStudyByGroupOfStudy(String code) {
	    return classifierRepository.findAreasOfStudyByGroupOfStudy(code);
	}

    public Curriculum create(HoisUserDetails user, CurriculumForm curriculumForm) {
        Curriculum curriculum = new Curriculum();
        curriculum.setSchool(em.getReference(School.class, user.getSchoolId()));
        updateCurriculumFiles(curriculum, curriculumForm.getNewFiles());
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
            updateModules(curriculum, curriculumForm.getModules());
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
      return curriculumRepository.save(curriculum);
    }

    private CurriculumVersion updateVersion(Curriculum curriculum, CurriculumVersion version, CurriculumVersionDto dto) {
        EntityUtil.bindToEntity(dto, version, classifierRepository, "curriculumStudyForm", "modules",
                "specialities", "schoolDepartment", "occupationModules", "status");
        updateCurriculumVersionSpecialities(version, dto.getSpecialitiesReferenceNumbers());
        updateCurriculumVersionModules(version, dto.getModules());
        updateCurriculumVersionOccupationalModules(version, dto.getOccupationModules());
        updateVersionStudyForm(curriculum, version, dto);
        updateSchoolDepartment(version, dto);
        return version;
    }

    private void updateSchoolDepartment(CurriculumVersion version, CurriculumVersionDto dto) {
        if (dto.getSchoolDepartment() != null) {
            version.setSchoolDepartment(em.getReference(SchoolDepartment.class, dto.getSchoolDepartment()));
        }
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


    private void updateCurriculumVersionSpecialities(CurriculumVersion version, Set<Long> specRefNums) {
      EntityUtil.bindEntityCollection(version.getSpecialities(), cs -> EntityUtil.getId(cs.getCurriculumSpeciality()), specRefNums, s -> {
          CurriculumVersionSpeciality cvs = new CurriculumVersionSpeciality();
          cvs.setCurriculumVersion(version);
          cvs.setCurriculumSpeciality(em.getReference(CurriculumSpeciality.class, s));
          return cvs;
      });
    }
    
    private void updateCurriculumVersionModules(CurriculumVersion version,
            Set<CurriculumVersionHigherModuleDto> modules) {
        
        EntityUtil.bindEntityCollection(version.getModules(), CurriculumVersionHigherModule::getId, modules, CurriculumVersionHigherModuleDto::getId, 
                dto -> createCurriculumVersionHigherModule(version, dto), this::updateCurriculumVersionHigherModule);
    }
    
    private CurriculumVersionHigherModule createCurriculumVersionHigherModule(CurriculumVersion curriculumVersion, CurriculumVersionHigherModuleDto dto) {
        CurriculumVersionHigherModule module = new CurriculumVersionHigherModule();
        module.setCurriculumVersion(curriculumVersion);
        return updateCurriculumVersionHigherModule(dto, module);
    }
    
    private CurriculumVersionHigherModule updateCurriculumVersionHigherModule(CurriculumVersionHigherModuleDto dto, CurriculumVersionHigherModule module) {
        EntityUtil.bindToEntity(dto, module, classifierRepository, "subjects", "electiveModules", "specialities");
        updateCurriculumVersionModuleElectiveModules(module, dto.getElectiveModules());
        updateCurriculumVersionModuleSubjects(module, dto.getSubjects());
        if(!Boolean.TRUE.equals(module.getMinorSpeciality())) {
            updateVersionModuleSpecialities(module.getCurriculumVersion(), module, dto.getSpecialitiesReferenceNumbers());
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

                EntityUtil.bindToEntity(dto, subject, "nameEt", "nameEt", "credits", "school", "subjectId", "electiveModule");
                subject.setSubject(subjectRepository.getOne(dto.getSubjectId()));
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

    private void updateCurriculumVersionOccupationalModules(CurriculumVersion updatedVersion,
            Set<CurriculumVersionOccupationModuleDto> occupationModules) {
        EntityUtil.bindEntityCollection(updatedVersion.getOccupationModules(), CurriculumVersionOccupationModule::getId, occupationModules, 
                CurriculumVersionOccupationModuleDto::getId, dto -> createOccupationModule(updatedVersion, dto), this::updateOccupationModule);
    }
    
    private CurriculumVersionOccupationModule createOccupationModule(CurriculumVersion curriculumVersion, CurriculumVersionOccupationModuleDto dto) {
        CurriculumVersionOccupationModule occupationModule = new CurriculumVersionOccupationModule();
              Optional<CurriculumModule> curriculumModule = curriculumVersion.getCurriculum().getModules().stream()
              .filter(it -> it.getId().equals(dto.getCurriculumModule())).findFirst();
        if (curriculumModule.isPresent()) {
          occupationModule.setCurriculumModule(curriculumModule.get());
        }
        occupationModule.setCurriculumVersion(curriculumVersion);
        return updateOccupationModule(dto, occupationModule);
    }
    
    private CurriculumVersionOccupationModule updateOccupationModule(CurriculumVersionOccupationModuleDto dto, CurriculumVersionOccupationModule occupationModule) {
        EntityUtil.bindToEntity(dto, occupationModule,
        classifierRepository, "curriculumModule", "capacities", "themes", "yearCapacities");
        
        Set<CurriculumVersionOccupationModuleCapacity> newOccupationModuleCapacities =
        dto.getCapacities().stream().filter(Objects::nonNull)
            .map(capacityDto -> updateCapacities(capacityDto, occupationModule)).collect(Collectors.toSet());

        occupationModule.setCapacities(newOccupationModuleCapacities);

        Set<CurriculumVersionOccupationModuleYearCapacity> newOccupationModuleYearCapacities =
        dto.getYearCapacities().stream().filter(Objects::nonNull)
            .map(capacityDto -> updateYearCapacities(capacityDto, occupationModule)).collect(Collectors.toSet());
        occupationModule.setYearCapacities(newOccupationModuleYearCapacities);

        occupationModule.setThemes(StreamUtil.toMappedSet(it -> updateThemes(it, occupationModule), dto.getThemes()));
        return occupationModule;
    }

    private CurriculumVersionOccupationModuleTheme updateThemes(CurriculumVersionOccupationModuleThemeDto dto,
            CurriculumVersionOccupationModule updatedOccupationModule) {
        CurriculumVersionOccupationModuleTheme theme = dto.getId() == null ? new CurriculumVersionOccupationModuleTheme() :
            EntityUtil.find(dto.getId(), updatedOccupationModule.getThemes()).get();

        EntityUtil.bindToEntity(dto, theme, classifierRepository, "capacities", "outcomes");

        Set<CurriculumVersionOccupationModuleThemeCapacity> newOccupationModuleThemeCapacities =
                dto.getCapacities().stream().filter(Objects::nonNull)
                .map(capacityDto -> updateThemeCapacities(capacityDto, theme)).collect(Collectors.toSet());

        theme.setCapacities(newOccupationModuleThemeCapacities);
        EntityUtil.bindEntityCollection(theme.getOutcomes(), o -> EntityUtil.getId(o.getOutcome()), dto.getOutcomes(), d -> {
            CurriculumVersionOccupationModuleOutcome outcome = new CurriculumVersionOccupationModuleOutcome();
            outcome.setOutcome(em.getReference(CurriculumModuleOutcome.class, d));
            return outcome;
        });

        return theme;
    }

    private CurriculumVersionOccupationModuleCapacity updateCapacities(CurriculumVersionOccupationModuleCapacityDto dto,
            CurriculumVersionOccupationModule updatedOccupationModule) {

        Optional<CurriculumVersionOccupationModuleCapacity> o = dto.getId() == null ? Optional.empty() :
            updatedOccupationModule.getCapacities().stream().filter(c -> c.getId().equals(dto.getId())).findFirst();

        return EntityUtil.bindToEntity(dto, o.isPresent() ? o.get() : new CurriculumVersionOccupationModuleCapacity(), classifierRepository);
    }

    private CurriculumVersionOccupationModuleYearCapacity updateYearCapacities(
            CurriculumVersionOccupationModuleYearCapacityDto dto,
            CurriculumVersionOccupationModule updatedOccupationModule) {
        CurriculumVersionOccupationModuleYearCapacity capacity = dto.getId() == null ? new CurriculumVersionOccupationModuleYearCapacity() :
            EntityUtil.find(dto.getId(), updatedOccupationModule.getYearCapacities()).get();
        return EntityUtil.bindToEntity(dto, capacity, classifierRepository);
    }

    private CurriculumVersionOccupationModuleThemeCapacity updateThemeCapacities(
            CurriculumVersionOccupationModuleThemeCapacityDto dto, CurriculumVersionOccupationModuleTheme updatedTheme) {
        CurriculumVersionOccupationModuleThemeCapacity capacity = dto.getId() == null ? new CurriculumVersionOccupationModuleThemeCapacity() :
            EntityUtil.find(dto.getId(), updatedTheme.getCapacities()).get();
        return EntityUtil.bindToEntity(dto, capacity, classifierRepository);
    }

    private static void updateVersionModuleSpecialities(CurriculumVersion version,
            CurriculumVersionHigherModule module, Set<Long> specsRefNums) {
        Set<CurriculumVersionHigherModuleSpeciality> newSpecs = new HashSet<>();

        if(specsRefNums != null && !specsRefNums.isEmpty()) {
            Map<Long, CurriculumVersionHigherModuleSpeciality> oldSpecs = StreamUtil.toMap(s -> EntityUtil.getId(s.getSpeciality().getCurriculumSpeciality()), module.getSpecialities());
            Map<Long, CurriculumVersionSpeciality> selectedSpecs = StreamUtil.toMap(s -> s.getCurriculumSpeciality().getReferenceNumber(), version.getSpecialities());

            for(Long s : specsRefNums) {
                if(s.longValue() > 0 && oldSpecs.containsKey(s)) {
                    newSpecs.add(oldSpecs.get(s));
                } else {
                  CurriculumVersionSpeciality versionSpeciality = selectedSpecs.get(s);
                  CurriculumVersionHigherModuleSpeciality newSpec = new CurriculumVersionHigherModuleSpeciality();
                  newSpec.setSpeciality(versionSpeciality);
                  newSpec.setModule(module);
//                  versionSpeciality.getModuleSpecialities().add(newSpec);
                  newSpecs.add(newSpec);
                }
            }
        }
        module.setSpecialities(newSpecs);
    }

    private void updateModules(Curriculum curriculum, Set<CurriculumModuleDto> modules) {
        EntityUtil.bindEntityCollection(curriculum.getModules(), CurriculumModule::getId, modules, 
           CurriculumModuleDto::getId, dto -> createCurriculumModule(curriculum, dto), this::updateCurriculumModule);
    }

    private CurriculumModule createCurriculumModule(Curriculum curriculum, CurriculumModuleDto dto) {
        CurriculumModule module = new CurriculumModule();
        module.setCurriculum(curriculum);
        curriculum.getModules().add(module);
        return updateCurriculumModule(dto, module);
    }

    private CurriculumModule updateCurriculumModule(CurriculumModuleDto dto, CurriculumModule module) {
        EntityUtil.bindToEntity(dto, module, classifierRepository, "occupations", "competences", "outcomes");
        EntityUtil.bindEntityCollection(module.getOccupations(), o -> EntityUtil.getCode(o.getOccupation()), dto.getOccupations(), occupationCode -> {
            Classifier c = EntityUtil.validateClassifier(classifierRepository.getOne(occupationCode),
                    MainClassCode.OSAKUTSE, MainClassCode.KUTSE, MainClassCode.SPETSKUTSE);
            return new CurriculumModuleOccupation(c);
          });
        EntityUtil.bindEntityCollection(module.getCompetences(), c -> EntityUtil.getCode(c.getCompetence()), dto.getCompetences(), competenceCode -> {
            return new CurriculumModuleCompetence(EntityUtil.validateClassifier(classifierRepository.getOne(competenceCode), MainClassCode.KOMPETENTS));
        });
        updateCurriculumModuleOutcomes(module, dto.getOutcomes());
        return module;
    }

    // TODO: rewrite using EntityUtil
    private static void updateCurriculumModuleOutcomes(CurriculumModule module, Set<CurriculumModuleOutcomeDto> outcomes) {
        Set<CurriculumModuleOutcome> newOutComes = new HashSet<>();
        if(outcomes != null) {
            for(CurriculumModuleOutcomeDto dto : outcomes) {
                CurriculumModuleOutcome outcome = dto.getId() == null ? new CurriculumModuleOutcome() :
                    EntityUtil.find(dto.getId(), module.getOutcomes()).get();

                newOutComes.add(EntityUtil.bindToEntity(dto, outcome));
            }
        }
        module.setOutcomes(newOutComes);
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
    
    private void updateJointPartners(Curriculum curriculum, Set<CurriculumJointPartnerDto> jointPartners) {
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

    private void updateDepartments(Curriculum curriculum, Set<Long> newDepartments) {
        EntityUtil.bindEntityCollection(curriculum.getDepartments(), c -> EntityUtil.getId(c.getSchoolDepartment()), newDepartments, d -> {
            CurriculumDepartment cd = new CurriculumDepartment();
            cd.setCurriculum(curriculum);
            cd.setSchoolDepartment(em.getReference(SchoolDepartment.class, d));
            return cd;
        });
    }
    
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

    private void updateLanguages(Curriculum target, Set<String> languageCodes) {
        EntityUtil.bindEntityCollection(target.getStudyLanguages(), e -> EntityUtil.getCode(e.getStudyLang()), languageCodes, code -> {
            CurriculumStudyLanguage csl = new CurriculumStudyLanguage();
            csl.setCurriculum(target);
            csl.setStudyLang(classifierRepository.getOne(code));
            return csl;
        });
    }

    public CurriculumVersionDto saveVersion(CurriculumVersion curriculumVersion, CurriculumVersionDto curriculumVersionDto) {
        CurriculumVersion updatedCurriculumVersion = updateVersion(curriculumVersion.getCurriculum(), curriculumVersion, curriculumVersionDto);

        return CurriculumVersionDto.of(curriculumVersionRepository.save(updatedCurriculumVersion));
    }

    public CurriculumVersionDto createVersion(Curriculum curriculum, CurriculumVersionDto dto) {
        CurriculumVersion curriculumVersion = new CurriculumVersion();
        curriculumVersion.setCurriculum(curriculum);
        setCurriculumVersionStatus(curriculumVersion, CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_S);
        CurriculumVersion updatedCurriculumVersion = updateVersion(curriculumVersion.getCurriculum(), curriculumVersion, dto);
        return CurriculumVersionDto.of(curriculumVersionRepository.save(updatedCurriculumVersion));
    }

    public List<CurriculumVersionHigherModuleSubjectDto> getSubjects(Curriculum curriculum) {
        Long schoolId = EntityUtil.getId(curriculum.getSchool());
        List<CurriculumJointPartner> estonianJointPartners = 
                curriculum.getJointPartners().stream().filter(jp -> jp.getEhisSchool() != null).collect(Collectors.toList());
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

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        return StreamUtil.toMappedList(CurriculumVersionHigherModuleSubjectDto::of, subjects);
    }

    public void deleteVersion(CurriculumVersion curriculumVersion) {
        EntityUtil.deleteEntity(curriculumVersion, em);
    }

    public Curriculum updateCurriculumSpecialities(Curriculum curriculum,
            CurriculumDto form) {
        updateCurriculumSpecialities(curriculum, form.getSpecialities());
        return EntityUtil.save(curriculum, em);
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
             new ClassifierSelection(null, resultAsString(r, 0), resultAsString(r, 1), null, null, null, null, null, null, null)
        , data);
    }

    public Curriculum saveCurriculumModule(Curriculum curriculum, CurriculumDto form) {
        curriculum.setOccupation(form.getOccupation());
        updateOccupations(curriculum, form.getOccupations());
        updateModules(curriculum, form.getModules());
        return EntityUtil.save(curriculum, em);
    }

    public void deleteModule(CurriculumModule module) {
        EntityUtil.deleteEntity(module, em);
        module.getCurriculum().getModules().remove(module);
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
        // TODO enable when ehis sending is working
        // ehisCurriculumService.sendToEhis(user, curriculum);
        curriculum.setEhisStatus(classifierRepository.findOne(CurriculumEhisStatus.OPPEKAVA_EHIS_STAATUS_A.name()));
        curriculum.setEhisChanged(LocalDate.now());
        return EntityUtil.save(curriculum, em);
    }

    public Curriculum updateFromEhis(Curriculum curriculum) {
        setCurriculumStatus(curriculum, CurriculumStatus.OPPEKAVA_STAATUS_K);
        curriculum.setEhisStatus(classifierRepository.findOne(CurriculumEhisStatus.OPPEKAVA_EHIS_STAATUS_R.name()));
        curriculum.setEhisChanged(LocalDate.now());
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

    public boolean isVersionUnique(Long schoolId, UniqueCommand command) {
        boolean codeExists;
        if(command.getId() == null) {
            codeExists = curriculumVersionRepository.existsByCurriculumSchoolIdAndCode(schoolId, command.getParamValue());
        } else {
            codeExists = curriculumVersionRepository.existsByCurriculumSchoolIdAndCodeAndIdNot(schoolId, command.getParamValue(), command.getId());
        }
        return !codeExists;
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

    public void deleteHigherCurriculumVersionModule(CurriculumVersionHigherModule module) {
        EntityUtil.deleteEntity(module, em);
        module.getCurriculumVersion().getModules().remove(module);
    }

    public void saveCurriculumVersionSpecialities(CurriculumVersion curriculumVersion,
            CurriculumVersionHigherModuleForm form) {
        updateCurriculumVersionSpecialities(curriculumVersion, form.getCurriculumVersionSpecialities());
        curriculumVersionSpecialityRepository.save(curriculumVersion.getSpecialities());
    }

    public CurriculumVersionHigherModule createHigherCurriculumVersionModule(CurriculumVersion curriculumVersion,
            CurriculumVersionHigherModuleForm form) {
        if(!Boolean.TRUE.equals(form.getMinorSpeciality())) {
            saveCurriculumVersionSpecialities(curriculumVersion, form);
        }
        CurriculumVersionHigherModule module = createCurriculumVersionHigherModule(curriculumVersion, form);
        return EntityUtil.save(module, em);
    }

    public CurriculumVersionHigherModule updateHigherCurriculumVersionModule(CurriculumVersionHigherModule module, 
            CurriculumVersion curriculumVersion,
            CurriculumVersionHigherModuleForm form) {
        if(!Boolean.TRUE.equals(module.getMinorSpeciality())) {
            saveCurriculumVersionSpecialities(curriculumVersion, form);
        }
        updateCurriculumVersionHigherModule(form, module);
        return EntityUtil.save(module, em);
    }

    public CurriculumVersion updateHigherCurriculumVersionModules(CurriculumVersion curriculumVersion,
            CurriculumVersionDto form) {
        updateCurriculumVersionSpecialities(curriculumVersion, 
                form.getSpecialitiesReferenceNumbers());
        updateCurriculumVersionModules(curriculumVersion, form.getModules());
        return EntityUtil.save(curriculumVersion, em);
    }

    public CurriculumModule createCurriculumModule(Curriculum curriculum, CurriculumModuleForm form) {
        updateOccupations(curriculum, form.getCurriculumOccupations());
        curriculumRepository.save(curriculum);
        CurriculumModule module = createCurriculumModule(curriculum, form.getCurriculumModule());
        return EntityUtil.save(module, em);
    }

    public CurriculumModule updateCurriculumModule(CurriculumModule module, CurriculumModuleForm form) {
        updateOccupations(module.getCurriculum(), form.getCurriculumOccupations());
        curriculumRepository.save(module.getCurriculum());
        updateCurriculumModule(form.getCurriculumModule(), module);
        return EntityUtil.save(module, em);
    }

    public CurriculumVersionOccupationModule createImplementationPlanModule(CurriculumVersion curriculumVersion,
            CurriculumVersionOccupationModuleDto dto) {
        CurriculumVersionOccupationModule occupationModule = createOccupationModule(curriculumVersion, dto);
        curriculumVersion.getOccupationModules().add(occupationModule);
        return EntityUtil.save(occupationModule, em);
    }

    public CurriculumVersionOccupationModule updateImplementationPlanModule(
            CurriculumVersionOccupationModule occupationModule, CurriculumVersionOccupationModuleDto dto) {
        return EntityUtil.save(updateOccupationModule(dto, occupationModule), em);
    }

    public CurriculumVersion closeVersion(CurriculumVersion curriculumVersion) {
        setCurriculumVersionStatus(curriculumVersion, CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C);
        return EntityUtil.save(curriculumVersion, em);
    }

    public CurriculumVersion confirmVersion(CurriculumVersion curriculumVersion) {
        setCurriculumVersionStatus(curriculumVersion, CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K);
        return EntityUtil.save(curriculumVersion, em);
    }

    public Curriculum saveAndProceedCurriculum(Curriculum curriculum, CurriculumForm curriculumForm) {
        setCurriculumStatus(curriculum, CurriculumStatus.OPPEKAVA_STAATUS_M);
        return save(curriculum, curriculumForm);
    }

    public CurriculumVersionDto saveAndConfirmVersion(CurriculumVersion curriculumVersion,
            CurriculumVersionDto curriculumVersionDto) {
        setCurriculumVersionStatus(curriculumVersion, CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K);
        return saveVersion(curriculumVersion, curriculumVersionDto);
    }

    private void setCurriculumStatus(Curriculum curriculum, CurriculumStatus status) {
        curriculum.setStatus(classifierRepository.getOne(status.name()));
    }

    private void setCurriculumVersionStatus(CurriculumVersion curriculumVersion, CurriculumVersionStatus status) {
        curriculumVersion.setStatus(classifierRepository.getOne(status.name()));
    }
}
