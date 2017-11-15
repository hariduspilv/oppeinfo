package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;

public interface CurriculumVersionHigherModuleSubjectRepository 
    extends JpaRepository<CurriculumVersionHigherModuleSubject, Long>, 
        JpaSpecificationExecutor<CurriculumVersionHigherModuleSubject>{

}
