package ee.hitsa.ois.web.dto.enterprise;

import java.util.List;

import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.enterprise.EnterpriseSchool;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class PracticeEnterpriseDto extends VersionedCommand {
	private Long id;
	private String enterpriseName;
	private String regCode;
	private String countryCode;
	private Boolean isPerson;
	private List<Contract> contracts;
	private EnterpriseSchool enterpriseSchool;
	private String addressOid;
    private String addressAds;
    private String address;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getAddressAds() {
		return addressAds;
	}
	public void setAddressAds(String addressAds) {
		this.addressAds = addressAds;
	}

	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public EnterpriseSchool getEnterpriseSchool() {
		return enterpriseSchool;
	}

	public void setEnterpriseSchool(EnterpriseSchool enterpriseSchool) {
		this.enterpriseSchool = enterpriseSchool;
	}

	public Boolean getIsPerson() {
		return isPerson;
	}

	public void setIsPerson(Boolean isPerson) {
		this.isPerson = isPerson;
	}

	public String getAddressOid() {
		return addressOid;
	}

	public void setAddressOid(String addressOid) {
		this.addressOid = addressOid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
