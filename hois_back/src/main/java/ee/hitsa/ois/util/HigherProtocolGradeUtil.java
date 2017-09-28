package ee.hitsa.ois.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.MidtermTask;
import ee.hitsa.ois.domain.MidtermTaskStudentResult;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.SubjectAssessment;

public abstract class HigherProtocolGradeUtil {

    public static HigherAssessment calculateGrade(ProtocolStudent ps) {
        boolean isDistinctiveAssessment = isDistinctive(ps.getProtocol());

        List<MidtermTaskStudentResult> results = getStudentResults(ps);
        if (anyMidtermTaksResultMissing(ps, results)) {
            return getNegativeScore(isDistinctiveAssessment);
        }
        if(noTaskWithPoints(ps)) {
            return isDistinctiveAssessment ? null : HigherAssessment.KORGHINDAMINE_A;
        }
        BigDecimal finalScore = BigDecimal.ZERO;
        for (MidtermTaskStudentResult result : results) {
            MidtermTask task = result.getMidtermTask();
            if (hasThreshold(task) && !threshHoldExceeded(result)) {
                return getNegativeScore(isDistinctiveAssessment);
            }
            if(!MidtermTaskUtil.resultIsText(result.getMidtermTask())) {
                finalScore = finalScore.add(getTaskScore(result));
            }
        }
        finalScore = finalScore.setScale(0, BigDecimal.ROUND_HALF_UP); 
        return getFinalGrade(finalScore.intValue(), isDistinctiveAssessment);
    }

    private static boolean noTaskWithPoints(ProtocolStudent ps) {
        return ps.getProtocol().getProtocolHdata().getSubjectStudyPeriod()
                .getMidtermTasks().stream()
                .noneMatch(t -> !MidtermTaskUtil.resultIsText(t));
    }

    public static boolean isDistinctive(Protocol protocol) {
        Classifier assessment = protocol.getProtocolHdata().getSubjectStudyPeriod().getSubject()
                .getAssessment();
        return !ClassifierUtil.equals(SubjectAssessment.HINDAMISVIIS_A, assessment);
    }

    private static List<MidtermTaskStudentResult> getStudentResults(ProtocolStudent ps) {
        List<MidtermTask> tasks = ps.getProtocol().getProtocolHdata().getSubjectStudyPeriod().getMidtermTasks();
        List<MidtermTaskStudentResult> results = getAllStudentResults(tasks);
        results = filterThisStudentsResults(results, ps);
        return results;
    }

    private static List<MidtermTaskStudentResult> filterThisStudentsResults(List<MidtermTaskStudentResult> results,
            ProtocolStudent ps) {
        Long studentId = EntityUtil.getId(ps.getStudent());
        return results.stream().filter(
                sr -> EntityUtil.getId(sr.getDeclarationSubject().getDeclaration().getStudent())
                .equals(studentId)).collect(Collectors.toList());
    }

    private static List<MidtermTaskStudentResult> getAllStudentResults(List<MidtermTask> tasks) {
        List<MidtermTaskStudentResult> results = new ArrayList<>();
        for (MidtermTask task : tasks) {
            results.addAll(task.getStudentResults());
        }
        return results;
    }

    private static boolean anyMidtermTaksResultMissing(ProtocolStudent ps, List<MidtermTaskStudentResult> results) {
        List<MidtermTask> tasks = ps.getProtocol().getProtocolHdata().getSubjectStudyPeriod().getMidtermTasks();
        return results.size() != tasks.size();
    }

    private static HigherAssessment getNegativeScore(boolean isDistinctiveAssessment) {
        return isDistinctiveAssessment ? HigherAssessment.KORGHINDAMINE_0 : HigherAssessment.KORGHINDAMINE_M;
    }

    private static boolean hasThreshold(MidtermTask task) {
        return Boolean.TRUE.equals(task.getThreshold()) && task.getThresholdPercentage() != null;
    }

    private static boolean threshHoldExceeded(MidtermTaskStudentResult result) {
        BigDecimal maxPoints = result.getMidtermTask().getMaxPoints();
        BigDecimal threshold = BigDecimal.valueOf(result.getMidtermTask().getThresholdPercentage().longValue());
        BigDecimal points = result.getPoints();
        return points.compareTo(maxPoints.multiply(threshold).divide(BigDecimal.valueOf(100))) >= 0;
    }

    private static BigDecimal getTaskScore(MidtermTaskStudentResult result) {
        BigDecimal maxPoints = result.getMidtermTask().getMaxPoints();
        BigDecimal percenage = BigDecimal.valueOf(result.getMidtermTask().getPercentage().longValue());
        BigDecimal points = result.getPoints();
        return points.divide(maxPoints).multiply(percenage);
    }

    private static HigherAssessment getFinalGrade(int finalScore, boolean isDistinctiveAssessment) {
        // TODO meaningful names for magic constants
        if (isDistinctiveAssessment) {
            if (finalScore < 51) {
                return HigherAssessment.KORGHINDAMINE_0;
            } else if (isBetween(finalScore, 51, 60)) {
                return HigherAssessment.KORGHINDAMINE_1;
            } else if (isBetween(finalScore, 61, 70)) {
                return HigherAssessment.KORGHINDAMINE_2;
            } else if (isBetween(finalScore, 71, 80)) {
                return HigherAssessment.KORGHINDAMINE_3;
            } else if (isBetween(finalScore, 81, 90)) {
                return HigherAssessment.KORGHINDAMINE_4;
            }
            return HigherAssessment.KORGHINDAMINE_5;
        }
        return finalScore > 50 ? HigherAssessment.KORGHINDAMINE_A : HigherAssessment.KORGHINDAMINE_M;
    }

    private static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    public static List<String> getPossibleGrades(Protocol protocol) {
        Boolean isDistinctive = Boolean.valueOf(HigherProtocolGradeUtil.isDistinctive(protocol));
        return Arrays.stream(HigherAssessment.values())
                .filter(val -> val.getIsDistinctive() == null || val.getIsDistinctive()
                .equals(isDistinctive)).map(Enum::name).collect(Collectors.toList());
    }
}
