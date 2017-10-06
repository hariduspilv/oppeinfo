package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;

public abstract class JournalUtil {
    
    public static boolean hasFinalEntry(Journal journal) {
        return journal.getJournalEntries().stream()
                .anyMatch(je -> ClassifierUtil
                        .equals(JournalEntryType.SISSEKANNE_L, je.getEntryType()));
    }
    
    public static boolean canBeConfirmed(HoisUserDetails user, Journal journal) {
        return (UserUtil.isSchoolAdmin(user, journal.getSchool()) || UserUtil.isTeacher(user, journal.getSchool())) && 
                ClassifierUtil.equals(JournalStatus.PAEVIK_STAATUS_T, journal.getStatus());
    }
    
    public static boolean canBeUnconfirmed(HoisUserDetails user, Journal journal) {
        return UserUtil.isSchoolAdmin(user, journal.getSchool())  && 
                ClassifierUtil.equals(JournalStatus.PAEVIK_STAATUS_K, journal.getStatus());
    }

}
