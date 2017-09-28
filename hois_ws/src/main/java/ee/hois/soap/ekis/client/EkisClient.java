package ee.hois.soap.ekis.client;

import java.util.Map;

import javax.xml.ws.BindingProvider;

import ee.hois.soap.LogContext;
import ee.hois.soap.SoapHandler;
import ee.hois.soap.SoapUtil;
import ee.hois.soap.ekis.client.generated.Answer;
import ee.hois.soap.ekis.client.generated.ContentArray;
import ee.hois.soap.ekis.client.generated.Wdois;
import ee.hois.soap.ekis.client.generated.WdoisPortType;

public class EkisClient {

    private final Wdois service = new Wdois(Wdois.class.getResource("/wsdl/ekis/ekis_toend.wsdl"));

    public RegisterCertificateResponse registerCertificate(EkisRequestContext requestCtx, RegisterCertificateRequest request) {
        WdoisPortType port = initializePort(requestCtx, request.getQguid(), "registerCertificate", "?page=ois2_toend/registerCertificate");
        LogContext ctx = ctx(port);

        Answer result = SoapUtil.withExceptionHandler(ctx, () -> {
            return port.registerCertificate(
                    request.getQguid(), request.getEhisId(), request.getOisId(), request.getStudent(), request.getEmail(),
                    request.getSubject(), request.getBody(), request.getItemCreator(), request.getType(), request.getInstitution());
        });
        return new RegisterCertificateResponse(ctx, result);
    }

    public RegisterDirectiveResponse registerDirective(EkisRequestContext requestCtx, RegisterDirectiveRequest request) {
        WdoisPortType port = initializePort(requestCtx, request.getQguid(), "registerDirective", "?page=ois2_kaskkiri/registerDirective");
        LogContext ctx = ctx(port);

        Answer result = SoapUtil.withExceptionHandler(ctx, () -> {
            ContentArray content = new ContentArray();
            content.getContent().addAll(request.getContent());
            return port.registerDirective(
                    request.getQguid(), request.getEhisId(), request.getOisId(), request.getDirectiveType(), request.getTitle(),
                    request.getItemCreator(), request.getCreateTime().toString(), request.getManager(), content,
                    request.getWdId());
        });
        return new RegisterDirectiveResponse(ctx, result);
    }

    public RegisterPracticeContractResponse registerPracticeContract(EkisRequestContext requestCtx, RegisterPracticeContractRequest request) {
        WdoisPortType port = initializePort(requestCtx, request.getQguid(), "registerDirective", "?page=ois2_praktika/registerPracticeContract");
        LogContext ctx = ctx(port);

        Answer result = SoapUtil.withExceptionHandler(ctx, () -> {
            return port.registerPracticeContract(request.getQguid(), request.getEhisId(), request.getOisId(), request.getCreateDate(),
                    request.getManager(), request.getStIdCode(), request.getStFirstNames(), request.getStLastName(), request.getStEmail(),
                    request.getStCurricula(), request.getStForm(), request.getStCourse(), request.getStEkap(), request.getStHours(),
                    request.getStModule(), request.getOrgName(), request.getOrgCode(), request.getOrgContactName(), request.getOrgTel(),
                    request.getOrgEmail(), request.getOrgTutorName(), request.getOrgTutorTel(), request.getOrgTutorEmail(), request.getProgramme());
        });
        return new RegisterPracticeContractResponse(ctx, result);
    }

    private WdoisPortType initializePort(EkisRequestContext ctx, String id, String queryName, String requestPath) {
        WdoisPortType port = service.getWdoisPort();
        Map<String, Object> context = ((BindingProvider) port).getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ctx.getEndpoint() + requestPath);
        context.put(SoapHandler.LOG_CONTEXT, new LogContext(id, queryName));
        return port;
    }

    private static LogContext ctx(WdoisPortType port) {
        return (LogContext)((BindingProvider) port).getRequestContext().get(SoapHandler.LOG_CONTEXT);
    }

}
