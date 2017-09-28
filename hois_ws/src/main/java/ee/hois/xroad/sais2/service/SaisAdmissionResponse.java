package ee.hois.xroad.sais2.service;

import ee.hois.soap.LogContext;
import ee.hois.soap.LogResult;
import ee.hois.xroad.sais2.generated.AdmissionExportResponse;

public class SaisAdmissionResponse extends LogResult<AdmissionExportResponse> {

    public SaisAdmissionResponse(LogContext log, AdmissionExportResponse result) {
        super(log, result);
    }
}
