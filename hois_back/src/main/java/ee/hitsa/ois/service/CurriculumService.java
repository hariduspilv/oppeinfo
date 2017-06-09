package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumDepartmentRepository;
import ee.hitsa.ois.repository.CurriculumFileRepository;
import ee.hitsa.ois.repository.CurriculumModuleOutcomeRepository;
import ee.hitsa.ois.repository.CurriculumModuleRepository;
import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.SchoolDepartmentRepository;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.CurriculumFileForm;
import ee.hitsa.ois.web.commandobject.CurriculumForm;
import ee.hitsa.ois.web.commandobject.CurriculumSearchCommand;
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
    private CurriculumModuleRepository curriculumModuleRepository;
    @Autowired
    private CurriculumModuleOutcomeRepository curriculumVersionOccupationModuleOutcomeRepository;
    @Autowired
    private CurriculumFileRepository curriculumFileRepository;

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

	public List<Classifier> getAreasOfStudyByGroupOfStudy(String code) {
	    return classifierRepository.findAreasOfStudyByGroupOfStudy(code);
	}

    public Curriculum create(HoisUserDetails user, CurriculumForm curriculumForm) {
        Curriculum curriculum = new Curriculum();
        curriculum.setSchool(em.getReference(School.class, user.getSchoolId()));
        curriculum.setStateCurriculum(EntityUtil.getOptionalOne(StateCurriculum.class, curriculumForm.getStateCurriculum(), em));
        updateCurriculumFiles(curriculum, curriculumForm.getNewFiles());
        return save(curriculum, curriculumForm);
    }

    public Curriculum save(Curriculum curriculum, CurriculumForm curriculumForm) {

      EntityUtil.bindToEntity(curriculumForm, curriculum, classifierRepository,
              "versions", "studyLanguages", "studyForms", "schoolDepartments", "files",
              "jointPartners", "specialities", "modules", "occupations", "grades");
      
      if(curriculum.getId() != null) {
          updateCurriculumFiles(curriculum, StreamUtil.toMappedSet(CurriculumFileDto::of, curriculumForm.getFiles()));
      }

      updateDepartments(curriculum, curriculumForm.getSchoolDepartments());
      updateLanguages(curriculum, curriculumForm.getStudyLanguages());
      updateStudyForms(curriculum, curriculumForm.getStudyForms());
      updateGrades(curriculum, curriculumForm.getGrades());
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
                    EntityUtil.find(dto.getId(), curriculum.getVersions());

                newVersions.add(updateVersion(curriculum, version, dto));
            }
        }
        curriculum.setVersions(newVersions);
    }

    private CurriculumVersion updateVersion(Curriculum curriculum, CurriculumVersion version, CurriculumVersionDto dto) {
        EntityUtil.bindToEntity(dto, version, classifierRepository, "curriculumStudyForm", "modules",
                "specialities", "schoolDepartment", "occupationModules");
        updateCurriculumVersionSpecialities(curriculum.getSpecialities(), version, dto.getSpecialitiesReferenceNumbers());
        updateCurriculumVersionModules(version, dto.getModules());
        updateCurriculumVersionOccupationalModules(version, dto.getOccupationModules());
        updateVersionStudyForm(curriculum, version, dto);
        updateSchoolDepartment(version, dto);
        return version;
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
                    EntityUtil.find(dto.getId(), version.getModules());

                EntityUtil.bindToEntity(dto, module, classifierRepository, "subjects", "electiveModules", "specialities");
                module.setCurriculumVersion(version);
                updateCurriculumVersionModuleElectiveModules(module, dto.getElectiveModules());
                updateCurriculumVersionModuleSubjects(module, dto.getSubjects());
                updateVersionModuleSpecialities(version, module, dto.getSpecialitiesReferenceNumbers());
                newModules.add(module);
            }
        }
        version.setModules(newModules);
    }
    
    private static void updateCurriculumVersionModuleElectiveModules(CurriculumVersionHigherModule module,
            Set<CurriculumVersionElectiveModuleDto> electiveModules) {
        Set<CurriculumVersionElectiveModule> newSet = new HashSet<>();
        if(electiveModules != null) {
            for(CurriculumVersionElectiveModuleDto dto : electiveModules) {
                CurriculumVersionElectiveModule newElectiveModule = dto.getId() == null ? new CurriculumVersionElectiveModule() :
                    EntityUtil.find(dto.getId(), module.getElectiveModules());

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
                    EntityUtil.find(dto.getId(), module.getSubjects());

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
        Set<CurriculumVersionOccupationModule> newOccupationModules = new HashSet<>();
        if(occupationModules != null) {
            for(CurriculumVersionOccupationModuleDto dto : occupationModules) {
                CurriculumVersionOccupationModule occupationModule = dto.getId() == null ? new CurriculumVersionOccupationModule() :
                    EntityUtil.find(dto.getId(), updatedVersion.getOccupationModules());

                EntityUtil.bindToEntity(dto, occupationModule,
                        classifierRepository, "curriculumModule", "capacities", "themes", "yearCapacities");

                Optional<CurriculumModule> curriculumModule = updatedVersion.getCurriculum().getModules().stream()
                        .filter(it -> it.getId().equals(dto.getCurriculumModule())).findFirst();
                if (curriculumModule.isPresent()) {
                    occupationModule.setCurriculumModule(curriculumModule.get());
                }

                Set<CurriculumVersionOccupationModuleCapacity> newOccupationModuleCapacities =
                        dto.getCapacities().stream().filter(Objects::nonNull)
                        .map(capacityDto -> updateCapacities(capacityDto, occupationModule)).collect(Collectors.toSet());

                occupationModule.setCapacities(newOccupationModuleCapacities);

                Set<CurriculumVersionOccupationModuleYearCapacity> newOccupationModuleYearCapacities =
                        dto.getYearCapacities().stream().filter(Objects::nonNull)
                        .map(capacityDto -> updateYearCapacities(capacityDto, occupationModule)).collect(Collectors.toSet());
                occupationModule.setYearCapacities(newOccupationModuleYearCapacities);

                occupationModule.setThemes(StreamUtil.toMappedSet(it -> updateThemes(it, occupationModule), dto.getThemes()));

                newOccupationModules.add(occupationModule);
            }
        }
        updatedVersion.setOccupationModules(newOccupationModules);
    }

    private CurriculumVersionOccupationModuleTheme updateThemes(CurriculumVersionOccupationModuleThemeDto dto,
            CurriculumVersionOccupationModule updatedOccupationModule) {
        CurriculumVersionOccupationModuleTheme theme = dto.getId() == null ? new CurriculumVersionOccupationModuleTheme() :
            EntityUtil.find(dto.getId(), updatedOccupationModule.getThemes());

        EntityUtil.bindToEntity(dto, theme, classifierRepository, "capacities", "outcomes");

        Set<CurriculumVersionOccupationModuleThemeCapacity> newOccupationModuleThemeCapacities =
                dto.getCapacities().stream().filter(Objects::nonNull)
                .map(capacityDto -> updateThemeCapacities(capacityDto, theme)).collect(Collectors.toSet());

        theme.setCapacities(newOccupationModuleThemeCapacities);
        EntityUtil.bindEntityCollection(theme.getOutcomes(), o -> EntityUtil.getId(o.getOutcome()), dto.getOutcomes(), d -> {
            CurriculumVersionOccupationModuleOutcome outcome = new CurriculumVersionOccupationModuleOutcome();
            outcome.setOutcome(curriculumVersionOccupationModuleOutcomeRepository.getOne(d));
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
            EntityUtil.find(dto.getId(), updatedOccupationModule.getYearCapacities());
        return EntityUtil.bindToEntity(dto, capacity, classifierRepository);
    }

    private CurriculumVersionOccupationModuleThemeCapacity updateThemeCapacities(
            CurriculumVersionOccupationModuleThemeCapacityDto dto, CurriculumVersionOccupationModuleTheme updatedTheme) {
        CurriculumVersionOccupationModuleThemeCapacity capacity = dto.getId() == null ? new CurriculumVersionOccupationModuleThemeCapacity() :
            EntityUtil.find(dto.getId(), updatedTheme.getCapacities());
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

    private void updateModules(Curriculum curriculum, Set<CurriculumModuleDto> modules) {
        Set<CurriculumModule> newModules = new HashSet<>();

        if(modules != null) {
            for(CurriculumModuleDto dto : modules) {
                CurriculumModule module = dto.getId() == null ? new CurriculumModule() : EntityUtil.find(dto.getId(), curriculum.getModules());

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
                    EntityUtil.find(dto.getId(), module.getOutcomes());

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
                    EntityUtil.find(dto.getId(), curriculum.getOccupations());

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
            updateJointPartner(dto, jointPartner);
            return jointPartner;
        }, this::updateJointPartner);
    }
    
    public void updateJointPartner(CurriculumJointPartnerDto dto, CurriculumJointPartner partner) {
        EntityUtil.bindToEntity(dto, partner, classifierRepository);
    }
    
    private void updateCurriculumSpecialities(Curriculum curriculum, Set<CurriculumSpecialityDto> specialities) {
        EntityUtil.bindEntityCollection(curriculum.getSpecialities(), CurriculumSpeciality::getId, 
                specialities, CurriculumSpecialityDto::getId, dto -> {
         CurriculumSpeciality speciality = new CurriculumSpeciality();
         speciality.setCurriculum(curriculum);
         updateSpeciality(dto, speciality);
         return speciality;
        }, this::updateSpeciality);
    }
    
    private void updateSpeciality(CurriculumSpecialityDto dto, CurriculumSpeciality speciality) {
        EntityUtil.bindToEntity(dto, speciality, classifierRepository, "curriculum");
    }
    
    private void updateGrades(Curriculum curriculum, Set<CurriculumGradeDto> gradeDtos) {
        EntityUtil.bindEntityCollection(curriculum.getGrades(), CurriculumGrade::getId, gradeDtos, 
                CurriculumGradeDto::getId, dto -> {
            CurriculumGrade grade = new CurriculumGrade();
            updateGrade(dto, grade);
            return grade;
        }, this::updateGrade);
    }


    private void updateGrade(CurriculumGradeDto dto, CurriculumGrade grade) {
        EntityUtil.bindToEntity(dto, grade, classifierRepository);
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

    public CurriculumVersionDto create(Curriculum curriculum, CurriculumVersionDto dto) {
        CurriculumVersion curriculumVersion = new CurriculumVersion();
        curriculumVersion.setCurriculum(curriculum);
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

            if(!CollectionUtils.isEmpty(pointPartnersEhisSchools)) {
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
        EntityUtil.deleteEntity(curriculumVersionRepository, curriculumVersion);
    }

    public Curriculum updateCurriculumSpecialities(Curriculum curriculum,
            CurriculumDto form) {
        updateCurriculumSpecialities(curriculum, form.getSpecialities());
        return curriculumRepository.save(curriculum);
    }

    public List<ClassifierSelection> getCurriculumVersionHmoduleTypes(Long schoolId) {
        final String FROM = "from curriculum_version_hmodule cvm "
                + "join curriculum_version cv on cv.id = cvm.curriculum_version_id "
                + "join curriculum c on c.id = cv.curriculum_id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(FROM);

        qb.filter("cvm.type_code = 'KORGMOODUL_M'");
        qb.filter("cvm.type_name_et is not null");

        // school is optional as external expert can view the form
        qb.optionalCriteria("c.school_id = :schoolId", "schoolId", schoolId);

        List<?> data = qb.select("distinct cvm.type_name_et, cvm.type_name_en", em).getResultList();
        return StreamUtil.toMappedList(r ->
             new ClassifierSelection(null, resultAsString(r, 0), resultAsString(r, 1), null, null, null, null, null, null)
        , data);
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
        updateCurriculumVersionOccupationalModules(curriculumVersion, form.getOccupationModules());
        return curriculumVersionRepository.save(curriculumVersion);
    }

    public Curriculum updateCurriculumGrades(Curriculum curriculum, CurriculumDto form) {
        updateGrades(curriculum, form.getGrades());
        return curriculumRepository.save(curriculum);
    }

    public Curriculum closeCurriculum(Curriculum curriculum) {
        curriculum.setStatus(classifierRepository.findOne(CurriculumStatus.OPPEKAVA_STAATUS_C.name()));
        if(!CollectionUtils.isEmpty(curriculum.getVersions())){
            Classifier statusClosed = classifierRepository.findOne(CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C.name());
            for(CurriculumVersion version : curriculum.getVersions()) {
                version.setStatus(statusClosed);
            }
        }
        return curriculumRepository.save(curriculum);
    }

    public CurriculumFile createCurriculumFile(Curriculum curriculum, CurriculumFileForm curriculumFileForm) {
        CurriculumFile curriculumFile = new CurriculumFile();
        EntityUtil.bindToEntity(curriculumFileForm, curriculumFile, classifierRepository, "oisFile");
        curriculumFile.setCurriculum(curriculum);
        curriculumFile.setOisFile(EntityUtil.bindToEntity(curriculumFileForm.getOisFile(), new OisFile()));
        return curriculumFileRepository.save(curriculumFile);
    }

    public void deleteCurriculumFile(CurriculumFile curriculumFile) {
        EntityUtil.deleteEntity(curriculumFileRepository, curriculumFile);
    }

    public boolean isVersionUnique(Long schoolId, UniqueCommand command) {
        Boolean codeExists;
        if(command.getId() == null) {
            codeExists = curriculumVersionRepository.existsByCurriculumSchoolIdAndCode(schoolId, command.getParamValue());
        } else {
            codeExists = curriculumVersionRepository.existsByCurriculumSchoolIdAndCodeAndIdNot(schoolId, command.getParamValue(), command.getId());
        }
        return Boolean.FALSE.equals(codeExists);
    }

    public boolean isHigherMerCodeUnique(UniqueCommand command) {
        Boolean codeExists;
        if(command.getId() == null) {
            codeExists = curriculumRepository.existsByMerCode(command.getParamValue());
        } else {
            codeExists = curriculumRepository.existsByMerCodeAndIdNot(command.getParamValue(), command.getId());
        }
        return Boolean.FALSE.equals(codeExists);
    }

    public boolean isHigherCodeUnique(Long schoolId, UniqueCommand command) {
        Boolean codeExists;
        if(command.getId() == null) {
            codeExists = curriculumRepository.existsBySchoolIdAndCode(schoolId, command.getParamValue());
        } else {
            codeExists = curriculumRepository.existsBySchoolIdAndCodeAndIdNot(schoolId, command.getParamValue(), command.getId());
        }
        return Boolean.FALSE.equals(codeExists);
    }

    public boolean isVocationalNameUnique(Long schoolId, UniqueCommand command) {
        
        String column = command.getParamName();
        final List<String> COLUMN_NAMES = Arrays.asList("nameEt", "nameEn", "nameRu");
        AssertionFailedException.throwIf(!COLUMN_NAMES.contains(column),
                "Only name fields are validated here!");
        
        Boolean exists = null;
        
        if(command.getId() == null) {
            if(column.equals("nameEn")) {
                exists = curriculumRepository.existsBySchoolIdAndNameEn(schoolId, command.getParamValue());
            } else if(column.equals("nameRu")) {
                exists = curriculumRepository.existsBySchoolIdAndNameRu(schoolId, command.getParamValue());
            } else {
                exists = curriculumRepository.existsBySchoolIdAndNameEt(schoolId, command.getParamValue());
            }
        } else {
            if(column.equals("nameEn")) {
                exists = curriculumRepository.existsBySchoolIdAndNameEnAndIdNot(schoolId, command.getParamValue(), command.getId());
            } else if(column.equals("nameRu")) {
                exists = curriculumRepository.existsBySchoolIdAndNameRuAndIdNot(schoolId, command.getParamValue(), command.getId());
            } else {
                exists = curriculumRepository.existsBySchoolIdAndNameEtAndIdNot(schoolId, command.getParamValue(), command.getId());
            }
        }
        return Boolean.FALSE.equals(exists);
    }

    public boolean isVocationalMerCodeUnique(Long schoolId, UniqueCommand command) {
        return curriculumRepository.count((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            if(command.getId() != null) {
                filters.add(cb.notEqual(root.get("id"), command.getId()));
            }
            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            filters.add(cb.equal(root.get("merCode"), command.getParamValue()));
            
            LocalDate now = LocalDate.now();
            filters.add(cb.or(cb.lessThanOrEqualTo(root.get("validFrom"), now), cb.isNull(root.get("validFrom"))));
            filters.add(cb.or(cb.greaterThanOrEqualTo(root.get("validThru"), now), cb.isNull(root.get("validThru"))));
            
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }) == 0;
    }
}
