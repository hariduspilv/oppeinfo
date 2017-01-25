package ee.hitsa.ois.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.Building;
import ee.hitsa.ois.web.dto.BuildingDto;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    @Query("select new ee.hitsa.ois.web.dto.BuildingDto(b.id, b.code, b.name, b.address, b.version) from Building b where b.school.id=?1")
    Page<BuildingDto> findAllBySchool_id(Long schoolId, Pageable pageable);
}
