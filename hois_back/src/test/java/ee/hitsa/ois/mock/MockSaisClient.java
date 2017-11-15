package ee.hitsa.ois.mock;

import ee.hois.soap.LogContext;
import ee.hois.xroad.helpers.XRoadHeaderV4;
import ee.hois.xroad.sais2.generated.AdmissionExportResponse;
import ee.hois.xroad.sais2.generated.AllAdmissionsExportRequest;
import ee.hois.xroad.sais2.generated.AllAppsExportRequest;
import ee.hois.xroad.sais2.generated.AppExportResponse;
import ee.hois.xroad.sais2.generated.ClassificationExport;
import ee.hois.xroad.sais2.service.SaisAdmissionResponse;
import ee.hois.xroad.sais2.service.SaisApplicationResponse;
import ee.hois.xroad.sais2.service.SaisClassificationResponse;
import ee.hois.xroad.sais2.service.SaisClient;

public class MockSaisClient extends SaisClient {

    @Override
    public SaisClassificationResponse classificationsExport(XRoadHeaderV4 xRoadHeader) {
        LogContext log = xRoadHeader.logContext();
        log.setOutgoingXml("Test - päringut ei koostatud");
        return new SaisClassificationResponse(log, new ClassificationExport());
    }

    @Override
    public SaisAdmissionResponse admissionsExport(XRoadHeaderV4 xRoadHeader, AllAdmissionsExportRequest requestValue) {
        LogContext log = xRoadHeader.logContext();
        log.setOutgoingXml("Test - päringut ei koostatud");
        return new SaisAdmissionResponse(log, new AdmissionExportResponse());
    }

    @Override
    public SaisApplicationResponse applicationsExport(XRoadHeaderV4 xRoadHeader, AllAppsExportRequest requestValue) {
        LogContext log = xRoadHeader.logContext();
        log.setOutgoingXml("Test - päringut ei koostatud");
        return new SaisApplicationResponse(log, new AppExportResponse());
    }
}
