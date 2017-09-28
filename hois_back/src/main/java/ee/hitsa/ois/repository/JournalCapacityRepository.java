package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.timetable.JournalCapacity;

public interface JournalCapacityRepository extends JpaRepository<JournalCapacity, Long> {
}
