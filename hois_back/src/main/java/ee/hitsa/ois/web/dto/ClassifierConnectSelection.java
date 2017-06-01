package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.ClassifierConnect;

public class ClassifierConnectSelection {

    private final ClassifierSelection classifier;
    private final ClassifierSelection connectClassifier;
    private final String mainClassifierCode;

    public ClassifierConnectSelection(Classifier classifier, Classifier connectClassifier,
            String mainClassifierCode) {
        this.classifier = ClassifierSelection.of(classifier);
        this.connectClassifier =  ClassifierSelection.of(connectClassifier);
        this.mainClassifierCode = mainClassifierCode;
    }

    public ClassifierConnectSelection(ClassifierSelection classifier, ClassifierSelection connectClassifier,
            String mainClassifierCode) {
        this.classifier = classifier;
        this.connectClassifier = connectClassifier;
        this.mainClassifierCode = mainClassifierCode;
    }

    public static ClassifierConnectSelection of(ClassifierConnect cc) {
        return new ClassifierConnectSelection(cc.getClassifier(), cc.getConnectClassifier(), cc.getMainClassifierCode());
    }

    public ClassifierSelection getClassifier() {
        return classifier;
    }
    public ClassifierSelection getConnectClassifier() {
        return connectClassifier;
    }
    public String getMainClassifierCode() {
        return mainClassifierCode;
    }



}
