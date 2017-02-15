package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.Building;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    @Query("select new ee.hitsa.ois.web.dto.AutocompleteResult(b.id, b.name, b.name) from Building b where b.school.id=?1")
    List<AutocompleteResult<Long>> findAllBySchool_id(Long schoolId);
}
