package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.timetable.TimetableEvent;

public interface TimetableEventRepository extends JpaRepository<TimetableEvent, Long> {

}
