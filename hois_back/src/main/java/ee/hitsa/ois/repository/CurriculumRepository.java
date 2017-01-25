package ee.hitsa.ois.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumForSearch;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

	public Page<Curriculum> findAll(Specification<Curriculum> spec, Pageable pageable);
	
	@Query(nativeQuery=true, name=CurriculumForSearch.SEARCH_QUERY)
	public List<CurriculumForSearch> search(
			String nameEt, boolean ignoreByNameEt, boolean ignoreNameEn, 
			Date validFrom, boolean ignoreValidFrom, 
			Date validThru, boolean ignoreValidThru, 
			Double creditsMin, boolean ignoreCreditsMin, 
			Double creditsMax, boolean ignoreCreditsMax, 
			boolean isJoint, 
			String code, boolean ignoreCode, 
			String htmCode, boolean ignoreHtmCode, 
			List<Long> schools, boolean ignoreSchool, 
			List<String> status, boolean ignoreStatus, 
			List<String> ehisStatus, boolean ignoreEhisStatus, 
			List<String> iscedClassCodes, boolean ignoreIscedClassCodes, 
			List<String> studyLevel, boolean ignoreStudyLevel, 
			List<String> ekrLevels, boolean ignoreEkrLevel,
			List<String> languages, boolean ignoreLanguage, 
			List<Long> departments, boolean ignoreDepartments);

	public long count(Specification<Curriculum> spec);

	List<AutocompleteResult<Long>> findAllBySchool_id(Long schoolId);
}
