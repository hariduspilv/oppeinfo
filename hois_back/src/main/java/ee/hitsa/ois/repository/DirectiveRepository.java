package ee.hitsa.ois.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.directive.Directive;

public interface DirectiveRepository extends JpaRepository<Directive, Long> {

    Boolean existsByCanceledDirectiveId(Long canceledDirectiveId);

    // fixme: instead of ConfirmDateIsNotNull - DirectiveNrIsNotNull ?
    List<Directive> findDistinctBySchool_IdAndConfirmDateGreaterThanEqualAndConfirmDateLessThanEqualAndType_CodeEqualsAndConfirmDateIsNotNull(Long schoolId, LocalDate from, LocalDate thru, String type);
}
