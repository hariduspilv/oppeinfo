package ee.hitsa.ois.web.commandobject.enterprise;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class PracticeEnterprisePersonCommand extends VersionedCommand {
	
	@ClassifierRestriction(MainClassCode.RIIK)
	private String country;
	private String firstname;
	private String lastname;
	private String phone;
	private String email;
	private String idcode;
	private String position;
	private Boolean supervisor;
	private Boolean contact;
	private String name;
	private String regCode;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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
		return supervisor;
	}

	public void setSupervisor(Boolean supervisor) {
		this.supervisor = supervisor;
	}

	public Boolean getContact() {
		return contact;
	}

	public void setContact(Boolean contact) {
		this.contact = contact;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}
}
