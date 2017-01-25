package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ee.hitsa.ois.ClassifierJsonDeserializer;
import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
@Entity
@Table(name = "curriculum_study_lang")
public class CurriculumStudyLanguage extends BaseEntityWithId {

	private static final long serialVersionUID = 6613489310489701663L;

	public CurriculumStudyLanguage() {
    }

	public CurriculumStudyLanguage(Classifier studyLang) {
        this.studyLang = studyLang;
    }

    @ManyToOne(optional = false)
	@JsonDeserialize(using = ClassifierJsonDeserializer.class)
	private Classifier studyLang;

	public Classifier getStudyLang() {
		return studyLang;
	}

	public void setStudyLang(Classifier studyLang) {
		this.studyLang = studyLang;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime * ((studyLang == null) ? 0 : studyLang.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        CurriculumStudyLanguage other = (CurriculumStudyLanguage) obj;
        if (studyLang == null) {
            if (other.studyLang != null)
                return false;
        } else if (!studyLang.equals(other.studyLang))
            return false;
        return true;
    }


}
