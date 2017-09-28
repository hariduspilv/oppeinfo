package ee.hois.xroad.sais2.service;

import ee.hois.soap.LogContext;
import ee.hois.soap.LogResult;
import ee.hois.xroad.sais2.generated.ClassificationExport;

public class SaisClassificationResponse extends LogResult<ClassificationExport> {

    public SaisClassificationResponse(LogContext log, ClassificationExport result) {
        super(log, result);
    }
}
