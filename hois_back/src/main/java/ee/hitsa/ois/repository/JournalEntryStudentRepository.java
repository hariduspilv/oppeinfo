package ee.hitsa.ois.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ee.hitsa.ois.domain.timetable.JournalEntryStudent;

public interface JournalEntryStudentRepository extends JpaRepository<JournalEntryStudent, Long> {

@Modifying
@Query(nativeQuery = true, value = ""
        + "update journal_entry_student "
        + "set absence_accepted = current_timestamp, "
        + "absence_code = :absence "
        + "where id in :studentEntries")
void acceptAbsences(@Param("absence") String absence, @Param("studentEntries") Set<Long> studentEntries);

@Modifying
@Query(nativeQuery = true, value = ""
        + "update journal_entry_student_lesson_absence "
        + "set absence_accepted = current_timestamp, "
        + "absence_code = :absence "
        + "where id in :lessonAbsenceIds")
void acceptAbsenceLessons(@Param("absence") String absence, @Param("lessonAbsenceIds") Set<Long> lessonAbsenceIds);


}
