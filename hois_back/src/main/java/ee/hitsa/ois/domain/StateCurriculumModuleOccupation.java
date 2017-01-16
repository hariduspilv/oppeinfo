package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * TODO:
 * Make bidirectional mapping in order to add stateCurriculumModuleId to equals() and hashCode().
 * It works fine now, but problems will occur in case StateCurriculumModuleOccupations of 
 * different StateCurriculumModules need to be in the same Set
 * 
 * PS: you are going to need following annotations:
 * @JsonManagedReference
 * @JsonBackReference
 * 
 * And do not forget to change StateCurriculumModule.setOccupations()!
 */
@Entity
public class StateCurriculumModuleOccupation extends BaseEntityWithId {

	private char type;
	@ManyToOne
	@JoinColumn(name = "occupation_code", referencedColumnName = "code")
	private Classifier occupation;

	public StateCurriculumModuleOccupation() {}
	
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

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "StateCurriculumModuleOccupation [id=" + this.getId() + ", type=" + type + ", occupation=" + occupation.getCode() + "]";
	}
}
