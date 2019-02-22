package ee.hois.xroad.ariregister.service;

import java.util.Map;

import javax.xml.ws.BindingProvider;

import ee.hois.soap.LogContext;
import ee.hois.soap.SoapHandler;
import ee.hois.soap.SoapUtil;
import ee.hois.xroad.ariregister.generated.Arireg;
import ee.hois.xroad.ariregister.generated.AriregXtee;
import ee.hois.xroad.ariregister.generated.LihtandmedV1;
import ee.hois.xroad.ariregister.generated.LihtandmedV1Response;
import ee.hois.xroad.ariregister.generated.ParinglihtV5Paring;
import ee.hois.xroad.ariregister.generated.XRoadClientIdentifierType;
import ee.hois.xroad.ariregister.generated.XRoadServiceIdentifierType;
import ee.hois.xroad.helpers.XRoadHeaderV4;

public class AriregisterClient {
	private static final String WSDL = "/wsdl/ariregister/ariregister.wsdl";

    private final Arireg service = new Arireg(AriregisterClient.class.getResource(WSDL));
    
    public LihtandmedResponse lihtandmed(XRoadHeaderV4 xRoadHeader, ParinglihtV5Paring requestValue) {
        AriregXtee port = initializePort(xRoadHeader);
        LogContext ctx = ctx(port);

        LihtandmedV1Response result = SoapUtil.withExceptionHandler(ctx, () -> {
            LihtandmedV1 request = new LihtandmedV1();
            request.setKeha(requestValue);
            XRoadServiceIdentifierType service = new XRoadServiceIdentifierType();
            service.setMemberClass(xRoadHeader.getService().getMemberClass());
            service.setMemberCode(xRoadHeader.getService().getMemberCode());
            service.setSubsystemCode(xRoadHeader.getService().getSubsystemCode());
            service.setXRoadInstance(xRoadHeader.getService().getxRoadInstance());
            service.setServiceCode(xRoadHeader.getService().getServiceCode());
            service.setServiceVersion(xRoadHeader.getService().getServiceVersion());
            
            XRoadClientIdentifierType client = new XRoadClientIdentifierType();
            client.setXRoadInstance(xRoadHeader.getClient().getXRoadInstance());
            client.setMemberClass(xRoadHeader.getClient().getMemberClass());
            client.setMemberCode(xRoadHeader.getClient().getMemberCode());
            client.setSubsystemCode(xRoadHeader.getClient().getSubSystemCode());
            
            return port.lihtandmedV1(request, null, null, null, null, null);
        });
        return new LihtandmedResponse(ctx, result);
    }

    private AriregXtee initializePort(XRoadHeaderV4 header) {
    	AriregXtee port = service.getAriregXtee();
        Map<String, Object> context = ((BindingProvider) port).getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, header.getEndpoint());
        context.put(XRoadHeaderV4.XROAD_HEADER, header);
        context.put(SoapHandler.LOG_CONTEXT, header.logContext());
        return port;
    }

    private static LogContext ctx(AriregXtee port) {
        return (LogContext)((BindingProvider) port).getRequestContext().get(SoapHandler.LOG_CONTEXT);
    }
}
