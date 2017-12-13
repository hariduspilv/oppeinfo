package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.teacher.TeacherMobility;

public interface TeacherMobilityRepository extends JpaRepository<TeacherMobility, Long> {
}
