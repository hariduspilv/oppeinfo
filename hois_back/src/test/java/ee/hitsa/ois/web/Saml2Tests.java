package ee.hitsa.ois.web;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.Issuer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Element;

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.web.sso.saml.SAMLBuilder;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class Saml2Tests {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;
    
    @Before
    public void before() {
        StringHttpMessageConverter mc = new StringHttpMessageConverter();
        mc.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_HTML));
        restTemplate.getRestTemplate().getMessageConverters().add(mc);
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
    }

    @Test
    public void redirect() throws Exception {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/SingleSignOnService");
        //uriBuilder.queryParam("SAMLRequest", "lZLNbtswEIRfReBdoigbrk3YBtQYQQykjWEpPfQSrMVVzUQiVe4qbd%2B%2Bktyf9GKgJwLDneHHwa4J2qbTec9nd8SvPRJH39vGkZ4uNqIPTnsgS9pBi6S50kX%2B4V5nSaq74NlXvhFvLNcdQISBrXci2u824mm5qLOsWqamVot5qlZpXZ1wmaVQm4WBxXym1EqloLJaRJ8w0ODciCFosBP1uHfE4HiQUvUuVipW8zJd6dlMq%2FlnEe2G31gHPLnOzB1pKQ2%2Bnr2lWVL3z5apTxDlKDydoHqRI2kmj2hswIplUTyIKP8NfeMd9S2GAsOrrfDxeP83NqCnhAE4QTNltt70DSbduZNjL5IuZxZDRZNa5nkposOvDt9bZ6z7cr2%2B02WI9F1ZHuLDQ1GK7XpM1VMdYfs%2FNC0yGGD4A7OWb6PWl834OEDsdwff2OpHdOtDC3ydcVSsietpVHMARxYdDzU2jf92ExAYN4JDj0JuL0%2F%2Bu3%2Fbnw%3D%3D");
        //uriBuilder.queryParam("SigAlg", "http%3A%2F%2Fwww.w3.org%2F2000%2F09%2Fxmldsig%23rsa-sha1");
        //uriBuilder.queryParam("Signature", "oVhizIs9oLlEQLbEjl3w7YXUtxKyZefUJgpRdOU5wtBmzmlDSqo65PE0BSHE6y%2FI%2Bog381zkuqXhipvYLxxYovNkdiTrep94Tm3ZJQjSc4a4T%2FqtRYFi2fkEJvoiFheZFpgAnd8JvaXKiSQI4126z5rWC1CD10k3EDJSV6IPEn0LutMy5m6%2BTcX6jsFeJlcYpCNpG3%2FTyRnoom0XJqEEIke1wuntVPYHD6QF0ZolJ6sHfcJ8v8YXAWgqpOHaTnYpyWJdVTxQQFSebgFO%2BD7ewLhJAPlaW7hnfpcsrc%2Fy8M%2BdOir%2BVdOxgTIEnDYHl6B5VfoLM1YWk3Z1h%2Bp3SeM2bw%3D%3D");
        uriBuilder.queryParam("SAMLRequest", URLEncoder.encode(getSAMLRequest(), "UTF-8"));

        URI uri = uriBuilder.build(true).toUri();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, HttpEntity.EMPTY, String.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    //@Test
    public void loggedin() {
        testConfigurationService.userToRole(Role.ROLL_A, restTemplate);
        // TODO make request for logged in user
    }

    private static String getSAMLRequest() throws Exception {
        return encodeMessage(buildAuthnRequest());
    }

    private static AuthnRequest buildAuthnRequest() {
        AuthnRequest authnRequest = SAMLBuilder.buildSAMLObject(AuthnRequest.class, AuthnRequest.DEFAULT_ELEMENT_NAME);
        
        authnRequest.setID(SAMLBuilder.randomSAMLId());
        authnRequest.setIssueInstant(new DateTime());
        authnRequest.setAssertionConsumerServiceURL("http://localhost/test-sp/acs");

        Issuer issuer = SAMLBuilder.buildSAMLObject(Issuer.class, Issuer.DEFAULT_ELEMENT_NAME);
        issuer.setValue("http://test-sp");
        authnRequest.setIssuer(issuer);

        return authnRequest;
    }

    private static String encodeMessage(SAMLObject message) throws Exception {
        Element domMessage = XMLObjectSupport.marshall(message);
        String messageXML = SerializeSupport.nodeToString(domMessage);
        
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        Deflater deflater = new Deflater(Deflater.DEFLATED, true);
        DeflaterOutputStream deflaterStream = new DeflaterOutputStream(bytesOut, deflater);
        deflaterStream.write(messageXML.getBytes("UTF-8"));
        deflaterStream.finish();

        return Base64Support.encode(bytesOut.toByteArray(), Base64Support.UNCHUNKED);
    }

}
