package ee.hitsa.ois.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;

import ee.hitsa.ois.bdoc.MobileIdSigningSession;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.AuthUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.sk.mid.MidClient;
import ee.sk.mid.MidDisplayTextFormat;
import ee.sk.mid.MidHashToSign;
import ee.sk.mid.MidHashType;
import ee.sk.mid.MidLanguage;
import ee.sk.mid.MidSignature;
import ee.sk.mid.exception.MidNotMidClientException;
import ee.sk.mid.rest.dao.MidSessionStatus;
import ee.sk.mid.rest.dao.request.MidCertificateRequest;
import ee.sk.mid.rest.dao.request.MidSignatureRequest;
import ee.sk.mid.rest.dao.response.MidCertificateChoiceResponse;
import ee.sk.mid.rest.dao.response.MidSignatureResponse;
import eu.europa.esig.dss.x509.ocsp.OCSPSource;
import org.digidoc4j.Configuration;
import org.digidoc4j.Container;
import org.digidoc4j.ContainerBuilder;
import org.digidoc4j.DataFile;
import org.digidoc4j.DataToSign;
import org.digidoc4j.DigestAlgorithm;
import org.digidoc4j.OCSPSourceBuilder;
import org.digidoc4j.Signature;
import org.digidoc4j.SignatureBuilder;
import org.digidoc4j.SignatureProfile;
import org.digidoc4j.TSLCertificateSource;
import org.digidoc4j.impl.asic.SKCommonCertificateVerifier;
import org.digidoc4j.impl.asic.tsl.LazyCertificatePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import ee.hitsa.ois.bdoc.UnsignedBdocContainer;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.exception.HoisException;
import eu.europa.esig.dss.validation.CertificateVerifier;
import eu.europa.esig.dss.validation.OCSPCertificateVerifier;
import eu.europa.esig.dss.x509.CertificateToken;
import eu.europa.esig.dss.x509.RevocationToken;

@Service
public class BdocService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${hois.digidoc4j.testMode:#{null}}")
    private Boolean isTestMode;
    @Value("${mobileid.signDisplayText}")
    private String signDisplayText;
    @Value("${mobileid.signDisplayTextEn}")
    private String signDisplayTextEn;

    private Configuration configuration;

    @Autowired
    private MidClient client;

    @PostConstruct
    public void postConstruct() {
        if (Boolean.TRUE.equals(isTestMode)) {
            System.setProperty("digidoc4j.mode", "TEST");
            configuration = new Configuration(Configuration.Mode.TEST);
            configuration.setTslLocation("https://open-eid.github.io/test-TL/tl-mp-test-EE.xml");
        } else {
            configuration = new Configuration(Configuration.Mode.PROD);
        }
        log.info("Digidoc4j is in {} mode", configuration.isTest() ? "TEST" : "PRODUCTION");
    }

    public UnsignedBdocContainer createUnsignedBdocContainer(String fileName, String mediaType, byte[] data, String certificateInHex) {
        DataFile dataFile = new DataFile(data, fileName, mediaType);
        
        InputStream in = null;
        X509Certificate cert509 = null;

        try {
            byte[] certEntryBytes = DatatypeConverter.parseHexBinary(certificateInHex);
            in = new ByteArrayInputStream(certEntryBytes);
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            cert509 = (X509Certificate) certFactory.generateCertificate(in);
        } catch (Exception ex) {
            log.error("BDOC PREPARE SIGNATURE ERROR: "+ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (in != null) {
                try {
                	in.close();
                } catch(IOException e) { e.printStackTrace(); }
            }
        }
        
        Container container = ContainerBuilder.aContainer()
                .withConfiguration(configuration)
                .withDataFile(dataFile)
                .build();
        	
        DataToSign dataToSign = SignatureBuilder.aSignature(container)
                .withSignatureDigestAlgorithm(DigestAlgorithm.SHA256)
                .withSigningCertificate(cert509)
                .withSignatureProfile(SignatureProfile.LT)
                .buildDataToSign();
        
        UnsignedBdocContainer unsignedBdocContainer = new UnsignedBdocContainer();
        unsignedBdocContainer.setContainer(container);
        unsignedBdocContainer.setDataToSign(dataToSign);
        
        return unsignedBdocContainer;
    }

    private static InputStream getSignedBdoc(Container container, DataToSign dataToSign, String signatureInHex) {
        byte[] signatureBytes = DatatypeConverter.parseHexBinary(signatureInHex);
        Signature signature = dataToSign.finalize(signatureBytes);
        log.info("BDOC signature OK");
        container.addSignature(signature);
        log.info("BDOC signature added to container");
        return container.saveAsStream();
    }

    public OisFile getSignedBdoc(Container container, DataToSign dataToSign, String signature, String fileNamePrefix) {
        try(InputStream bdocInputStream = getSignedBdoc(container, dataToSign, signature)) {
            OisFile bdoc = new OisFile();
            bdoc.setFname(fileNamePrefix + ".asice");
            bdoc.setFtype(Container.DocumentType.ASICE.toString());
            bdoc.setFdata(StreamUtils.copyToByteArray(bdocInputStream));
            return bdoc;
        } catch (Exception e) {
            log.error("BDOC_ERROR save file error");
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
        OCSPSource ocspSource = OCSPSourceBuilder.anOcspSource().withConfiguration(configuration).build();
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

    private CertificateVerifier getCertificateVerifier(OCSPSource ocspSource) {
        //org.digidoc4j.impl.bdoc.xades.XadesValidationDssFacade
        CertificateVerifier certificateVerifier = new SKCommonCertificateVerifier();
        certificateVerifier.setOcspSource(ocspSource);
        TSLCertificateSource tsl = configuration.getTSL();
        certificateVerifier.setTrustedCertSource(tsl);
        certificateVerifier.setCrlSource(null); //Disable CRL checks
        certificateVerifier.setSignatureCRLSource(null); //Disable CRL checks
        return certificateVerifier;
    }

    public MobileIdSigningSession mobileIdSignatureRequest(String idcode, String mobileNumber, DataFile dataFile,
            Language lang) {
        mobileNumber = AuthUtil.validateMobileNumber(mobileNumber);

        Container container = ContainerBuilder.aContainer()
                .withConfiguration(configuration)
                .withDataFile(dataFile)
                .build();

        X509Certificate certificate = getMobileIdCertificate(idcode, mobileNumber);
        DataToSign dataToSign = SignatureBuilder.aSignature(container)
                .withSigningCertificate(certificate)
                .withSignatureDigestAlgorithm(DigestAlgorithm.SHA256)
                .withSignatureProfile(SignatureProfile.LT)
                .buildDataToSign();

        MidHashToSign hashToSign = MidHashToSign.newBuilder()
                .withDataToHash(dataToSign.getDataToSign())
                .withHashType(MidHashType.SHA256)
                .build();

        MidSignatureRequest signatureRequest = MidSignatureRequest.newBuilder()
                .withNationalIdentityNumber(idcode)
                .withPhoneNumber(mobileNumber)
                .withHashToSign(hashToSign)
                .withLanguage(Language.EN.equals(lang) ? MidLanguage.ENG : MidLanguage.EST)
                .withDisplayText(Language.EN.equals(lang) ? signDisplayTextEn : signDisplayText)
                .withDisplayTextFormat(MidDisplayTextFormat.GSM7)
                .build();

        MidSignatureResponse response = client.getMobileIdConnector().sign(signatureRequest);

        MobileIdSigningSession session = new MobileIdSigningSession(response.getSessionID(), dataToSign, container);
        session.setVerificationCode(hashToSign.calculateVerificationCode());
        return session;
    }

    public X509Certificate getMobileIdCertificate(String idcode, String mobileNumber) {
        MidCertificateRequest request = MidCertificateRequest.newBuilder()
                .withPhoneNumber(mobileNumber)
                .withNationalIdentityNumber(idcode)
                .build();

        try {
            MidCertificateChoiceResponse response = client.getMobileIdConnector().getCertificate(request);
            return client.createMobileIdCertificate(response);
        } catch (MidNotMidClientException e) {
            log.info(e.getMessage());
            throw new ValidationFailedException("main.login.mobileid.notMobileIdUser");
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ValidationFailedException("main.messages.error.mobileIdSignFailed");
        }
    }

    public OisFile mobileIdSign(MobileIdSigningSession session, String fileNamePrefix) {
        try {
            MidSessionStatus sessionStatus = client.getSessionStatusPoller()
                    .fetchFinalSignatureSessionStatus(session.getSessionID());

            MidSignature mobileIdSignature = client.createMobileIdSignature(sessionStatus);

            Signature signature = session.getDataToSign().finalize(mobileIdSignature.getValue());
            session.getContainer().addSignature(signature);

            OisFile bdoc = new OisFile();
            bdoc.setFname(fileNamePrefix + ".asice");
            bdoc.setFtype(Container.DocumentType.ASICE.toString());
            bdoc.setFdata(StreamUtils.copyToByteArray(session.getContainer().saveAsStream()));
            return bdoc;
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ValidationFailedException("main.messages.error.mobileIdSignFailed");
        }
    }
}
