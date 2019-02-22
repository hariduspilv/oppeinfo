package ee.hitsa.ois.domain.enterprise;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class EnterpriseSchoolIscedClass extends BaseEntityWithId {
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = true, updatable = true)
	@JsonIgnore
    private EnterpriseSchool enterpriseSchool;
	@ManyToOne(fetch = FetchType.LAZY)
	private Classifier iscedClass;
	private Integer places;
	private String addInfo;
	
	public void setEnterpriseSchool(EnterpriseSchool enterpriseSchool) {
		this.enterpriseSchool = enterpriseSchool;
	}
	
	public EnterpriseSchool getEnterpriseSchool() {
		return this.enterpriseSchool;
	}

	public Classifier getIscedClass() {
		return iscedClass;
	}

	public void setIscedClass(Classifier iscedClass) {
		this.iscedClass = iscedClass;
	}

	public Integer getPlaces() {
		return places;
	}

	public void setPlaces(Integer places) {
		this.places = places;
	}

	public String getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}
}
