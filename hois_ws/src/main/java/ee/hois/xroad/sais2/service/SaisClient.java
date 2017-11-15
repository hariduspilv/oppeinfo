package ee.hois.xroad.sais2.service;

import java.util.Map;

import javax.xml.ws.BindingProvider;

import ee.hois.soap.LogContext;
import ee.hois.soap.SoapHandler;
import ee.hois.soap.SoapUtil;
import ee.hois.xroad.helpers.XRoadHeaderV4;
import ee.hois.xroad.sais2.generated.AllAdmissionsExport;
import ee.hois.xroad.sais2.generated.AllAdmissionsExportRequest;
import ee.hois.xroad.sais2.generated.AllAdmissionsExportResponse;
import ee.hois.xroad.sais2.generated.AllApplicationsExport;
import ee.hois.xroad.sais2.generated.AllApplicationsExportResponse;
import ee.hois.xroad.sais2.generated.AllAppsExportRequest;
import ee.hois.xroad.sais2.generated.ClassificationsExport;
import ee.hois.xroad.sais2.generated.ClassificationsExportResponse;
import ee.hois.xroad.sais2.generated.XRoadV6;
import ee.hois.xroad.sais2.generated.XRoadSoap;

public class SaisClient {

    private static final String WSDL = "/wsdl/sais/sais3.wsdl";

    private final XRoadV6 service = new XRoadV6(SaisClient.class.getResource(WSDL));

    public SaisClassificationResponse classificationsExport(XRoadHeaderV4 xRoadHeader) {
        XRoadSoap port = initializePort(xRoadHeader);
        LogContext ctx = ctx(port);

        ClassificationsExportResponse result = SoapUtil.withExceptionHandler(ctx, () -> {
            ClassificationsExport request = new ClassificationsExport();
            return port.classificationsExport(request);
        });
        return new SaisClassificationResponse(ctx, result != null ? result.getResponse() : null);
    }

    public SaisAdmissionResponse admissionsExport(XRoadHeaderV4 xRoadHeader, AllAdmissionsExportRequest requestValue) {
        XRoadSoap port = initializePort(xRoadHeader);
        LogContext ctx = ctx(port);

        AllAdmissionsExportResponse result = SoapUtil.withExceptionHandler(ctx, () -> {
            AllAdmissionsExport request = new AllAdmissionsExport();
            request.setRequest(requestValue);
            return port.allAdmissionsExport(request);
        });
        return new SaisAdmissionResponse(ctx, result != null ? result.getResponse() : null);
    }

    public SaisApplicationResponse applicationsExport(XRoadHeaderV4 xRoadHeader, AllAppsExportRequest requestValue) {
        XRoadSoap port = initializePort(xRoadHeader);
        LogContext ctx = ctx(port);

        AllApplicationsExportResponse result = SoapUtil.withExceptionHandler(ctx, () -> {
            AllApplicationsExport request = new AllApplicationsExport();
            request.setRequest(requestValue);
            return port.allApplicationsExport(request);
        });

        return new SaisApplicationResponse(ctx, result != null ? result.getResponse() : null);
    }

    private XRoadSoap initializePort(XRoadHeaderV4 header) {
        XRoadSoap port = service.getXRoadSoap();
        Map<String, Object> context = ((BindingProvider) port).getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, header.getEndpoint());
        context.put(XRoadHeaderV4.XROAD_HEADER, header);
        context.put(SoapHandler.LOG_CONTEXT, header.logContext());
        return port;
    }

    private static LogContext ctx(XRoadSoap port) {
        return (LogContext)((BindingProvider) port).getRequestContext().get(SoapHandler.LOG_CONTEXT);
    }
}
