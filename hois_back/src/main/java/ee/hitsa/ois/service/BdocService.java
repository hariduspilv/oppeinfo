package ee.hitsa.ois.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.soap.Detail;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

import org.digidoc4j.Configuration;
import org.digidoc4j.Container;
import org.digidoc4j.ContainerBuilder;
import org.digidoc4j.DataToSign;
import org.digidoc4j.DigestAlgorithm;
import org.digidoc4j.Signature;
import org.digidoc4j.SignatureBuilder;
import org.digidoc4j.SignatureProfile;
import org.digidoc4j.TSLCertificateSource;
import org.digidoc4j.impl.bdoc.SKCommonCertificateVerifier;
import org.digidoc4j.impl.bdoc.ocsp.OcspSourceBuilder;
import org.digidoc4j.impl.bdoc.ocsp.SKOnlineOCSPSource;
import org.digidoc4j.impl.bdoc.tsl.LazyCertificatePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import ee.hitsa.ois.bdoc.MobileIdSession;
import ee.hitsa.ois.bdoc.UnsignedBdocContainer;
import ee.hitsa.ois.config.MobileIdProperties;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.util.CertificateUtil;
import ee.hois.soap.dds.service.AddDataFileRequest;
import ee.hois.soap.dds.service.AddDataFileResponse;
import ee.hois.soap.dds.service.CreateSignedDocResponse;
import ee.hois.soap.dds.service.DigiDocServiceClient;
import ee.hois.soap.dds.service.GetSignedDocResponse;
import ee.hois.soap.dds.service.GetStatusInfoResponse;
import ee.hois.soap.dds.service.MobileSignRequest;
import ee.hois.soap.dds.service.MobileSignResponse;
import ee.hois.soap.dds.service.StartSessionResponse;
import eu.europa.esig.dss.validation.CertificateVerifier;
import eu.europa.esig.dss.validation.OCSPCertificateVerifier;
import eu.europa.esig.dss.x509.CertificateToken;
import eu.europa.esig.dss.x509.RevocationToken;

@Service
public class BdocService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String BDOC_MIME_TYPE = "application/vnd.etsi.asic-e+zip";

    @Value("${hois.digidoc4j.testMode:#{null}}")
    private Boolean isTestMode;

    private Configuration configuration;

    @Autowired
    private DigiDocServiceClient ddsClient;
    @Autowired
    private MobileIdProperties properties;

    @PostConstruct
    public void postConstruct() {
        if (Boolean.TRUE.equals(isTestMode)) {
            configuration = new Configuration(Configuration.Mode.TEST);
            configuration.setTslLocation("https://open-eid.github.io/test-TL/tl-mp-test-EE.xml");
        } else {
            configuration = new Configuration(Configuration.Mode.PROD);
        }
        log.info("Digidoc4j is in {} mode", configuration.isTest() ? "TEST" : "PRODUCTION");
    }

    private UnsignedBdocContainer createUnsignedBdocContainer(String fileName, String mediaType, ByteArrayInputStream data, String certificateInHex) {
        UnsignedBdocContainer unsignedBdocContainer = new UnsignedBdocContainer();
        Container bdocContainer = createBdocContainer();
        bdocContainer.addDataFile(data, fileName, mediaType);
        unsignedBdocContainer.setContainer(bdocContainer);
        unsignedBdocContainer.setDataToSign(getDataToSign(bdocContainer, certificateInHex));
        return unsignedBdocContainer;
    }

    public UnsignedBdocContainer createUnsignedBdocContainer(String fileName, String applicationPdfValue, byte[] file, String certificate) {
        return createUnsignedBdocContainer(fileName, applicationPdfValue, new ByteArrayInputStream(file), certificate);
    }

    private DataToSign getDataToSign(Container container, String certificateInHex) {
        SignatureBuilder signatureBuilder = SignatureBuilder.aSignature(container)
                .withSignatureDigestAlgorithm(DigestAlgorithm.SHA256)
                .withSigningCertificate(CertificateUtil.getCertificateFromHex(certificateInHex));
        if (Boolean.TRUE.equals(isTestMode)) {
            //using no profile mode to pass OCSP, for signing with PROD ID-card in TEST mode. https://github.com/open-eid/digidoc4j/wiki/Questions-&-Answers#if-ocsp-request-has-failed
            signatureBuilder.withSignatureProfile(SignatureProfile.B_EPES);
        }
        return signatureBuilder.buildDataToSign();
    }

    private Container createBdocContainer() {
        return ContainerBuilder.aContainer("BDOC")
        .withConfiguration(configuration)
        .build();
    }

    private static InputStream getSignedBdoc(Container container, DataToSign dataToSign, String signatureInHex) {
        Signature signature = dataToSign.finalize(CertificateUtil.hexToBytes(signatureInHex));
        container.addSignature(signature);
        return container.saveAsStream();
    }

    public OisFile getSignedBdoc(UnsignedBdocContainer unsignedBdocContainer, String signature, String fileNamePrefix) {
        try(InputStream bdocInputStream = getSignedBdoc(unsignedBdocContainer.getContainer(), unsignedBdocContainer.getDataToSign(), signature)) {
            OisFile bdoc = new OisFile();
            bdoc.setFname(fileNamePrefix + ".bdoc");
            bdoc.setFtype(BDOC_MIME_TYPE);
            bdoc.setFdata(StreamUtils.copyToByteArray(bdocInputStream));
            return bdoc;
        } catch (IOException e) {
            throw new HoisException("main.messages.error.bdocSigningFailed", e);
        }
    }

    public boolean isValidEstonianIdCardCertificate(X509Certificate certificate) {
        //in test mode skip OCSP check
        if (Boolean.TRUE.equals(isTestMode)) {
            return true;
        }

        try {
            return doOcspCheck(certificate).isValid();
        } catch (Exception e) {
            log.error("estonian id card certificate ocsp check failed", e);
            return false;
        }
    }

    private RevocationToken doOcspCheck(X509Certificate certificate) {
        SKOnlineOCSPSource ocspSource = OcspSourceBuilder.anOcspSource().withConfiguration(configuration).build();
        CertificateVerifier certificateVerifier = getCertificateVerifier(ocspSource);
        LazyCertificatePool lazyCertificatePool = new LazyCertificatePool(certificateVerifier.getTrustedCertSource());

        OCSPCertificateVerifier ocspCertificateVerifier = new OCSPCertificateVerifier(ocspSource, lazyCertificatePool);
        CertificateToken certificateToken = new CertificateToken(certificate);

        List<CertificateToken> issuers = lazyCertificatePool.get(certificateToken.getIssuerX500Principal());
        if (!issuers.isEmpty()) {
            certificateToken.isSignedBy(issuers.get(0));
        }
        return ocspCertificateVerifier.check(certificateToken);
    }

    private CertificateVerifier getCertificateVerifier(SKOnlineOCSPSource ocspSource) {
        //org.digidoc4j.impl.bdoc.xades.XadesValidationDssFacade
        CertificateVerifier certificateVerifier = new SKCommonCertificateVerifier();
        certificateVerifier.setOcspSource(ocspSource);
        TSLCertificateSource tsl = configuration.getTSL();
        certificateVerifier.setTrustedCertSource(tsl);
        certificateVerifier.setCrlSource(null); //Disable CRL checks
        certificateVerifier.setSignatureCRLSource(null); //Disable CRL checks
        return certificateVerifier;
    }
    
    public MobileIdSession mobileSign(String fileName, String mimeType, byte[] file, String mobileNumber) {
        MobileIdSession session = new MobileIdSession();
        try {
            StartSessionResponse sessionResponse = ddsClient.startSession(properties.getEndpoint());
            if (!DigiDocServiceClient.RESPONSE_SUCCESS.equals(sessionResponse.getStatus())) {
                log.warn("Failed to create mobile signing session: {}", sessionResponse.getStatus());
                return session;
            }
            session.setSesscode(sessionResponse.getSesscode());
            CreateSignedDocResponse containerResponse = ddsClient.createSignedDoc(session.getSesscode(), properties.getEndpoint());
            if (!DigiDocServiceClient.RESPONSE_SUCCESS.equals(containerResponse.getStatus())) {
                log.warn("Failed to create mobile signing container: {}", containerResponse.getStatus());
                return session;
            }
            AddDataFileRequest addFileRequest = new AddDataFileRequest();
            addFileRequest.setSesscode(session.getSesscode());
            addFileRequest.setFileName(fileName);
            addFileRequest.setMimeType(mimeType);
            addFileRequest.setSize(file.length);
            addFileRequest.setContent(Base64.getEncoder().encodeToString(file));
            AddDataFileResponse addFileResponse = ddsClient.addDataFile(addFileRequest, properties.getEndpoint());
            if (!DigiDocServiceClient.RESPONSE_SUCCESS.equals(addFileResponse.getStatus())) {
                log.warn("Failed to add data file for mobile signing: {}", addFileResponse.getStatus());
                return session;
            }
            MobileSignRequest signRequest = new MobileSignRequest();
            signRequest.setSesscode(session.getSesscode());
            signRequest.setSignersCountry("EE");
            signRequest.setSignerPhoneNo(mobileNumber);
            signRequest.setServiceName(properties.getServiceName());
            signRequest.setAdditionalDataToBeDisplayed(properties.getMessageToDisplay());
            signRequest.setLanguage("EST");
            signRequest.setMessagingMode("asynchClientServer");
            signRequest.setReturnDocInfo(false);
            signRequest.setReturnDocData(false);
            MobileSignResponse signResponse = ddsClient.mobileSign(signRequest, properties.getEndpoint());
            if (!DigiDocServiceClient.RESPONSE_SUCCESS.equals(signResponse.getStatus()) || !"0".equals(signResponse.getStatusCode())) {
                log.warn("Failed mobile sign: {} {}", signResponse.getStatus(), signResponse.getStatusCode());
                return session;
            }
            session.setChallengeID(signResponse.getChallengeID());
        } catch (SOAPFaultException e) {
            logFault(e.getFault());
        }
        return session;
    }
    
    public static boolean closeMobileSession(String statusCode) {
        return !"OUTSTANDING_TRANSACTION".equals(statusCode) && !"SIGNATURE".equals(statusCode);
    }
    
    public String mobileSignStatus(Integer sesscode) {
        try {
            GetStatusInfoResponse response = ddsClient.getStatusInfo(sesscode, properties.getEndpoint());
            if (closeMobileSession(response.getStatusCode())) {
                try {
                    ddsClient.closeSession(sesscode, properties.getEndpoint());
                } catch (SOAPFaultException e) {
                    logFault(e.getFault());
                }
            }
            return response.getStatusCode();
        } catch (SOAPFaultException e) {
            logFault(e.getFault());
            return null;
        }
    }

    public OisFile getMobileSignedBdoc(Integer sesscode, String fileNamePrefix) {
        try {
            GetSignedDocResponse response = ddsClient.getSignedDoc(sesscode, properties.getEndpoint());
            if (!DigiDocServiceClient.RESPONSE_SUCCESS.equals(response.getStatus()) || response.getSignedDocData() == null) {
                log.warn("Failed to get signed document: {}", response.getStatus());
                return null;
            }
            try {
                ddsClient.closeSession(sesscode, properties.getEndpoint());
            } catch (SOAPFaultException e) {
                logFault(e.getFault());
            }
            OisFile bdoc = new OisFile();
            bdoc.setFname(fileNamePrefix + ".bdoc");
            bdoc.setFtype(BDOC_MIME_TYPE);
            bdoc.setFdata(Base64.getDecoder().decode(response.getSignedDocData().replaceAll("\\s", "")));
            return bdoc;
        } catch (SOAPFaultException e) {
            logFault(e.getFault());
            return null;
        }
    }

    private static void logFault(SOAPFault fault) {
        Detail detail = fault.getDetail();
        log.info("SOAPFault {} detail message: {}", fault.getFaultString(), (detail != null ? detail.getTextContent() : ""));
    }
    
}
