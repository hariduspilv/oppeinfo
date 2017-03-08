package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.StudyPeriod;

public interface StudyPeriodRepository extends JpaRepository<StudyPeriod, Long>, JpaSpecificationExecutor<StudyPeriod> {

}