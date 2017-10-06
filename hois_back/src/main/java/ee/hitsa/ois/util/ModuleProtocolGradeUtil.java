package ee.hitsa.ois.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.protocol.ProtocolVdata;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.VocationalGradeType;

public abstract class ModuleProtocolGradeUtil {
    
    
    public static OccupationalGrade calculateGrade(ProtocolStudent ps) {
        boolean isDisinctive = isDistinctive(ps.getProtocol().getProtocolVdata());
        if(notAddedToAnyJournal(ps)) {
            return null;
        }
        List<JournalStudent> journalStudents = getJournalStudentsForThisStudyYear(ps);
        Long occupationModule = EntityUtil.getId(ps.getProtocol().getProtocolVdata().getCurriculumVersionOccupationModule());
        journalStudents = getJournalStudentsForThisModule(journalStudents, occupationModule);
        if(anyJournalFinalResultMissing(journalStudents)) {
            return null;
        }
        List<JournalEntryStudent> finalResults = getFinalResults(journalStudents);
        return isDisinctive ? calculateAverage(finalResults) : calculateIsPassed(finalResults);        
    }

    private static boolean isDistinctive(ProtocolVdata protocolVdata) {
        return ClassifierUtil.equals(VocationalGradeType.KUTSEHINDAMISVIIS_E, 
                protocolVdata.getCurriculumVersionOccupationModule().getAssessment());
    }
    
    private static boolean notAddedToAnyJournal(ProtocolStudent ps) {
        return CollectionUtils.isEmpty(ps.getStudent().getJournalStudents());
    }
    
    private static List<JournalStudent> getJournalStudentsForThisStudyYear(ProtocolStudent ps) {
        Long protocolStudyYearId = EntityUtil.getId(ps.getProtocol().getProtocolVdata()
                .getStudyYear());
        return ps.getStudent().getJournalStudents().stream()
                .filter(js -> EntityUtil.getId(js.getJournal().getStudyYear())
                        .equals(protocolStudyYearId)).collect(Collectors.toList());
    }
    
    private static List<JournalStudent> getJournalStudentsForThisModule(List<JournalStudent> journalStudents, Long occupationModule) {
        return journalStudents.stream()
                .filter(js -> js.getJournal().getJournalOccupationModuleThemes().stream()
                        .anyMatch(jt -> EntityUtil.getId(jt.getCurriculumVersionOccupationModuleTheme().getModule())
                                .equals(occupationModule))).collect(Collectors.toList());
    }
    
    private static boolean anyJournalFinalResultMissing (List<JournalStudent> journalStudents) {
        int journalsCount = getJournalsCount(journalStudents);
        int journalsWithPositiveResults = getJournalsCountWithPositiveFinalGrade(journalStudents);
        return journalsCount == journalsWithPositiveResults;
    }

    private static int getJournalsCount(List<JournalStudent> journalStudents) {
        Set<Long> set = new HashSet<>();
        for(JournalStudent js : journalStudents) {
            set.add(EntityUtil.getId(js.getJournal()));
        }
        return set.size();
    }
    
    private static int getJournalsCountWithPositiveFinalGrade(List<JournalStudent> journalStudents) {
        Set<Long> set = new HashSet<>();
        for(JournalStudent js : journalStudents) {
            if(hasPositiveFinalGrade(js.getJournalEntryStudents())) {
                set.add(EntityUtil.getId(js.getJournal()));
            }
        }
        return set.size();
    }

    private static boolean hasPositiveFinalGrade(Set<JournalEntryStudent> journalEntryStudents) {
        return journalEntryStudents.stream().anyMatch(jes -> isFinalResult(jes) && isPositiveGrade(jes));
    }
    
    private static boolean isFinalResult(JournalEntryStudent jes) {
        return ClassifierUtil.equals(JournalEntryType.SISSEKANNE_L, jes.getJournalEntry().getEntryType());
    }

    private static boolean isPositiveGrade(JournalEntryStudent jes) {
        return jes.getGrade() != null && OccupationalGrade.isPositive(EntityUtil.getCode(jes.getGrade()));
    }
    
    private static List<JournalEntryStudent> getFinalResults(List<JournalStudent> results) {
        ArrayList<JournalEntryStudent> finalResults = new ArrayList<>();
        for(JournalStudent js : results) {
            finalResults.addAll(js.getJournalEntryStudents());
        }
        return finalResults.stream().filter(jes -> isFinalResult(jes)).collect(Collectors.toList());
    }

    private static OccupationalGrade calculateAverage(List<JournalEntryStudent> finalResults) {
        return OccupationalGrade.KUTSEHINDAMINE_5;
    }

    private static OccupationalGrade calculateIsPassed(List<JournalEntryStudent> finalResults) {
        return allGradesPositive(finalResults) ? OccupationalGrade.KUTSEHINDAMINE_A : OccupationalGrade.KUTSEHINDAMINE_MA;
    }
    
    private static boolean allGradesPositive(List<JournalEntryStudent> finalResults) {
        return finalResults.stream().allMatch(r -> isPositiveGrade(r));
    }

}
