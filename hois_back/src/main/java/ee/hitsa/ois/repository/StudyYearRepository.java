package ee.hitsa.ois.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import ee.hitsa.ois.domain.StudyYear;

public interface StudyYearRepository extends JpaRepository<StudyYear, Long>, JpaSpecificationExecutor<StudyYear> {

    // todo: remove nativeQuery
    @Query(value = "select c.code, c.name_et, c.name_en, sy.id, sy.start_date, sy.end_date, sy.count " +
                        "from classifier c left outer join " +
            "(select y.id, y.start_date, y.end_date, y.year_code, count(p.study_year_id) " +
            "from study_year y left outer join study_period p on y.id = p.study_year_id " +
            "where y.school_id = ?1 group by y.id) sy on c.code = sy.year_code " +
            "where c.main_class_code = 'OPPEAASTA' order by c.code desc", nativeQuery = true)
    List<Object[]> findStudyYearsBySchool(Long schoolId);

}
