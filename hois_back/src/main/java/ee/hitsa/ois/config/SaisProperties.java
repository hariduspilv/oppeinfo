package ee.hitsa.ois.config;

import ee.hitsa.ois.validation.NotEmpty;
import ee.hois.xroad.helpers.XRoadHeaderV4;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties("sais")
public class SaisProperties {

    @NotEmpty
    private String endpoint;
    @NotEmpty
    private String useridprefix;
    @NotEmpty
    private Map<String, String> client;
    @NotEmpty
    private Map<String, String> service;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getUseridprefix() {
        return useridprefix;
    }

    public void setUseridprefix(String useridprefix) {
        this.useridprefix = useridprefix;
    }

    public Map<String, String> getClient() {
        return client;
    }

    public void setClient(Map<String, String> client) {
        this.client = client;
    }

    public Map<String, String> getService() {
        return service;
    }

    public void setService(Map<String, String> service) {
        this.service = service;
    }

    public XRoadHeaderV4 xroadHeader(String serviceCode, String userIdcode) {
        XRoadHeaderV4.Client clientHeader = new XRoadHeaderV4.Client();
        clientHeader.setXRoadInstance(client.get("xRoadInstance"));
        clientHeader.setMemberClass(client.get("memberClass"));
        clientHeader.setMemberCode(client.get("memberCode"));
        clientHeader.setSubSystemCode(client.get("subsystemCode"));

        XRoadHeaderV4.Service serviceHeader = new XRoadHeaderV4.Service();
        serviceHeader.setxRoadInstance(service.get("xRoadInstance"));
        serviceHeader.setMemberClass(service.get("memberClass"));
        serviceHeader.setMemberCode(service.get("memberCode"));
        serviceHeader.setServiceCode(serviceCode);
        // sais does not use service version
        serviceHeader.setServiceVersion(null);
        serviceHeader.setSubsystemCode(service.get("subsystemCode"));

        XRoadHeaderV4 header = new XRoadHeaderV4();
        header.setClient(clientHeader);
        header.setService(serviceHeader);
        header.setEndpoint(endpoint);
        header.setUserId(getUseridprefix() + userIdcode);
        return header;
    }
}
