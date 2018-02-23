package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.Required;

public class BuildingForm extends VersionedCommand {

    @Required
    @Size(max = 20)
    private String code;
    @Size(max = 255)
    private String name;
    @Size(max = 255)
    private String address;
    @Size(max = 50)
    private String addressAds;
    @Size(max = 50)
    private String addressAdsOid;

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
    
}
