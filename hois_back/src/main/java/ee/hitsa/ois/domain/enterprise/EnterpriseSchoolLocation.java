package ee.hitsa.ois.domain.enterprise;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class EnterpriseSchoolLocation extends BaseEntityWithId {
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = true, updatable = true)
	@JsonIgnore
    private EnterpriseSchool enterpriseSchool;
	@ManyToOne(fetch = FetchType.LAZY)
    private Classifier country;
	@ManyToOne(fetch = FetchType.LAZY)
    private Classifier language;
	private String nameEt;
	private String addressOid;
	private String addressAds;
	private String address;
	
	public Classifier getCountry() {
		return country;
	}
	
	public void setCountry(Classifier country) {
		this.country = country;
	}
	
	public Classifier getLanguage() {
		return language;
	}
	
	public void setLanguage(Classifier language) {
		this.language = language;
	}
	
	public String getAddressOid() {
		return addressOid;
	}
	public void setAddressOid(String addressOid) {
		this.addressOid = addressOid;
	}
	
	public EnterpriseSchool getEnterpriseSchool() {
		return enterpriseSchool;
	}
	public void setEnterpriseSchool(EnterpriseSchool enterpriseSchool) {
		this.enterpriseSchool = enterpriseSchool;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressAds() {
		return addressAds;
	}

	public void setAddressAds(String addressAds) {
		this.addressAds = addressAds;
	}

	public String getNameEt() {
		return nameEt;
	}

	public void setNameEt(String nameEt) {
		this.nameEt = nameEt;
	}
	
}
