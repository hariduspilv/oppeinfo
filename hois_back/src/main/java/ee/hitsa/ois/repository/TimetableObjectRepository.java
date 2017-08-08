package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.Timetable;
import ee.hitsa.ois.domain.timetable.TimetableObject;

public interface TimetableObjectRepository extends JpaRepository<TimetableObject, Long> {

    public TimetableObject findByJournalAndTimetable(Journal journal, Timetable timetable);

}
