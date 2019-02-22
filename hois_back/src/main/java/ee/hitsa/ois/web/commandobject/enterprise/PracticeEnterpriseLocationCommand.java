package ee.hitsa.ois.web.commandobject.enterprise;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class PracticeEnterpriseLocationCommand {
	@ClassifierRestriction(MainClassCode.RIIK)
	private String country;
	@ClassifierRestriction(MainClassCode.OPPEKEEL)
	private String language;
	private String nameEt;
	private String addressOid;
	private String addressAds;
	private String address;
	private String regCode;
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
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

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public String getAddressOid() {
		return addressOid;
	}

	public void setAddressOid(String addressOid) {
		this.addressOid = addressOid;
	}

	public String getNameEt() {
		return nameEt;
	}

	public void setNameEt(String nameEt) {
		this.nameEt = nameEt;
	}
}
