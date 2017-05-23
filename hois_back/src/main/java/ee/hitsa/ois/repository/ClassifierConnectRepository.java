package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ee.hitsa.ois.domain.ClassifierConnect;

public interface ClassifierConnectRepository extends JpaRepository<ClassifierConnect, String>, JpaSpecificationExecutor<ClassifierConnect>  {

	void removeAllByClassifierCodeAndConnectClassifierCode(String classifierCode, String connectClassifierCode);

	List<ClassifierConnect> findAllByClassifierCode(String classifierCode);

	void removeAllByClassifierCodeOrConnectClassifierCode(String classifierCode,String connectClassifierCode);

	void removeAllByClassifierCode(String classifierCode);

	@Modifying
	@Query(value =
	    "insert into "
	  + "classifier_connect(classifier_code, connect_classifier_code, inserted, version, main_classifier_code) "
	  + "values(:classifierCode, :connectClassifierCode, current_timestamp, 0, :mainClassifierCode)", nativeQuery = true)
	public void saveNewConnection (@Param("classifierCode") String classifierCode,
			                       @Param("connectClassifierCode") String connectClassifierCode,
			                       @Param("mainClassifierCode") String mainClassifierCode);
}
