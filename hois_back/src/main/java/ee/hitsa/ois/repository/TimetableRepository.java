package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.timetable.Timetable;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {

}
