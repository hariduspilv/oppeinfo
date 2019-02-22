package ee.hitsa.ois.domain.enterprise;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class EnterpriseSchoolPerson extends BaseEntityWithId {
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = true, updatable = true)
	@JsonIgnore
    private EnterpriseSchool enterpriseSchool;
	private String firstname;
	private String lastname;
	private String phone;
	@Email
	private String email;
	private String idcode;
	@ManyToOne(fetch = FetchType.LAZY)
	private Classifier idcodeCountry;
	private String position;
	private Boolean isSupervisor;
	private Boolean isContact;
	
	public EnterpriseSchool getEnterpriseSchool() {
		return enterpriseSchool;
	}
	
	public void setEnterpriseSchool(EnterpriseSchool enterpriseSchool) {
		this.enterpriseSchool = enterpriseSchool;
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
	
	public Classifier getIdcodeCountry() {
		return idcodeCountry;
	}
	
	public void setIdcodeCountry(Classifier idcodeCountry) {
		this.idcodeCountry = idcodeCountry;
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
}
