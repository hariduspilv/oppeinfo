package ee.hitsa.ois.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.web.dto.directive.DirectiveCoordinatorDto;

public interface DirectiveCoordinatorRepository extends JpaRepository<DirectiveCoordinator, Long> {

    @Query("select new ee.hitsa.ois.web.dto.directive.DirectiveCoordinatorDto(dc.id, dc.name, dc.idcode, dc.version, dc.isDirective, dc.isCertificate, dc.isCertificateDefault) from DirectiveCoordinator dc where dc.school.id=?1")
    Page<DirectiveCoordinatorDto> findAllBySchool_id(Long schoolId, Pageable pageable);
}
