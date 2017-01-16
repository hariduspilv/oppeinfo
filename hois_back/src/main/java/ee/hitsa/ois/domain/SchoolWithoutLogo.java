package ee.hitsa.ois.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="SchoolWithoutLogo")
@Table(name="school")
public class SchoolWithoutLogo extends BaseEntityWithId {
    @Column(updatable = false)
    private String nameEt;

	public String getNameEt() {
		return nameEt;
	}
    
    
}
