package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ee.hitsa.ois.ClassifierJsonDeserializer;

@Entity
public class CurriculumStudyForm extends BaseEntityWithId {

	private static final long serialVersionUID = 6613489310489701663L;

	@ManyToOne(optional = false)
	@JsonDeserialize(using = ClassifierJsonDeserializer.class)
	private Classifier studyForm;

    public CurriculumStudyForm() {
    }

    public CurriculumStudyForm(Classifier studyForm) {
        this.studyForm = studyForm;
    }

    public Classifier getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(Classifier studyForm) {
        this.studyForm = studyForm;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime * ((studyForm == null) ? 0 : studyForm.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        CurriculumStudyForm other = (CurriculumStudyForm) obj;
        if (studyForm == null) {
            if (other.studyForm != null)
                return false;
        } else if (!studyForm.equals(other.studyForm))
            return false;
        return true;
    }

}
