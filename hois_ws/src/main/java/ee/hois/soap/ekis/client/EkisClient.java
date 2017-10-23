package ee.hois.soap.ekis.client;

import java.util.Map;

import javax.xml.ws.BindingProvider;

import ee.hois.soap.LogContext;
import ee.hois.soap.SoapHandler;
import ee.hois.soap.SoapUtil;
import ee.hois.soap.ekis.client.generated.Answer;
import ee.hois.soap.ekis.client.generated.Content;
import ee.hois.soap.ekis.client.generated.ContentArray;
import ee.hois.soap.ekis.client.generated.Wdois;
import ee.hois.soap.ekis.client.generated.WdoisPortType;

public class EkisClient {

    private final Wdois service = new Wdois(Wdois.class.getResource("/wsdl/ekis/ekis_toend.wsdl"));

    public RegisterCertificateResponse registerCertificate(EkisRequestContext requestCtx, RegisterCertificateRequest request) {
        WdoisPortType port = initializePort(requestCtx, request.getQguid(), "registerCertificate", "?page=ois2_toend");
        LogContext ctx = ctx(port);

        Answer result = SoapUtil.withExceptionHandler(ctx, () -> {
            return port.registerCertificate(
                    request.getQguid(), request.getEhisId(),
                    request.getOisId(), request.getStudent(),
                    optional(request.getEmail()), request.getSubject(),
                    request.getBody(), request.getItemCreator(),
                    request.getType(), optional(request.getInstitution()));
        });
        return new RegisterCertificateResponse(ctx, result);
    }

    public RegisterDirectiveResponse registerDirective(EkisRequestContext requestCtx, RegisterDirectiveRequest request) {
        WdoisPortType port = initializePort(requestCtx, request.getQguid(), "registerDirective", "?page=ois2_kaskkiri");
        LogContext ctx = ctx(port);

        Answer result = SoapUtil.withExceptionHandler(ctx, () -> {
            ContentArray content = new ContentArray();
            for(Content c : request.getContent()) {
                c.setStartDate(optional(c.getStartDate()));
                c.setEndDate(optional(c.getEndDate()));
                c.setReason(optional(c.getReason()));
                c.setLoad(optional(c.getLoad()));
                c.setCurricula(optional(c.getCurricula()));
                c.setForm(optional(c.getForm()));
                c.setGroup(optional(c.getGroup()));
                c.setFinsource(optional(c.getFinsource()));
                c.setOuterschool(optional(c.getOuterschool()));
                c.setLang(optional(c.getLang()));
                c.setKudos(optional(c.getKudos()));
                c.setDegree(optional(c.getDegree()));
                c.setEap(optional(c.getEap()));
            }
            content.getContent().addAll(request.getContent());
            return port.registerDirective(
                    request.getQguid(), request.getEhisId(), request.getOisId(),
                    request.getDirectiveType(), request.getTitle(),
                    request.getItemCreator(), request.getCreateTime().toString(),
                    request.getManager(), content,
                    request.getWdId());
        });
        return new RegisterDirectiveResponse(ctx, result);
    }

    public RegisterPracticeContractResponse registerPracticeContract(EkisRequestContext requestCtx, RegisterPracticeContractRequest request) {
        WdoisPortType port = initializePort(requestCtx, request.getQguid(), "registerPracticeContract", "?page=ois2_praktika");
        LogContext ctx = ctx(port);

        Answer result = SoapUtil.withExceptionHandler(ctx, () -> {
            return port.registerPracticeContract(request.getQguid(), request.getEhisId(),
                    request.getOisId(),
                    // TODO remove?
                    request.getCreateDate(),
                    request.getManager(), request.getStIdCode(),
                    request.getStFirstNames(), request.getStLastName(),
                    request.getStEmail(), request.getStCurricula(),
                    request.getStForm(), request.getStCourse(),
                    request.getStEkap(), request.getStHours(),
                    request.getStModule(), request.getOrgName(),
                    request.getOrgCode(), request.getOrgContactName(),
                    optional(request.getOrgTel()), request.getOrgEmail(),
                    request.getOrgTutorName(), optional(request.getOrgTutorTel()),
                    optional(request.getOrgTutorEmail()), request.getProgramme());
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

    /**
     * Replace missing optional string values with empty string.
     * Required for rpc/literal
     */
    private static String optional(String value) {
        return value != null ? value : "";
    }
}
