package ee.hitsa.ois.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Curriculum;
import ee.hitsa.ois.domain.CurriculumDepartment;
import ee.hitsa.ois.domain.CurriculumFile;
import ee.hitsa.ois.domain.CurriculumForSearch;
import ee.hitsa.ois.domain.CurriculumGrade;
import ee.hitsa.ois.domain.CurriculumJointPartner;
import ee.hitsa.ois.domain.CurriculumModule;
import ee.hitsa.ois.domain.CurriculumSpeciality;
import ee.hitsa.ois.domain.CurriculumStudyForm;
import ee.hitsa.ois.domain.CurriculumStudyLanguage;
import ee.hitsa.ois.domain.School;
import ee.hitsa.ois.domain.SchoolDepartment;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumDepartmentRepository;
import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.repository.SchoolDepartmentRepository;
import ee.hitsa.ois.util.CurriculumSorter;
import ee.hitsa.ois.util.SearchUtil;
import ee.hitsa.ois.web.commandobject.CurriculumForm;
import ee.hitsa.ois.web.commandobject.CurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;

@Transactional
@Service
public class CurriculumService {
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

	public Iterable<CurriculumDepartment> findAllDepartments() {
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

	public Page<CurriculumForSearch> search(CurriculumSearchCommand curriculumSearchCommand, Pageable pageable) {
		System.out.println("Search command: " + curriculumSearchCommand.toString());
		System.out.println("Pageable: " + pageable.toString());

		boolean ignoreByNameEt = !(curriculumSearchCommand.getLang() == Language.ET && curriculumSearchCommand.getName() != null);
		boolean ignoreNameEn = !(curriculumSearchCommand.getLang() == Language.EN && curriculumSearchCommand.getName() != null);
		boolean ignoreValidFrom = SearchUtil.ingoreValue(curriculumSearchCommand.getValidFromMillis());
		boolean ignoreValidThru = SearchUtil.ingoreValue(curriculumSearchCommand.getValidThruMillis());
		boolean ignoreCreditsMin = SearchUtil.ingoreValue(curriculumSearchCommand.getCreditsMin());
		boolean ignoreCreditsMax = SearchUtil.ingoreValue(curriculumSearchCommand.getCreditsMax());
		boolean ignoreCode = SearchUtil.ingoreValue(curriculumSearchCommand.getCode());
		boolean ignoreHtmCode = SearchUtil.ingoreValue(curriculumSearchCommand.getHtmCode());
		boolean ignoreSchool = SearchUtil.ignoreList(curriculumSearchCommand.getSchool());
		boolean ignoreStatus = SearchUtil.ignoreList(curriculumSearchCommand.getStatus());
		boolean ignoreEhisLevel = SearchUtil.ignoreList(curriculumSearchCommand.getEhisStatus());
		boolean ignoreIscedClassCode = SearchUtil.ignoreList(curriculumSearchCommand.getIscedClassCode());
		boolean ignoreStudyLevel = SearchUtil.ignoreList(curriculumSearchCommand.getStudyLevel());
		boolean ignoreLanguage = SearchUtil.ignoreList(curriculumSearchCommand.getStudyLanguage());
		boolean ignoreEkrLevel = SearchUtil.ignoreList(curriculumSearchCommand.getEkrLevel());
		boolean ignoreDepartments = SearchUtil.ignoreList(curriculumSearchCommand.getDepartment());

		Date validFrom = SearchUtil.millisToDate(curriculumSearchCommand.getValidFromMillis());
		Date validThru = SearchUtil.millisToDate(curriculumSearchCommand.getValidThruMillis());
		String name = SearchUtil.contains(curriculumSearchCommand.getName());
		boolean isJoint = Boolean.TRUE.equals(curriculumSearchCommand.getIsJoint());
		Double creditsMin = curriculumSearchCommand.getCreditsMin() == null ? Double.valueOf(0) : curriculumSearchCommand.getCreditsMin();
		Double creditsMax = curriculumSearchCommand.getCreditsMax() == null ? Double.valueOf(0): curriculumSearchCommand.getCreditsMax();
		String code = SearchUtil.contains(curriculumSearchCommand.getCode());
		String htmCode = SearchUtil.contains(curriculumSearchCommand.getHtmCode());
		List<Long> schools = SearchUtil.getLongList(curriculumSearchCommand.getSchool());
		List<String> statuses = SearchUtil.getStringList(curriculumSearchCommand.getStatus());
		List<String> ehisStatuses = SearchUtil.getStringList(curriculumSearchCommand.getEhisStatus());
		List<String> iscedClassCodes = SearchUtil.getStringList(curriculumSearchCommand.getIscedClassCode());
		List<String> studyLevels = SearchUtil.getStringList(curriculumSearchCommand.getStudyLevel());
		List<String> languages = SearchUtil.getStringList(curriculumSearchCommand.getStudyLanguage());
		List<String> ekrLevels = SearchUtil.getStringList(curriculumSearchCommand.getEkrLevel());
		List<Long> departments = SearchUtil.getLongList(curriculumSearchCommand.getDepartment());

		List<CurriculumForSearch> list =
				curriculumRepository.search(name, ignoreByNameEt, ignoreNameEn,
						validFrom, ignoreValidFrom, validThru, ignoreValidThru,
						creditsMin, ignoreCreditsMin, creditsMax, ignoreCreditsMax, isJoint,
						code, ignoreCode, htmCode, ignoreHtmCode, schools, ignoreSchool,
						statuses, ignoreStatus, ehisStatuses, ignoreEhisLevel,
						iscedClassCodes, ignoreIscedClassCode, studyLevels, ignoreStudyLevel,
						ekrLevels, ignoreEkrLevel, languages, ignoreLanguage, departments, ignoreDepartments);
    	return CurriculumSorter.sort(list, pageable);
    }

	public boolean isUnique(School school, UniqueCommand command) {
        return curriculumRepository.count((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            if(command.getId() != null) {
                filters.add(cb.notEqual(root.get("id"), command.getId()));
            }
            if(command.getParamName().equals("code")) {
                filters.add(cb.equal(root.get("school").get("id"), school.getId()));
            }
            final Set<String> ALLOWED_PROPERTIES = new HashSet<>(Arrays.asList("code", "merCode"));
            if(ALLOWED_PROPERTIES.contains(command.getParamName())) {
                filters.add(cb.equal(root.get(command.getParamName()), command.getParamValue()));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }) == 0;
    }

	public List<Classifier> getAreasOfStudyByGroupOfStudy(String code) {
	    return classifierRepository.findAreasOfStudyByGroupOfStudy(code);
	}

    public Curriculum save(Curriculum curriculum, CurriculumForm curriculumForm) {
        
        if(curriculumForm.getSchoolDepartments() != null) {
            Set<CurriculumDepartment> departments = curriculumForm.getSchoolDepartments()
                  .stream().map(department -> new CurriculumDepartment(schoolDepartmentRepository.findOne(department.getId()))).collect(Collectors.toSet());
            merge(curriculum.getDepartments(), departments);

        }

        if(curriculumForm.getStudyLanguageClassifiers() != null) {
            Set<CurriculumStudyLanguage> curriculumStudyLanguages = curriculumForm.getStudyLanguageClassifiers()
                    .stream().map(classifier -> new CurriculumStudyLanguage(classifierService.findOne(classifier.getCode()))).collect(Collectors.toSet());
            merge(curriculum.getStudyLanguages(), curriculumStudyLanguages);
        }

        if(curriculumForm.getStudyFormClassifiers() != null) {
            Set<CurriculumStudyForm> curriculumStudyForms = curriculumForm.getStudyFormClassifiers()
                    .stream().map(classifier -> new CurriculumStudyForm(classifierService.findOne(classifier.getCode()))).collect(Collectors.toSet());
            merge(curriculum.getStudyForms(), curriculumStudyForms);
        }
        
        return curriculumRepository.save(curriculum);
    }

	private <T> Set<T> merge(Set<T> originalSet, Set<T> changedSet) {
        if(originalSet != null && changedSet != null) {
            originalSet.addAll(changedSet);
            originalSet.retainAll(changedSet);
        }
        return originalSet;
    }

}
