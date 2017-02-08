package ee.hitsa.ois.repository;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ee.hitsa.ois.domain.ClassifierConnect;

public interface ClassifierConnectRepository extends JpaRepository<ClassifierConnect, String>  {

	public Page<ClassifierConnect> findAll(Specification<ClassifierConnect> spec, Pageable pageable);

	@Transactional
	void removeAllByClassifierCodeAndConnectClassifierCode(String classifierCode, String connectClassifierCode);

	ArrayList<ClassifierConnect> findAllByClassifierCode(String classifierCode);

	@Transactional
	void removeAllByClassifierCodeOrConnectClassifierCode(String classifierCode,String connectClassifierCode);

	@Transactional
	void removeAllByClassifierCode(String classifierCode);

	@Modifying
	@Transactional
	@Query(value =
	    "insert into "
	  + "classifier_connect(classifier_code, connect_classifier_code, inserted, version, main_classifier_code) "
	  + "values(:classifierCode, :connectClassifierCode, current_timestamp, 0, :mainClassifierCode)", nativeQuery = true)
	public void saveNewConnection (@Param("classifierCode") String classifierCode,
			                       @Param("connectClassifierCode") String connectClassifierCode,
			                       @Param("mainClassifierCode") String mainClassifierCode);

    public List<ClassifierConnect> findAll(Specification<ClassifierConnect> specification, Sort sort);
}
