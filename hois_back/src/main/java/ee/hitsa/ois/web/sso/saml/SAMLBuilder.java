package ee.hitsa.ois.web.sso.saml;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallerFactory;
import org.opensaml.core.xml.schema.XSAny;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.core.xml.schema.impl.XSStringBuilder;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.AttributeValue;
import org.opensaml.saml.saml2.core.Audience;
import org.opensaml.saml.saml2.core.AudienceRestriction;
import org.opensaml.saml.saml2.core.AuthenticatingAuthority;
import org.opensaml.saml.saml2.core.AuthnContext;
import org.opensaml.saml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml.saml2.core.AuthnStatement;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDType;
import org.opensaml.saml.saml2.core.Status;
import org.opensaml.saml.saml2.core.StatusCode;
import org.opensaml.saml.saml2.core.StatusMessage;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.signature.SignableXMLObject;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureConstants;
import org.opensaml.xmlsec.signature.support.Signer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 
 * based on: https://github.com/OpenConext/Mujina
 *
 */
public class SAMLBuilder {

    private static final XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
    private static final MarshallerFactory marshallerFactory = XMLObjectProviderRegistrySupport.getMarshallerFactory();

    public static <T> T buildSAMLObject(final Class<T> objectClass, QName qName) {
        return objectClass.cast(builderFactory.getBuilder(qName).buildObject(qName));
    }

    public static Issuer buildIssuer(String issuingEntityName) {
        Issuer issuer = buildSAMLObject(Issuer.class, Issuer.DEFAULT_ELEMENT_NAME);
        issuer.setValue(issuingEntityName);
        issuer.setFormat(NameIDType.ENTITY);
        return issuer;
    }

    private static Subject buildSubject(String subjectNameId, String subjectNameIdType, String recipient,
            String inResponseTo) {
        NameID nameID = buildSAMLObject(NameID.class, NameID.DEFAULT_ELEMENT_NAME);
        nameID.setValue(subjectNameId);
        nameID.setFormat(subjectNameIdType);

        Subject subject = buildSAMLObject(Subject.class, Subject.DEFAULT_ELEMENT_NAME);
        subject.setNameID(nameID);

        SubjectConfirmation subjectConfirmation = buildSAMLObject(SubjectConfirmation.class,
                SubjectConfirmation.DEFAULT_ELEMENT_NAME);
        subjectConfirmation.setMethod(SubjectConfirmation.METHOD_BEARER);

        SubjectConfirmationData subjectConfirmationData = buildSAMLObject(SubjectConfirmationData.class,
                SubjectConfirmationData.DEFAULT_ELEMENT_NAME);

        subjectConfirmationData.setRecipient(recipient);
        subjectConfirmationData.setInResponseTo(inResponseTo);
        subjectConfirmationData.setNotOnOrAfter(new DateTime().plusMinutes(8 * 60));
        subjectConfirmationData.setAddress(recipient);

        subjectConfirmation.setSubjectConfirmationData(subjectConfirmationData);

        subject.getSubjectConfirmations().add(subjectConfirmation);

        return subject;
    }

    public static Status buildStatus(String value) {
        Status status = buildSAMLObject(Status.class, Status.DEFAULT_ELEMENT_NAME);
        StatusCode statusCode = buildSAMLObject(StatusCode.class, StatusCode.DEFAULT_ELEMENT_NAME);
        statusCode.setValue(value);
        status.setStatusCode(statusCode);
        return status;
    }

    public static Status buildStatus(String value, String subStatus, String message) {
        Status status = buildStatus(value);

        StatusCode subStatusCode = buildSAMLObject(StatusCode.class, StatusCode.DEFAULT_ELEMENT_NAME);
        subStatusCode.setValue(subStatus);
        status.getStatusCode().setStatusCode(subStatusCode);

        StatusMessage statusMessage = buildSAMLObject(StatusMessage.class, StatusMessage.DEFAULT_ELEMENT_NAME);
        statusMessage.setMessage(message);
        status.setStatusMessage(statusMessage);

        return status;
    }

    public static Assertion buildAssertion(SAMLPrincipal principal, Status status, String entityId) {
        Assertion assertion = buildSAMLObject(Assertion.class, Assertion.DEFAULT_ELEMENT_NAME);

        if (status.getStatusCode().getValue().equals(StatusCode.SUCCESS)) {
            Subject subject = buildSubject(principal.getNameID(), principal.getNameIDType(),
                    principal.getAssertionConsumerServiceURL(), principal.getRequestID());
            assertion.setSubject(subject);
        }

        Issuer issuer = buildIssuer(entityId);

        Audience audience = buildSAMLObject(Audience.class, Audience.DEFAULT_ELEMENT_NAME);
        audience.setAudienceURI(principal.getServiceProviderEntityID());
        AudienceRestriction audienceRestriction = buildSAMLObject(AudienceRestriction.class,
                AudienceRestriction.DEFAULT_ELEMENT_NAME);
        audienceRestriction.getAudiences().add(audience);

        Conditions conditions = buildSAMLObject(Conditions.class, Conditions.DEFAULT_ELEMENT_NAME);
        conditions.getAudienceRestrictions().add(audienceRestriction);
        assertion.setConditions(conditions);

        AuthnStatement authnStatement = buildAuthnStatement(new DateTime(), entityId);

        assertion.setIssuer(issuer);
        assertion.getAuthnStatements().add(authnStatement);

        assertion.getAttributeStatements().add(buildAttributeStatement(principal.getAttributes()));

        assertion.setID(randomSAMLId());
        assertion.setIssueInstant(new DateTime());

        return assertion;
    }

    public static void signAssertion(SignableXMLObject signableXMLObject, Credential signingCredential, String algorithm)
            throws Exception {
        Signature signature = buildSAMLObject(Signature.class, Signature.DEFAULT_ELEMENT_NAME);

        signature.setSigningCredential(signingCredential);
        signature.setSignatureAlgorithm(algorithm);
        signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);

        signableXMLObject.setSignature(signature);

        marshallerFactory.getMarshaller(signableXMLObject).marshall(signableXMLObject);
        Signer.signObject(signature);
    }

    public static Optional<String> getStringValueFromXMLObject(XMLObject xmlObj) {
        if (xmlObj instanceof XSString) {
            return Optional.of(((XSString) xmlObj).getValue());
        } else if (xmlObj instanceof XSAny) {
            XSAny xsAny = (XSAny) xmlObj;
            String textContent = xsAny.getTextContent();
            if (StringUtils.hasText(textContent)) {
                return Optional.of(textContent);
            }
            List<XMLObject> unknownXMLObjects = xsAny.getUnknownXMLObjects();
            if (!CollectionUtils.isEmpty(unknownXMLObjects)) {
                XMLObject xmlObject = unknownXMLObjects.get(0);
                if (xmlObject instanceof NameID) {
                    NameID nameID = (NameID) xmlObject;
                    return Optional.of(nameID.getValue());
                }
            }
        }
        return Optional.empty();
    }

    public static String randomSAMLId() {
        return "_" + UUID.randomUUID().toString();
    }

    private static AuthnStatement buildAuthnStatement(DateTime authnInstant, String entityID) {
        AuthnContextClassRef authnContextClassRef = buildSAMLObject(AuthnContextClassRef.class,
                AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
        authnContextClassRef.setAuthnContextClassRef(AuthnContext.PASSWORD_AUTHN_CTX);

        AuthenticatingAuthority authenticatingAuthority = buildSAMLObject(AuthenticatingAuthority.class,
                AuthenticatingAuthority.DEFAULT_ELEMENT_NAME);
        authenticatingAuthority.setURI(entityID);

        AuthnContext authnContext = buildSAMLObject(AuthnContext.class, AuthnContext.DEFAULT_ELEMENT_NAME);
        authnContext.setAuthnContextClassRef(authnContextClassRef);
        authnContext.getAuthenticatingAuthorities().add(authenticatingAuthority);

        AuthnStatement authnStatement = buildSAMLObject(AuthnStatement.class, AuthnStatement.DEFAULT_ELEMENT_NAME);
        authnStatement.setAuthnContext(authnContext);

        authnStatement.setAuthnInstant(authnInstant);

        return authnStatement;

    }

    private static AttributeStatement buildAttributeStatement(List<SAMLAttribute> attributes) {
        AttributeStatement attributeStatement = buildSAMLObject(AttributeStatement.class, AttributeStatement.DEFAULT_ELEMENT_NAME);

        for (SAMLAttribute entry : attributes) {
            attributeStatement.getAttributes().add(buildAttribute(entry.getName(), entry.getValues()));
        }
        return attributeStatement;
    }

    private static Attribute buildAttribute(String name, Collection<String> values) {
        XSStringBuilder stringBuilder = (XSStringBuilder) builderFactory.getBuilder(XSString.TYPE_NAME);

        Attribute attribute = buildSAMLObject(Attribute.class, Attribute.DEFAULT_ELEMENT_NAME);
        attribute.setName(name);
        attribute.setNameFormat("urn:oasis:names:tc:SAML:2.0:attrname-format:uri");
        List<XSString> xsStringList = values.stream().map(value -> {
            XSString stringValue = stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
            stringValue.setValue(value);
            return stringValue;
        }).collect(Collectors.toList());

        attribute.getAttributeValues().addAll(xsStringList);
        return attribute;
    }

}
