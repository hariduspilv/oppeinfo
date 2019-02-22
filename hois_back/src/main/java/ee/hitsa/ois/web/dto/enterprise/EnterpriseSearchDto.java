package ee.hitsa.ois.web.dto.enterprise;

import java.time.LocalDate;

public class EnterpriseSearchDto {
	
	private Long id;
	private String enterpriseName;
	private String regCode;
	private String countryCode;
	private String address;
	private String contactPersons;
	private String ratingCode;
	private Boolean isActive;
	private LocalDate ratingThru;
	
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getRegCode() {
		return regCode;
	}
	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactPersons() {
		return contactPersons;
	}
	public void setContactPersons(String contactPersons) {
		this.contactPersons = contactPersons;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getRatingCode() {
		return ratingCode;
	}
	public void setRatingCode(String ratingCode) {
		this.ratingCode = ratingCode;
	}
	public LocalDate getRatingThru() {
		return ratingThru;
	}
	public void setRatingThru(LocalDate ratingThru) {
		this.ratingThru = ratingThru;
	}
}
