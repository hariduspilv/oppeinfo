package ee.hitsa.ois.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.Hibernate;

@MappedSuperclass
/**
 * TODO: add equals() and hashCode()
 * Currently these method cause errors while changing list of StateCurriculumModuleOccupations for some reason
 */
public abstract class BaseEntityWithId extends BaseEntity {
    private static final long serialVersionUID = 6764182316089816485L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id == null ? 31 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || id == null || !Hibernate.getClass(this).equals(Hibernate.getClass(obj))) {
            return false;
        }

        return id.equals(((BaseEntityWithId) obj).id);
    }
}
