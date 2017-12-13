package ee.hitsa.ois.util;

import java.math.BigDecimal;
import java.util.Set;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.SubjectLanguage;
import ee.hitsa.ois.enums.SubjectStatus;

public class SubjectUtil {

    public static Set<Classifier> getLanguages(Subject subject) {
        return StreamUtil.toMappedSet(SubjectLanguage::getLanguage, subject.getSubjectLanguages());
    }

    public static String subjectName(String code, String name, BigDecimal credits) {
        return String.format("%1$s - %2$s (%3$s EAP)", code, name, credits.toString());
    }
    
    public static String subjectName(String code, String name) {
        return String.format("%1$s (%2$s)", name, code);
    }
    
    public static boolean isActive(Subject subject) {
        return ClassifierUtil.equals(SubjectStatus.AINESTAATUS_K, subject.getStatus());
    }
}
