package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.Declaration;

public interface DeclarationRepository extends JpaRepository<Declaration, Long>, JpaSpecificationExecutor<Declaration> {
    
    public Declaration findByStudentIdAndStudyPeriodId(Long studentId, Long studyPeriodId);
    
    @Query(nativeQuery = true, value = 
            "select exists"
            + "(select id from declaration "
            +  "where student_id = ?1 "
            +  "and study_period_id in "
            +   "(select id from study_period "
            +   "where end_date < current_date))")
    public Boolean studentHasPreviousDeclarations(Long studentId);

}
