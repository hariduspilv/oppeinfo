package ee.hitsa.ois.web.dto.juhan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class JuhanBuildingDto {

    private Long id;
    private String code;
    private String name;
    private String addressOid;
    private String address;

    public JuhanBuildingDto(Long id, String code, String name, String addressOid, String address) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.addressOid = addressOid;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getAddressOid() {
        return addressOid;
    }

    public void setAddressOid(String addressOid) {
        this.addressOid = addressOid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
