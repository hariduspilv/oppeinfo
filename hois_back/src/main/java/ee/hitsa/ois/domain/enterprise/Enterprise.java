package ee.hitsa.ois.domain.enterprise;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Email;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Contract;

@Entity
public class Enterprise extends BaseEntityWithId {
	
	@OneToMany(cascade = CascadeType.DETACH, orphanRemoval = true)
    @JoinColumn(name = "enterprise_id", nullable = true, updatable = true)
    private List<Contract> contracts = new ArrayList<>();
    @Column(nullable = false)
    private String regCode;
    @Column(nullable = false)
    private String name;
    private String contactPersonName;
    private String contactPersonPhone;
    @Email
    private String contactPersonEmail;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier country;
    private Boolean isPerson;
    private String addressOid;
    private String addressAds;
    private String address;
    @OneToMany(cascade = CascadeType.DETACH, orphanRemoval = true)
    @JoinColumn(name = "enterprise_id", nullable = true, updatable = true)
    private List<EnterpriseSchool> enterpriseSchools = new ArrayList<>();
    private LocalDateTime ebusinessUpdated;
    
    public List<EnterpriseSchool> getEnterpriseSchools() {
    	return enterpriseSchools;
    }
    
    public void setEnterpriseSchools(List<EnterpriseSchool> enterpriseSchools) {
    	this.enterpriseSchools = enterpriseSchools;
    }
    
    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }
    
    public Classifier getCountry() {
    	return country;
    }
    
    public void setCountry(Classifier country) {
    	this.country = country;
    }

	public Boolean getPerson() {
		return isPerson;
	}

	public void setPerson(Boolean isPerson) {
		this.isPerson = isPerson;
	}

	public String getAddressOid() {
		return addressOid;
	}

	public void setAddressOid(String addressOid) {
		this.addressOid = addressOid;
	}

	public String getAddressAds() {
		return addressAds;
	}

	public void setAddressAds(String addressAds) {
		this.addressAds = addressAds;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDateTime getEbusinessUpdated() {
		return ebusinessUpdated;
	}

	public void setEbusinessUpdated(LocalDateTime ebusinessUpdated) {
		this.ebusinessUpdated = ebusinessUpdated;
	}
	
	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}
	
	public List<Contract> getContracts() {
		return contracts;
	}

}