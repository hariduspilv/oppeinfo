package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.DirectiveCoordinatorDto;

public interface DirectiveCoordinatorRepository extends JpaRepository<DirectiveCoordinator, Long> {

    @Query("select new ee.hitsa.ois.web.dto.DirectiveCoordinatorDto(dc.id, dc.name, dc.idcode, dc.version, dc.isDirective, dc.isCertificate, dc.isCertificateDefault) from DirectiveCoordinator dc where dc.school.id=?1")
    Page<DirectiveCoordinatorDto> findAllBySchool_id(Long schoolId, Pageable pageable);

    @Query("select new ee.hitsa.ois.web.dto.AutocompleteResult(dc.id, dc.name, dc.name) from DirectiveCoordinator dc where dc.school.id=?1")
    List<AutocompleteResult<Long>> findAllForSelect(Long schoolId);

}
