package ee.hitsa.ois.util;

import java.math.BigDecimal;

import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.enums.SubjectStatus;

public abstract class SubjectUtil {

    public static String subjectName(String code, String name, BigDecimal credits) {
        if (credits == null) {
            return subjectName(code, name);
        }
        return String.format("%1$s - %2$s (%3$s EAP)", code, name, credits.toString());
    }

    public static String subjectName(String code, String name) {
        return String.format("%1$s (%2$s)", name, code);
    }
    
    public static String subjectNameWithoutCode(String name, BigDecimal credits) {
        if (credits == null) {
            return name;
        }
        return String.format("%1$s (%2$s EAP)", name, credits);
    }

    public static boolean isActive(Subject subject) {
        return ClassifierUtil.equals(SubjectStatus.AINESTAATUS_K, subject.getStatus());
    }
}
