package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.Classifier;

public interface ClassifierRepository extends JpaRepository<Classifier, String> {

	/**
	 * TODO: Spring says to cache concrete classes
     * if this kind of cache is even usable
	 */
    @Cacheable(cacheNames = "classifier")
    @Override
    Classifier findOne(String code);

    @Override
    @CacheEvict(cacheNames = "classifier", allEntries = true)
    void delete(String code);

    @Override
    @CacheEvict(cacheNames = "classifier", allEntries = true)
    <S extends Classifier> S save(S entity);

    //caching this way is bad idea
    //@Cacheable(cacheNames = "classifier", key="#spec.getSearchCommand().getCacheKey()+#pageable.getPageNumber()+#pageable.getPageSize()+#pageable.getOffset()+#pageable.getSort()")
    Page<Classifier> findAll(Specification<Classifier> spec, Pageable pageable);

    //caching this way is bad idea
    //@Cacheable(cacheNames = "classifier", key="#spec.getSearchCommand().getCacheKey()")
    List<Classifier> findAll(Specification<Classifier> spec);

    //caching this way is bad idea
    //@Cacheable(cacheNames = "classifier", key="#spec.getSearchCommand().getCacheKey()+#sort.toString()")
    List<Classifier> findAll(Specification<Classifier> spec, Sort sort);


	Page<Classifier> findAllByMainClassCodeAndNameEtContainingIgnoreCaseAndCodeContaining(String mainClassCode, String nameEt, String code, Pageable pageable);
	Page<Classifier> findAllByMainClassCodeAndNameEtContainingIgnoreCaseAndValueStartingWithIgnoreCase(String mainClassCode,
			String nameEt, String code, Pageable pageable);
	Page<Classifier> findAllByMainClassCodeAndNameEnContainingIgnoreCaseAndValueStartingWithIgnoreCase(String mainClassCode,
			String nameEn, String code, Pageable pageable);
	Page<Classifier> findAllByMainClassCodeAndCodeContaining(String mainClassCode, String code, Pageable pageable);

	List<Classifier> findByCode(String code);

	List<Classifier> findTop20ByNameEtStartingWithIgnoreCaseAndMainClassCodeOrderByNameEtAsc(String nameEt, String mainClassCode);
	List<Classifier> findTop20ByNameEnStartingWithIgnoreCaseAndMainClassCodeOrderByNameEnAsc(String nameEn, String mainClassCode);

	List<Classifier> findAllByMainClassCodeOrderByCode(String mainClassCode);

	@CacheEvict(cacheNames = "classifier", allEntries = true)
	List<Classifier> removeByCode(String code);

	@Query(value = "select * from classifier as c inner join classifier_connect as cc on c.code = cc.connect_classifier_code where cc.classifier_code = ?1", nativeQuery = true)
	List<Classifier> findParents(String code);

	@Query(value = "select * from classifier as c inner join classifier_connect as cc on c.code = cc.connect_classifier_code where cc.classifier_code = ?1 and main_class_code = ?2", nativeQuery = true)
	List<Classifier> findParentsByMainClassifier(String code, String parentsMainClassifierCode);

	@Query(value = "select * from classifier where code in(select classifier_code from classifier_connect where connect_classifier_code = ?1) order by name_et", nativeQuery = true)
	List<Classifier> findChildren(String code);
	
	@Query(value = 
	        "select * from classifier as a where a.code in "
	        + "(select connect_classifier_code from classifier_connect where classifier_code in "
	            + "(select connect_classifier_code from classifier_connect where classifier_code in "
	                + "(select classifier_code from classifier_connect where connect_classifier_code = ?1)))"
	        + " and a.main_class_code = 'ISCED_VALD'", nativeQuery = true)
	List<Classifier> findAreasOfStudyByGroupOfStudy(String code);

}
