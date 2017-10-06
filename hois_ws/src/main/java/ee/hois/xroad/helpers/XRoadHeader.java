package ee.hois.xroad.helpers;

import ee.hois.soap.LogContext;

public class XRoadHeader {
    public static final String XROAD_HEADER = "xRoadHeader";

    private String consumer;
    private String producer;
    private String userId;
    private String service;
    private String id;
    private String endpoint;

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public LogContext logContext() {
        return new LogContext(getId(), getService());
    }
}
