package ee.hitsa.ois.mock;

import ee.hois.soap.LogContext;
import ee.hois.soap.ekis.client.EkisClient;
import ee.hois.soap.ekis.client.EkisRequestContext;
import ee.hois.soap.ekis.client.RegisterCertificateRequest;
import ee.hois.soap.ekis.client.RegisterCertificateResponse;
import ee.hois.soap.ekis.client.RegisterDirectiveRequest;
import ee.hois.soap.ekis.client.RegisterDirectiveResponse;
import ee.hois.soap.ekis.client.RegisterPracticeContractRequest;
import ee.hois.soap.ekis.client.RegisterPracticeContractResponse;
import ee.hois.soap.ekis.client.generated.Answer;

public class MockEkisClient extends EkisClient {

    @Override
    public RegisterCertificateResponse registerCertificate(EkisRequestContext requestCtx, RegisterCertificateRequest request) {
        LogContext log = logContext(request.getQguid(), "registerCertificate");
        log.setOutgoingXml("Test - päringut ei koostatud");
        return new RegisterCertificateResponse(log, new Answer());
    }

    @Override
    public RegisterDirectiveResponse registerDirective(EkisRequestContext requestCtx, RegisterDirectiveRequest request) {
        LogContext log = logContext(request.getQguid(), "registerCertificate");
        log.setOutgoingXml("Test - päringut ei koostatud");
        return new RegisterDirectiveResponse(log, new Answer());
    }

    @Override
    public RegisterPracticeContractResponse registerPracticeContract(EkisRequestContext requestCtx, RegisterPracticeContractRequest request) {
        LogContext log = logContext(request.getQguid(), "registerCertificate");
        log.setOutgoingXml("Test - päringut ei koostatud");
        return new RegisterPracticeContractResponse(log, new Answer());
    }

    private static LogContext logContext(String id, String queryName) {
        return new LogContext(id, queryName);
    }
}
