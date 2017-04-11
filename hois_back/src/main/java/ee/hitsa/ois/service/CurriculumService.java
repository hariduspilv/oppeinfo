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
import ee.hitsa.ois.repository.CurriculumSpecialityRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.SchoolDepartmentRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
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

    /*
     * Left joins should be removed and corresponding conditions moved to where clause
     */
//    private static String CURRICULUM_FROM = ""
//            + "from curriculum c "
//            + "inner join school on school.id = c.school_id "
//            + "left join classifier_connect cc1 on cc1.classifier_code = c.orig_study_level_code "
//            + "inner join classifier orig_study_level on orig_study_level.code = c.orig_study_level_code "
//            + "inner join classifier status on status.code = c.status_code "
//            + "left join classifier_connect cc2 on cc2.classifier_code = c.isced_class_code "
//            + "left join classifier_connect cc3 on cc3.classifier_code = cc2.connect_classifier_code"
//            ;
//    private static String CURRICULUM_SELECT = 
//            "distinct c.id, c.name_et, c.name_en, "
//            + "c.orig_study_level_code, c.credits, c.valid_from, c.valid_thru, "
//            + "c.status_code, c.is_higher, "
//            + "school.id as schoolId, school.name_et as schoolNameEt, school.name_en as schoolNameEn,"
//            + "orig_study_level.name_et as stLevNameEt, orig_study_level.name_en as stLevNameEn, "
//            + "status.name_et as statusNameEt, status.name_en as statusNameEn ";
    
//    public Page<CurriculumSearchDto> search(Long schoolId, CurriculumSearchCommand criteria, Pageable pageable) {
//        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(CURRICULUM_FROM, pageable);
//        qb.optionalContains(Arrays.asList("c.name_et", "c.name_en"), "name", criteria.getName());
//        qb.optionalCriteria("c.school_id = :schoolId", "schoolId", schoolId);
//        qb.optionalCriteria("c.credits >= :creditsMin", "creditsMin", criteria.getCreditsMin());
//        qb.optionalCriteria("c.credits <= :creditsMax", "creditsMax", criteria.getCreditsMax());
//        qb.optionalCriteria("c.school_id in (:schoolIds)", "schoolIds", criteria.getSchool());
//        qb.optionalCriteria("c.status_code in (:statusCodes)", "statusCodes", criteria.getStatus());
//        qb.optionalCriteria("c.ehis_status_code in (:ehisStatusCodes)", "ehisStatusCodes", criteria.getEhisStatus());
//        qb.optionalCriteria("c.orig_study_level_code in (:studyLevel)", "studyLevel", criteria.getStudyLevel());
//        qb.optionalCriteria("cc1.connect_classifier_code in (:ektLevel)", "ektLevel", criteria.getEkrLevel());
//        qb.optionalContains("c.code", "code", criteria.getCode());
//        qb.optionalContains("c.mer_code", "merCode", criteria.getMerCode());
//        qb.optionalCriteria("c.valid_from >= :validFrom", "validFrom", criteria.getValidFrom(), DateUtils::firstMomentOfDay);
//        qb.optionalCriteria("c.valid_thru <= :validThru", "validThru", criteria.getValidThru(), DateUtils::lastMomentOfDay);
//        qb.optionalCriteria("c.isced_class_code in (:iscedClass)", "iscedClass", criteria.getIscedClassCode());
//        qb.optionalCriteria("c.group_code in (:curriculumGroup)", "curriculumGroup", criteria.getCurriculumGroup());
//        /**
//         * While in vocational curriculum in isced_class_code column are only saved 
//         * classifiers with main_class_code = 'ISCED_RYHM', in case higher education curriculum it may be whether 
//         * 'ISCED_SUUN' or 'ISCED_VALD' (or empty).
//         * Thus isced_suun may be isced_class or it's parent
//         * and isced_vald may be isced_class, it's parent or grand parent
//         *  
//         * PS: Parents are classifiers connected to classifier with classifier_connect table 
//         * and referenced by connect_classifier_code column.
//         */
//        qb.optionalCriteria("(cc2.connect_classifier_code in (:iscedSuun) OR c.isced_class_code in (:iscedSuun))", "iscedSuun", criteria.getIscedSuun());
//        qb.optionalCriteria("(cc3.connect_classifier_code = :iscedVald OR c.isced_class_code = :iscedVald OR cc2.connect_classifier_code = :iscedVald)", "iscedVald", criteria.getIscedVald());
//        
//        qb.optionalCriteria("exists (select id from curriculum_department where curriculum_id = c.id and school_department_id in(:departments))", "departments", criteria.getDepartment());
//        qb.optionalCriteria("exists (select id from curriculum_study_lang where curriculum_id = c.id and study_lang_code in(:studyLangs))", "studyLangs", criteria.getStudyLanguage());
//        
//        if(criteria.getIsJoint() != null && criteria.getIsJoint().equals(Boolean.TRUE)) {
//            qb.filter("c.is_joint");
//        }
//        Page<Object[]> curriculums = JpaQueryUtil.pagingResult(qb, CURRICULUM_SELECT, em, pageable);
//        return curriculums.map(c -> {
//            CurriculumSearchDto dto = new CurriculumSearchDto();
//            dto.setId(resultAsLong(c, 0));
//            dto.setNameEt(resultAsString(c, 1));
//            dto.setNameEn(resultAsString(c, 2));
//            dto.setOrigStudyLevel(resultAsString(c, 3));
//            dto.setCredits(resultAsLong(c, 4));
//            dto.setValidFrom(resultAsLocalDate(c,  5));
//            dto.setValidThru(resultAsLocalDate(c,  6));
//            dto.setStatus(resultAsString(c, 7));
//            dto.setHigher(resultAsBoolean(c, 8));
//            AutocompleteResult school = new AutocompleteResult(resultAsLong(c, 9), resultAsString(c, 10), resultAsString(c, 11));
//            dto.setSchool(school);
//            return dto;
//        });
//    }

    @SuppressWarnings("unchecked")
    public Page<CurriculumSearchDto> search(Long schoolId, CurriculumSearchCommand criteria, Pageable pageable) {
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

    public Curriculum create(HoisUserDetails user, CurriculumForm curriculumForm) {
        Curriculum curriculum = new Curriculum();
        curriculum.setSchool(schoolRepository.getOne(user.getSchoolId()));
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
      // Versions are now saved on separate form
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
        if(studyForm.isPresent()) {
            version.setCurriculumStudyForm(studyForm.get());
        } else {
          version.setCurriculumStudyForm(null);
        }
    }
    

    private static void updateCurriculumVersionSpecialities(Set<CurriculumSpeciality> curricSpecs, CurriculumVersion version, Set<Long> specRefNums) {
        Set<CurriculumVersionSpeciality> newSpecialities = new HashSet<>();
        if(specRefNums != null) {
          Map<Long, CurriculumVersionSpeciality> oldSpecsMap = StreamUtil.toMap(s -> EntityUtil.getId(s.getCurriculumSpeciality()), version.getSpecialities());
          Map<Long, CurriculumSpeciality> curricSpecsMap = StreamUtil.toMap(CurriculumSpeciality::getReferenceNumber, curricSpecs);

          specRefNums.forEach(s -> {
              if(s.longValue() > 0 && oldSpecsMap.containsKey(s)) {
                  newSpecialities.add(oldSpecsMap.get(s));
              } else {
                CurriculumVersionSpeciality newSpec = new CurriculumVersionSpeciality();
                assert curricSpecsMap.containsKey(s) : "Curriculum speciality must be added to Curriculum before adding to Curriculum version!";
                CurriculumSpeciality curriculumSpeciality = curricSpecsMap.get(s);
                newSpec.setCurriculumSpeciality(curriculumSpeciality);
                newSpec.setCurriculumVersion(version);
//                version.getSpecialities().add(newSpec);
//                curriculumSpeciality.getCurriculumVersionSpecialities().add(newSpec);
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
            Map<Long, CurriculumVersionHigherModuleSpeciality> oldSpecs = StreamUtil.toMap(s -> EntityUtil.getId(s.getSpeciality().getCurriculumSpeciality()), module.getSpecialities());
            Map<Long, CurriculumVersionSpeciality> selectedSpecs = StreamUtil.toMap(s -> s.getCurriculumSpeciality().getReferenceNumber(), version.getSpecialities());

            specsRefNums.forEach(s -> {
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
            EntityUtil.bindClassifierCollection(oldSet, c -> EntityUtil.getCode(c.getCompetence()), competences, competenceCode -> {
                return new CurriculumModuleCompetence(EntityUtil.validateClassifier(classifierRepository.getOne(competenceCode), MainClassCode.KOMPETENTS));
            });
        } else {
            module.setCompetences(new HashSet<>());
        }
    }

    private void updateCurriculumModuleOccupations(CurriculumModule module, Set<String> occupations) {
        if(occupations != null) {
            Set<CurriculumModuleOccupation> oldSet = module.getOccupations();
            EntityUtil.bindClassifierCollection(oldSet, o -> EntityUtil.getCode(o.getOccupation()), occupations, occupaionCode -> {
              Classifier c = EntityUtil.validateClassifier(classifierRepository.getOne(occupaionCode),
                      MainClassCode.OSAKUTSE, MainClassCode.KUTSE, MainClassCode.SPETSKUTSE);
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
            EntityUtil.bindClassifierCollection(oldList, s -> EntityUtil.getCode(s.getSpeciality()), specialities, specialityCode -> {
                Classifier c = EntityUtil.validateClassifier(classifierRepository.getOne(specialityCode), MainClassCode.SPETSKUTSE);
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
                CurriculumSpeciality updatedSpeciality = EntityUtil.bindToEntity(dto, speciality, classifierRepository);
                updatedSpeciality.setCurriculum(curriculum);
                newSpecialities.add(updatedSpeciality);
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
            .stream().map(d -> new CurriculumDepartment(schoolDepartmentRepository.getOne(d))).collect(Collectors.toSet());
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
            EntityUtil.bindClassifierCollection(storedStudyForms, sf -> EntityUtil.getCode(sf.getStudyForm()), studyForms, studyForm -> {
                // add new link
                Classifier c = EntityUtil.validateClassifier(classifierRepository.getOne(studyForm), MainClassCode.OPPEVORM);
                return new CurriculumStudyForm(c);
            });
        } else {
            curriculum.setStudyForms(new HashSet<>());
        }
    }

    private void updateLanguages(Curriculum target, Set<String> languageCodes) {
        if(languageCodes != null && !languageCodes.isEmpty()) {
          // TODO use EntityUtil.bindClassifierCollection
          Map<String, CurriculumStudyLanguage> langs = StreamUtil.toMap(e -> EntityUtil.getCode(e.getStudyLang()), target.getStudyLanguages());

          Set<CurriculumStudyLanguage> newSet = new HashSet<>();
          for(String lang : languageCodes) {
              CurriculumStudyLanguage csl = langs.get(lang);
              if(csl != null) {
                  newSet.add(csl);
              } else {
                  newSet.add(new CurriculumStudyLanguage(classifierRepository.getOne(lang)));
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
}
