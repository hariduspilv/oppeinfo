package ee.hitsa.ois.domain.statecurriculum;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

/**
 * TODO:
 * Make bidirectional mapping in order to add stateCurriculumId to equals() and hashCode().
 * It works fine now, but problems will occur in case StateCurriculumOccupations of 
 * different StateCurriculums need to be in the same Set
 * 
 * And do not forget to change StateCurriculum.setOccupations()!
 */
@Entity
public class StateCurriculumOccupation extends BaseEntityWithId{

	@ManyToOne(fetch = FetchType.LAZY)
	private Classifier occupation;

	public StateCurriculumOccupation() {
	}
	
	public StateCurriculumOccupation(Classifier occupation) {
	    this.occupation = occupation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StateCurriculumOccupation other = (StateCurriculumOccupation) obj;
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation))
			return false;
		return true;
	}

	public Classifier getOccupation() {
		return occupation;
	}

	public void setOccupation(Classifier occupation) {
		this.occupation = occupation;
	}
}
