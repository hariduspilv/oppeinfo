package ee.hitsa.ois.config;

import java.util.Map;

import ee.hitsa.ois.validation.NotEmpty;
import ee.hois.xroad.helpers.XRoadHeaderV4;

public class XroadProperties {

    @NotEmpty
    private String endpoint;
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

    public XRoadHeaderV4 xroadHeader(String serviceCode) {
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
        serviceHeader.setSubsystemCode(service.get("subsystemCode"));

        XRoadHeaderV4 header = new XRoadHeaderV4();
        header.setClient(clientHeader);
        header.setService(serviceHeader);
        header.setEndpoint(endpoint);
        return header;
    }
}
