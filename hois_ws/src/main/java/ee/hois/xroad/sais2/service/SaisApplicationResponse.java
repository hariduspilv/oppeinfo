package ee.hois.xroad.sais2.service;

import ee.hois.soap.LogContext;
import ee.hois.soap.LogResult;
import ee.hois.xroad.sais2.generated.AppExportResponse;

public class SaisApplicationResponse extends LogResult<AppExportResponse> {

    public SaisApplicationResponse(LogContext log, AppExportResponse result) {
        super(log, result);
    }
}
