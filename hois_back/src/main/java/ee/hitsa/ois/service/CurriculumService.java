package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import org.springframework.data.domain.Sort;
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
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumDepartmentRepository;
import ee.hitsa.ois.repository.CurriculumModuleOutcomeRepository;
import ee.hitsa.ois.repository.CurriculumModuleRepository;
import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.repository.CurriculumSpecialityRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.SchoolDepartmentRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.StateCurriculumRepository;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.CurriculumForm;
import ee.hitsa.ois.web.commandobject.CurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.SubjectSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.curriculum.CurriculumDto;
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
    private SchoolDepartmentRepository schoolDepartmentRepository;
	@Autowired
    private CurriculumDepartmentRepository curriculumDepartmentRepository;
	@Autowired
	private CurriculumVersionRepository curriculumVersionRepository;
	@Autowired
	private SubjectRepository subjectRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private CurriculumSpecialityRepository curriculumSpecialityRepository;
    @Autowired
    private StateCurriculumRepository stateCurriculumRepository;
    @Autowired
    private CurriculumModuleRepository curriculumModuleRepository;
    @Autowired
    private CurriculumModuleOutcomeRepository curriculumVersionOccupationModuleOutcomeRepository;

    @SuppressWarnings("unchecked")
    public Page<CurriculumSearchDto> search(Long schoolId, CurriculumSearchCommand criteria, Pageable pageable) {
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

            if(schoolId != null) {
                filters.add(cb.equal(root.get("school").get("id"), schoolId));
            }
            else if(!CollectionUtils.isEmpty(criteria.getSchool())) {
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

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable, em);
    }

    public List<CurriculumDepartment> findAllDepartments() {
        return curriculumDepartmentRepository.findAll();
    }

    public void delete(Curriculum curriculum) {
        EntityUtil.deleteEntity(curriculumRepository, curriculum);
    }

	public boolean isUnique(Long schoolId, UniqueCommand command) {
        // TODO use existBy
        return curriculumRepository.count((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            if(command.getId() != null) {
                filters.add(cb.notEqual(root.get("id"), command.getId()));
            }
            if("code".equals(command.getParamName())) {
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
        Boolean codeExists;
        if(command.getFk() == null) {
            codeExists = curriculumVersionRepository.existsByCurriculumSchoolIdAndCode(schoolId, command.getParamValue());
        } else {
            codeExists = curriculumVersionRepository.existsByCurriculumSchoolIdAndCodeAndCurriculumIdNot(schoolId, command.getParamValue(), command.getFk());
        }
        return Boolean.FALSE.equals(codeExists);

    }

	public List<Classifier> getAreasOfStudyByGroupOfStudy(String code) {
	    return classifierRepository.findAreasOfStudyByGroupOfStudy(code);
	}

    public Curriculum create(HoisUserDetails user, CurriculumForm curriculumForm) {
        Curriculum curriculum = new Curriculum();
        curriculum.setSchool(schoolRepository.getOne(user.getSchoolId()));
        if(curriculumForm.getStateCurriculum() != null) {
            curriculum.setStateCurriculum(stateCurriculumRepository.getOne(curriculumForm.getStateCurriculum()));
        }
        return save(curriculum, curriculumForm);
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
      /*
       * TODO: remove line below as now saving curriculum versions is done on separate form via own controller.
       * Beware, that a lot of tests from CurriculumControllerTest are going to fail.
       */
      updateVersions(curriculum, curriculumForm.getVersions());

      return curriculumRepository.save(curriculum);
    }

    private void updateVersions(Curriculum curriculum, Set<CurriculumVersionDto> versions) {
        Set<CurriculumVersion> newVersions = new HashSet<>();
        if(versions != null) {
            for(CurriculumVersionDto dto : versions) {
                CurriculumVersion version = dto.getId() == null ? new CurriculumVersion() :
                    curriculum.getVersions().stream().filter(v -> v.getId().equals(dto.getId())).findFirst().get();
                CurriculumVersion updatedVersion = updateVersion(curriculum, version, dto);
                newVersions.add(updatedVersion);
            }
        }
        curriculum.setVersions(newVersions);
    }

    private CurriculumVersion updateVersion(Curriculum curriculum, CurriculumVersion version, CurriculumVersionDto dto) {
        CurriculumVersion updatedVersion = EntityUtil.bindToEntity(dto, version, classifierRepository, "curriculumStudyForm", "modules",
                "specialities", "schoolDepartment", "occupationModules");
        saveNewlyAddedSpecialities(curriculum, dto);
        updateCurriculumVersionSpecialities(curriculum.getSpecialities(), updatedVersion, dto.getSpecialitiesReferenceNumbers());
        updateCurriculumVersionModules(updatedVersion, dto.getModules());
        updateCurriculumVersionOccupationalModules(updatedVersion, dto.getOccupationModules());
        updateVersionStudyForm(curriculum, updatedVersion, dto);
        updateSchoolDepartment(updatedVersion, dto);
        return updatedVersion;
    }

    private void saveNewlyAddedSpecialities(Curriculum curriculum, CurriculumVersionDto dto) {
        Set<CurriculumSpecialityDto> newSpecs = dto.getNewCurriculumSpecialities();
        if(!CollectionUtils.isEmpty(newSpecs)) {
            Set<CurriculumSpeciality> newSavedSpecs = new HashSet<>();

            for(CurriculumSpecialityDto ns : newSpecs) {
              Long oldRefNum = ns.getReferenceNumber();
              CurriculumSpeciality newSpec = EntityUtil.bindToEntity(ns, new CurriculumSpeciality(), classifierRepository);
              newSpec.setCurriculum(curriculum);
              newSpec = curriculumSpecialityRepository.save(newSpec);
              newSpec.setReferenceNumber(oldRefNum);
              newSavedSpecs.add(newSpec);
            }
            curriculum.getSpecialities().addAll(newSavedSpecs);
        }
    }

    private void updateSchoolDepartment(CurriculumVersion version, CurriculumVersionDto dto) {
        if (dto.getSchoolDepartment() != null) {
            version.setSchoolDepartment(schoolDepartmentRepository.getOne(dto.getSchoolDepartment()));
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
        version.setCurriculumStudyForm(studyForm.orElse(null));
    }


    private static void updateCurriculumVersionSpecialities(Set<CurriculumSpeciality> curricSpecs, CurriculumVersion version, Set<Long> specRefNums) {
        Set<CurriculumVersionSpeciality> newSpecialities = new HashSet<>();
        if(specRefNums != null) {
          Map<Long, CurriculumVersionSpeciality> oldSpecsMap = StreamUtil.toMap(s -> EntityUtil.getId(s.getCurriculumSpeciality()), version.getSpecialities());
          Map<Long, CurriculumSpeciality> curricSpecsMap = StreamUtil.toMap(CurriculumSpeciality::getReferenceNumber, curricSpecs);

          for(Long s : specRefNums) {
              if(s.longValue() > 0 && oldSpecsMap.containsKey(s)) {
                  newSpecialities.add(oldSpecsMap.get(s));
              } else {
                CurriculumVersionSpeciality newSpec = new CurriculumVersionSpeciality();
                AssertionFailedException.throwIf(!curricSpecsMap.containsKey(s), "Curriculum speciality must be added to Curriculum before adding to Curriculum version!");
                CurriculumSpeciality curriculumSpeciality = curricSpecsMap.get(s);
                newSpec.setCurriculumSpeciality(curriculumSpeciality);
                newSpec.setCurriculumVersion(version);
//                version.getSpecialities().add(newSpec);
//                curriculumSpeciality.getCurriculumVersionSpecialities().add(newSpec);
                newSpecialities.add(newSpec);
              }
          }
        }
        version.setSpecialities(newSpecialities);
      }

    private void updateCurriculumVersionModules(CurriculumVersion version,
            Set<CurriculumVersionHigherModuleDto> modules) {
        Set<CurriculumVersionHigherModule> newModules = new HashSet<>();
        if(modules != null) {
            for(CurriculumVersionHigherModuleDto dto : modules) {
                CurriculumVersionHigherModule module = dto.getId() == null ? new CurriculumVersionHigherModule() :
                    version.getModules().stream().filter(m -> m.getId().equals(dto.getId())).findFirst().get();
                module = EntityUtil.bindToEntity(dto, module, classifierRepository, "subjects", "electiveModules", "specialities");
                module.setCurriculumVersion(version);
                updateCurriculumVersionModuleSubjects(module, dto.getSubjects());
                updateCurriculumVersionModuleElectiveModules(module, dto.getElectiveModules());
                updateVersionModuleSpecialities(version, module, dto.getSpecialitiesReferenceNumbers());
                newModules.add(module);
            }
        }
        version.setModules(newModules);
    }

    private void updateCurriculumVersionOccupationalModules(CurriculumVersion updatedVersion,
            Set<CurriculumVersionOccupationModuleDto> occupationModules) {
        Set<CurriculumVersionOccupationModule> newOccupationModules = new HashSet<>();
        if(occupationModules != null) {
            for(CurriculumVersionOccupationModuleDto dto : occupationModules) {
                CurriculumVersionOccupationModule occupationModule = dto.getId() == null ? new CurriculumVersionOccupationModule() :
                    updatedVersion.getOccupationModules().stream().filter(m -> m.getId().equals(dto.getId())).findFirst().get();
                CurriculumVersionOccupationModule updatedOccupationModule = EntityUtil.bindToEntity(dto, occupationModule,
                        classifierRepository, "curriculumModule", "capacities", "themes", "yearCapacities");

                Optional<CurriculumModule> curriculumModule = updatedVersion.getCurriculum().getModules().stream()
                        .filter(it -> it.getId().equals(dto.getCurriculumModule())).findFirst();
                if (curriculumModule.isPresent()) {
                    updatedOccupationModule.setCurriculumModule(curriculumModule.get());
                }

                Set<CurriculumVersionOccupationModuleCapacity> newOccupationModuleCapacities =
                        dto.getCapacities().stream().filter(Objects::nonNull)
                        .map(capacityDto -> updateCapacities(capacityDto, updatedOccupationModule)).collect(Collectors.toSet());

                updatedOccupationModule.setCapacities(newOccupationModuleCapacities);

                Set<CurriculumVersionOccupationModuleYearCapacity> newOccupationModuleYearCapacities =
                        dto.getYearCapacities().stream().filter(Objects::nonNull)
                        .map(capacityDto -> updateYearCapacities(capacityDto, updatedOccupationModule)).collect(Collectors.toSet());
                updatedOccupationModule.setYearCapacities(newOccupationModuleYearCapacities);

                Set<CurriculumVersionOccupationModuleTheme> newOccupationModuleThemes = new HashSet<>();
                for(CurriculumVersionOccupationModuleThemeDto it : dto.getThemes()) {
                    newOccupationModuleThemes.add(updateThemes(it, updatedOccupationModule));
                }
                updatedOccupationModule.setThemes(newOccupationModuleThemes);


                newOccupationModules.add(updatedOccupationModule);
            }
        }
        updatedVersion.setOccupationModules(newOccupationModules);
    }

    private CurriculumVersionOccupationModuleTheme updateThemes(CurriculumVersionOccupationModuleThemeDto dto,
            CurriculumVersionOccupationModule updatedOccupationModule) {
        CurriculumVersionOccupationModuleTheme theme = dto.getId() == null ? new CurriculumVersionOccupationModuleTheme() :
            updatedOccupationModule.getThemes().stream().filter(c -> c.getId().equals(dto.getId())).findFirst().get();

        CurriculumVersionOccupationModuleTheme updatedTheme =
                EntityUtil.bindToEntity(dto, theme, classifierRepository, "capacities", "outcomes");

        Set<CurriculumVersionOccupationModuleThemeCapacity> newOccupationModuleThemeCapacities =
                dto.getCapacities().stream().filter(Objects::nonNull)
                .map(capacityDto -> updateThemeCapacities(capacityDto, updatedTheme)).collect(Collectors.toSet());

        updatedTheme.setCapacities(newOccupationModuleThemeCapacities);
        updateModuleThemeOutcomes(updatedTheme, dto.getOutcomes());
        return updatedTheme;
    }
    
    private void updateModuleThemeOutcomes(CurriculumVersionOccupationModuleTheme theme,  Set<Long> newOutcomes) {
      EntityUtil.bindEntityCollection(theme.getOutcomes(), o -> EntityUtil.getId(o.getOutcome()), newOutcomes, d -> {
          CurriculumVersionOccupationModuleOutcome outcome = new CurriculumVersionOccupationModuleOutcome();
          outcome.setOutcome(curriculumVersionOccupationModuleOutcomeRepository.getOne(d));
          return outcome;
      });
    }

    private CurriculumVersionOccupationModuleCapacity updateCapacities(CurriculumVersionOccupationModuleCapacityDto dto,
            CurriculumVersionOccupationModule updatedOccupationModule) {
        
        Optional<CurriculumVersionOccupationModuleCapacity> o = dto.getId() == null ? Optional.of(new CurriculumVersionOccupationModuleCapacity()) :
            updatedOccupationModule.getCapacities().stream().filter(c -> c.getId().equals(dto.getId())).findFirst();
        
        if(o.isPresent()) {
            return EntityUtil.bindToEntity(dto, o.get(), classifierRepository);
        }
        return EntityUtil.bindToEntity(dto, new CurriculumVersionOccupationModuleCapacity(), classifierRepository);
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
                  versionSpeciality.getModuleSpecialities().add(newSpec);
                  newSpecs.add(newSpec);
                }
            }
        }
        module.setSpecialities(newSpecs);
    }

    private void updateCurriculumVersionModuleSubjects(CurriculumVersionHigherModule module,
            Set<CurriculumVersionHigherModuleSubjectDto> subjects) {
        Set<CurriculumVersionHigherModuleSubject> newSubjects = new HashSet<>();
        if(subjects != null) {
            for(CurriculumVersionHigherModuleSubjectDto dto : subjects) {
                CurriculumVersionHigherModuleSubject subject = dto.getId() == null ? new CurriculumVersionHigherModuleSubject() :
                    module.getSubjects().stream().filter(s -> s.getId().equals(dto.getId())).findFirst().get();
                subject = EntityUtil.bindToEntity(dto, subject, "nameEt", "nameEt", "credits", "school", "subjectId");
                subject.setSubject(subjectRepository.getOne(dto.getSubjectId()));
                subject.setModule(module);
                newSubjects.add(subject);
            }
        }
        module.setSubjects(newSubjects);
    }

    private static void updateCurriculumVersionModuleElectiveModules(CurriculumVersionHigherModule module,
            Set<CurriculumVersionElectiveModuleDto> electiveModules) {
        Set<CurriculumVersionElectiveModule> newSet = new HashSet<>();
        if(electiveModules != null) {
            for(CurriculumVersionElectiveModuleDto dto : electiveModules) {
                CurriculumVersionElectiveModule newElectiveModule = dto.getId() == null ? new CurriculumVersionElectiveModule() :
                    module.getElectiveModules().stream().filter(e -> e.getId().equals(dto.getId())).findFirst().get();
                newElectiveModule = EntityUtil.bindToEntity(dto, newElectiveModule, "subjects");
                updateCurriculumVersionEleciveModulesSubjects(module.getSubjects(), newElectiveModule, dto.getSubjects());
                newSet.add(newElectiveModule);
            }
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
            for(CurriculumModuleDto dto : modules) {
                CurriculumModule module = dto.getId() == null ? new CurriculumModule() : curriculum.getModules().
                        stream().filter(a -> a.getId().equals(dto.getId())).findFirst().get();

                module = EntityUtil.bindToEntity(dto, module, classifierRepository, "occupations", "competences", "outcomes");
                updateCurriculumModuleOccupations(module, dto.getOccupations());
                updateCurriculumModuleCompetences(module, dto.getCompetences());
                updateCurriculumModuleOutcomes(module, dto.getOutcomes());
                newModules.add(module);
            }
        }
        curriculum.setModules(newModules);
    }

    private static void updateCurriculumModuleOutcomes(CurriculumModule module, Set<CurriculumModuleOutcomeDto> outcomes) {
        Set<CurriculumModuleOutcome> newOutComes = new HashSet<>();
        if(outcomes != null) {
            for(CurriculumModuleOutcomeDto dto : outcomes) {
                CurriculumModuleOutcome outcome = dto.getId() == null ? new CurriculumModuleOutcome() :
                    module.getOutcomes().stream().filter(o -> o.getId().equals(dto.getId())).findFirst().get();

                outcome = EntityUtil.bindToEntity(dto, outcome);
                newOutComes.add(outcome);
            }
        }
        module.setOutcomes(newOutComes);
    }

    private void updateCurriculumModuleCompetences(CurriculumModule module, Set<String> competences) {
        EntityUtil.bindEntityCollection(module.getCompetences(), c -> EntityUtil.getCode(c.getCompetence()), competences, competenceCode -> {
            return new CurriculumModuleCompetence(EntityUtil.validateClassifier(classifierRepository.getOne(competenceCode), MainClassCode.KOMPETENTS));
        });
    }

    private void updateCurriculumModuleOccupations(CurriculumModule module, Set<String> occupations) {
        EntityUtil.bindEntityCollection(module.getOccupations(), o -> EntityUtil.getCode(o.getOccupation()), occupations, occupaionCode -> {
          Classifier c = EntityUtil.validateClassifier(classifierRepository.getOne(occupaionCode),
                  MainClassCode.OSAKUTSE, MainClassCode.KUTSE, MainClassCode.SPETSKUTSE);
          return new CurriculumModuleOccupation(c);
        });
    }

    private void updateOccupations(Curriculum curriculum, Set<CurriculumOccupationDto> occupations) {
        Set<CurriculumOccupation> newOccupations = new HashSet<>();
        if(occupations != null) {
            for(CurriculumOccupationDto dto : occupations) {
                CurriculumOccupation occupation = dto.getId() == null ? new CurriculumOccupation() :
                    curriculum.getOccupations().stream().filter(o -> o.getId().equals(dto.getId())).findFirst().get();
                occupation = EntityUtil.bindToEntity(dto, occupation, classifierRepository, "specialities");
                updateCurriculumOccupationSpecialities(occupation, dto.getSpecialities());
                newOccupations.add(occupation);
            }
        }
        curriculum.setOccupations(newOccupations);
    }

    private void updateCurriculumOccupationSpecialities(CurriculumOccupation occupation, Set<String> specialities) {
        EntityUtil.bindEntityCollection(occupation.getSpecialities(), s -> EntityUtil.getCode(s.getSpeciality()), specialities, specialityCode -> {
            Classifier c = EntityUtil.validateClassifier(classifierRepository.getOne(specialityCode), MainClassCode.SPETSKUTSE);
            return new CurriculumOccupationSpeciality(c);
        });
    }

    private void updateJointPartners(Curriculum curriculum, Set<CurriculumJointPartnerDto> jointPartners) {
        Set<CurriculumJointPartner> newJointPartners = new HashSet<>();
        if(jointPartners != null) {
            for(CurriculumJointPartnerDto dto : jointPartners) {
                CurriculumJointPartner partner = dto.getId() == null ? new CurriculumJointPartner() :
                    curriculum.getJointPartners().stream().filter(p -> p.getId().equals(dto.getId())).findFirst().get();
                newJointPartners.add(EntityUtil.bindToEntity(dto, partner, classifierRepository));
            }
        }
        curriculum.setJointPartners(newJointPartners);
    }

    private void updateCurriculumSpecialities(Curriculum curriculum, Set<CurriculumSpecialityDto> specialities) {
        Set<CurriculumSpeciality> newSpecialities = new HashSet<>();
        if(specialities != null) {
            for(CurriculumSpecialityDto dto : specialities) {
                CurriculumSpeciality speciality = dto.getId() == null ? new CurriculumSpeciality() :
                    curriculum.getSpecialities().stream().filter(s -> s.getId().equals(dto.getId())).findFirst().get();
                CurriculumSpeciality updatedSpeciality = EntityUtil.bindToEntity(dto, speciality, classifierRepository, "curriculum");
                updatedSpeciality.setCurriculum(curriculum);
                newSpecialities.add(updatedSpeciality);
            }
        }
        curriculum.setSpecialities(newSpecialities);
    }

    private void updateGrades(Curriculum curriculum, Set<CurriculumGradeDto> grades) {
        Set<CurriculumGrade> newGrades = new HashSet<>();
        if(grades != null) {
            for(CurriculumGradeDto dto : grades) {
                CurriculumGrade grade = dto.getId() == null ? new CurriculumGrade() :
                    curriculum.getGrades().stream().filter(g -> g.getId().equals(dto.getId())).findFirst().get();
                newGrades.add(EntityUtil.bindToEntity(dto, grade, classifierRepository));
            }
        }
        curriculum.setGrades(newGrades);
    }

    private void updateDepartments(Curriculum curriculum, Set<Long> newDepartments) {
        EntityUtil.bindEntityCollection(curriculum.getDepartments(), c -> EntityUtil.getId(c.getSchoolDepartment()), newDepartments, d -> {
            CurriculumDepartment cd = new CurriculumDepartment();
            cd.setCurriculum(curriculum);
            cd.setSchoolDepartment(schoolDepartmentRepository.getOne(d));
            return cd;
        });
    }

    public void updateCurriculumFiles(Curriculum curriculum, Set<CurriculumFileDto> newFileDtos) {
          Set<CurriculumFile> newFiles = new HashSet<>();
          if(newFileDtos != null) {
              for(CurriculumFileDto dto : newFileDtos) {
                  CurriculumFile file = dto.getId() == null ? new CurriculumFile() :
                      curriculum.getFiles().stream().filter(f -> f.getId().equals(dto.getId())).findFirst().get();
                  file = EntityUtil.bindToEntity(dto, file, classifierRepository);
                  // TODO: probably it would be better not to set new oisFile if it's not changed
                  file.setOisFile(EntityUtil.bindToEntity(dto.getOisFile(), new OisFile()));
                  newFiles.add(file);
              }
          }
          curriculum.setFiles(newFiles);
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

        CurriculumVersion c = curriculumVersionRepository.save(updatedCurriculumVersion);

        return CurriculumVersionDto.of(c);
    }

    public CurriculumVersionDto create(Curriculum curriculum, CurriculumVersionDto dto) {
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

    public void deleteVersion(CurriculumVersion curriculumVersion) {
        EntityUtil.deleteEntity(curriculumVersionRepository, curriculumVersion);
    }

    public CurriculumSpeciality createCurriculumSpeciality(CurriculumSpecialityDto form) {
        CurriculumSpeciality speciality = new CurriculumSpeciality();
        speciality.setCurriculum(curriculumRepository.getOne(form.getCurriculum()));
        return saveCurriculumSpeciality(speciality, form);
    }

    public CurriculumSpeciality saveCurriculumSpeciality(CurriculumSpeciality speciality,
            CurriculumSpecialityDto form) {
        EntityUtil.bindToEntity(form, speciality, classifierRepository, "curriculum");
        return curriculumSpecialityRepository.save(speciality);
    }

    public void deleteSpeciality(CurriculumSpeciality speciality) {
        EntityUtil.deleteEntity(curriculumSpecialityRepository, speciality);
    }

    public List<ClassifierSelection> getCurriculumVersionHmoduleTypes(Long schoolId) {
        final String SELECT = " distinct cvm.type_name_et, cvm.type_name_en ";
        final String FROM = "from curriculum_version_hmodule cvm "
                + "join curriculum_version cv on cv.id = cvm.curriculum_version_id "
                + "join curriculum c on c.id = cv.curriculum_id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(FROM);
        qb.filter("cvm.type_code = 'KORGMOODUL_M'");
        qb.filter("cvm.type_name_et is not null");
        /*
         * criteria is optional as external expert can view the form
         */
        qb.optionalCriteria("c.school_id = :schoolId", "schoolId", schoolId);

        List<?> data = qb.select(SELECT, em).getResultList();
        return StreamUtil.toMappedList(r ->
             new ClassifierSelection(null, resultAsString(r, 0), resultAsString(r, 1), null, null, null, null, null, null)
        , data);
    }

    /**
     * Method not finished yet
     */
    public List<StateCurriculum> getStateCurricula(Sort sort) {
        return stateCurriculumRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            
            List<Predicate> validCurrently = new ArrayList<>();
            
            List<Predicate> validInFuture = new ArrayList<>();
            LocalDate now = LocalDate.now();

            validCurrently.add(cb.or(cb.lessThanOrEqualTo(root.get("validFrom"), now), cb.isNull(root.get("validFrom"))));
            validCurrently.add(cb.or(cb.greaterThanOrEqualTo(root.get("validThru"), now), cb.isNull(root.get("validThru"))));
            validCurrently.add(cb.equal(root.get("status").get("code"), "OPPEKAVA_STAATUS_K")); //TODO: use enum
            
            filters.addAll(validCurrently);
            filters.addAll(validInFuture);

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, sort);
    }

    public Curriculum saveCurriculumModule(Curriculum curriculum, CurriculumDto form) {
        curriculum.setOccupation(form.getOccupation());
        updateOccupations(curriculum, form.getOccupations());
        updateModules(curriculum, form.getModules());
        return curriculumRepository.save(curriculum);
    }

    public void deleteModule(CurriculumModule module) {
        EntityUtil.deleteEntity(curriculumModuleRepository, module);
    }

    public CurriculumVersion updateHigherCurriculumVersionModules(CurriculumVersion curriculumVersion,
            CurriculumVersionDto form) {
        updateCurriculumVersionSpecialities(curriculumVersion.getCurriculum().getSpecialities(), curriculumVersion, form.getSpecialitiesReferenceNumbers());
        updateCurriculumVersionModules(curriculumVersion, form.getModules());
        return curriculumVersionRepository.save(curriculumVersion);
    }

    public CurriculumVersion updateVocationalCurriculumImplementationPlanModules(CurriculumVersion curriculumVersion,
            CurriculumVersionDto form) {
        // TODO Auto-generated method stub
        updateCurriculumVersionOccupationalModules(curriculumVersion, form.getOccupationModules());
        return curriculumVersionRepository.save(curriculumVersion);
    }
}
