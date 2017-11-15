package ee.hois.xroad.helpers;

import ee.hois.soap.LogContext;

public class XRoadHeaderV4 {

    public static final String XROAD_HEADER = "xRoadHeader";
    private static final String PROTOCOL_VERSION = "4.0";

    private Client client;
    private Service service;
    private String userId;
    private String id;
    private String endpoint;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static String getProtocolVersion() {
        return PROTOCOL_VERSION;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public LogContext logContext() {
        String queryName = String.format("%s.%s", service.getSubsystemCode(), service.getServiceCode());
        String serviceVersion = service.getServiceVersion();
        if(serviceVersion != null) {
            queryName = queryName + ("." + serviceVersion);
        }
        return new LogContext(getId(), queryName);
    }

    public static class Client {
        private String xRoadInstance;
        private String memberClass;
        private String memberCode;
        private String subSystemCode;

        public String getXRoadInstance() {
            return xRoadInstance;
        }

        public void setXRoadInstance(String xRoadInstance) {
            this.xRoadInstance = xRoadInstance;
        }

        public String getMemberClass() {
            return memberClass;
        }

        public void setMemberClass(String memberClass) {
            this.memberClass = memberClass;
        }

        public String getMemberCode() {
            return memberCode;
        }

        public void setMemberCode(String memberCode) {
            this.memberCode = memberCode;
        }

        public String getSubSystemCode() {
            return subSystemCode;
        }

        public void setSubSystemCode(String subSystemCode) {
            this.subSystemCode = subSystemCode;
        }
    }

    public static class Service {
        private String xRoadInstance;
        private String memberClass;
        private String memberCode;
        private String subsystemCode;
        private String serviceCode;
        private String serviceVersion = "v1";

        public String getxRoadInstance() {
            return xRoadInstance;
        }

        public void setxRoadInstance(String xRoadInstance) {
            this.xRoadInstance = xRoadInstance;
        }

        public String getMemberClass() {
            return memberClass;
        }

        public void setMemberClass(String memberClass) {
            this.memberClass = memberClass;
        }

        public String getMemberCode() {
            return memberCode;
        }

        public void setMemberCode(String memberCode) {
            this.memberCode = memberCode;
        }

        public String getSubsystemCode() {
            return subsystemCode;
        }

        public void setSubsystemCode(String subsystemCode) {
            this.subsystemCode = subsystemCode;
        }

        public String getServiceCode() {
            return serviceCode;
        }

        public void setServiceCode(String serviceCode) {
            this.serviceCode = serviceCode;
        }

        public String getServiceVersion() {
            return serviceVersion;
        }

        public void setServiceVersion(String serviceVersion) {
            this.serviceVersion = serviceVersion;
        }
    }
}
