package ee.hitsa.ois.web.commandobject.curriculum;

import ee.hitsa.ois.domain.curriculum.CurriculumAddress;
import ee.hitsa.ois.util.EntityUtil;

public class CurriculumAddressForm {

    private Long id;
    private String address;
    private String addressAds;
    private String addressOid;
    private String addressOv;
    
    public static CurriculumAddressForm of(CurriculumAddress address) {
        return EntityUtil.bindToDto(address, new CurriculumAddressForm());
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    
    public String getAddressOv() {
        return addressOv;
    }
    public void setAddressOv(String addressOv) {
        this.addressOv = addressOv;
    }
    
}
