package ee.hitsa.ois.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.digidoc4j.Configuration;
import org.digidoc4j.Container;
import org.digidoc4j.ContainerBuilder;
import org.digidoc4j.DataToSign;
import org.digidoc4j.DigestAlgorithm;
import org.digidoc4j.Signature;
import org.digidoc4j.SignatureBuilder;
import org.digidoc4j.SignatureProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.bdoc.UnsignedBdocContainer;
import ee.hitsa.ois.util.CertificateUtil;

@Transactional
@Service
public class BdocService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${hois.digidoc4j.testMode:#{null}}")
    private Boolean isTest;

    private Configuration configuration;

    @PostConstruct
    public void initialize() {
        if (Boolean.TRUE.equals(isTest)) {
            setConfiguration(new Configuration(Configuration.Mode.TEST));
            getConfiguration().setTslLocation("https://open-eid.github.io/test-TL/tl-mp-test-EE.xml");
        } else {
            setConfiguration(new Configuration(Configuration.Mode.PROD));
        }
        log.info("Digidoc4j is in {} mode", getConfiguration().isTest() ? "TEST" : "PRODUCTION");
    }

    public UnsignedBdocContainer createUnsignedBdocContainer(String fileName, String mediaType, ByteArrayInputStream data, String certificateInHex) {
        UnsignedBdocContainer unsignedBdocContainer = new UnsignedBdocContainer();
        Container bdocContainer = createBdocContainer();
        bdocContainer.addDataFile(data, fileName, mediaType);
        unsignedBdocContainer.setContainer(bdocContainer);
        unsignedBdocContainer.setDataToSign(getDataToSign(bdocContainer, certificateInHex));
        return unsignedBdocContainer;
    }

    private DataToSign getDataToSign(Container container, String certificateInHex) {
        SignatureBuilder signatureBuilder = SignatureBuilder.aSignature(container)
                .withSignatureDigestAlgorithm(DigestAlgorithm.SHA256)
                .withSigningCertificate(CertificateUtil.getCertificateFromHex(certificateInHex));
        if (Boolean.TRUE.equals(isTest)) {
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

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public InputStream getSignedBdoc(Container container, DataToSign dataToSign, String signatureInHex) {
        Signature signature = dataToSign.finalize(CertificateUtil.hexToBytes(signatureInHex));
        container.addSignature(signature);
        return container.saveAsStream();
    }

}
