package ee.hitsa.ois.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.protocol.ProtocolVdata;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalOccupationModuleTheme;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.VocationalGradeType;

public abstract class ModuleProtocolGradeUtil {
    
    private static final int SCALE = 5;
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    /**
     * Threshold for not distinctive assessment (A/MA).
     * If average grade is equal to this or larger, then grade is positive (A)
     */
    private static final BigDecimal NOT_DISTINCTIVE_POSITIVE_THRESHOLD = BigDecimal.valueOf(2.5);
    
    public static OccupationalGrade calculateGrade(ProtocolStudent ps) {
        boolean isDisinctive = isDistinctive(ps.getProtocol().getProtocolVdata());
        List<JournalStudent> journalStudents = getJournalStudents(ps);
        Long occupationModule = 
                EntityUtil.getId(ps.getProtocol().getProtocolVdata().getCurriculumVersionOccupationModule());
        journalStudents = getJournalStudentsForThisModule(journalStudents, occupationModule);
        List<JournalEntryStudent> finalResults = getFinalResults(journalStudents);

        BigDecimal average = calculateAverage(finalResults);
        return isDisinctive ? distinctiveGrade(average) : notDistinctiveGrade(average);        
    }

    private static boolean isDistinctive(ProtocolVdata protocolVdata) {
        return ClassifierUtil.equals(VocationalGradeType.KUTSEHINDAMISVIIS_E, 
                protocolVdata.getCurriculumVersionOccupationModule().getAssessment());
    }

    private static List<JournalStudent> getJournalStudents(ProtocolStudent ps) {
        return ps.getStudent().getJournalStudents().stream().collect(Collectors.toList());
    }
    
    private static List<JournalStudent> getJournalStudentsForThisModule(
            List<JournalStudent> journalStudents, Long occupationModule) {
        return journalStudents.stream()
                .filter(js -> js.getJournal().getJournalOccupationModuleThemes().stream()
                        .anyMatch(jt -> 
                        EntityUtil.getId(jt.getCurriculumVersionOccupationModuleTheme().getModule())
                                .equals(occupationModule))).collect(Collectors.toList());
    }
    
    private static boolean isFinalResult(JournalEntryStudent jes) {
        return ClassifierUtil.equals(JournalEntryType.SISSEKANNE_L, jes.getJournalEntry().getEntryType());
    }
    
    private static List<JournalEntryStudent> getFinalResults(List<JournalStudent> results) {
        ArrayList<JournalEntryStudent> finalResults = new ArrayList<>();
        for(JournalStudent js : results) {
            finalResults.addAll(js.getJournalEntryStudents());
        }
        return finalResults.stream().filter(jes -> isFinalResult(jes))
                .filter(jes -> jes.getGrade() != null).collect(Collectors.toList());
    }

    private static BigDecimal calculateAverage(List<JournalEntryStudent> finalResults) {
        
        BigDecimal total = BigDecimal.ZERO;
        
        for(JournalEntryStudent jes : finalResults) {
            String grade = EntityUtil.getNullableCode(jes.getGrade());
            List<BigDecimal> proportions = StreamUtil
                    .toMappedList(ModuleProtocolGradeUtil::getProportion, 
                                  ModuleProtocolGradeUtil.getThemes(jes));
            total = add(total, proportions, grade);
        }
        return total;
    }
    
    private static BigDecimal getProportion(JournalOccupationModuleTheme t) {
        return t.getCurriculumVersionOccupationModuleTheme().getProportion().divide(HUNDRED, SCALE, BigDecimal.ROUND_HALF_UP);
    }
    
    private static Stream<JournalOccupationModuleTheme> getThemes(JournalEntryStudent jes) {
        return jes.getJournalEntry().getJournal().getJournalOccupationModuleThemes().stream()
                .filter(ModuleProtocolGradeUtil::notNullProportion);
    }
    
    private static boolean notNullProportion(JournalOccupationModuleTheme t) {
        return t.getCurriculumVersionOccupationModuleTheme().getProportion() != null;
    }
    
    public static BigDecimal add(BigDecimal total, List<BigDecimal> proportions, String grade) {
        BigDecimal sum = total;
        if(!CollectionUtils.isEmpty(proportions)){
            BigDecimal proportion = proportions.stream().reduce((c, summ) -> c.add(summ)).get();
            sum = sum.add(proportion.multiply(getMark(grade)));
        }
        return sum;
    }

    public static BigDecimal getMark(String grade) {
        return BigDecimal.valueOf(OccupationalGrade.valueOf(grade).getMark());
    }
    
    public static OccupationalGrade notDistinctiveGrade(BigDecimal average) {
        return positiveNotDistinctiveGrade(average) ? 
                OccupationalGrade.KUTSEHINDAMINE_A : OccupationalGrade.KUTSEHINDAMINE_MA;
    }
    
    public static boolean positiveNotDistinctiveGrade(BigDecimal average) {
        return average.compareTo(NOT_DISTINCTIVE_POSITIVE_THRESHOLD) >= 0;
    }

    public static OccupationalGrade distinctiveGrade(BigDecimal average) {
        int grade = average.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        
        if(grade < 3) {
            return OccupationalGrade.KUTSEHINDAMINE_2;
        } else if (grade == 3) {
            return OccupationalGrade.KUTSEHINDAMINE_3;
        } else if (grade == 4) {
            return OccupationalGrade.KUTSEHINDAMINE_4;
        }
        return OccupationalGrade.KUTSEHINDAMINE_5;
    }
}
