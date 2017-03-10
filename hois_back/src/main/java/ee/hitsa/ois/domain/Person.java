package ee.hitsa.ois.domain;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ee.hitsa.ois.util.PersonUtil;

@Entity
public class Person extends BaseEntityWithId {

    private String firstname;
    private String lastname;
    private String idcode;
    private String foreignIdcode;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier sex;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier citizenship;
    private String bankaccount;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier language;
    private String nativeLanguage;
    private String phone;
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier residenceCountry;
    private String postcode;
    private String email;
    private LocalDate birthdate;
    @OneToMany(mappedBy = "person")
    private Set<User> users;

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getForeignIdcode() {
        return foreignIdcode;
    }

    public void setForeignIdcode(String foreignIdcode) {
        this.foreignIdcode = foreignIdcode;
    }

    public Classifier getSex() {
        return sex;
    }

    public void setSex(Classifier sex) {
        this.sex = sex;
    }

    public Classifier getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(Classifier citizenship) {
        this.citizenship = citizenship;
    }

    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public Classifier getLanguage() {
        return language;
    }

    public void setLanguage(Classifier language) {
        this.language = language;
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

    public Classifier getResidenceCountry() {
        return residenceCountry;
    }

    public void setResidenceCountry(Classifier residenceCountry) {
        this.residenceCountry = residenceCountry;
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    @Transient
    public String getFullname() {
        return PersonUtil.fullname(firstname, lastname);
    }
}
