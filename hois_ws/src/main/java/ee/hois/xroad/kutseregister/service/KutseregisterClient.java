package ee.hois.xroad.kutseregister.service;

import java.util.Collections;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import ee.hois.soap.LogContext;
import ee.hois.soap.SoapHandler;
import ee.hois.soap.SoapUtil;
import ee.hois.xroad.helpers.XRoadHeaderV4;
import ee.hois.xroad.kutseregister.generated.Kutseregister;
import ee.hois.xroad.kutseregister.generated.Kutsetunnistus;
import ee.hois.xroad.kutseregister.generated.KutsetunnistusVastus;
import ee.hois.xroad.kutseregister.generated.MuutunudKutsestandardidParing;
import ee.hois.xroad.kutseregister.generated.MuutunudKutsestandardidVastus;
import ee.hois.xroad.kutseregister.generated.Xteeport;

public class KutseregisterClient {

    private static final String WSDL = "/wsdl/kutseregister/kutseregister.wsdl";
    private final Kutseregister service = new Kutseregister(Kutseregister.class.getResource(WSDL));

    public KutsetunnistusResponse kutsetunnistus(XRoadHeaderV4 xRoadHeader, Kutsetunnistus request) {
        Xteeport port = initializePort(xRoadHeader);
        LogContext ctx = ctx(port);

        KutsetunnistusVastus result = SoapUtil.withExceptionHandler(ctx, () -> {
            return port.kutsetunnistus(request);
        });

        return new KutsetunnistusResponse(ctx, result);
    }

    public MuutunudKutsestandardidResponse muutunudKutsestandardid(XRoadHeaderV4 xRoadHeader, MuutunudKutsestandardidParing request) {
        Xteeport port = initializePort(xRoadHeader);
        LogContext ctx = ctx(port);

        MuutunudKutsestandardidVastus result = SoapUtil.withExceptionHandler(ctx, () -> {
            return port.muutunudKutsestandardid(request);
        });

        return new MuutunudKutsestandardidResponse(ctx, result);
    }

    private Xteeport initializePort(XRoadHeaderV4 header) {
        Xteeport port = service.getXteeport();
        Map<String, Object> context = ((BindingProvider) port).getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, header.getEndpoint());
        context.put(XRoadHeaderV4.XROAD_HEADER, header);
        context.put(SoapHandler.LOG_CONTEXT, header.logContext());
        // kutseregister only works with specific namespace prefix(es)
        context.put("soap.env.ns.map", Collections.singletonMap("soapenv", "http://schemas.xmlsoap.org/soap/envelope/"));
        return port;
    }

    private static LogContext ctx(Xteeport port) {
        return (LogContext)((BindingProvider) port).getRequestContext().get(SoapHandler.LOG_CONTEXT);
    }
}
