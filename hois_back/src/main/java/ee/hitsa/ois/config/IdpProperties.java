package ee.hitsa.ois.config;

import net.shibboleth.utilities.java.support.resource.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/*@Component
@Validated
@ConfigurationProperties("idp")*/
public class IdpProperties {
    @Autowired
    private ConversionService conversionService;

    private Resource keystoreResource;
    private Resource keyVersionResource;

    public Resource getKeystoreResource() {
		return keystoreResource;
	}
    
    public void setKeystoreResource(Resource keystoreResource) {
		this.keystoreResource = conversionService.convert(keystoreResource, Resource.class);
	}
    
    public Resource getKeyVersionResource() {
        return keyVersionResource;
    }

    public void setKeyVersionResource(String keyVersionResource) {
        this.keyVersionResource = conversionService.convert(keyVersionResource, Resource.class);
    }
}
