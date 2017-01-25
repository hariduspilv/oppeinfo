package ee.hitsa.ois.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.DirectiveCoordinator;
import ee.hitsa.ois.web.dto.DirectiveCoordinatorDto;

public interface DirectiveCoordinatorRepository extends JpaRepository<DirectiveCoordinator, Long> {

    @Query("select new ee.hitsa.ois.web.dto.DirectiveCoordinatorDto(dc.id, dc.name, dc.idCode, dc.version) from DirectiveCoordinator dc where dc.school.id=?1")
    Page<DirectiveCoordinatorDto> findAllBySchool_id(Long schoolId, Pageable pageable);
}
