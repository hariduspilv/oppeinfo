package ee.hois.xroad.rahvastikuregister.service;

import java.lang.invoke.MethodHandles;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.hois.soap.LogContext;
import ee.hois.soap.SoapHandler;
import ee.hois.soap.SoapUtil;
import ee.hois.xroad.helpers.XRoadHeaderV4;
import ee.hois.xroad.rahvastikuregister.generated.RR434;
import ee.hois.xroad.rahvastikuregister.generated.RR434Response;
import ee.hois.xroad.rahvastikuregister.generated.XRoadAdapterPortType;
import ee.hois.xroad.rahvastikuregister.generated.XRoadService;

public class RahvastikuregisterClient {
    
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String WSDL = "/wsdl/rahvastikuregister/rahvastikuregister.wsdl";
    private final XRoadService service = new XRoadService(XRoadService.class.getResource(WSDL));
    
    public RRResponseResult requestPersonData(XRoadHeaderV4 header, RR434 request) {
        XRoadAdapterPortType port = initializePort(header);
        LOG.info("[RR] Port initialized");
        LogContext ctx = ctx(port);
        LOG.info("[RR] CTX created");
        
        RR434Response result = SoapUtil.withExceptionHandler(ctx, () -> {
            return port.rr434(request);
        });
        
        return new RRResponseResult(ctx, result);
    }

    private XRoadAdapterPortType initializePort(XRoadHeaderV4 header) {
        XRoadAdapterPortType port = service.getMainPort();
        Map<String, Object> context = ((BindingProvider) port).getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, header.getEndpoint());
        context.put(XRoadHeaderV4.XROAD_HEADER, header);
        context.put(SoapHandler.LOG_CONTEXT, header.logContext());
        return port;
    }

    private static LogContext ctx(XRoadAdapterPortType port) {
        return (LogContext)((BindingProvider) port).getRequestContext().get(SoapHandler.LOG_CONTEXT);
    }
}
