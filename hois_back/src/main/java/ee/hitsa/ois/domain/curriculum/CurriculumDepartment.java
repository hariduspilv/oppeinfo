package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.school.SchoolDepartment;

@Entity
public class CurriculumDepartment extends BaseEntityWithId {

    private static final long serialVersionUID = 3605519101805076842L;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = false, updatable = false)
    private Curriculum curriculum;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SchoolDepartment schoolDepartment;

    public CurriculumDepartment() {
    }

    public CurriculumDepartment(SchoolDepartment schoolDepartment) {
        this.schoolDepartment = schoolDepartment;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
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
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        }

        CurriculumDepartment other = (CurriculumDepartment) obj;
        if (schoolDepartment == null) {
            if (other.schoolDepartment != null)
                return false;
        } else if (!schoolDepartment.equals(other.schoolDepartment))
            return false;
        return true;
    }
}
