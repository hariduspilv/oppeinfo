package ee.hitsa.ois.domain.enterprise;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.school.School;

@Entity
public class EnterpriseSchool extends BaseEntityWithId {
	
	@ManyToOne(optional = false)
    @JoinColumn(nullable = false, insertable = true, updatable = true)
	@JsonIgnore
    private Enterprise enterprise;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = true)
	@JsonIgnore
    private School school;
	private Boolean isActive;
	private Boolean isApplication;
	private String addressOid;
	private String addressAds;
	private String address;
	private String postcode;
	@ManyToOne
	private Classifier rating;
	private String ratingInfo;
	private LocalDate ratingThru;
	@Email
    private String email;
	private String phone;
	@ManyToOne
	private Classifier language;
	private Integer places;
	private String placesDescr;
	private String addInfo;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "enterprise_school_id", nullable = false, updatable = false , insertable = false)
	private List<EnterpriseSchoolLocation> enterpriseSchoolLocations = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "enterprise_school_id", nullable = false,updatable = false , insertable = false)
    private List<EnterpriseSchoolPerson> enterpriseSchoolPersons = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "enterprise_school_id", nullable = false, updatable = false , insertable = false)
    private List<EnterpriseSchoolIscedClass> enterpriseSchoolIscedClasses = new ArrayList<>();
	
	public Enterprise getEnterprise() {
		return enterprise;
	}
	
	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean isActive) {
		this.isActive = isActive;
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
	
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Classifier getLanguage() {
		return language;
	}

	public void setLanguage(Classifier language) {
		this.language = language;
	}

	public Integer getPlaces() {
		return places;
	}

	public void setPlaces(Integer places) {
		this.places = places;
	}

	public String getPlacesDescr() {
		return placesDescr;
	}

	public void setPlacesDescr(String placesDescr) {
		this.placesDescr = placesDescr;
	}

	public String getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSchool(School school) {
		this.school = school;
	}
	
	public School getSchool() {
		return school;
	}
	
	public List<EnterpriseSchoolPerson> getEnterpriseSchoolPersons() {
		return enterpriseSchoolPersons;
	}
	
	public void setEnterpriseSchoolPersons(List<EnterpriseSchoolPerson> enterpriseSchoolPersons) {
		this.enterpriseSchoolPersons = enterpriseSchoolPersons;
	}
	
	public List<EnterpriseSchoolLocation> getEnterpriseSchoolLocations() {
		return enterpriseSchoolLocations;
	}
	
	public void setEnterpriseSchoolLocations(List<EnterpriseSchoolLocation> enterpriseSchoolLocations) {
		this.enterpriseSchoolLocations = enterpriseSchoolLocations;
	}
	
	public List<EnterpriseSchoolIscedClass> getEnterpriseSchoolIscedClasses() {
		return enterpriseSchoolIscedClasses;
	}
	
	public void setEnterpriseSchoolIscedClasses(List<EnterpriseSchoolIscedClass> enterpriseSchoolIscedClasses) {
		this.enterpriseSchoolIscedClasses = enterpriseSchoolIscedClasses;
	}

	public Boolean getApplication() {
		return isApplication;
	}

	public void setApplication(Boolean isApplication) {
		this.isApplication = isApplication;
	}

	public Classifier getRating() {
		return rating;
	}

	public void setRating(Classifier rating) {
		this.rating = rating;
	}

	public String getRatingInfo() {
		return ratingInfo;
	}

	public void setRatingInfo(String ratingInfo) {
		this.ratingInfo = ratingInfo;
	}

	public LocalDate getRatingThru() {
		return ratingThru;
	}

	public void setRatingThru(LocalDate ratingThru) {
		this.ratingThru = ratingThru;
	}

}
