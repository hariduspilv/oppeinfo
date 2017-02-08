package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.util.EntityUtil;

import java.time.LocalDate;

public class PersonDto {

    private String firstname;
    private String lastname;
    private String idcode;
    private String phone;
    private String address;
    private String postcode;
    private String email;
    private String citizenship;
    private String nativeLanguage;
    private LocalDate birthdate;
    private String sex;

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

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static PersonDto of(Person person) {
        return EntityUtil.bindToDto(person, new PersonDto());
    }


    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
