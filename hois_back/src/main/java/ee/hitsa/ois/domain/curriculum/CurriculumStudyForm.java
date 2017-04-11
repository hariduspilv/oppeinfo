package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class CurriculumStudyForm extends BaseEntityWithId {

	private static final long serialVersionUID = 6613489310489701663L;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Classifier studyForm;

    public CurriculumStudyForm() {
    }

    public CurriculumStudyForm(Classifier studyForm) {
        this.studyForm = studyForm;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
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
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        }

        CurriculumStudyForm other = (CurriculumStudyForm) obj;
        if (studyForm == null) {
            if (other.studyForm != null)
                return false;
        } else if (!studyForm.equals(other.studyForm))
            return false;
        return true;
    }

}
