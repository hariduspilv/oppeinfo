package ee.hitsa.ois.domain.subject;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
@Table(name = "subject_lang")
public class SubjectLanguage extends BaseEntityWithId {

    @JoinColumn(name = "lang_code")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier language;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Subject subject;

    public Classifier getLanguage() {
        return language;
    }

    public void setLanguage(Classifier language) {
        this.language = language;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
