package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.SubjectLanguage;

import java.util.Set;
import java.util.stream.Collectors;

public class SubjectUtil {

    public static Set<Classifier> getLanguages(Subject subject) {
        return subject.getSubjectLanguages().stream().map(SubjectLanguage::getLanguage).collect(Collectors.toSet());
    }

    public static String subjectName(String code, String name) {
        return String.format("%1$s - %2$s", code, name);
    }
}
