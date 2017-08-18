package ee.hitsa.ois.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum HigherAssessment {
    
    
    KORGHINDAMINE_0(Boolean.TRUE, Boolean.FALSE),
    KORGHINDAMINE_1(Boolean.TRUE, Boolean.TRUE),
    KORGHINDAMINE_2(Boolean.TRUE, Boolean.TRUE),
    KORGHINDAMINE_3(Boolean.TRUE, Boolean.TRUE),
    KORGHINDAMINE_4(Boolean.TRUE, Boolean.TRUE),
    KORGHINDAMINE_5(Boolean.TRUE, Boolean.TRUE),
    KORGHINDAMINE_A(Boolean.FALSE, Boolean.TRUE),
    KORGHINDAMINE_M(Boolean.FALSE, Boolean.FALSE),
    KORGHINDAMINE_MI(null, Boolean.FALSE);
    
    private final Boolean isDistinctive;
    private final Boolean isPositive;
 
    private HigherAssessment(Boolean isDistinctive, Boolean isPositive) {
        this.isDistinctive = isDistinctive;
        this.isPositive = isPositive;
    }

    public Boolean getIsDistinctive() {
        return isDistinctive;
    }

    public Boolean getIsPositive() {
        return isPositive;
    }

    public static boolean isPositive(String grade) {
        List<String> postiveGrades = Arrays.asList(HigherAssessment.values())
                .stream().filter(HigherAssessment::getIsPositive)
                .map(HigherAssessment::name).collect(Collectors.toList());
        return postiveGrades.contains(grade);
    }
}
