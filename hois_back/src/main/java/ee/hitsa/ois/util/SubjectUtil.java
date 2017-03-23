package ee.hitsa.ois.util;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.SubjectLanguage;

public class SubjectUtil {

    public static Set<Classifier> getLanguages(Subject subject) {
        return subject.getSubjectLanguages().stream().map(SubjectLanguage::getLanguage).collect(Collectors.toSet());
    }

    public static String subjectName(String code, String name) {
        return String.format("%1$s - %2$s", code, name);
    }

    public static String subjectName(String code, String name, BigDecimal credits) {
        return String.format("%1$s - %2$s (%3$s EAP)", code, name, credits.toString());
    }
}
