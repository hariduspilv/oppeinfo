package ee.hitsa.ois.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import ee.hitsa.ois.validation.Required;
import ee.hois.xroad.helpers.XRoadHeaderV4;

@Component
@Validated
@ConfigurationProperties("rtip")
public class RtipProperties extends XroadProperties {

    @Required
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public XRoadHeaderV4 xroadHeader(String serviceCode) {
        XRoadHeaderV4 header = super.xroadHeader(serviceCode);
        header.setUserId(user);
        return header;
    }
}
