package ee.hitsa.ois.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import ee.hitsa.ois.validation.NotEmpty;

@Component
@Validated
@ConfigurationProperties("hois.jwt")
public class HoisJwtProperties {

    @NotEmpty
    private String secret = "changeMe";
    @NotEmpty
    private String header = "Authorization";
    @NotEmpty
    private String tokenPrefix = "Bearer";
    @NotEmpty
    private String claimLoginMethod = "loginMethod";

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public String getClaimLoginMethod() {
        return claimLoginMethod;
    }

    public void setClaimLoginMethod(String claimLoginMethod) {
        this.claimLoginMethod = claimLoginMethod;
    }


}
