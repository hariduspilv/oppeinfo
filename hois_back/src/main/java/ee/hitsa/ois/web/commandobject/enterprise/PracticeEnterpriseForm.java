package ee.hitsa.ois.web.commandobject.enterprise;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.enterprise.Enterprise;
import ee.hitsa.ois.domain.enterprise.EnterpriseSchool;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseRegCodeResponseDto;

public class PracticeEnterpriseForm extends VersionedCommand {
	private Long id;
	private Long enterpriseSchoolId;
	private String registerAddressAds;
	private String registerAddressOid;
	private String registerAddress;
	private String address;
	private String addressAds;
	private String addressOid;
	@Required
	@ClassifierRestriction(MainClassCode.RIIK)
	private String country;
	private String email;
	@Required
	private String name;
	@Size(max = 100)
	private String placesDescr;
	private String places;
	private Boolean isApplication;
	private Boolean isActive;
	private Boolean isPerson;
	@ClassifierRestriction(MainClassCode.OPPEKEEL)
	private String language;
	@Size(max = 255)
	private String addInfo;
	private String phone;
	@Size(max = 8)
	private String postcode;
	@Size(max = 20)
	private String regCode;
	private LocalDateTime ebusinessUpdated;
	private String addressRegister;
	private String status;
	
	public static PracticeEnterpriseForm of(Enterprise enterprise, HoisUserDetails user, EnterpriseRegCodeResponseDto regCodeResponse) {
		Optional<EnterpriseSchool> enterpriseSchool = enterprise.getEnterpriseSchools().stream().filter(p->p.getSchool().getId().equals(user.getSchoolId())).findFirst();
		PracticeEnterpriseForm practiceEnterpriseForm = new PracticeEnterpriseForm();
		PracticeEnterpriseForm practiceEnterpriseWithEnterprise =  
				EntityUtil.bindToDto(enterprise, practiceEnterpriseForm, "enterpriseSchools", "country",
				"address", "addressAds", "addressOid",
				"contactPersonPhone", "contactPersonName", "contactPersonEmail", "contracts");
		if (enterprise.getCountry() != null) {
			practiceEnterpriseWithEnterprise.setCountryCode(enterprise.getCountry().getCode());
		}
		if (regCodeResponse.getStatus() != null) {
			practiceEnterpriseWithEnterprise.setStatus(regCodeResponse.getStatus());
			return practiceEnterpriseWithEnterprise;
		}
		if (enterpriseSchool.isPresent()) {
			EnterpriseSchool realSchool = enterpriseSchool.get();
			PracticeEnterpriseForm practiceEnterpriseWithEnterpriseAndSchool =  
					EntityUtil.bindToDto(realSchool, practiceEnterpriseWithEnterprise,"id", "language", "enterpriseSchools", 
							"enterpriseSchoolPersons", "enterpriseSchoolIscedClasses", "places");
			if (realSchool.getPlaces() != null) {
				practiceEnterpriseForm.setPlaces(realSchool.getPlaces());
			}
			practiceEnterpriseForm.setRegisterAddress(enterprise.getAddress());
			practiceEnterpriseForm.setRegisterAddressAds(enterprise.getAddressAds());
			practiceEnterpriseForm.setRegisterAddressOid(enterprise.getAddressOid());
			practiceEnterpriseForm.setLanguage(realSchool.getLanguage().getCode());
			practiceEnterpriseForm.setEnterpriseSchoolId(realSchool.getId());
			practiceEnterpriseForm.setAddressRegister(regCodeResponse.getAddress());
			return practiceEnterpriseWithEnterpriseAndSchool;
		}
        return practiceEnterpriseWithEnterprise;
    }
	
	public static PracticeEnterpriseForm of(Enterprise enterprise, HoisUserDetails user) {
		Optional<EnterpriseSchool> enterpriseSchool = enterprise.getEnterpriseSchools().stream().filter(p->p.getSchool().getId().equals(user.getSchoolId())).findFirst();
		PracticeEnterpriseForm practiceEnterpriseForm = new PracticeEnterpriseForm();
		PracticeEnterpriseForm practiceEnterpriseWithEnterprise =  
				EntityUtil.bindToDto(enterprise, practiceEnterpriseForm, "enterpriseSchools", "country",
				        "address", "addressAds", "addressOid",
				"contactPersonPhone", "contactPersonName", "contactPersonEmail", "contracts");
		if (enterprise.getCountry() != null) {
			practiceEnterpriseWithEnterprise.setCountryCode(enterprise.getCountry().getCode());
		}
		if (enterpriseSchool.isPresent()) {
			EnterpriseSchool realSchool = enterpriseSchool.get();
			PracticeEnterpriseForm practiceEnterpriseWithEnterpriseAndSchool =  
					EntityUtil.bindToDto(realSchool, practiceEnterpriseWithEnterprise,"id", "language", "enterpriseSchools", 
							"enterpriseSchoolPersons", "enterpriseSchoolIscedClasses", "places");
			if (realSchool.getPlaces() != null) {
				practiceEnterpriseForm.setPlaces(realSchool.getPlaces());
			}
			if (realSchool.getLanguage() != null) {
				practiceEnterpriseForm.setLanguage(realSchool.getLanguage().getCode());
			}
			practiceEnterpriseForm.setRegisterAddress(enterprise.getAddress());
            practiceEnterpriseForm.setRegisterAddressAds(enterprise.getAddressAds());
            practiceEnterpriseForm.setRegisterAddressOid(enterprise.getAddressOid());
			practiceEnterpriseForm.setEnterpriseSchoolId(realSchool.getId());
			return practiceEnterpriseWithEnterpriseAndSchool;
		}
        return practiceEnterpriseWithEnterprise;
    }
	
	public static PracticeEnterpriseForm of(EnterpriseSchool enterpriseSchool) {
        PracticeEnterpriseForm practiceEnterpriseForm = new PracticeEnterpriseForm();
        PracticeEnterpriseForm practiceEnterpriseWithEnterprise =  
                EntityUtil.bindToDto(enterpriseSchool.getEnterprise(), practiceEnterpriseForm, "enterpriseSchools", "country",
                "address", "addressAds", "addressOid",
                "contactPersonPhone", "contactPersonName", "contactPersonEmail", "contracts");
        if (enterpriseSchool.getEnterprise().getCountry() != null) {
            practiceEnterpriseWithEnterprise.setCountryCode(enterpriseSchool.getEnterprise().getCountry().getCode());
        }
        PracticeEnterpriseForm practiceEnterpriseWithEnterpriseAndSchool =  
                EntityUtil.bindToDto(enterpriseSchool, practiceEnterpriseWithEnterprise,"id", "language", "enterpriseSchools", 
                        "enterpriseSchoolPersons", "enterpriseSchoolIscedClasses", "places");
        if (enterpriseSchool.getPlaces() != null) {
            practiceEnterpriseForm.setPlaces(enterpriseSchool.getPlaces());
        }
        if (enterpriseSchool.getLanguage() != null) {
            practiceEnterpriseForm.setLanguage(enterpriseSchool.getLanguage().getCode());
        }
        Enterprise enterprise = enterpriseSchool.getEnterprise();
        practiceEnterpriseForm.setRegisterAddress(enterprise.getAddress());
        practiceEnterpriseForm.setRegisterAddressAds(enterprise.getAddressAds());
        practiceEnterpriseForm.setRegisterAddressOid(enterprise.getAddressOid());
        practiceEnterpriseForm.setEnterpriseSchoolId(enterpriseSchool.getId());
        return practiceEnterpriseWithEnterpriseAndSchool;
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
	public String getAddressOid() {
		return addressOid;
	}
	public void setAddressOid(String addressOid) {
		this.addressOid = addressOid;
	}
	public String getCountry() {
		return country;
	}
	public void setCountryCode(String country) {
		this.country = country;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlacesDescr() {
		return placesDescr;
	}
	public void setPlacesDescr(String placesDescr) {
		this.placesDescr = placesDescr;
	}
	public String getPlaces() {
		return places;
	}
	public void setPlaces(Integer places) {
	    if (places != null) {
	        this.places = Integer.toString(places.intValue());
	    } else {
	        this.places = null;
	    }
	}
	public Boolean getActive() {
		return isActive;
	}
	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getAddInfo() {
		return addInfo;
	}
	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getRegCode() {
		return regCode;
	}
	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getPerson() {
		return isPerson;
	}
	public void setPerson(Boolean isPerson) {
		this.isPerson = isPerson;
	}
	public Long getEnterpriseSchoolId() {
		return enterpriseSchoolId;
	}
	public void setEnterpriseSchoolId(Long enterpriseSchoolId) {
		this.enterpriseSchoolId = enterpriseSchoolId;
	}

	public LocalDateTime getEbusinessUpdated() {
		return ebusinessUpdated;
	}

	public void setEbusinessUpdated(LocalDateTime ebusinessUpdated) {
		this.ebusinessUpdated = ebusinessUpdated;
	}

	public String getAddressRegister() {
		return addressRegister;
	}

	public void setAddressRegister(String addressRegister) {
		this.addressRegister = addressRegister;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getApplication() {
		return isApplication;
	}

	public void setApplication(Boolean isApplication) {
		this.isApplication = isApplication;
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

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }
}
