package ee.hitsa.ois.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.util.EntityUtil;

@Entity
@IdClass(ClassifierConnectPk.class)
public class ClassifierConnect extends BaseEntity {

    private static final long serialVersionUID = -76010744797373856L;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier classifier;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier connectClassifier;

    private String mainClassifierCode;

    public String getMainClassifierCode() {
        return mainClassifierCode;
    }

    public void setMainClassifierCode(String mainClassifierCode) {
        this.mainClassifierCode = mainClassifierCode;
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
    }

    public Classifier getConnectClassifier() {
        return connectClassifier;
    }

    public void setConnectClassifier(Classifier connectClassifier) {
        this.connectClassifier = connectClassifier;
    }

    @Override
    public String toString() {
        return "ClassifierConnect [classCode=" + EntityUtil.getCode(classifier) + ", connectClassCode="
                + EntityUtil.getCode(connectClassifier) + "]";
    }
}

class ClassifierConnectPk implements Serializable {
    private static final long serialVersionUID = 2200613550126479766L;
    Classifier classifier;
    Classifier connectClassifier;

    ClassifierConnectPk() {
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
    }

    public Classifier getConnectClassifier() {
        return connectClassifier;
    }

    public void setConnectClassifier(Classifier connectClassifier) {
        this.connectClassifier = connectClassifier;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((classifier == null) ? 0 : classifier.hashCode());
        result = prime * result + ((connectClassifier == null) ? 0 : connectClassifier.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        // TODO does not work with hibernate proxy
        if (getClass() != obj.getClass())
            return false;
        ClassifierConnectPk other = (ClassifierConnectPk) obj;
        if (classifier == null) {
            if (other.classifier != null)
                return false;
        } else if (!classifier.equals(other.classifier))
            return false;
        if (connectClassifier == null) {
            if (other.connectClassifier != null)
                return false;
        } else if (!connectClassifier.equals(other.connectClassifier))
            return false;
        return true;
    }

}
