package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class CurriculumDepartment extends BaseEntityWithId {

	private static final long serialVersionUID = 3605519101805076842L;

	@ManyToOne(optional = false)
	private SchoolDepartment schoolDepartment;

	public CurriculumDepartment() {
    }

    public CurriculumDepartment(SchoolDepartment schoolDepartment) {
        this.schoolDepartment = schoolDepartment;
    }

    public SchoolDepartment getSchoolDepartment() {
		return schoolDepartment;
	}

	public void setSchoolDepartment(SchoolDepartment schoolDepartment) {
		this.schoolDepartment = schoolDepartment;
	}

    @Override
    public int hashCode() {
        return schoolDepartment.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        CurriculumDepartment other = (CurriculumDepartment) obj;
        if (schoolDepartment == null) {
            if (other.schoolDepartment != null)
                return false;
        } else if (!schoolDepartment.equals(other.schoolDepartment))
            return false;
        return true;
    }

}
