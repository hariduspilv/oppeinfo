package ee.hois.xroad.rtip.service;

import java.util.Map;

import javax.xml.ws.BindingProvider;

import ee.hois.soap.LogContext;
import ee.hois.soap.SoapHandler;
import ee.hois.soap.SoapUtil;
import ee.hois.xroad.helpers.XRoadHeaderV4;
import ee.hois.xroad.rtip.generated.TootajaPohiandmedRequestType;
import ee.hois.xroad.rtip.generated.TootajaPohiandmedResponseType;
import ee.hois.xroad.rtip.generated.TootajaPohiandmedType;
import ee.hois.xroad.rtip.generated.WebService;
import ee.hois.xroad.rtip.generated.WebServiceInterface;
import ee.hois.xroad.rtip.generated.ZEMPLOEESRequestType;
import ee.hois.xroad.rtip.generated.ZEMPLOEESResponseType;
import ee.hois.xroad.rtip.generated.ZEMPLOEESType;

public class RtipClient {

    private static final String WSDL = "/wsdl/rtip/rtip.wsdl";

    private final WebService service = new WebService(WebService.class.getResource(WSDL));

    public ZemploeesResult zEMPLOEES(XRoadHeaderV4 xRoadHeader, ZEMPLOEESRequestType requestValue) {
        WebServiceInterface port = initializePort(xRoadHeader);
        LogContext ctx = ctx(port);

        ZEMPLOEESResponseType result = SoapUtil.withExceptionHandler(ctx, () -> {
            ZEMPLOEESType request = new ZEMPLOEESType();
            request.setKeha(requestValue);

            return port.zEMPLOEES(request);
        });
        return new ZemploeesResult(ctx, result);
    }

    public TootajaPohiandmedResult tootajaPohiandmed(XRoadHeaderV4 xRoadHeader, TootajaPohiandmedRequestType requestValue) {
        WebServiceInterface port = initializePort(xRoadHeader);
        LogContext ctx = ctx(port);

        TootajaPohiandmedResponseType result = SoapUtil.withExceptionHandler(ctx, () -> {
            TootajaPohiandmedType request = new TootajaPohiandmedType();
            request.setKeha(requestValue);

            return port.tootajaPohiandmed(request);
        });
        return new TootajaPohiandmedResult(ctx, result);
    }

    private WebServiceInterface initializePort(XRoadHeaderV4 header) {
        WebServiceInterface port = service.getPort();
        Map<String, Object> context = ((BindingProvider) port).getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, header.getEndpoint());
        context.put(XRoadHeaderV4.XROAD_HEADER, header);
        context.put(SoapHandler.LOG_CONTEXT, header.logContext());
        return port;
    }

    private static LogContext ctx(WebServiceInterface port) {
        return (LogContext)((BindingProvider) port).getRequestContext().get(SoapHandler.LOG_CONTEXT);
    }
}
