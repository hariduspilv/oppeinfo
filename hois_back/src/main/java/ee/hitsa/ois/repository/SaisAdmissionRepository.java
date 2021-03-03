package ee.hitsa.ois.repository;

import ee.hitsa.ois.domain.sais.SaisAdmission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SaisAdmissionRepository extends JpaRepository<SaisAdmission, Long> {

    List<SaisAdmission> findByCode(String code);
    List<SaisAdmission> findByCodeAndCurriculumVersionCurriculumSchoolId(String code, Long schoolId);
    @Query("select sa from SaisAdmission sa where sa.code = :code " +
            "and sa.curriculumVersion.curriculum.school.id = :schoolId " +
            "and (sa.is_archived is null or sa.is_archived = false) " +
            "order by sa.id desc ")
    List<SaisAdmission> findFirstActiveSaisAdmissionByCodeAndSchoolId(
            @Param("code") String code, @Param("schoolId") Long schoolId, Pageable pageable);
    @Query("select sa from SaisAdmission sa where sa.saisId = :saisId " +
            "and sa.curriculumVersion.curriculum.school.id = :schoolId " +
            "and (sa.is_archived is null or sa.is_archived = false) " +
            "order by sa.id desc ")
    List<SaisAdmission> findFirstActiveSaisAdmissionBySaisIdAndSchoolId(
            @Param("saisId") String saisId, @Param("schoolId") Long schoolId, Pageable pageable);
    Optional<SaisAdmission> findById(Long id);
    SaisAdmission findFirstByCurriculumVersionIdOrderByIdDesc(Long id);

}
