package ee.hitsa.ois.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;

import javax.annotation.PostConstruct;

import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.credential.impl.KeyStoreCredentialResolver;
import org.opensaml.security.x509.X509Support;
import org.opensaml.xmlsec.signature.support.SignatureConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;

@Component
@ConfigurationProperties("idp")
public class IdpConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    private CredentialResolver credentialResolver;
    
    private String signatureAlgorithm = SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1;
    private String entityId;
    private String privateKey;
    private String certificate;
    private String passphrase;
    private String acsEndpoint;
    private int clockSkew;
    private int expires;
    private boolean compareEndpoints;
    private String loginRedirect;

    @PostConstruct
    private void postConstruct() throws Exception {
        credentialResolver = new KeyStoreCredentialResolver(keyStore(), Collections.singletonMap(entityId, passphrase));
    }

    private KeyStore keyStore() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(null, passphrase.toCharArray());
        try(InputStream is = new FileInputStream(privateKey)) {
            X509Certificate cert = X509Support.decodeCertificate(new File(certificate));
            PrivateKey privKey = KeyFactory.getInstance("RSA").generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(
                            StreamUtils.copyToString(is, StandardCharsets.UTF_8)
                            .replaceAll("-----BEGIN PRIVATE KEY-----|-----END PRIVATE KEY-----|\\s", "")
                            .getBytes(StandardCharsets.UTF_8))));
            keyStore.setKeyEntry(entityId, privKey, passphrase.toCharArray(), new Certificate[] {cert});
        } catch (Exception e) {
            log.error("Cannot load IdP certificate/key file", e);
        }
        return keyStore;
    }
    
    public Credential resolveCredential() throws ResolverException {
        return credentialResolver.resolveSingle(new CriteriaSet(new EntityIdCriterion(entityId)));
    }

    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }
    
    public void setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }
    
    public String getAcsEndpoint() {
        return acsEndpoint;
    }
    
    public void setAcsEndpoint(String acsEndpoint) {
        this.acsEndpoint = acsEndpoint;
    }

    public int getClockSkew() {
        return clockSkew;
    }

    public void setClockSkew(int clockSkew) {
        this.clockSkew = clockSkew;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public boolean isCompareEndpoints() {
        return compareEndpoints;
    }

    public void setCompareEndpoints(boolean compareEndpoints) {
        this.compareEndpoints = compareEndpoints;
    }

    public String getLoginRedirect() {
        return loginRedirect;
    }

    public void setLoginRedirect(String loginRedirect) {
        this.loginRedirect = loginRedirect;
    }

    public String getEntityId() {
        return entityId;
    }
    
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
    
    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }
    
}
