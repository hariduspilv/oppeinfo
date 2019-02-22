package ee.hitsa.ois.web.dto.enterprise;

import ee.hitsa.ois.domain.enterprise.EnterpriseSchoolPerson;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class EnterpriseSchoolPersonDto {
	private Long id;
	private String firstname;
	private String lastname;
	private String phone;
	private String email;
	private String idcode;
	private String country;
	private String position;
	private Boolean isSupervisor;
	private Boolean isContact;
	
	public static EnterpriseSchoolPersonDto of(EnterpriseSchoolPerson person) {
		EnterpriseSchoolPersonDto enterpriseSchoolPersonDto = EntityUtil.bindToDto(person, new EnterpriseSchoolPersonDto(), "country");
		if (person.getIdcodeCountry() != null) {
			enterpriseSchoolPersonDto.setCountry(person.getIdcodeCountry().getCode());
		}
		return enterpriseSchoolPersonDto;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdcode() {
		return idcode;
	}
	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Boolean getSupervisor() {
		return isSupervisor;
	}
	public void setSupervisor(Boolean isSupervisor) {
		this.isSupervisor = isSupervisor;
	}
	public Boolean getContact() {
		return isContact;
	}
	public void setContact(Boolean isContact) {
		this.isContact = isContact;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
