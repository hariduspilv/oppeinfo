package ee.hois.soap.ekis.client;

public class EkisRequestContext {

    private final String endpoint;

    public EkisRequestContext(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
