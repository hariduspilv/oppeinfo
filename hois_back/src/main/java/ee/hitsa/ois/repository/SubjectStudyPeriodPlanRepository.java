package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodPlan;

public interface SubjectStudyPeriodPlanRepository 
    extends JpaRepository<SubjectStudyPeriodPlan, Long>, JpaSpecificationExecutor<SubjectStudyPeriodPlan> {

}
