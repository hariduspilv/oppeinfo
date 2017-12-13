package ee.hitsa.ois.web.sso.saml;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * based on: https://github.com/OpenConext/Mujina
 *
 */
public class SAMLPrincipal implements Principal {

    private String serviceProviderEntityID;
    private String requestID;
    private String assertionConsumerServiceURL;
    private String relayState;

    private final List<SAMLAttribute> attributes = new ArrayList<>();

    private String nameID;
    private String nameIDType;

    public SAMLPrincipal(String nameID, String nameIDType, List<SAMLAttribute> attributes) {
        this.nameID = nameID;
        this.nameIDType = nameIDType;
        this.attributes.addAll(attributes);
    }

    public SAMLPrincipal(String nameID, String nameIDType, List<SAMLAttribute> attributes,
            String serviceProviderEntityID, String requestID, String assertionConsumerServiceURL, String relayState) {
        this(nameID, nameIDType, attributes);
        this.serviceProviderEntityID = serviceProviderEntityID;
        this.requestID = requestID;
        this.assertionConsumerServiceURL = assertionConsumerServiceURL;
        this.relayState = relayState;
    }

    public String getServiceProviderEntityID() {
        return serviceProviderEntityID;
    }

    public String getRequestID() {
        return requestID;
    }

    public String getAssertionConsumerServiceURL() {
        return assertionConsumerServiceURL;
    }

    public String getRelayState() {
        return relayState;
    }

    public List<SAMLAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return nameID;
    }

    public String getNameID() {
        return nameID;
    }

    public String getNameIDType() {
        return nameIDType;
    }

}
