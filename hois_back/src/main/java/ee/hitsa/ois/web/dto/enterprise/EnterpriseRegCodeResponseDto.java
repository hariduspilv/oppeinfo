package ee.hitsa.ois.web.dto.enterprise;

public class EnterpriseRegCodeResponseDto {
	private String regCode;
	private String address;
	private String addressAds;
	private String addressOid;
	private String registerAddress;
	private String registerAddressAds;
	private String registerAddressOid;
	private String name;
	private Boolean exists;
	private String status;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getExists() {
		return exists;
	}
	public void setExists(Boolean exists) {
		this.exists = exists;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddressAds() {
		return addressAds;
	}
	public void setAddressAds(String addressAds) {
		this.addressAds = addressAds;
	}
	public String getAddressOid() {
		return addressOid;
	}
	public void setAddressOid(String addressOid) {
		this.addressOid = addressOid;
	}
    public String getRegisterAddress() {
        return registerAddress;
    }
    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }
    public String getRegisterAddressAds() {
        return registerAddressAds;
    }
    public void setRegisterAddressAds(String registerAddressAds) {
        this.registerAddressAds = registerAddressAds;
    }
    public String getRegisterAddressOid() {
        return registerAddressOid;
    }
    public void setRegisterAddressOid(String registerAddressOid) {
        this.registerAddressOid = registerAddressOid;
    }
	
}
