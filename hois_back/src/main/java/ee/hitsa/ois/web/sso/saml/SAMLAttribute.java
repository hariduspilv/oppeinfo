package ee.hitsa.ois.web.sso.saml;

import java.util.Collection;
import java.util.Collections;

public class SAMLAttribute {

    private final String name;
    private final Collection<String> values;

    public SAMLAttribute(String name, String value) {
        this(name, Collections.singletonList(value));
    }

    public SAMLAttribute(String name, Collection<String> values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public Collection<String> getValues() {
        return values;
    }

    public String getValue() {
        return String.join(", ", values);
    }

}
