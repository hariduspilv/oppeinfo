package ee.hois.xroad.sais2.service;

import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import ee.hois.soap.LogContext;
import ee.hois.soap.SoapHandler;
import ee.hois.soap.SoapUtil;
import ee.hois.xroad.helpers.XRoadHeader;
import ee.hois.xroad.sais2.generated.AdmissionExportResponse;
import ee.hois.xroad.sais2.generated.AllAdmissionsExportRequest;
import ee.hois.xroad.sais2.generated.AllAppsExportRequest;
import ee.hois.xroad.sais2.generated.AppExportResponse;
import ee.hois.xroad.sais2.generated.ClassificationExport;
import ee.hois.xroad.sais2.generated.EmptyParameters;
import ee.hois.xroad.sais2.generated.XRoad;
import ee.hois.xroad.sais2.generated.XRoadSoap;

public class SaisClient {

    private static final String WSDL = "/wsdl/sais/sais3.wsdl";

    private final XRoad service = new XRoad(SaisClient.class.getResource(WSDL));

    public SaisClassificationResponse classificationsExport(XRoadHeader xRoadHeader) {
        XRoadSoap port = initializePort(xRoadHeader);
        LogContext ctx = ctx(port);

        ClassificationExport result = SoapUtil.withExceptionHandler(ctx, () -> {
            Holder<EmptyParameters> request = new Holder<>(new EmptyParameters());
            Holder<ClassificationExport> response = new Holder<>();

            port.classificationsExport(request, response);
            return response.value;
        });
        return new SaisClassificationResponse(ctx, result);
    }

    public SaisAdmissionResponse admissionsExport(XRoadHeader xRoadHeader, AllAdmissionsExportRequest requestValue) {
        XRoadSoap port = initializePort(xRoadHeader);
        LogContext ctx = ctx(port);

        AdmissionExportResponse result = SoapUtil.withExceptionHandler(ctx, () -> {
            Holder<AllAdmissionsExportRequest> request = new Holder<>(requestValue);
            Holder<AdmissionExportResponse> response = new Holder<>();

            port.allAdmissionsExport(request, response);
            return response.value;
        });
        return new SaisAdmissionResponse(ctx, result);
    }

    public SaisApplicationResponse applicationsExport(XRoadHeader xRoadHeader, AllAppsExportRequest requestValue) {
        XRoadSoap port = initializePort(xRoadHeader);
        LogContext ctx = ctx(port);

        AppExportResponse result = SoapUtil.withExceptionHandler(ctx, () -> {
            Holder<AllAppsExportRequest> request = new Holder<>(requestValue);
            Holder<AppExportResponse> response = new Holder<>();

            port.allApplicationsExport(request, response);
            return response.value;
        });

        return new SaisApplicationResponse(ctx, result);
    }

    private XRoadSoap initializePort(XRoadHeader header) {
        XRoadSoap port = service.getXRoadSoap();
        Map<String, Object> context = ((BindingProvider) port).getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, header.getEndpoint());
        context.put(XRoadHeader.XROAD_HEADER, header);
        context.put(SoapHandler.LOG_CONTEXT, header.logContext());
        return port;
    }

    private static LogContext ctx(XRoadSoap port) {
        return (LogContext)((BindingProvider) port).getRequestContext().get(SoapHandler.LOG_CONTEXT);
    }
}
