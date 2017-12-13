package ee.hitsa.ois.web.sso.idp;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.web.sso.saml.SAMLBuilder.buildAssertion;
import static ee.hitsa.ois.web.sso.saml.SAMLBuilder.buildIssuer;
import static ee.hitsa.ois.web.sso.saml.SAMLBuilder.buildSAMLObject;
import static ee.hitsa.ois.web.sso.saml.SAMLBuilder.buildStatus;
import static ee.hitsa.ois.web.sso.saml.SAMLBuilder.signAssertion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.log.CommonsLogLogChute;
import org.joda.time.DateTime;
import org.opensaml.core.config.InitializationService;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.SAMLException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.binding.security.impl.MessageLifetimeSecurityHandler;
import org.opensaml.saml.common.binding.security.impl.MessageReplaySecurityHandler;
import org.opensaml.saml.common.binding.security.impl.ReceivedEndpointSecurityHandler;
import org.opensaml.saml.common.messaging.context.SAMLBindingContext;
import org.opensaml.saml.common.messaging.context.SAMLEndpointContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLSelfEntityContext;
import org.opensaml.saml.saml2.binding.decoding.impl.HTTPRedirectDeflateDecoder;
import org.opensaml.saml.saml2.binding.encoding.impl.HTTPPostEncoder;
import org.opensaml.saml.saml2.binding.encoding.impl.HTTPPostSimpleSignEncoder;
import org.opensaml.saml.saml2.binding.security.impl.SAML2AuthnRequestsSignedSecurityHandler;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.NameIDType;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.Status;
import org.opensaml.saml.saml2.core.StatusCode;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.opensaml.saml.saml2.metadata.SingleSignOnService;
import org.opensaml.security.credential.Credential;
import org.opensaml.storage.ReplayCache;
import org.opensaml.storage.impl.MemoryStorageService;
import org.opensaml.xmlsec.SignatureSigningParameters;
import org.opensaml.xmlsec.context.SecurityParametersContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.config.IdpConfiguration;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.sso.saml.SAMLAttribute;
import ee.hitsa.ois.web.sso.saml.SAMLBuilder;
import ee.hitsa.ois.web.sso.saml.SAMLPrincipal;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;

public class SAMLMessageHandler {

    private static final int VALID_UNTIL_DURATION = 14400000;
    private static final String ATTRIBUTE_SCOPE = "@hois.ee";
    private static final String IDCODE_PREFIX = "ee:EID:";
    private static final Map<String, String> ROLE_MAP = new HashMap<>();
    private static final List<RoleSet> ROLE_SETS = Arrays.asList(
            new RoleSet("employee", "staff", "faculty"),
            new RoleSet("member", "student", "staff", "faculty"));

    static {
        ROLE_MAP.put(Role.ROLL_T.name(), "student");
        ROLE_MAP.put(Role.ROLL_O.name(), "faculty");
        ROLE_MAP.put(Role.ROLL_P.name(), "staff");
        ROLE_MAP.put(Role.ROLL_A.name(), "staff");
        ROLE_MAP.put(Role.ROLL_V.name(), "staff");
    }

    @Autowired
    private IdpConfiguration idpConfiguration;
    @Autowired
    private EntityManager em;

    private VelocityEngine velocityEngine;
    private MessageReplaySecurityHandler replayValidator;
    private MessageLifetimeSecurityHandler lifetimeValidator;
    private SAML2AuthnRequestsSignedSecurityHandler signedValidator;

    @PostConstruct
    private void postConstruct() throws Exception {
        InitializationService.initialize();

        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("input.encoding", "UTF-8");
        velocityEngine.setProperty("output.encoding", "UTF-8");
        velocityEngine.setProperty("resource.loader", "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.setProperty("runtime.log.logsystem", new CommonsLogLogChute());
        velocityEngine.init();

        replayValidator = new MessageReplaySecurityHandler();
        replayValidator.setExpires(VALID_UNTIL_DURATION);
        ReplayCache replaceCache = new ReplayCache();
        MemoryStorageService storageService = new MemoryStorageService();
        storageService.setId("replay-cache");
        storageService.setCleanupInterval(VALID_UNTIL_DURATION);
        storageService.initialize();
        replaceCache.setStorage(storageService);
        replaceCache.initialize();
        replayValidator.setReplayCache(replaceCache);
        replayValidator.initialize();

        lifetimeValidator = new MessageLifetimeSecurityHandler();
        lifetimeValidator.setClockSkew(TimeUnit.SECONDS.toMillis(idpConfiguration.getClockSkew()));
        lifetimeValidator.setMessageLifetime(TimeUnit.SECONDS.toMillis(idpConfiguration.getExpires()));
        lifetimeValidator.initialize();
        
        signedValidator = new SAML2AuthnRequestsSignedSecurityHandler();
        signedValidator.initialize();
    }

    public MessageContext<SAMLObject> extractSAMLMessageContext(HttpServletRequest request) 
            throws ComponentInitializationException, MessageDecodingException {
        HTTPRedirectDeflateDecoder decoder = new HTTPRedirectDeflateDecoder();
        decoder.setParserPool(XMLObjectProviderRegistrySupport.getParserPool());
        decoder.setHttpServletRequest(request);
        decoder.initialize();
        decoder.decode();
        return decoder.getMessageContext();
    }

    @SuppressWarnings("unchecked")
    private void validateRequest(HttpServletRequest request, MessageContext<SAMLObject> context)
            throws SAMLException, ComponentInitializationException {
        try {
            if (idpConfiguration.isCompareEndpoints()) {
                ReceivedEndpointSecurityHandler receivedEndpoint = new ReceivedEndpointSecurityHandler();
                receivedEndpoint.setHttpServletRequest(request);
                receivedEndpoint.initialize();
                receivedEndpoint.invoke(context);
            }
            replayValidator.invoke(context);
            lifetimeValidator.invoke(context);
            signedValidator.invoke(context);
        } catch (MessageHandlerException e) {
            throw new SAMLException("Incoming request message is not valid", e);
        }
    }

    public void sendAuthnResponse(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
            throws Exception {

        MessageContext<SAMLObject> requestMessageContext = extractSAMLMessageContext(request);
        AuthnRequest authnRequest = (AuthnRequest) requestMessageContext.getMessage();
        
        validateRequest(request, requestMessageContext);

        String assertionConsumerServiceURL = idpConfiguration.getAcsEndpoint();
        if(assertionConsumerServiceURL == null) {
            assertionConsumerServiceURL = authnRequest.getAssertionConsumerServiceURL();
        }

        SAMLPrincipal principal = new SAMLPrincipal(authentication.getName(), NameIDType.UNSPECIFIED,
                attributes(authentication), authnRequest.getIssuer().getValue(), authnRequest.getID(),
                assertionConsumerServiceURL, requestMessageContext.getSubcontext(SAMLBindingContext.class).getRelayState());

        String entityId = idpConfiguration.getEntityId();
        Credential signingCredential = idpConfiguration.resolveCredential();
        String signatureAlgorithm = idpConfiguration.getSignatureAlgorithm();

        Response authResponse = buildSAMLObject(Response.class, Response.DEFAULT_ELEMENT_NAME);
        Issuer issuer = buildIssuer(entityId);

        authResponse.setIssuer(issuer);
        authResponse.setID(SAMLBuilder.randomSAMLId());
        authResponse.setIssueInstant(new DateTime());
        authResponse.setInResponseTo(principal.getRequestID());

        Status status = buildStatus(StatusCode.SUCCESS);
        Assertion assertion = buildAssertion(principal, status, entityId);
        signAssertion(assertion, signingCredential, signatureAlgorithm);

        authResponse.getAssertions().add(assertion);
        authResponse.setDestination(principal.getAssertionConsumerServiceURL());

        authResponse.setStatus(status);

        Endpoint endpoint = buildSAMLObject(Endpoint.class, SingleSignOnService.DEFAULT_ELEMENT_NAME);
        endpoint.setLocation(principal.getAssertionConsumerServiceURL());

        MessageContext<SAMLObject> responseMessageContext = new MessageContext<>();

        responseMessageContext
            .getSubcontext(SAMLPeerEntityContext.class, true)
            .getSubcontext(SAMLEndpointContext.class, true)
            .setEndpoint(endpoint);
        responseMessageContext.setMessage(authResponse);
        SignatureSigningParameters signatureSigningParameters = new SignatureSigningParameters();
        signatureSigningParameters.setSigningCredential(signingCredential);
        signatureSigningParameters.setSignatureAlgorithm(signatureAlgorithm);
        responseMessageContext
            .getSubcontext(SecurityParametersContext.class, true)
            .setSignatureSigningParameters(signatureSigningParameters);

        responseMessageContext
            .getSubcontext(SAMLSelfEntityContext.class, true)
            .setEntityId(entityId);
        SAMLBindingSupport.setRelayState(responseMessageContext, principal.getRelayState());

        HTTPPostEncoder encoder = getEncoder(response, responseMessageContext);
        encoder.encode();
    }

    private HTTPPostEncoder getEncoder(HttpServletResponse response, MessageContext<SAMLObject> responseMessageContext)
            throws ComponentInitializationException {
        HTTPPostSimpleSignEncoder encoder = new HTTPPostSimpleSignEncoder();
        encoder.setVelocityEngine(velocityEngine);
        encoder.setMessageContext(responseMessageContext);
        encoder.setHttpServletResponse(response);
        encoder.initialize();
        return encoder;
    }

    private List<SAMLAttribute> attributes(Authentication authentication) throws SAMLException {
        HoisUserDetails user = HoisUserDetails.fromPrincipal(authentication);
        List<?> data = em.createNativeQuery(
                "select p.firstname, p.lastname, p.idcode, p.email, s.email as s_email, t.email as t_email"
                + " from user_ u inner join person p on u.person_id = p.id"
                + " left outer join student s on u.student_id = s.id"
                + " left outer join teacher t on u.teacher_id = t.id"
                + " where u.id = ?1")
                .setParameter(1, user.getUserId())
                .setMaxResults(1)
                .getResultList();
        if (data.isEmpty()) {
            throw new SAMLException("User not found");
        }
        Object row = data.get(0);
        String firstname = resultAsString(row, 0);
        String lastname = resultAsString(row, 1);
        String idcode = resultAsString(row, 2);
        String email = resultAsString(row, 3);
        String s_email = resultAsString(row, 4);
        String t_email = resultAsString(row, 5);
        if (StringUtils.hasText(t_email)) {
            email = t_email;
        } else if (StringUtils.hasText(s_email)) {
            email = s_email;
        }

        List<SAMLAttribute> result = new ArrayList<>();
        result.add(new SAMLAttribute("sn", lastname));
        result.add(new SAMLAttribute("cn", PersonUtil.fullname(firstname, lastname)));
        result.add(new SAMLAttribute("eduPersonPrincipalName", user.getPersonId() + ATTRIBUTE_SCOPE));
        result.add(new SAMLAttribute("mail", email));
        result.add(new SAMLAttribute("displayName", firstname));
        result.add(new SAMLAttribute("eduPersonAffiliation", roles(user.getPersonId())));
        result.add(new SAMLAttribute("givenName", firstname));
        if (StringUtils.hasText(idcode)) {
            result.add(new SAMLAttribute("schacPersonalUniqueID", IDCODE_PREFIX + idcode));
        }
        return result;
    }

    private Set<String> roles(Long personId) throws SAMLException {
        List<?> data = em.createNativeQuery(
                "select distinct role_code from user_ where person_id = ?1"
                + " and (valid_from is null or valid_from <= current_date)"
                + " and (valid_thru is null or valid_thru >= current_date)")
                .setParameter(1, personId)
                .getResultList();
        if (data.isEmpty()) {
            throw new SAMLException("Role not found");
        }
        Set<String> userRoles = StreamUtil.toMappedSet(ROLE_MAP::get, data.stream()
                .map(r -> resultAsString(r, 0))
                .filter(ROLE_MAP::containsKey));
        userRoles.addAll(StreamUtil.toMappedList(RoleSet::getName, ROLE_SETS.stream()
                .filter(set -> userRoles.containsAll(set.getMembers()))));

        return userRoles;
    }

    private static class RoleSet {
        private final String name;
        private final Set<String> members = new HashSet<>();

        public RoleSet(String name, String... members) {
            this.name = name;
            this.members.addAll(Arrays.asList(members));
        }

        public String getName() {
            return name;
        }

        public Set<String> getMembers() {
            return members;
        }
    }
}
