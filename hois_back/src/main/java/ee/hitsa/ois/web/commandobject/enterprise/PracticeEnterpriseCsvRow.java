package ee.hitsa.ois.web.commandobject.enterprise;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

@JsonPropertyOrder({
    "name",
    "regCode",
    "country",
    "person",
    "address",
    "addressAds",
    "addressOid",
    "zipCode",
    "email",
    "contactPhone",
    "collectiveLanguage",
    "places",
    "internshipAddInfo",
    "notes",
    "studentCanApply",
    "contactPersonFirstname",
    "contactPersonLastname",
    "contactPersonPhone",
    "contactPersonEmail",
    "contactPersonIdCode",
    "contactPersonIdCodeCountry",
    "contactPersonProfession",
    "contactPersonAddInfo",
    "contactPersonIsSupervisor",
    "contactPersonIsContact"})
public class PracticeEnterpriseCsvRow {
	private String name;
	private String regCode;
	@ClassifierRestriction(value = { MainClassCode.RIIK }, useClassifierValue = true)
	private String country;
	//Actually a boolean
	private String person;
	private String address;
	private String addressAds;
	private String addressOid;
	private String zipCode;
	@Email
	private String email;
	private String contactPhone;
	@ClassifierRestriction(value = { MainClassCode.OPPEKEEL }, useClassifierValue = true)
	private String collectiveLanguage;
	private String places;
	private String internshipAddInfo;
	private String notes;
	//Actually a boolean
	private String studentCanApply;
	private String contactPersonFirstname;
	private String contactPersonLastname;
	private String contactPersonPhone;
	@Email
	private String contactPersonEmail;
	private String contactPersonIdCode;
	@ClassifierRestriction(value = { MainClassCode.RIIK }, useClassifierValue = true)
	private String contactPersonIdCodeCountry;
	private String contactPersonProfession;
	private String contactPersonAddInfo;
	//Actually a boolean
	private String contactPersonIsSupervisor;
	//Actually a boolean
	private String contactPersonIsContact;
	
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
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
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getCollectiveLanguage() {
		return collectiveLanguage;
	}
	public void setCollectiveLanguage(String collectiveLanguage) {
		this.collectiveLanguage = collectiveLanguage;
	}
	public String getPlaces() {
		return places;
	}
	public void setPlaces(String places) {
		this.places = places;
	}
	public String getInternshipAddInfo() {
		return internshipAddInfo;
	}
	public void setInternshipAddInfo(String internshipAddInfo) {
		this.internshipAddInfo = internshipAddInfo;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getStudentCanApply() {
		return studentCanApply;
	}
	public void setStudentCanApply(String studentCanApply) {
		this.studentCanApply = studentCanApply;
	}
	public String getContactPersonFirstname() {
		return contactPersonFirstname;
	}
	public void setContactPersonFirstname(String contactPersonFirstname) {
		this.contactPersonFirstname = contactPersonFirstname;
	}
	public String getContactPersonLastname() {
		return contactPersonLastname;
	}
	public void setContactPersonLastname(String contactPersonLastname) {
		this.contactPersonLastname = contactPersonLastname;
	}
	public String getContactPersonPhone() {
		return contactPersonPhone;
	}
	public void setContactPersonPhone(String contactPersonPhone) {
		this.contactPersonPhone = contactPersonPhone;
	}
	public String getContactPersonEmail() {
		return contactPersonEmail;
	}
	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}
	public String getContactPersonIdCode() {
		return contactPersonIdCode;
	}
	public void setContactPersonIdCode(String contactPersonIdCode) {
		this.contactPersonIdCode = contactPersonIdCode;
	}
	public String getContactPersonIdCodeCountry() {
		return contactPersonIdCodeCountry;
	}
	public void setContactPersonIdCodeCountry(String contactPersonIdCodeCountry) {
		this.contactPersonIdCodeCountry = contactPersonIdCodeCountry;
	}
	public String getContactPersonProfession() {
		return contactPersonProfession;
	}
	public void setContactPersonProfession(String contactPersonProfession) {
		this.contactPersonProfession = contactPersonProfession;
	}
	public String getContactPersonAddInfo() {
		return contactPersonAddInfo;
	}
	public void setContactPersonAddInfo(String contactPersonAddInfo) {
		this.contactPersonAddInfo = contactPersonAddInfo;
	}
	public String getContactPersonIsSupervisor() {
		return contactPersonIsSupervisor;
	}
	public void setContactPersonIsSupervisor(String contactPersonIsSupervisor) {
		this.contactPersonIsSupervisor = contactPersonIsSupervisor;
	}
	public String getContactPersonIsContact() {
		return contactPersonIsContact;
	}
	public void setContactPersonIsContact(String contactPersonIsContact) {
		this.contactPersonIsContact = contactPersonIsContact;
	}
}
