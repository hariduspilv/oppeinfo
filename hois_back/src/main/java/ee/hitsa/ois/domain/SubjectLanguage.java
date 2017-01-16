package ee.hitsa.ois.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "subject_lang")
public class SubjectLanguage extends BaseEntityWithId {

    @JoinColumn(name = "lang_code")
    @ManyToOne(optional = false)
    private Classifier language;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Subject subject;

    public SubjectLanguage() { }

    public SubjectLanguage(Classifier language, Subject subject) {
        setLanguage(language);
        setSubject(subject);
    }

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
