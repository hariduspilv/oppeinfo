package ee.hois.xroad.helpers;

public class XRoadHeaderV4 {

    private Client client;

    private Service service;

    private String userId;

    private String id;

    private static final String protocolVersion = "4.0";

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
        return protocolVersion;
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

    public static class Client {
        private String xRoadInstantce;
        private String memberClass;
        private String memberCode;
        private String subSystemCode;

        public String getXRoadInstantce() {
            return xRoadInstantce;
        }

        public void setXRoadInstantce(String xRoadInstantce) {
            this.xRoadInstantce = xRoadInstantce;
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
