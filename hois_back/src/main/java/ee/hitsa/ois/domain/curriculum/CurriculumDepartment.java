package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.Hibernate;

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
    @JoinColumn(nullable = false, updatable = false)
    private SchoolDepartment schoolDepartment;

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
        }
        if (obj == null || !Hibernate.getClass(this).equals(Hibernate.getClass(obj))) {
            return false;
        }

        CurriculumDepartment other = (CurriculumDepartment) obj;
        return schoolDepartment == null ? other.schoolDepartment == null : schoolDepartment.equals(other.schoolDepartment);
    }
}
