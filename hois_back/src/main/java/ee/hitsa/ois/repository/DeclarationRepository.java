package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.Declaration;

public interface DeclarationRepository extends JpaRepository<Declaration, Long>, JpaSpecificationExecutor<Declaration> {

    /**
     * Check if subject is graded in protocol or has graded midterm task
     */
    @Query(nativeQuery = true, value = "select exists(select * from protocol_student ps "
            + "join protocol p on p.id = ps.protocol_id "
            + "join protocol_hdata phd on p.id = phd.protocol_id "
            + "where ps.student_id = ?1 and phd.subject_study_period_id = ?2 "
            + "and (ps.grade_code is not null or (ps.add_info is not null and ps.add_info <> ''))) "
            + "or exists(select * from midterm_task_student_result r where r.declaration_subject_id = ?3)")
    boolean subjectAssessed(Long studentId, Long subjectStudyPeriodId, Long declarationSubjectId);

}
