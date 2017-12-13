package ee.hitsa.ois.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import ee.hitsa.ois.validation.NotEmpty;
import ee.hois.xroad.helpers.XRoadHeaderV4;

@Component
@Validated
@ConfigurationProperties("kutseregister")
public class KutseregisterProperties extends XroadProperties {

    public static final String KUTSETUNNISTUS_SERVICE_CODE = "kutsetunnistus";
    private static final Map<String, String> SERVICE_VERSIONS = new HashMap<>();
    static {
        SERVICE_VERSIONS.put(KUTSETUNNISTUS_SERVICE_CODE, "v2");
    }

    @NotEmpty
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
        String serviceVersion = SERVICE_VERSIONS.get(serviceCode);
        if(serviceVersion != null) {
            header.getService().setServiceVersion(serviceVersion);
        }
        header.setUserId(user);
        return header;
    }
}
