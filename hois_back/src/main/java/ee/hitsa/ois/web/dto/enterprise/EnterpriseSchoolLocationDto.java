package ee.hitsa.ois.web.dto.enterprise;

public class EnterpriseSchoolLocationDto {
	
	private Long id;
	private String nameEt;
	private String language;
	private String country;
	private String address;
	private String addressOid;
	private String addressAds;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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

	public String getNameEt() {
		return nameEt;
	}

	public void setNameEt(String nameEt) {
		this.nameEt = nameEt;
	}
}
