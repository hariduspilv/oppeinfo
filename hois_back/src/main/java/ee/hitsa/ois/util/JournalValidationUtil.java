package ee.hitsa.ois.util;

import java.util.Optional;

import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public abstract class JournalValidationUtil extends JournalUtil {
    
    public static void asssertCanBeConfirmed(HoisUserDetails user, Journal journal) {
        if(!canBeConfirmed(user, journal)) {
            throw new ValidationFailedException("cannot be confirmed");
        }
    }
    
    public static void asssertCanBeUnconfirmed(HoisUserDetails user, Journal journal) {
        if(!canBeUnconfirmed(user, journal)) {
            throw new ValidationFailedException("cannot be unconfirmed");
        }
    }
    
    public static void assertIsConfirmer(HoisUserDetails user, Journal journal) {
        if (user.isTeacher()) {
            Optional<JournalTeacher> teacher =
                    journal.getJournalTeachers().stream().filter(it -> EntityUtil.getId(it.getTeacher()).equals(user.getTeacherId())).findFirst();
            if (!teacher.isPresent() || !Boolean.TRUE.equals(teacher.get().getIsConfirmer())) {
                throw new ValidationFailedException("journal.messages.teacherNotAllowedToChangeEndDate");
            }
        }
    }

    public static void assertAddStudentsToJournal(HoisUserDetails user, Journal journal) {
        if (user.isTeacher() && !CollectionUtils.isEmpty(journal.getJournalEntries())) {
            throw new ValidationFailedException("journal.messages.addingStudentIsNotAllowed");
        }
    }

    public static void assertRemoveStudentsFromJournal(HoisUserDetails user, Journal journal) {
        if (user.isTeacher() && !CollectionUtils.isEmpty(journal.getJournalEntries())) {
            throw new ValidationFailedException("journal.messages.removingStudentIsNotAllowed");
        }
    }

}
