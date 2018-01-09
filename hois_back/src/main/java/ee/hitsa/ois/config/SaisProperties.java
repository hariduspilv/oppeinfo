package ee.hitsa.ois.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import ee.hitsa.ois.validation.Required;
import ee.hois.xroad.helpers.XRoadHeaderV4;

@Component
@Validated
@ConfigurationProperties("sais")
public class SaisProperties extends XroadProperties {

    @Required
    private String useridprefix;

    public String getUseridprefix() {
        return useridprefix;
    }

    public void setUseridprefix(String useridprefix) {
        this.useridprefix = useridprefix;
    }

    public XRoadHeaderV4 xroadHeader(String serviceCode, String userIdcode) {
        XRoadHeaderV4 header = super.xroadHeader(serviceCode);
        // sais does not use service version
        header.getService().setServiceVersion(null);
        header.setUserId(getUseridprefix() + userIdcode);
        return header;
    }
}
