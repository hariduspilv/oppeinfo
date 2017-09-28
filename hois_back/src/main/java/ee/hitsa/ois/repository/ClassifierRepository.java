package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.Classifier;

public interface ClassifierRepository extends JpaRepository<Classifier, String>, JpaSpecificationExecutor<Classifier> {

	@Query(value = "select * from classifier as c inner join classifier_connect as cc on c.code = cc.connect_classifier_code where cc.classifier_code = ?1", nativeQuery = true)
	List<Classifier> findParents(String code);

	@Query(value = "select * from classifier as c inner join classifier_connect as cc on c.code = cc.connect_classifier_code where cc.classifier_code = ?1 and main_class_code = ?2", nativeQuery = true)
	List<Classifier> findParentsByMainClassifier(String code, String parentsMainClassifierCode);

	@Query(value = "select * from classifier where code in (select classifier_code from classifier_connect where connect_classifier_code = ?1) order by name_et", nativeQuery = true)
	List<Classifier> findChildren(String code);

	@Query(value =
	        "select * from classifier as a where a.code in "
	        + "(select connect_classifier_code from classifier_connect where classifier_code in "
	            + "(select connect_classifier_code from classifier_connect where classifier_code in "
	                + "(select classifier_code from classifier_connect where connect_classifier_code = ?1)))"
	        + " and a.main_class_code = 'ISCED_VALD' and is_higher", nativeQuery = true)
	List<Classifier> findAreasOfStudyByGroupOfStudy(String code);

    List<Classifier> findAllByMainClassCode(String mainClassCode);

    Classifier findByValueAndMainClassCode(String value, String mainClassCode);
}
