package ee.hitsa.ois.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import ee.hitsa.ois.validation.Required;

@Component
@Validated
@ConfigurationProperties("hois.jwt")
public class HoisJwtProperties {

    @Required
    private String secret = "changeMe";
    @Required
    private String header = "Authorization";
    @Required
    private String tokenPrefix = "Bearer";
    @Required
    private String claimLoginMethod = "loginMethod";
    @Required
    private String claimIdcode = "idcode";
    @Required
    private String claimMobileNumber = "mobileNumber";
    @Required
    private String claimAuthHash = "authHash";

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

    public String getClaimIdcode() {
        return claimIdcode;
    }

    public void setClaimIdcode(String claimIdcode) {
        this.claimIdcode = claimIdcode;
    }

    public String getClaimMobileNumber() {
        return claimMobileNumber;
    }
    
    public void setClaimMobileNumber(String claimMobileNumber) {
        this.claimMobileNumber = claimMobileNumber;
    }

    public String getClaimAuthHash() {
        return claimAuthHash;
    }

    public void setClaimAuthHash(String claimAuthHash) {
        this.claimAuthHash = claimAuthHash;
    }

}
