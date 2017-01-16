package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import ee.hitsa.ois.domain.Building;

public interface BuildingRepository extends CrudRepository<Building, Long>, JpaSpecificationExecutor<Building> {

}
