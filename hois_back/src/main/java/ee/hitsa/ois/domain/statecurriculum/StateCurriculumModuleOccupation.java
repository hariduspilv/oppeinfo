package ee.hitsa.ois.domain.statecurriculum;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

/**
 * TODO:
 * Make bidirectional mapping in order to add stateCurriculumModuleId to equals() and hashCode().
 * It works fine now, but problems will occur in case StateCurriculumModuleOccupations of 
 * different StateCurriculumModules need to be in the same Set
 * 
 * And do not forget to change StateCurriculumModule.setOccupations()!
 */
@Entity
public class StateCurriculumModuleOccupation extends BaseEntityWithId {

	private Character type;
	@ManyToOne(fetch = FetchType.LAZY)
	private Classifier occupation;

	public StateCurriculumModuleOccupation() {}
	
    public StateCurriculumModuleOccupation(Classifier occupation) {
        this.occupation = occupation;
        setType(Character.valueOf(occupation.getCode().charAt(0)));
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
        // TODO does not work with hibernate proxy
		if (getClass() != obj.getClass())
			return false;
		StateCurriculumModuleOccupation other = (StateCurriculumModuleOccupation) obj;
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

	public Character getType() {
		return type;
	}

	public void setType(Character type) {
		this.type = type;
	}
}
