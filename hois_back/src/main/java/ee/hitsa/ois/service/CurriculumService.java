package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
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

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.ClassifierConnect;
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
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumDepartmentRepository;
import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.SchoolDepartmentRepository;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.CurriculumForm;
import ee.hitsa.ois.web.commandobject.CurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.SubjectSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.curriculum.CurriculumFileDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumGradeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumJointPartnerDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleOutcomeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumOccupationDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSpecialityDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionElectiveModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleSubjectDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleCapacityDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleOutcomeDto;
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
    private ClassifierService classifierService;
	@Autowired
	private ClassifierRepository classifierRepository;
	@Autowired
    private SchoolDepartmentRepository schoolDepartmentRepository;
	@Autowired
    private CurriculumDepartmentRepository curriculumDepartmentRepository;
	@Autowired
	private CurriculumVersionRepository curriculumVersionRepository;
	@Autowired
	private SubjectRepository subjectRepository;

	public List<CurriculumDepartment> findAllDepartments() {
		return curriculumDepartmentRepository.findAll();
	}

    public Curriculum save(Curriculum curriculum) {
        return curriculumRepository.save(curriculum);
    }

    public void delete(Curriculum curriculum) {
        curriculumRepository.delete(curriculum);
    }

    /**
     * For testing
     */
    public Curriculum getOne(Long id) {
    	return curriculumRepository.getOne(id);
    }

    @SuppressWarnings("unchecked")
    public Page<CurriculumSearchDto> search(CurriculumSearchCommand criteria, Pageable pageable) {
        return JpaQueryUtil.query(CurriculumSearchDto.class, Curriculum.class, (root, query, cb) -> {
            ((CriteriaQuery<CurriculumSearchDto>)query).select(cb.construct(CurriculumSearchDto.class,
                root.get("id"), root.get("nameEt"), root.get("nameEn"),
                root.get("credits"), root.get("validFrom"), root.get("validThru"), root.get("higher"),
                root.get("status").get("code"), root.get("origStudyLevel").get("code"),
                root.get("school").get("id"), root.get("school").get("nameEt"), root.get("school").get("nameEn")));

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
            if(Boolean.TRUE.equals(criteria.getIsJoint())) {
                filters.add(cb.equal(root.get("joint"), Boolean.TRUE));
            }
            if (Boolean.TRUE.equals(criteria.getIsVocational())) {
                filters.add(cb.equal(cb.substring(root.get("origStudyLevel").get("value"), 1, 1), "4"));
            } else if (Boolean.FALSE.equals(criteria.getIsVocational())) {
                filters.add(cb.notEqual(cb.substring(root.get("origStudyLevel").get("value"), 1, 1), "4"));
            }

            propertyContains(() -> root.get("code"), cb, criteria.getCode(), filters::add);
            propertyContains(() -> root.get("merCode"), cb, criteria.getMerCode(), filters::add);

            if(!CollectionUtils.isEmpty(criteria.getSchool())) {
                filters.add(root.get("school").get("id").in(criteria.getSchool()));
            }
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
            if(!CollectionUtils.isEmpty(criteria.getEkrLevel())) {
                Subquery<String> targetQuery = query.subquery(String.class);
                Root<ClassifierConnect> targetRoot = targetQuery.from(ClassifierConnect.class);
                targetQuery = targetQuery.select(targetRoot.get("classifier").get("code")).where(targetRoot.get("connectClassifier").get("code").in(criteria.getEkrLevel()));
                filters.add(root.get("origStudyLevel").get("code").in(targetQuery));
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

            return cb.and(filters.toArray(new Predicate[filters.size()]));
	    }, pageable, em);
    }

	public boolean isUnique(Long schoolId, UniqueCommand command) {
        return curriculumRepository.count((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            if(command.getId() != null) {
                filters.add(cb.notEqual(root.get("id"), command.getId()));
            }
            if(command.getParamName().equals("code")) {
                filters.add(cb.equal(root.get("school").get("id"), schoolId));
            }
            if(Boolean.TRUE.equals(command.getCountOnlyValid())) {
                filters.add(cb.greaterThanOrEqualTo(root.get("validThru"), LocalDate.now()));
            }
            final Set<String> ALLOWED_PROPERTIES = new HashSet<>(Arrays.asList("code", "merCode"));
            if(ALLOWED_PROPERTIES.contains(command.getParamName())) {
                filters.add(cb.equal(root.get(command.getParamName()), command.getParamValue()));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }) == 0;
    }

    public boolean isVersionUnique(Long schoolId, UniqueCommand command) {
        if(command.getFk() == null) {
            command.setFk(Long.valueOf(0));
        }
        long count = curriculumVersionRepository.countBySchoolAndOtherCurriculums
                (schoolId, command.getParamValue(), command.getFk());
        return count == 0;
    }

	public List<Classifier> getAreasOfStudyByGroupOfStudy(String code) {
	    return classifierRepository.findAreasOfStudyByGroupOfStudy(code);
	}

    public Curriculum save(Curriculum curriculum, CurriculumForm curriculumForm) {

      EntityUtil.bindToEntity(curriculumForm, curriculum, classifierRepository,
              "versions", "studyLanguages", "studyForms", "schoolDepartments", "files",
              "jointPartners", "specialities", "modules", "occupations", "grades");

      updateDepartments(curriculum, curriculumForm.getSchoolDepartments());
      updateLanguages(curriculum, curriculumForm.getStudyLanguages());
      updateStudyForms(curriculum, curriculumForm.getStudyForms());

      /*
       * If dto has an ID, currently there is no check
       * whether there is an element with such ID in collection.
       * However, exception will be thrown anyway
       */
      updateGrades(curriculum, curriculumForm.getGrades());
      updateCurriculumFiles(curriculum, curriculumForm.getFiles());
      updateCurriculumSpecialities(curriculum, curriculumForm.getSpecialities());
      updateJointPartners(curriculum, curriculumForm.getJointPartners());
      updateOccupations(curriculum, curriculumForm.getOccupations());
      updateModules(curriculum, curriculumForm.getModules());
      updateVersions(curriculum, curriculumForm.getVersions());

      return curriculumRepository.save(curriculum);
    }

    private void updateVersions(Curriculum curriculum, Set<CurriculumVersionDto> versions) {
        Set<CurriculumVersion> newVersions = new HashSet<>();
        if(versions != null) {
            versions.forEach(dto -> {
                CurriculumVersion version = dto.getId() == null ? new CurriculumVersion() :
                    curriculum.getVersions().stream().filter(v -> v.getId().equals(dto.getId())).findFirst().get();
                CurriculumVersion updatedVersion = updateVersion(curriculum, version, dto);
                newVersions.add(updatedVersion);
            });
        }
        curriculum.setVersions(newVersions);
    }

    private CurriculumVersion updateVersion(Curriculum curriculum, CurriculumVersion version, CurriculumVersionDto dto) {
        CurriculumVersion updatedVersion = EntityUtil.bindToEntity(dto, version, classifierRepository, "curriculumStudyForm", "modules",
                "specialities", "schoolDepartment", "occupationModules");
        updateCurriculumVersionSpecialities(curriculum, updatedVersion, dto.getSpecialitiesReferenceNumbers());
        updateCurriculumVersionModules(updatedVersion, dto.getModules());
        updateCurriculumVersionOccupationalModules(updatedVersion, dto.getOccupationModules());
        updateVersionStudyForm(curriculum, updatedVersion, dto);
        updateSchoolDepartment(updatedVersion, dto);
        return updatedVersion;
    }

    private void updateSchoolDepartment(CurriculumVersion version, CurriculumVersionDto dto) {
        if (dto.getSchoolDepartment() != null) {
            version.setSchoolDepartment(schoolDepartmentRepository.findOne(dto.getSchoolDepartment()));
        }
    }

    /**
     * TODO: not yet tested
     * Unlike other methods here, this requires curriculum to be saved
     */
    private static void updateVersionStudyForm(Curriculum curriculum, CurriculumVersion version, CurriculumVersionDto dto) {
        if(curriculum.getId() == null) {
            return;
        }
        String code = dto.getCurriculumStudyForm();
        Optional<CurriculumStudyForm> studyForm = curriculum.getStudyForms().stream()
                .filter(s -> EntityUtil.getCode(s.getStudyForm()).equals(code)).findFirst();
        if(studyForm.isPresent()) {
            version.setCurriculumStudyForm(studyForm.get());
        } else {
          version.setCurriculumStudyForm(null);
        }
    }

    private static void updateCurriculumVersionSpecialities(Curriculum curriculum, CurriculumVersion version, Set<Long> specialities) {
      Set<CurriculumVersionSpeciality> newSpecialities = new HashSet<>();
      if(specialities != null) {
        Map<Long, CurriculumVersionSpeciality> oldSpecs = version.getSpecialities().stream()
                  .collect(Collectors.toMap(s -> EntityUtil.getId(s.getCurriculumSpeciality()), s -> s));
        Map<Long, CurriculumSpeciality> selectedSpecs = curriculum.getSpecialities().stream()
                .collect(Collectors.toMap(s -> s.getReferenceNumber(), s -> s));
        specialities.forEach(s -> {
            if(s.longValue() > 0 && oldSpecs.keySet().contains(s)) {
                newSpecialities.add(oldSpecs.get(s));
            } else {
              CurriculumVersionSpeciality newSpec = new CurriculumVersionSpeciality();
              CurriculumSpeciality curriculumSpeciality = selectedSpecs.get(s);
              newSpec.setCurriculumSpeciality(curriculumSpeciality);
              newSpec.setCurriculumVersion(version);
              version.getSpecialities().add(newSpec);
              curriculumSpeciality.getCurriculumVersionSpecialities().add(newSpec);
              newSpecialities.add(newSpec);
            }
        });
      }
      version.setSpecialities(newSpecialities);
    }

    private void updateCurriculumVersionModules(CurriculumVersion version,
            Set<CurriculumVersionHigherModuleDto> modules) {
        Set<CurriculumVersionHigherModule> newModules = new HashSet<>();
        if(modules != null) {
            modules.forEach(dto -> {
                CurriculumVersionHigherModule module = dto.getId() == null ? new CurriculumVersionHigherModule() :
                    version.getModules().stream().filter(m -> m.getId().equals(dto.getId())).findFirst().get();
                module = EntityUtil.bindToEntity(dto, module, classifierRepository, "subjects", "electiveModules", "specialities");
                updateCurriculumVersionModuleSubjects(module, dto.getSubjects());
                updateCurriculumVersionModuleElectiveModules(module, dto.getElectiveModules());
                updateVersionModuleSpecialities(version, module, dto.getSpecialitiesReferenceNumbers());
                newModules.add(module);
            });
        }
        version.setModules(newModules);
    }

    private void updateCurriculumVersionOccupationalModules(CurriculumVersion updatedVersion,
            Set<CurriculumVersionOccupationModuleDto> occupationModules) {
        Set<CurriculumVersionOccupationModule> newOccupationModules = new HashSet<>();
        if(occupationModules != null) {
            occupationModules.forEach(dto -> {
                CurriculumVersionOccupationModule occupationModule = dto.getId() == null ? new CurriculumVersionOccupationModule() :
                    updatedVersion.getOccupationModules().stream().filter(m -> m.getId().equals(dto.getId())).findFirst().get();
                CurriculumVersionOccupationModule updatedOccupationModule = EntityUtil.bindToEntity(dto, occupationModule,
                        classifierRepository, "curriculumModule", "capacities", "themes", "yearCapacities");

                Optional<CurriculumModule> curriculumModule = updatedVersion.getCurriculum().getModules().stream()
                        .filter(it -> it.getId().equals(dto.getCurriculumModule())).findFirst();
                if (curriculumModule.isPresent()) {
                    updatedOccupationModule.setCurriculumModule(curriculumModule.get());
                }

                Set<CurriculumVersionOccupationModuleCapacity> newOccupationModuleCapacities = new HashSet<>();
                dto.getCapacities().forEach(it -> {
                    newOccupationModuleCapacities.add(updateCapacities(it, updatedOccupationModule));
                });
                updatedOccupationModule.setCapacities(newOccupationModuleCapacities);

                Set<CurriculumVersionOccupationModuleYearCapacity> newOccupationModuleYearCapacities = new HashSet<>();
                dto.getYearCapacities().forEach(it -> {
                    newOccupationModuleYearCapacities.add(updateYearCapacities(it, updatedOccupationModule));
                });
                updatedOccupationModule.setYearCapacities(newOccupationModuleYearCapacities);

                Set<CurriculumVersionOccupationModuleTheme> newOccupationModuleThemes = new HashSet<>();
                dto.getThemes().forEach(it -> {
                    newOccupationModuleThemes.add(updateThemes(it, updatedOccupationModule));
                });
                updatedOccupationModule.setThemes(newOccupationModuleThemes);


                newOccupationModules.add(updatedOccupationModule);
            });
        }
        updatedVersion.setOccupationModules(newOccupationModules);
    }

    private CurriculumVersionOccupationModuleTheme updateThemes(CurriculumVersionOccupationModuleThemeDto dto,
            CurriculumVersionOccupationModule updatedOccupationModule) {
        CurriculumVersionOccupationModuleTheme theme = dto.getId() == null ? new CurriculumVersionOccupationModuleTheme() :
            updatedOccupationModule.getThemes().stream().filter(c -> c.getId().equals(dto.getId())).findFirst().get();

        CurriculumVersionOccupationModuleTheme updatedTheme =
                EntityUtil.bindToEntity(dto, theme, classifierRepository, "capacities", "outcomes");

        Set<CurriculumVersionOccupationModuleThemeCapacity> newOccupationModuleThemeCapacities = new HashSet<>();
        dto.getCapacities().forEach(it -> {
            newOccupationModuleThemeCapacities.add(updateThemeCapacities(it, updatedTheme));
        });
        updatedTheme.setCapacities(newOccupationModuleThemeCapacities);

        Set<CurriculumVersionOccupationModuleOutcome> newOccupationModuleThemeOutcome = new HashSet<>();
        dto.getOutcomes().forEach(it -> {
            newOccupationModuleThemeOutcome.add(updateThemeOutcomes(it, updatedTheme, updatedOccupationModule.getCurriculumModule().getOutcomes()));
        });
        updatedTheme.setOutcomes(newOccupationModuleThemeOutcome);

        return updatedTheme;
    }

    private CurriculumVersionOccupationModuleCapacity updateCapacities(CurriculumVersionOccupationModuleCapacityDto dto,
            CurriculumVersionOccupationModule updatedOccupationModule) {
        CurriculumVersionOccupationModuleCapacity capacity = dto.getId() == null ? new CurriculumVersionOccupationModuleCapacity() :
            updatedOccupationModule.getCapacities().stream().filter(c -> c.getId().equals(dto.getId())).findFirst().get();
        return EntityUtil.bindToEntity(dto, capacity, classifierRepository);
    }

    private CurriculumVersionOccupationModuleYearCapacity updateYearCapacities(
            CurriculumVersionOccupationModuleYearCapacityDto dto,
            CurriculumVersionOccupationModule updatedOccupationModule) {
        CurriculumVersionOccupationModuleYearCapacity capacity = dto.getId() == null ? new CurriculumVersionOccupationModuleYearCapacity() :
            updatedOccupationModule.getYearCapacities().stream().filter(c -> c.getId().equals(dto.getId())).findFirst().get();
        return EntityUtil.bindToEntity(dto, capacity, classifierRepository);
    }

    private CurriculumVersionOccupationModuleThemeCapacity updateThemeCapacities(
            CurriculumVersionOccupationModuleThemeCapacityDto dto, CurriculumVersionOccupationModuleTheme updatedTheme) {
        CurriculumVersionOccupationModuleThemeCapacity capacity = dto.getId() == null ? new CurriculumVersionOccupationModuleThemeCapacity() :
            updatedTheme.getCapacities().stream().filter(c -> c.getId().equals(dto.getId())).findFirst().get();
        return EntityUtil.bindToEntity(dto, capacity, classifierRepository);
    }


    private CurriculumVersionOccupationModuleOutcome updateThemeOutcomes(CurriculumVersionOccupationModuleOutcomeDto dto,
            CurriculumVersionOccupationModuleTheme updatedTheme, Set<CurriculumModuleOutcome> outcomes) {
        CurriculumVersionOccupationModuleOutcome outcome = dto.getId() == null ? new CurriculumVersionOccupationModuleOutcome() :
            updatedTheme.getOutcomes().stream().filter(c -> c.getId().equals(dto.getId())).findFirst().get();

        CurriculumVersionOccupationModuleOutcome updatedThemeOutcome = EntityUtil.bindToEntity(dto, outcome, classifierRepository);

        Optional<CurriculumModuleOutcome> curriculumOutcome = outcomes.stream().filter(it -> it.getId().equals(dto.getOutcome())).findFirst();
        if (curriculumOutcome.isPresent()) {
            updatedThemeOutcome.setOutcome(curriculumOutcome.get());
        }

        return updatedThemeOutcome;
    }

    private static void updateVersionModuleSpecialities(CurriculumVersion version,
            CurriculumVersionHigherModule module, Set<Long> specsRefNums) {
        Set<CurriculumVersionHigherModuleSpeciality> newSpecs = new HashSet<>();
        
        if(specsRefNums != null && !specsRefNums.isEmpty()) {
            
            Map<Long, CurriculumVersionHigherModuleSpeciality> oldSpecs = module.getSpecialities().stream()
                    .collect(Collectors.toMap(s -> EntityUtil.getId(s.getSpeciality().getCurriculumSpeciality()), s -> s));
            
            Map<Long, CurriculumVersionSpeciality> selectedSpecs = version.getSpecialities().stream().collect(Collectors
                    .toMap(s -> s.getCurriculumSpeciality().getReferenceNumber(), s -> s));
          
            specsRefNums.forEach(s -> {
                if(s.longValue() > 0 && oldSpecs.keySet().contains(s)) {
                    newSpecs.add(oldSpecs.get(s));
                } else {
                  CurriculumVersionSpeciality versionSpeciality = selectedSpecs.get(s);
                  CurriculumVersionHigherModuleSpeciality newSpec = new CurriculumVersionHigherModuleSpeciality();
                  newSpec.setSpeciality(versionSpeciality);
                  newSpec.setModule(module);
                  versionSpeciality.getModuleSpecialities().add(newSpec);
                  newSpecs.add(newSpec);
                }
            });
        }
        module.setSpecialities(newSpecs);
    }

    private void updateCurriculumVersionModuleSubjects(CurriculumVersionHigherModule module,
            Set<CurriculumVersionHigherModuleSubjectDto> subjects) {
        Set<CurriculumVersionHigherModuleSubject> newSubjects = new HashSet<>();
        if(subjects != null) {
            subjects.forEach(dto -> {
                CurriculumVersionHigherModuleSubject subject = dto.getId() == null ? new CurriculumVersionHigherModuleSubject() :
                    module.getSubjects().stream().filter(s -> s.getId().equals(dto.getId())).findFirst().get();
                subject = EntityUtil.bindToEntity(dto, subject, "nameEt", "nameEt", "credits", "school", "subjectId");
                subject.setSubject(subjectRepository.getOne(dto.getSubjectId()));
                newSubjects.add(subject);
            });
        }
        module.setSubjects(newSubjects);
    }

    private static void updateCurriculumVersionModuleElectiveModules(CurriculumVersionHigherModule module,
            Set<CurriculumVersionElectiveModuleDto> electiveModules) {
        Set<CurriculumVersionElectiveModule> newSet = new HashSet<>();
        if(electiveModules != null) {
            electiveModules.forEach(dto -> {
                CurriculumVersionElectiveModule newElectiveModule = dto.getId() == null ? new CurriculumVersionElectiveModule() :
                    module.getElectiveModules().stream().filter(e -> e.getId().equals(dto.getId())).findFirst().get();
                newElectiveModule = EntityUtil.bindToEntity(dto, newElectiveModule, "subjects");
                updateCurriculumVersionEleciveModulesSubjects(module.getSubjects(), newElectiveModule, dto.getSubjects());
                newSet.add(newElectiveModule);
            });
        }
        module.setElectiveModules(newSet);
    }

    private static void updateCurriculumVersionEleciveModulesSubjects(Set<CurriculumVersionHigherModuleSubject> moduleSubjects,
            CurriculumVersionElectiveModule newElectiveModule, Set<Long> electiveModuleSubjects) {
        if(moduleSubjects != null && electiveModuleSubjects != null) {
            Set<CurriculumVersionHigherModuleSubject> newSubjects = moduleSubjects.stream()
                    .filter(s -> electiveModuleSubjects.contains(s.getSubject().getId())).collect(Collectors.toSet());
            newElectiveModule.setSubjects(newSubjects);
        } else {
            newElectiveModule.setSubjects(new HashSet<>());
        }
    }

    private void updateModules(Curriculum curriculum, Set<CurriculumModuleDto> modules) {
        Set<CurriculumModule> newModules = new HashSet<>();

        if(modules != null) {
            modules.forEach(dto -> {
                CurriculumModule module = dto.getId() == null ? new CurriculumModule() : curriculum.getModules().
                        stream().filter(a -> a.getId().equals(dto.getId())).findFirst().get();

                module = EntityUtil.bindToEntity(dto, module, classifierRepository, "occupations", "competences", "outcomes");
                updateCurriculumModuleOccupations(module, dto.getOccupations());
                updateCurriculumModuleCompetences(module, dto.getCompetences());
                updateCurriculumModuleOutcomes(module, dto.getOutcomes());
                newModules.add(module);
            });
        }
        curriculum.setModules(newModules);
    }


    private static void updateCurriculumModuleOutcomes(CurriculumModule module, Set<CurriculumModuleOutcomeDto> outcomes) {
        Set<CurriculumModuleOutcome> newOutComes = new HashSet<>();
        if(outcomes != null) {
            outcomes.forEach(dto -> {
                CurriculumModuleOutcome outcome = dto.getId() == null ? new CurriculumModuleOutcome() :
                    module.getOutcomes().stream().filter(o -> o.getId().equals(dto.getId())).findFirst().get();

                outcome = EntityUtil.bindToEntity(dto, outcome);
                newOutComes.add(outcome);
            });
        }
        module.setOutcomes(newOutComes);
    }

    private void updateCurriculumModuleCompetences(CurriculumModule module, Set<String> competences) {
        if(competences != null) {
            Set<CurriculumModuleCompetence> oldSet = module.getCompetences();
            if(oldSet == null) {
                module.setCompetences(new HashSet<>());
                oldSet = module.getCompetences();
            }
            EntityUtil.bindClassifierCollection(oldSet, c -> EntityUtil.getCode(c.getCompetence()), competences, competenceCode -> {
                Classifier c = classifierRepository.getOne(competenceCode);
                if(!MainClassCode.KOMPETENTS.name().equals(c.getMainClassCode())) {
                    throw new IllegalArgumentException("Wrong classifier code: "+c.getMainClassCode());
                }
                return new CurriculumModuleCompetence(c);
            });
        } else {
            module.setCompetences(new HashSet<>());
        }
    }

    private void updateCurriculumModuleOccupations(CurriculumModule module, Set<String> occupations) {
        if(occupations != null) {
            Set<CurriculumModuleOccupation> oldSet = module.getOccupations();
            if(oldSet == null) {
                module.setOccupations(new HashSet<>());
                oldSet = module.getOccupations();
            }
            EntityUtil.bindClassifierCollection(oldSet, o -> EntityUtil.getCode(o.getOccupation()), occupations, occupaionCode -> {
              Classifier c = classifierRepository.getOne(occupaionCode);
              if(!MainClassCode.OSAKUTSE.name().equals(c.getMainClassCode()) &&
                      !MainClassCode.KUTSE.name().equals(c.getMainClassCode()) &&
                      !MainClassCode.SPETSKUTSE.name().equals(c.getMainClassCode())) {
                  throw new IllegalArgumentException("Wrong classifier code: "+c.getMainClassCode());
              }
              return new CurriculumModuleOccupation(c);
            });
        } else {
            module.setOccupations(new HashSet<>());
        }
    }

    private void updateOccupations(Curriculum curriculum, Set<CurriculumOccupationDto> occupations) {
        Set<CurriculumOccupation> newOccupations = new HashSet<>();
        if(occupations != null) {
            occupations.forEach(dto -> {
                CurriculumOccupation occupation = dto.getId() == null ? new CurriculumOccupation() :
                    curriculum.getOccupations().stream().filter(o -> o.getId().equals(dto.getId())).findFirst().get();
                occupation = EntityUtil.bindToEntity(dto, occupation, classifierRepository, "specialities");
                updateCurriculumOccupationSpecialities(occupation, dto.getSpecialities());
                newOccupations.add(occupation);
            });
        }
        curriculum.setOccupations(newOccupations);
    }

    private void updateCurriculumOccupationSpecialities(CurriculumOccupation occupation, Set<String> specialities) {
        if(specialities != null) {
            Set<CurriculumOccupationSpeciality> oldList = occupation.getSpecialities();
            if(oldList == null) {
                occupation.setSpecialities(new HashSet<>());
            }
            EntityUtil.bindClassifierCollection(oldList, s -> EntityUtil.getCode(s.getSpeciality()), specialities, specialityCode -> {
                Classifier c = classifierRepository.getOne(specialityCode);
                if(!MainClassCode.SPETSKUTSE.name().equals(c.getMainClassCode())) {
                    throw new IllegalArgumentException("Wrong classifier code: "+c.getMainClassCode());
                }
                return new CurriculumOccupationSpeciality(c);
            });
        } else {
            occupation.setSpecialities(new HashSet<>());
        }
    }

    private void updateJointPartners(Curriculum curriculum, Set<CurriculumJointPartnerDto> jointPartners) {
        Set<CurriculumJointPartner> newJointPartners = new HashSet<>();
        if(jointPartners != null) {
            jointPartners.forEach(dto -> {
                CurriculumJointPartner partner = dto.getId() == null ? new CurriculumJointPartner() :
                    curriculum.getJointPartners().stream().filter(p -> p.getId().equals(dto.getId())).findFirst().get();
                newJointPartners.add(EntityUtil.bindToEntity(dto, partner, classifierRepository));
            });
        }
        curriculum.setJointPartners(newJointPartners);
    }

    private void updateCurriculumSpecialities(Curriculum curriculum, Set<CurriculumSpecialityDto> specialities) {
        Set<CurriculumSpeciality> newSpecialities = new HashSet<>();
        if(specialities != null) {
            specialities.forEach(dto -> {
                CurriculumSpeciality speciality = dto.getId() == null ? new CurriculumSpeciality() :
                    curriculum.getSpecialities().stream().filter(s -> s.getId().equals(dto.getId())).findFirst().get();
                newSpecialities.add(EntityUtil.bindToEntity(dto, speciality, classifierRepository));
            });
        }
        curriculum.setSpecialities(newSpecialities);
    }

    private void updateGrades(Curriculum curriculum, Set<CurriculumGradeDto> grades) {
        Set<CurriculumGrade> newGrades = new HashSet<>();
        if(grades != null) {
            grades.forEach(dto -> {
                CurriculumGrade grade = dto.getId() == null ? new CurriculumGrade() :
                    curriculum.getGrades().stream().filter(g -> g.getId().equals(dto.getId())).findFirst().get();
                newGrades.add(EntityUtil.bindToEntity(dto, grade, classifierRepository));
            });
        }
        curriculum.setGrades(newGrades);
    }

    private void updateDepartments(Curriculum curriculum, Set<Long> newDepartments) {
        //TODO: get rid of this approach? (Requires equals() and hashCode())
        if(newDepartments != null) {
            Set<CurriculumDepartment> departments = newDepartments
            .stream().map(d -> new CurriculumDepartment(schoolDepartmentRepository.findOne(d))).collect(Collectors.toSet());
            merge(curriculum.getDepartments(), departments);
          } else {
              curriculum.setDepartments(new HashSet<>());
          }
    }

    public void updateCurriculumFiles(Curriculum curriculum, Set<CurriculumFileDto> newFileDtos) {
          Set<CurriculumFile> newFiles = new HashSet<>();
          if(newFileDtos != null) {
              newFileDtos.forEach(dto -> {
                  CurriculumFile file = dto.getId() == null ? new CurriculumFile() :
                      curriculum.getFiles().stream().filter(f -> f.getId().equals(dto.getId())).findFirst().get();
                  file = EntityUtil.bindToEntity(dto, file, classifierRepository);
                  // TODO: probably it would be better not to set new oisFile if it's not changed
                  file.setOisFile(EntityUtil.bindToEntity(dto.getOisFile(), new OisFile()));
                  newFiles.add(file);
              });
          }
          curriculum.setFiles(newFiles);
    }

    public void updateStudyForms(Curriculum curriculum, Set<String> studyForms) {
        if(studyForms != null) {
            Set<CurriculumStudyForm> storedStudyForms = curriculum.getStudyForms();
            if(storedStudyForms == null) {
                curriculum.setStudyForms(new HashSet<>());
            }
            EntityUtil.bindClassifierCollection(storedStudyForms, sf -> EntityUtil.getCode(sf.getStudyForm()), studyForms, studyForm -> {
                // add new link
                Classifier c = classifierRepository.getOne(studyForm);
             // verify that domain code is from OPPEVORM and raise IllegalArgumentException if wrong
                if(!MainClassCode.OPPEVORM.name().equals(c.getMainClassCode())) {
                    throw new IllegalArgumentException("Wrong classifier code: "+c.getMainClassCode());
                }
//                return new CurriculumStudyForm(c, curriculum);
                return new CurriculumStudyForm(c);
            });
        } else {
            curriculum.setStudyForms(new HashSet<>());
        }
    }

    private void updateLanguages(Curriculum target, Set<String> languageCodes) {
        if(languageCodes != null && !languageCodes.isEmpty()) {
          Map<String, CurriculumStudyLanguage> langs = target.getStudyLanguages().stream()
                  .collect(Collectors.toMap(e -> EntityUtil.getCode(e.getStudyLang()), e -> e));
          Set<CurriculumStudyLanguage> newSet = new HashSet<>();
          for(String lang : languageCodes) {
              if(langs.keySet().contains(lang)) {
                  newSet.add(langs.get(lang));
              } else {
                  newSet.add(new CurriculumStudyLanguage(classifierService.findOne(lang)));
              }
          }
          target.setStudyLanguages(newSet);
        } else {
            //delete all language connections
            target.setStudyLanguages(new HashSet<>());
        }
    }
    /**
     * Method requires hashCode() and equals() methods
     */
	private static <T> Set<T> merge(Set<T> originalSet, Set<T> changedSet) {
        if(originalSet != null && changedSet != null) {
            originalSet.addAll(changedSet);
            originalSet.retainAll(changedSet);
        }
        return originalSet;
    }

    public CurriculumVersionDto save(CurriculumVersion curriculumVersion, CurriculumVersionDto curriculumVersionDto) {
        CurriculumVersion updatedCurriculumVersion = updateVersion(curriculumVersion.getCurriculum(), curriculumVersion, curriculumVersionDto);
        return CurriculumVersionDto.of(curriculumVersionRepository.save(updatedCurriculumVersion));
    }

    public CurriculumVersionDto save(Curriculum curriculum, CurriculumVersionDto dto) {
        CurriculumVersion curriculumVersion = new CurriculumVersion();
        curriculumVersion.setCurriculum(curriculum);
        CurriculumVersion updatedCurriculumVersion = updateVersion(curriculumVersion.getCurriculum(), curriculumVersion, dto);
        return CurriculumVersionDto.of(curriculumVersionRepository.save(updatedCurriculumVersion));
    }

    public Page<CurriculumVersionHigherModuleSubjectDto> getSubjects(SubjectSearchCommand subjectSearchCommand, Pageable pageable) {
        return subjectRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            Collection<String> ehisSchools = subjectSearchCommand.getEhisSchools();
            if(!CollectionUtils.isEmpty(ehisSchools)) {
                filters.add(root.get("school").get("ehisSchool").get("code").in(ehisSchools));
            }

            filters.add(cb.equal(root.get("status").get("code"), SubjectStatus.AINESTAATUS_K.name()));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(CurriculumVersionHigherModuleSubjectDto::of);
    }
}
