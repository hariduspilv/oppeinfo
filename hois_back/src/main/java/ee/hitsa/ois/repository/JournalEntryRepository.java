package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.timetable.JournalEntry;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {

}
