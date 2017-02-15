package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Subject;
import ee.hitsa.ois.domain.SubjectLanguage;

import java.util.Set;
import java.util.stream.Collectors;

public class SubjectUtil {

    public static Set<Classifier> getLanguages(Subject subject) {
        return subject.getSubjectLanguages().stream().map(SubjectLanguage::getLanguage).collect(Collectors.toSet());
    }
}
