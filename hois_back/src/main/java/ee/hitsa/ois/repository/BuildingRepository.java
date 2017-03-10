package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.Building;

public interface BuildingRepository extends JpaRepository<Building, Long> {

}
