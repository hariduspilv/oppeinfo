package ee.hitsa.ois.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.school.School;

@Entity
public class Building extends BaseEntityWithId {

    private String code;
    private String name;
    private String address;
    private String addressAds;
    private String addressAdsOid;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    @OneToMany(mappedBy = "building")
    private List<Room> rooms;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddressAdsOid() {
        return addressAdsOid;
    }

    public void setAddressAdsOid(String addressAdsOid) {
        this.addressAdsOid = addressAdsOid;
    }

    public School getSchool() {
       return school;
    }

    public void setSchool(School school) {
       this.school = school;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
